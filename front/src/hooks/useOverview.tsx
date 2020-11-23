import { useCallback, useMemo, useState } from 'react'
import { ClubAccount, Participation } from '@types'
import { QueryResult, usePaginatedQuery, useQuery } from 'react-query'
import { Client } from '@enums'
import { getPersonalActivities, getPersonalClubs } from '@client'
import moment from 'moment'

interface OverviewContext {
  clubs: QueryResult<ClubAccount[]>,
  activities: QueryResult<Participation[]>,
  mainClub: ClubAccount | undefined,
  showCalendar: boolean,
  date: string,
  selected: string,
  onChangeDate: (date: string) => void,
  onChangeSelectedDate: (date: string) => () => void,
  toggleShowCalendar: (set: boolean) => () => void
}

export const useOverview = (): OverviewContext => {
  const [showCalendar, setShowCalendar] = useState(true)
  const [date, setDate] = useState(() => moment().toISOString())
  const [selected, setSelected] = useState(() => moment().toISOString())

  const clubs = useQuery(Client.MY_CLUBS, getPersonalClubs)
  const activities = usePaginatedQuery(
    [Client.MY_ACTIVITIES, {
      from: moment(date).startOf('month').format('YYYY-MM-DD'),
      to: moment(date).endOf('month').format('YYYY-MM-DD')
    }],
    getPersonalActivities
  )

  const mainClub = useMemo(() => {
    if (!clubs.data) {
      return undefined
    }

    if (clubs.data.length === 1) {
      return clubs.data[0] || undefined
    }

    const active = clubs.data.filter(i => i.accepted)

    if (active.length === 1) {
      return active[0] || undefined
    }
  }, [clubs.data])

  const onChangeDate = useCallback((newDate: string) => {
    setDate(newDate)
  }, [setDate])

  const onChangeSelectedDate = useCallback((set: string) => () => {
    setSelected(set)
  }, [setSelected])

  const toggleShowCalendar = useCallback((set: boolean) => () => {
    setShowCalendar(set)
  }, [setShowCalendar])

  return {
    clubs,
    activities,
    mainClub,
    showCalendar,
    date,
    selected,
    onChangeDate,
    onChangeSelectedDate,
    toggleShowCalendar
  }
}
