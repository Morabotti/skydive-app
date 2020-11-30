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
  ClubMemberRequest,
  UpdateActivity,
  Participation,
  ClubAccount,
  PaginationQuery,
  ClubQuery,
  DateRangeQuery,
  MyActivities,
  ActivityQuery,
  User,
  UpdateUser,
  UserQuery,
  CreateUser,
  ClubAccountQuery
} from '@types'

const addAuthToken = () => ({
  'Authorization': localStorage.getItem(LocalStorageKeys.TOKEN) || ''
})

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const searchParams = (objects: any[]) => {
  const query = new URLSearchParams()

  for (const object of objects) {
    for (const key in object) {
      if (object[key] !== null
        && object[key] !== ''
        && String(object[key]).trim() !== ''
      ) {
        query.set(key, String(object[key]))
      }
    }
  }

  return query.toString()
}

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

export const getClubs = (
  key: string,
  pagination: PaginationQuery,
  clubQuery?: ClubQuery
): Promise<PaginationResult<Club>> => fetch(
  `/api/club?${searchParams([pagination, clubQuery])}`,
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

export const getClubBySlug = (
  key: string,
  slug: string
): Promise<Club> => fetch(
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
  key: string,
  clubId: number,
  pagination: PaginationQuery,
  clubAccountQuery: ClubAccountQuery
): Promise<PaginationResult<ClubAccount>> => fetch(
  `/api/club/${clubId}/member?${searchParams([pagination, clubAccountQuery])}`,
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
): Promise<ClubAccount> => fetch(
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

export const deleteClubMember = ([
  clubId,
  memberId
]: [number, number]): Promise<Response> => fetch(
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
): Promise<ClubAccount> => fetch(
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

export const requestToJoinClub = ([
  clubId,
  clubMemberRequest
]: [number, ClubMemberRequest]): Promise<ClubAccount> => fetch(
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

export const leaveClub = (
  clubId: number
): Promise<Response> => fetch(
  `/api/club/${clubId}/member/leave`,
  {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)

export const acceptClubMemberRequest = ([
  clubId,
  memberId
]: [number, number]): Promise<ClubAccount> => fetch(
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

export const declineClubMemberRequest = ([
  clubId,
  memberId
]: [number, number]): Promise<Response> => fetch(
  `/api/club/${clubId}/decline/${memberId}`,
  {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)

export const getActivities = (
  key: string,
  pagination: PaginationQuery,
  dateRange: DateRangeQuery,
  activityQuery: ActivityQuery
): Promise<PaginationResult<Activity>> => fetch(
  `/api/activity?${searchParams([pagination, dateRange, activityQuery])}`,
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
  key: string,
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

export const getPersonalClubs = (): Promise<ClubAccount[]> => fetch(
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

export const getPersonalActivities = (
  key: string,
  dateRange: DateRangeQuery
): Promise<MyActivities> => fetch(
  `/api/personal/activity?${searchParams([dateRange])}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const participateAsUser = (
  activityId: number
): Promise<Participation> => fetch(
  `/api/activity/${activityId}/participate/user`,
  {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const participateAsPilot = (
  activityId: number,
  plane: Plane
): Promise<Participation> => fetch(
  `/api/activity/${activityId}/participate/pilot`,
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

export const removeParticipationAsUser = (
  activityId: number
): Promise<Response> => fetch(
  `/api/activity/${activityId}/participate/user`,
  {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const removeParticipationAsPilot = (
  activityId: number
): Promise<Response> => fetch(
  `/api/activity/${activityId}/participate/pilot`,
  {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)

export const getUsers = (
  key: string,
  pagination: PaginationQuery,
  userQuery: UserQuery
): Promise<PaginationResult<User>> => fetch(
  `/api/user?${searchParams([pagination, userQuery])}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getUserById = (
  id: number
): Promise<User> => fetch(
  `/api/user/${id}`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const updateUser = (
  id: number,
  update: UpdateUser
): Promise<User> => fetch(
  `/api/user/${id}`,
  {
    method: 'PUT',
    body: JSON.stringify(update),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const deleteUser = (
  id: number
): Promise<Response> => fetch(
  `/api/user/${id}`,
  {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)

export const getUsersActivities = (
  id: number
): Promise<MyActivities> => fetch(
  `/api/user/${id}/activity`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const getUsersClubs = (
  id: number
): Promise<ClubAccount[]> => fetch(
  `/api/user/${id}/club`,
  {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())

export const createUser = (
  user: CreateUser
): Promise<User> => fetch(
  `/api/user`,
  {
    method: 'POST',
    body: JSON.stringify(user),
    headers: {
      'Content-Type': 'application/json', ...addAuthToken()
    }
  }
)
  .then(checkResponse)
  .then((res) => res.json())
