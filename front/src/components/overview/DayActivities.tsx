import React from 'react'
import { Activity, Participation } from '@types'
import { createStyles, makeStyles } from '@material-ui/core'
import moment from 'moment'
import { CenterMessage } from '@components/common'
import { CalendarRemove } from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  wrapping: {
    padding: theme.spacing(1, 2),
    height: '100%'
  }
}))

interface Props {
  loading: boolean,
  participation: Participation[],
  activities: Activity[],
  selected: string
}

export const DayActivities = ({
  loading,
  participation,
  activities,
  selected
}: Props) => {
  const classes = useStyles()

  if (loading) {
    return (
      <div>
        Loading TODO
      </div>
    )
  }

  if (participation.length === 0 && activities.length === 0) {
    return (
      <div className={classes.wrapping}>
        <CenterMessage
          text={`No activities on ${moment(selected).format('dddd')}`}
          icon={CalendarRemove}
        />
      </div>
    )
  }

  return (
    <div>
      TODO
    </div>
  )
}
