import React from 'react'
import { Activity, Participation } from '@types'
import { DashboardSection } from '@components/common'
import { makeStyles, createStyles } from '@material-ui/core'

import {
  ActivityMethods,
  ActivityMonth,
  ActivityViewSelector,
  ActivityActions
} from '@components/overview'

const useStyles = makeStyles(() => createStyles({
  wrapper: {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column'
  }
}))

interface Props {
  loading: boolean,
  participation?: Participation[],
  activities?: Activity[],
  showCalendar: boolean,
  date: string,
  selected: string,
  onChangeDate: (date: string) => void,
  onChangeSelectedDate: (date: string) => () => void,
  toggleShowCalendar: (set: boolean) => () => void
}

export const ActivityOverview = ({
  loading,
  participation,
  activities,
  showCalendar,
  date,
  selected,
  onChangeDate,
  onChangeSelectedDate,
  toggleShowCalendar
}: Props) => {
  const classes = useStyles()

  return (
    <DashboardSection
      title='Activities'
      loading={loading}
      variant='no-padding'
      actions={
        <ActivityMethods
          showCalendar={showCalendar}
          loading={loading}
          toggleShowCalendar={toggleShowCalendar}
        />
      }
    >
      <div className={classes.wrapper}>
        <ActivityMonth
          date={date}
          loading={loading}
          onChangeDate={onChangeDate}
        />
        <ActivityViewSelector
          loading={loading}
          date={date}
          selected={selected}
          showCalendar={showCalendar}
          participation={participation}
          activities={activities}
          onChangeSelectedDate={onChangeSelectedDate}
        />
        <ActivityActions
          loading={loading}
        />
      </div>
    </DashboardSection>
  )
}
