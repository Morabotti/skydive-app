import React, { memo, useMemo } from 'react'
import { Activity, Participation } from '@types'
import { createStyles, makeStyles } from '@material-ui/core'
import moment from 'moment'

import {
  CalendarDay,
  CalendarHeading,
  DayActivities
} from '@components/overview'

const calendarDays = ['Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa', 'Su']

const useStyles = makeStyles(theme => createStyles({
  calendar: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr 1fr 1fr 1fr 1fr 1fr',
    gridTemplateRows: 'auto'
  },
  wrapping: {
    padding: theme.spacing(1, 2),
    minHeight: 370
  },
  fullHeight: {
    display: 'flex',
    flexDirection: 'column',
    flexGrow: 1
  }
}))

interface Props {
  loading: boolean,
  participation?: Participation[],
  activities?: Activity[],
  date: string,
  selected: string,
  onChangeSelectedDate: (date: string) => () => void
}

export const ActivityCalendar = memo(({
  loading,
  participation,
  activities,
  date,
  selected,
  onChangeSelectedDate
}: Props) => {
  const classes = useStyles()

  const dates = useMemo(() => {
    const first = moment(date).utc().startOf('month').startOf('day').toISOString()
    const last = moment(date).utc().endOf('month').startOf('day').toISOString()

    const before = moment(first)

    const beforeRaw = before.diff(
      moment(first).subtract(moment(first).days() - 1, 'days'),
      'days'
    )

    const afterRaw = Math.abs(moment(last).day() - 7)

    return {
      before: beforeRaw === -1 ? 6 : beforeRaw,
      month: moment(date).daysInMonth(),
      after: afterRaw === 7 ? 0 : afterRaw,
      first,
      last
    }
  }, [date])

  const filterd = useMemo(() => {
    if (!participation || !activities) {
      return {
        participation: [],
        activities: []
      }
    }

    return {
      participation: participation.filter(i =>
        moment(i.activity?.startDate).isSame(selected, 'day')
      ),
      activities: activities?.filter(i => moment(i.startDate).isSame(selected, 'day'))
    }
  }, [participation, activities, selected])

  return (
    <div className={classes.fullHeight}>
      <div className={classes.wrapping}>
        <div className={classes.calendar}>
          {calendarDays.map(day => (
            <CalendarHeading
              key={day}
              day={day}
              loading={loading}
            />
          ))}
        </div>
        <div className={classes.calendar}>
          {(dates.before === 0 ? [] : [...Array(dates.before)]).map((e, i) => (
            <CalendarDay
              key={i}
              loading={loading}
              date={moment(dates.first)
                .subtract(dates.before, 'days')
                .add(i, 'day').toISOString()
              }
              dummy
            />
          ))}
          {[...Array(dates.month)].map((e, i) => {
            const date = moment(dates.first).add(i, 'day')

            const dayParticipation = participation?.filter(i =>
              i.activity?.startDate && moment(i.activity.startDate).isSame(date, 'day')
            )

            const dayActivities = activities?.filter(i =>
              i.startDate && moment(i.startDate).isSame(date, 'day')
            )

            return (
              <CalendarDay
                key={i}
                loading={loading}
                date={date.toISOString()}
                onClick={onChangeSelectedDate(date.toISOString())}
                selectedDate={date.isSame(selected, 'day')}
                currentDate={date.isSame(moment(), 'day')}
                participation={dayParticipation}
                activities={dayActivities}
              />
            )
          })}
          {(dates.after === 0 ? [] : [...Array(dates.after)]).map((e, i) => (
            <CalendarDay
              key={i}
              loading={loading}
              date={moment(dates.last).add(i + 1, 'day').toISOString()}
              dummy
            />
          ))}
        </div>
      </div>
      <DayActivities
        loading={loading}
        activities={filterd.activities}
        participation={filterd.participation}
        selected={selected}
      />
    </div>
  )
})
