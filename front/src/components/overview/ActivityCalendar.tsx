import React, { memo, useMemo } from 'react'
import { Participation } from '@types'
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
    padding: theme.spacing(1, 2)
  },
  fullHeight: {
    display: 'flex',
    flexDirection: 'column',
    flexGrow: 1
  }
}))

interface Props {
  loading: boolean,
  activities?: Participation[],
  date: string,
  selected: string,
  onChangeSelectedDate: (date: string) => () => void
}

export const ActivityCalendar = memo(({
  loading,
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
    if (!activities) {
      return []
    }

    return activities.filter(i =>
      moment(i.activity?.startDate).isSame(selected, 'day')
    )
  }, [activities, selected])

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
            return (
              <CalendarDay
                key={i}
                loading={loading}
                date={date.toISOString()}
                onClick={onChangeSelectedDate(date.toISOString())}
                selectedDate={date.isSame(selected, 'day')}
                currentDate={date.isSame(moment(), 'day')}
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
        activities={filterd}
        selected={selected}
      />
    </div>
  )
})
