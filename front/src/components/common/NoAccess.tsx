import React, { memo } from 'react'
import { Typography as T, makeStyles, createStyles } from '@material-ui/core'
import { AccountRemove } from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  root: {
    textAlign: 'center',
    color: theme.palette.text.secondary
  },
  icon: {
    width: '6em',
    height: '6em'
  },
  text: {
    fontSize: theme.typography.h4.fontSize
  }
}))

export const NoAccess = memo(() => {
  const classes = useStyles()

  return (
    <div className={classes.root}>
      <div>
        <AccountRemove className={classes.icon} />
      </div>
      <T variant='h2' className={classes.text}>You have no access to this view</T>
    </div>
  )
})
