import React, { memo } from 'react'
import { Participation } from '@types'
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
  activities?: Participation[]
}

export const MonthActivityList = memo(({
  loading,
  activities
}: Props) => {
  const classes = useStyles()
  console.log(activities)

  if (loading) {
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
