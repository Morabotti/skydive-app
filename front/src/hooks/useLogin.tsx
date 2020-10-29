import { useEffect, useCallback, useState } from 'react'
import { useInitialContext, useAuth } from '@hooks'
import { AuthRoles } from '@enums'
import { LoginRequest } from '@types'
import { useHistory, useLocation } from 'react-router'

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
  const { search } = useLocation()
  const { push } = useHistory()
  const { setAuth, auth } = useAuth()
  const { error, loading, setRequest } = useInitialContext(false, false)
  const [remember, setRemember] = useState(true)
  const [show, setShow] = useState(true)
  const [login, setLogin] = useState<LoginRequest>({
    username: '',
    password: ''
  })

  const handleChange = useCallback((
    name: keyof LoginRequest
  ) => (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    event.persist()

    setLogin(prev => ({
      ...prev,
      [name]: event.target.value
    }))
  }, [setLogin])

  const togglePassword = useCallback(() => {
    setShow(prev => !prev)
  }, [setShow])

  const toggleRemember = useCallback(() => {
    setRemember(prev => !prev)
  }, [setRemember])

  const onSubmit = useCallback(async (e?: React.FormEvent) => {
    e?.preventDefault()

    /*
    if (login.username === '' || login.password === '') {
      return
    }
    */

    setAuth({
      token: 'sdasadasdsaddas',
      user: {
        role: AuthRoles.ADMIN,
        username: 'Test User'
      }
    })
    setRequest(false, false)

    /*
    setRequest(true, false)
    try {
      const response = await loginRequest(login)
      localStorage.setItem(LocalStorageKeys.TOKEN, response.token)
      setRequest(false, false)
      setAuth(response)
    }
    catch (e) {
      setRequest(false, true)
    }
    */
  }, [setRequest, setAuth])

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
