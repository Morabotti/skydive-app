import React from 'react'
import { Button, createStyles, makeStyles } from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  wrapper: {
    margin: theme.spacing(0, 3, 2)
  }
}))

interface Props {
  onNewUser: () => void
}

export const UsersActions = ({
  onNewUser
}: Props) => {
  const classes = useStyles()

  return (
    <div className={classes.wrapper}>
      <Button
        onClick={onNewUser}
        variant='outlined'
        fullWidth
        color='primary'
      >
        Create new user
      </Button>
    </div>
  )
}
