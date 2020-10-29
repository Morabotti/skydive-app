import React from 'react'
import { customPalette } from '@theme'
import clsx from 'clsx'

import {
  makeStyles,
  createStyles,
  CircularProgress
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  root: {
    display: 'flex',
    height: '100%',
    alignItems: 'center',
    justifyContent: 'center',
    outline: 0
  },
  wrap: {
    textAlign: 'center',
    userSelect: 'none',
    zIndex: 1000,
    color: customPalette.header.primaryColor
  },
  icon: {
    marginBottom: theme.spacing(2)
  },
  public: {
    height: '400px'
  }
}))

interface Props {
  manualHeight?: boolean
}

export const PageSuspense = ({ manualHeight }: Props) => {
  const classes = useStyles()

  return (
    <div
      className={clsx(classes.root, {
        [classes.public]: manualHeight
      })}
    >
      <div className={classes.wrap}>
        <CircularProgress className={classes.icon} size={65} color='inherit' />
      </div>
    </div>
  )
}
