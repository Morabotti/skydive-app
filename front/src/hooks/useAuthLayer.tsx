import { useEffect, useCallback } from 'react'
import { AuthUser } from '@types'
import { useAuth } from '@hooks'
import { useLocation } from 'react-router-dom'
import { LocalStorageKeys } from '@enums'
import { checkSession } from '@client'

interface AuthContext {
  loading: boolean,
  auth: null | AuthUser,
  pathname: string
}

export const useAuthLayer = (): AuthContext => {
  const { loading, auth, stopLoading, setAuth, revokeAuth } = useAuth()
  const { pathname } = useLocation()

  const getStatus = useCallback(async () => {
    const token = localStorage.getItem(LocalStorageKeys.TOKEN)
    if (auth === null && loading) {
      if (token) {
        try {
          const client = await checkSession(token)
          setAuth(client)
        }
        catch (e) {
          revokeAuth()
        }
      }
      else {
        stopLoading()
      }
    }
  }, [loading, auth, stopLoading, revokeAuth, setAuth])

  useEffect(() => {
    getStatus()
  }, [getStatus])

  return {
    loading,
    auth,
    pathname
  }
}
