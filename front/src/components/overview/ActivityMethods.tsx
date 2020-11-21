import React from 'react'
import { CalendarMonth, CalendarText } from 'mdi-material-ui'
import { customPalette } from '@theme'
import { Skeleton } from '@material-ui/lab'
import clsx from 'clsx'

import {
  makeStyles,
  createStyles,
  IconButton,
  Tooltip
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  spacing: {
    '& > *:not(:last-child)': {
      marginRight: theme.spacing(0.5)
    }
  },
  active: {
    color: customPalette.header.primaryColor,
    cursor: 'default'
  }
}))

interface Props {
  loading?: boolean,
  disabled?: boolean,
  showCalendar: boolean,
  toggleShowCalendar: (set: boolean) => () => void
}

export const ActivityMethods = ({
  loading,
  disabled,
  showCalendar,
  toggleShowCalendar
}: Props) => {
  const classes = useStyles()

  if (loading) {
    <div className={classes.spacing}>
      <Skeleton
        variant='circle'
        width={24}
        height={24}
      />
      <Skeleton
        variant='circle'
        width={24}
        height={24}
      />
    </div>
  }

  return (
    <div className={classes.spacing}>
      {disabled ? (
        <IconButton
          size='small'
          disabled
        >
          <CalendarMonth />
        </IconButton>
      ) : (
        <Tooltip title='Show calendar'>
          <IconButton
            size='small'
            className={clsx({ [classes.active]: showCalendar })}
            onClick={toggleShowCalendar(true)}
          >
            <CalendarMonth />
          </IconButton>
        </Tooltip>
      )}
      {disabled ? (
        <IconButton
          size='small'
          disabled
        >
          <CalendarText />
        </IconButton>
      ) : (
        <Tooltip title='Show events'>
          <IconButton
            size='small'
            className={clsx({ [classes.active]: !showCalendar })}
            onClick={toggleShowCalendar(false)}
          >
            <CalendarText />
          </IconButton>
        </Tooltip>
      )}
    </div>
  )
}
