import { LocalStorageKeys } from '@enums'
import { AuthUser, LoginRequest, RegisterUser } from '@types'

const addAuthToken = () => ({
  'Authorization': localStorage.getItem(LocalStorageKeys.TOKEN) || ''
})

const checkResponse = (res: Response): Promise<Response> => {
  if (!res.ok) {
    return res.text()
      .then((text) => {
        try {
          const jsonError = JSON.parse(text)
          if (jsonError.message) {
            return jsonError.message
          }
          return text
        }
        catch (_) {
          return text
        }
      })
      .then((message) => Promise.reject(new Error(`${message} (HTTP ${res.status.toString()})`)))
  }
  return Promise.resolve(res)
}

export const loginRequest = (login: LoginRequest): Promise<AuthUser> => fetch(
  `/api/auth/login`,
  {
    method: 'POST',
    body: JSON.stringify(login),
    headers: { 'Content-Type': 'application/json' }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const checkSession = (token: string): Promise<AuthUser> => fetch(
  `/api/auth/me`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': token
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const revokeSession = (): Promise<Response> => fetch(
  `/api/auth/logout`,
  {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)

export const userRegister = (newUser: RegisterUser): Promise<Response> => fetch(
  `/api/auth/register`,
  {
    method: 'POST',
    body: JSON.stringify(newUser),
    headers: { 'Content-Type': 'application/json' }
  }
)
  .then(checkResponse)
