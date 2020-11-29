import { useCallback, useState } from 'react'
import { useDebounce, usePagination, useCaching, useDashboard } from '@hooks'
import { QueryResult, useMutation, usePaginatedQuery, useQueryCache } from 'react-query'
import { AuthRoles, Client, NotificationType } from '@enums'
import { deleteUser, getUsers } from '@client'
import { PaginationResult, User } from '@types'
import { useHistory } from 'react-router-dom'

interface UsersContext {
  isList: boolean,
  users: QueryResult<PaginationResult<User>>,
  search: string,
  location: string,
  role: AuthRoles,
  showDeleted: boolean | null,

  select: User | null,
  deleting: User | null,
  anchor: HTMLButtonElement | null,

  toggleList: (set: boolean) => () => void,
  toggleDeleteDialog: (set: null | User) => () => void,
  setSearch: (set: string) => void,
  setLocation: (set: string) => void,
  setShowDeleted: (set: null | boolean) => void,
  setRole: (set: AuthRoles) => void,
  onResetFilters: () => void,
  onCloseMenu: () => void,
  onNavigation: () => void,
  onDelete: () => void,
  onSelectSettings: (user: User) => (e: React.MouseEvent<HTMLButtonElement>) => void
}

export const useUsers = (): UsersContext => {
  const { offset, limit } = usePagination()
  const { getCacheItem, setCacheItem } = useCaching()
  const { createNotification } = useDashboard()
  const { push } = useHistory()
  const queryCache = useQueryCache()

  const [isList, setIsList] = useState(getCacheItem<boolean>('usersInList'))
  const [search, setSearch] = useState('')
  const [location, setLocation] = useState('')
  const [role, setRole] = useState<AuthRoles>(AuthRoles.UNSET)
  const [showDeleted, setShowDeleted] = useState<boolean | null>(null)

  const [select, setSelect] = useState<User | null>(null)
  const [anchor, setAnchor] = useState<HTMLButtonElement | null>(null)
  const [deleting, setDeleting] = useState<User | null>(null)

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
    onSuccess: (res: Response, vars: number) => {
      queryCache.invalidateQueries(Client.GET_USERS)
      queryCache.invalidateQueries([Client.GET_USER_BY_ID, vars])
      queryCache.invalidateQueries(Client.GET_CLUB_MEMBERS)
      queryCache.invalidateQueries(Client.GET_ACTIVITY_PARTICIPATION)
    }
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

    try {
      await deleteMutation(deleting.id)
      createNotification('Successfully deleted user', NotificationType.INFO)
    }
    catch (e) {
      createNotification('Failed to delete user', NotificationType.ERROR)
    }
    setDeleting(null)
  }, [deleting, setDeleting, createNotification, deleteMutation])

  const toggleList = useCallback((set: boolean) => () => {
    setIsList(set)
    setCacheItem('usersInList', set)
  }, [setIsList, setCacheItem])

  const toggleDeleteDialog = useCallback((set: null | User) => () => {
    setDeleting(set)
    onCloseMenu()
  }, [setDeleting, onCloseMenu])

  return {
    isList,
    toggleList,
    toggleDeleteDialog,
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
    onSelectSettings
  }
}
