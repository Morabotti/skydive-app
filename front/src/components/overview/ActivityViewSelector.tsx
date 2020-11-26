import React from 'react'
import { Activity, Participation } from '@types'
import { ActivityCalendar, MonthActivityList } from '@components/overview'

interface Props {
  loading: boolean,
  participation?: Participation[],
  activities?: Activity[],
  showCalendar: boolean,
  date: string,
  selected: string,
  onChangeSelectedDate: (date: string) => () => void
}

export const ActivityViewSelector = ({
  loading,
  participation,
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
        participation={participation}
        activities={activities}
      />
    )
  }

  return (
    <ActivityCalendar
      date={date}
      loading={loading}
      selected={selected}
      participation={participation}
      activities={activities}
      onChangeSelectedDate={onChangeSelectedDate}
    />
  )
}
