import { LocalStorageKeys } from '@enums'

import {
  AuthUser,
  LoginRequest,
  RegisterUser,
  Club,
  ClubRequest,
  PaginationResult,
  Activity,
  Plane,
  UpdatePlane,
  User,
  ClubMemberRequest,
  UpdateActivity,
  Participation
} from '@types'

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

export const createClub = (club: ClubRequest): Promise<Club> => fetch(
  `/api/club`,
  {
    method: 'POST',
    body: JSON.stringify(club),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const updateClub = (
  clubId: number,
  club: ClubRequest
): Promise<Club> => fetch(
  `/api/club/${clubId}`,
  {
    method: 'PUT',
    body: JSON.stringify(club),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getClubs = (): Promise<PaginationResult<Club>> => fetch(
  `/api/club`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getClubById = (clubId: number): Promise<Club> => fetch(
  `/api/club/${clubId}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getClubBySlug = (slug: string): Promise<Club> => fetch(
  `/api/club/slug/${slug}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const deleteClub = (clubId: number): Promise<Response> => fetch(
  `/api/club/${clubId}`,
  {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)

export const getClubActivity = (
  clubId: number
): Promise<PaginationResult<Activity>> => fetch(
  `/api/club/${clubId}/activity`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getClubPlanes = (
  clubId: number
): Promise<Plane[]> => fetch(
  `/api/club/${clubId}/plane`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getClubPlaneById = (
  clubId: number,
  planeId: number
): Promise<Plane> => fetch(
  `/api/club/${clubId}/plane/${planeId}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const updatePlane = (
  clubId: number,
  planeId: number,
  plane: UpdatePlane
): Promise<Plane> => fetch(
  `/api/club/${clubId}/plane/${planeId}`,
  {
    method: 'PUT',
    body: JSON.stringify(plane),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const deletePlane = (
  clubId: number,
  planeId: number
): Promise<Response> => fetch(
  `/api/club/${clubId}/plane/${planeId}`,
  {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)

export const createPlane = (
  clubId: number,
  plane: UpdatePlane
): Promise<Plane> => fetch(
  `/api/club/${clubId}/plane`,
  {
    method: 'POST',
    body: JSON.stringify(plane),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getClubMembers = (
  clubId: number
): Promise<PaginationResult<User>> => fetch(
  `/api/club/${clubId}/member`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getClubMemberById = (
  clubId: number,
  memberId: number
): Promise<User> => fetch(
  `/api/club/${clubId}/member/${memberId}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const deleteClubMember = (
  clubId: number,
  memberId: number
): Promise<Response> => fetch(
  `/api/club/${clubId}/member/${memberId}`,
  {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)

export const addClubMember = (
  clubId: number,
  memberId: number,
  clubMemberRequest: ClubMemberRequest
): Promise<User> => fetch(
  `/api/club/${clubId}/member/${memberId}`,
  {
    method: 'POST',
    body: JSON.stringify(clubMemberRequest),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const requestToJoinClub = (
  clubId: number,
  clubMemberRequest: ClubMemberRequest
): Promise<User> => fetch(
  `/api/club/${clubId}/member/request`,
  {
    method: 'POST',
    body: JSON.stringify(clubMemberRequest),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const acceptClubMemberRequest = (
  clubId: number,
  memberId: number
): Promise<User> => fetch(
  `/api/club/${clubId}/accept/${memberId}`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const declineClubMemberRequest = (
  clubId: number,
  memberId: number
): Promise<User> => fetch(
  `/api/club/${clubId}/decline/${memberId}`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getActivities = (): Promise<PaginationResult<Activity>> => fetch(
  `/api/activity`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getActivityById = (
  activityId: number
): Promise<Activity> => fetch(
  `/api/activity/${activityId}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getActivityByToken = (
  token: string
): Promise<Activity> => fetch(
  `/api/activity/token/${token}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const createActivity = (
  activity: UpdateActivity
): Promise<Activity> => fetch(
  `/api/activity`,
  {
    method: 'POST',
    body: JSON.stringify(activity),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const updateActivity = (
  activityId: number,
  activity: UpdateActivity
): Promise<Activity> => fetch(
  `/api/activity/${activityId}`,
  {
    method: 'PUT',
    body: JSON.stringify(activity),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const deleteActivity = (
  activityId: number
): Promise<Response> => fetch(
  `/api/activity/${activityId}`,
  {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getActivityParticipation = (
  activityId: number
): Promise<Participation[]> => fetch(
  `/api/activity/${activityId}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getPersonalClubs = (): Promise<Club[]> => fetch(
  `/api/personal/club`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getPersonalActivities = (): Promise<Participation[]> => fetch(
  `/api/personal/activity`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())
