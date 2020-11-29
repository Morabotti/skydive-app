import { useCallback, useState } from 'react'
import { useDebounce, usePagination, useCaching, useDashboard } from '@hooks'
import { QueryCache, QueryResult, useMutation, usePaginatedQuery, useQueryCache } from 'react-query'
import { AuthRoles, Client, NotificationType } from '@enums'
import { createUser, deleteUser, getUsers } from '@client'
import { CreateUser, PaginationResult, User } from '@types'
import { useHistory } from 'react-router-dom'

const invalideUserRequests = (queryCache: QueryCache, id: number | null = null) => {
  queryCache.invalidateQueries(Client.GET_USERS)
  queryCache.invalidateQueries(Client.GET_CLUB_MEMBERS)
  queryCache.invalidateQueries(Client.GET_ACTIVITY_PARTICIPATION)

  if (id) {
    queryCache.invalidateQueries([Client.GET_USER_BY_ID, id])
  }
}

interface UsersContext {
  isList: boolean,
  extended: boolean,
  users: QueryResult<PaginationResult<User>>,
  search: string,
  location: string,
  role: AuthRoles,
  showDeleted: boolean | null,

  select: User | null,
  deleting: User | null,
  anchor: HTMLButtonElement | null,
  creating: boolean,

  toggleExtended: (set: boolean) => () => void,
  toggleList: (set: boolean) => () => void,
  toggleCreateDialog: (set: boolean) => () => void,
  toggleDeleteDialog: (set: null | User) => () => void,
  setSearch: (set: string) => void,
  setLocation: (set: string) => void,
  setShowDeleted: (set: null | boolean) => void,
  setRole: (set: AuthRoles) => void,
  onResetFilters: () => void,
  onCloseMenu: () => void,
  onNavigation: () => void,
  onDelete: () => void,
  onCreate: (set: CreateUser) => void,
  onSelectSettings: (user: User) => (e: React.MouseEvent<HTMLButtonElement>) => void
}

export const useUsers = (): UsersContext => {
  const { offset, limit } = usePagination()
  const { getCacheItem, setCacheItem } = useCaching()
  const { createNotification, setLoading } = useDashboard()
  const { push } = useHistory()
  const queryCache = useQueryCache()

  const [isList, setIsList] = useState(getCacheItem<boolean>('usersInList'))
  const [extended, setExtended] = useState(getCacheItem<boolean>('extendedLists'))
  const [search, setSearch] = useState('')
  const [location, setLocation] = useState('')
  const [role, setRole] = useState<AuthRoles>(AuthRoles.UNSET)
  const [showDeleted, setShowDeleted] = useState<boolean | null>(null)

  const [select, setSelect] = useState<User | null>(null)
  const [anchor, setAnchor] = useState<HTMLButtonElement | null>(null)
  const [deleting, setDeleting] = useState<User | null>(null)
  const [creating, setCreating] = useState(false)

  const debouncedSearch = useDebounce(search, 300)
  const debouncedLocation = useDebounce(location, 300)

  const users = usePaginatedQuery(
    [Client.GET_USERS, { limit, offset }, {
      search: debouncedSearch,
      location: debouncedLocation,
      role: role === AuthRoles.UNSET ? null : role,
      showDeleted
    }],
    getUsers
  )

  const [deleteMutation] = useMutation(deleteUser, {
    onSuccess: (res: Response, vars: number) => invalideUserRequests(queryCache, vars)
  })

  const [createMutation] = useMutation(createUser, {
    onSuccess: () => invalideUserRequests(queryCache)
  })

  const onCloseMenu = useCallback(() => {
    setAnchor(null)
    setSelect(null)
  }, [setAnchor, setSelect])

  const onSelectSettings = useCallback(
    (user: User) => (e: React.MouseEvent<HTMLButtonElement>) => {
      e.preventDefault()
      e.stopPropagation()

      setAnchor(e.currentTarget)
      setSelect(user)
    }, [setAnchor, setSelect]
  )

  const onResetFilters = useCallback(() => {
    setRole(AuthRoles.UNSET)
    setSearch('')
    setLocation('')
    setShowDeleted(null)
  }, [])

  const onNavigation = useCallback(() => {
    if (select === null) {
      return
    }

    push(`/dashboard/users/${select.id}`)

    onCloseMenu()
  }, [onCloseMenu, push, select])

  const onDelete = useCallback(async () => {
    if (deleting === null) {
      return
    }

    setLoading(true)
    try {
      await deleteMutation(deleting.id)
      createNotification('Successfully deleted user', NotificationType.INFO)
    }
    catch (e) {
      createNotification('Failed to delete user', NotificationType.ERROR)
    }
    setDeleting(null)
    setLoading(false)
  }, [deleting, setDeleting, createNotification, deleteMutation, setLoading])

  const onCreate = useCallback(async (set: CreateUser) => {
    setLoading(true)

    try {
      await createMutation(set)
      createNotification('Successfully created new user', NotificationType.INFO)
    }
    catch (e) {
      createNotification('Failed to create user', NotificationType.ERROR)
    }

    setCreating(false)
    setLoading(false)
  }, [setCreating, createNotification, setLoading, createMutation])

  const toggleList = useCallback((set: boolean) => () => {
    setIsList(set)
    setCacheItem('usersInList', set)
  }, [setIsList, setCacheItem])

  const toggleCreateDialog = useCallback((set: boolean) => () => {
    setCreating(set)
  }, [setCreating])

  const toggleDeleteDialog = useCallback((set: null | User) => () => {
    setDeleting(set)
    onCloseMenu()
  }, [setDeleting, onCloseMenu])

  const toggleExtended = useCallback((set: boolean) => () => {
    setExtended(set)
    setCacheItem('extendedLists', set)
  }, [setExtended, setCacheItem])

  return {
    isList,
    extended,
    toggleExtended,
    toggleList,
    toggleDeleteDialog,
    toggleCreateDialog,
    creating,
    users,
    role,
    location,
    showDeleted,
    search,
    select,
    deleting,
    anchor,
    setRole,
    setLocation,
    setShowDeleted,
    setSearch,
    onResetFilters,
    onCloseMenu,
    onNavigation,
    onDelete,
    onSelectSettings,
    onCreate
  }
}
