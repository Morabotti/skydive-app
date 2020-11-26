import React, { memo } from 'react'
import { Skeleton } from '@material-ui/lab'
import clsx from 'clsx'
import moment from 'moment'
import { customPalette } from '@theme'
import { Activity, Participation } from '@types'

import {
  makeStyles,
  createStyles,
  Typography as T,
  IconButton,
  colors
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  day: {
    padding: theme.spacing(0.5),
    position: 'relative',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: 52
  },
  center: {
    textAlign: 'center',
    overflow: 'hidden'
  },
  date: {
    fontWeight: theme.typography.fontWeightBold,
    fontSize: '1.05rem'
  },
  dummy: {
    color: theme.palette.text.hint,
    '& $date': {
      fontWeight: theme.typography.fontWeightMedium,
      cursor: 'pointer'
    }
  },
  circle: {
    position: 'absolute',
    width: 50,
    height: 50,
    borderRadius: '50%',
    zIndex: 5,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    cursor: 'pointer',
    border: `3px solid ${customPalette.header.primaryColor}`
  },
  notification: {
    width: 18,
    height: 18,
    position: 'absolute',
    borderRadius: '50%',
    zIndex: 10,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    cursor: 'pointer',
    pointerEvents: 'none'
  },
  participation: {
    backgroundColor: theme.palette.secondary.main,
    transform: 'translate(13px, -13px)',
    color: '#fff'
  },
  activities: {
    backgroundColor: colors.deepPurple[600],
    transform: 'translate(-13px, -13px)',
    color: '#fff'
  },
  notificationText: {
    fontSize: '0.7rem'
  },
  selected: {
    backgroundColor: customPalette.header.primaryColor,
    color: '#fff'
  },
  special: {
    '& $center': {
      display: 'none'
    }
  },
  customIcon: {
    width: 50,
    height: 50,
    [theme.breakpoints.down('xs')]: {
      width: 33,
      height: 33
    }
  }
}))

interface Props {
  date: string,
  participation?: Participation[],
  activities?: Activity[],
  onClick?: () => void,
  loading?: boolean,
  dummy?: boolean,
  currentDate?: boolean,
  selectedDate?: boolean
}

export const CalendarDay = memo(({
  loading,
  date,
  participation,
  activities,
  currentDate,
  onClick,
  dummy,
  selectedDate
}: Props) => {
  const classes = useStyles()

  if (loading) {
    return (
      <div className={classes.day}>
        <div className={classes.center}>
          <Skeleton variant='circle' width={25} height={25} />
        </div>
      </div>
    )
  }

  if (dummy) {
    return (
      <div className={clsx(classes.day, classes.dummy)}>
        <div className={classes.center}>
          <T
            variant='body1'
            className={classes.date}
          >{moment(date).format('D')}</T>
        </div>
      </div>
    )
  }

  return (
    <div
      className={classes.day}
    >
      <div className={classes.center}>
        <IconButton
          onClick={onClick}
          className={classes.customIcon}
          color='inherit'
        >
          <T
            variant='body1'
            className={classes.date}
          >{moment(date).format('D')}</T>
        </IconButton>
      </div>
      {currentDate && (
        <span className={classes.circle} onClick={onClick}>
          <T
            variant='body1'
            className={classes.date}
          >{moment(date).format('D')}</T>
        </span>
      )}
      {selectedDate && (
        <span className={clsx(classes.circle, classes.selected)}>
          <T
            variant='body1'
            className={classes.date}
          >{moment(date).format('D')}</T>
        </span>
      )}
      {participation && participation.length !== 0 && (
        <span className={clsx(classes.notification, classes.participation)}>
          <T
            variant='body1'
            className={classes.notificationText}
          >{participation.length}</T>
        </span>
      )}
      {activities && activities.length !== 0 && (
        <span className={clsx(classes.notification, classes.activities)}>
          <T
            variant='body1'
            className={classes.notificationText}
          >{activities.length}</T>
        </span>
      )}
    </div>
  )
})
