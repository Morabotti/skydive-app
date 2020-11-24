import React, { useState, createContext, ReactNode, useContext, useCallback } from 'react'
import { LocalStorageKeys } from '@enums'
import { AuthUser } from '@types'
import { useHistory } from 'react-router'
import { useQueryCache } from 'react-query'

interface AuthContext {
  loading: boolean,
  auth: null | AuthUser,
  setAuth: (user: AuthUser) => void,
  revokeAuth: () => void,
  stopLoading: () => void
}

interface Props {
  children: ReactNode
}

export const __AuthContext = createContext<AuthContext>({
  loading: true,
  auth: null,
  setAuth: () => {},
  revokeAuth: () => {},
  stopLoading: () => {}
})

export const AuthProvider = ({ children }: Props) => {
  const [loading, setLoading] = useState(true)
  const [ auth, setStateAuth ] = useState<null | AuthUser>(null)
  const queryCache = useQueryCache()
  const { push } = useHistory()

  const setAuth = useCallback((user: AuthUser) => {
    localStorage.setItem(LocalStorageKeys.TOKEN, user.token)
    setStateAuth(user)
    setLoading(false)
  }, [setStateAuth, setLoading])

  const revokeAuth = useCallback(() => {
    localStorage.removeItem(LocalStorageKeys.TOKEN)
    queryCache.clear()
    setStateAuth(null)
    push('/login')
    setLoading(false)
  }, [setStateAuth, push, setLoading, queryCache])

  const stopLoading = useCallback(() => {
    setLoading(false)
  }, [setLoading])

  return (
    <__AuthContext.Provider
      value={{
        loading,
        auth,
        setAuth,
        revokeAuth,
        stopLoading
      }}
    >
      {children}
    </__AuthContext.Provider>
  )
}

export const useAuth = (): AuthContext => useContext(__AuthContext)
