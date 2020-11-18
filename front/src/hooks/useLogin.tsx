import { useEffect, useCallback, useState } from 'react'
import { useInitialContext, useAuth, useCaching } from '@hooks'
import { LocalStorageKeys } from '@enums'
import { LoginRequest } from '@types'
import { useHistory, useLocation } from 'react-router'
import { loginRequest } from '@client'

interface LoginContext {
  error: boolean,
  loading: boolean,
  remember: boolean,
  showPassword: boolean,
  login: LoginRequest,
  onSubmit: (e?: React.FormEvent) => void,
  togglePassword: () => void,
  toggleRemember: () => void,
  onKeyDown: (e: React.KeyboardEvent<HTMLDivElement>) => void,
  handleChange: (
    name: keyof LoginRequest
  ) => (
    event: React.ChangeEvent<HTMLInputElement>
  ) => void
}

export const useLogin = (): LoginContext => {
  const { getCacheItem, setCacheItem, removeCacheItem } = useCaching()
  const { search } = useLocation()
  const { push } = useHistory()
  const { setAuth, auth } = useAuth()
  const { error, loading, setRequest } = useInitialContext(false, false)
  const [remember, setRemember] = useState(getCacheItem('lastUsername') !== '')
  const [show, setShow] = useState(true)
  const [login, setLogin] = useState<LoginRequest>({
    username: getCacheItem('lastUsername'),
    password: ''
  })

  const handleChange = useCallback((
    name: keyof LoginRequest
  ) => (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    event.persist()
    setRequest(false, false)

    setLogin(prev => ({
      ...prev,
      [name]: event.target.value
    }))
  }, [setLogin, setRequest])

  const togglePassword = useCallback(() => {
    setShow(prev => !prev)
  }, [setShow])

  const toggleRemember = useCallback(() => {
    setRemember(prev => !prev)
  }, [setRemember])

  const onSubmit = useCallback(async (e?: React.FormEvent) => {
    e?.preventDefault()

    if (login.username === '' || login.password === '') {
      return
    }

    setRequest(true, false)
    try {
      const response = await loginRequest(login)
      localStorage.setItem(LocalStorageKeys.TOKEN, response.token)

      if (remember) {
        setCacheItem('lastUsername', login.username)
      }
      else if (!remember && getCacheItem('lastUsername') !== '') {
        removeCacheItem('lastUsername')
      }

      setRequest(false, false)
      setAuth(response)
    }
    catch (e) {
      setRequest(false, true)
    }
  }, [
    setRequest,
    setAuth,
    login,
    setCacheItem,
    removeCacheItem,
    getCacheItem,
    remember
  ])

  useEffect(() => {
    if (auth !== null) {
      if (search !== '') {
        // eslint-disable-next-line
        push(search.replace(/^.*?\=/, ''))
      }
      else {
        push('/dashboard')
      }
    }
  }, [auth, push, search])

  const onKeyDown = useCallback((e: React.KeyboardEvent<HTMLDivElement>) => {
    if (e.key === 'Enter') {
      onSubmit()
    }
  }, [onSubmit])

  return {
    error,
    loading,
    login,
    showPassword: show,
    remember,
    onKeyDown,
    onSubmit,
    togglePassword,
    toggleRemember,
    handleChange
  }
}
