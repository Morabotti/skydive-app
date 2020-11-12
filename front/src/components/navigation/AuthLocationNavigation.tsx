import React, { memo } from 'react'
import { customPalette } from '@theme'
import clsx from 'clsx'

import {
  makeStyles,
  createStyles,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  locationBtnOffset: {
    marginRight: theme.spacing(2)
  },
  locationText: {
    color: customPalette.header.secondaryText,
    userSelect: 'none',
    fontWeight: 500
  },
  headerButton: {
    border: 'none',
    display: 'flex',
    margin: theme.spacing(0),
    padding: theme.spacing(0),
    color: customPalette.header.secondaryText,
    width: 'auto',
    background: 'transparent',
    overflow: 'visible',
    lineHeight: 'normal',
    outline: 'none',
    cursor: 'pointer',
    '-webkit-font-smoothing': 'inherit',
    '-moz-osx-font-smoothing': 'inherit',
    '-webkit-appearance': 'none',
    '&::-moz-focus-inner': {
      border: theme.spacing(0),
      padding: theme.spacing(0)
    }
  }
}))

export const AuthLocationNavigation = memo(() => {
  const classes = useStyles()

  return (
    <button
      className={clsx(classes.headerButton, classes.locationBtnOffset)}
    >
      <T variant='body1' className={classes.locationText}>Project 1</T>
    </button>
  )
})
