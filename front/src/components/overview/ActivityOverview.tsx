import React from 'react'
import { Participation } from '@types'

import {
  makeStyles,
  createStyles,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  container: {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column'
  },
  titleWrapper: {
    padding: theme.spacing(2, 0),
    [theme.breakpoints.down('sm')]: {
      padding: theme.spacing(0, 0, 2)
    }
  },
  fullHeight: {
    flexGrow: 1,
    padding: theme.spacing(1, 0)
  }
}))

interface Props {
  loading: boolean,
  activities?: Participation[]
}

export const ActivityOverview = ({
  loading,
  activities
}: Props) => {
  const classes = useStyles()
  console.log(loading, activities)

  return (
    <div className={classes.container}>
      <div className={classes.titleWrapper}>
        <T variant='h4'>Activities</T>
      </div>
    </div>
  )
}
