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
  toggleIsVisible: () => void
}

export const useActivities = (): ActivitiesContext => {
  const { offset, limit } = usePagination()
  const [isList, setIsList] = useState(true)
  const [search, setSearch] = useState('')
  const [access, setAccess] = useState<ActivityAccess>(ActivityAccess.OPEN)
  const [type, setType] = useState<ActivityType>(ActivityType.JUMP)
  const [from, setFrom] = useState(() => moment().startOf('month').toISOString())
  const [to, setTo] = useState(() => moment().endOf('month').toISOString())
  const [isVisible, setIsVisible] = useState<null | boolean>(null)

  const debouncedSearch = useDebounce(search, 300)

  const activities = usePaginatedQuery(
    [Client.GET_ACTIVITES, { limit, offset }, {
      from: moment(from).format('YYYY-MM-DD'),
      to: moment(from).format('YYYY-MM-DD')
    }, {
      search: debouncedSearch,
      access,
      type,
      visible: isVisible === null ? null : !isVisible
    }],
    getActivities
  )

  const toggleList = useCallback((set: boolean) => () => {
    setIsList(set)
  }, [setIsList])

  const toggleIsVisible = useCallback(() => {
    setIsVisible(prev => prev === null ? true : null)
  }, [setIsVisible])

  return {
    isList,
    toggleList,
    activities,
    type,
    access,
    search,
    isVisible,
    toggleIsVisible,
    to,
    from,
    setType,
    setFrom,
    setTo,
    setAccess,
    setSearch
  }
}
