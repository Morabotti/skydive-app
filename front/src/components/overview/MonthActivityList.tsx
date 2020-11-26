import React, { memo } from 'react'
import { Activity, Participation } from '@types'
import { createStyles, makeStyles } from '@material-ui/core'
import { CenterMessage } from '@components/common'
import { Cancel } from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  wrapping: {
    padding: theme.spacing(1, 2),
    height: '100%'
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
  activities?: Activity[]
}

export const MonthActivityList = memo(({
  loading,
  participation,
  activities
}: Props) => {
  const classes = useStyles()

  if (loading || !activities || !participation) {
    return (
      <div className={classes.fullHeight}>
        <div className={classes.wrapping}>
          Loading
        </div>
      </div>
    )
  }

  return (
    <div className={classes.fullHeight}>
      <div className={classes.wrapping}>
        <CenterMessage
          text='Not implemented'
          icon={Cancel}
        />
      </div>
    </div>
  )
})
