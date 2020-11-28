import { useCallback, useState } from 'react'
import { useDebounce, usePagination } from '@hooks'
import { QueryResult, usePaginatedQuery } from 'react-query'
import { ActivityAccess, ActivityType, Client } from '@enums'
import { getActivities } from '@client'
import { Activity, PaginationResult } from '@types'
import moment from 'moment'

interface ActivitiesContext {
  isList: boolean,
  activities: QueryResult<PaginationResult<Activity>>,
  search: string,
  access: ActivityAccess,
  type: ActivityType,
  from: string,
  to: string,
  isVisible: boolean | null,
  toggleList: (set: boolean) => () => void,
  setSearch: (set: string) => void,
  setAccess: (set: ActivityAccess) => void,
  setType: (set: ActivityType) => void,
  setTo: (set: string) => void,
  setFrom: (set: string) => void,
  setIsVisible: (set: boolean | null) => void,
  onResetFilters: () => void
}

export const useActivities = (): ActivitiesContext => {
  const { offset, limit } = usePagination()
  const [isList, setIsList] = useState(true)
  const [search, setSearch] = useState('')
  const [access, setAccess] = useState<ActivityAccess>(ActivityAccess.UNSET)
  const [type, setType] = useState<ActivityType>(ActivityType.UNSET)
  const [from, setFrom] = useState(() => moment().startOf('month').toISOString())
  const [to, setTo] = useState(() => moment().endOf('month').toISOString())
  const [isVisible, setIsVisible] = useState<null | boolean>(null)

  const debouncedSearch = useDebounce(search, 300)

  const activities = usePaginatedQuery(
    [Client.GET_ACTIVITES, { limit, offset }, {
      from: moment(from).format('YYYY-MM-DD'),
      to: moment(to).format('YYYY-MM-DD')
    }, {
      search: debouncedSearch,
      access: access === ActivityAccess.UNSET ? null : access,
      type: type === ActivityType.UNSET ? null : type,
      visible: isVisible === null ? null : !isVisible
    }],
    getActivities
  )

  const toggleList = useCallback((set: boolean) => () => {
    setIsList(set)
  }, [setIsList])

  const onResetFilters = useCallback(() => {
    setAccess(ActivityAccess.UNSET)
    setSearch('')
    setType(ActivityType.UNSET)
    setFrom(moment().startOf('month').toISOString())
    setTo(moment().endOf('month').toISOString())
    setIsVisible(null)
  }, [])

  return {
    isList,
    toggleList,
    activities,
    type,
    access,
    search,
    isVisible,
    to,
    from,
    setIsVisible,
    setType,
    setFrom,
    setTo,
    setAccess,
    setSearch,
    onResetFilters
  }
}
