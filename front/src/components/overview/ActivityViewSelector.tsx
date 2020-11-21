import React from 'react'
import { Participation } from '@types'
import { ActivityCalendar, MonthActivityList } from '@components/overview'

interface Props {
  loading: boolean,
  activities?: Participation[],
  showCalendar: boolean,
  date: string,
  selected: string,
  onChangeSelectedDate: (date: string) => () => void
}

export const ActivityViewSelector = ({
  loading,
  activities,
  showCalendar,
  date,
  selected,
  onChangeSelectedDate
}: Props) => {
  if (!showCalendar) {
    return (
      <MonthActivityList
        loading={loading}
        activities={activities}
      />
    )
  }

  return (
    <ActivityCalendar
      date={date}
      loading={loading}
      selected={selected}
      activities={activities}
      onChangeSelectedDate={onChangeSelectedDate}
    />
  )
}
