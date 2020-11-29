import React from 'react'
import { Button, createStyles, makeStyles } from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  wrapper: {
    margin: theme.spacing(0, 3, 2)
  }
}))

interface Props {
  onCreate: () => void
}

export const ClubsAdminActions = ({
  onCreate
}: Props) => {
  const classes = useStyles()

  return (
    <div className={classes.wrapper}>
      <Button
        onClick={onCreate}
        variant='outlined'
        fullWidth
        color='primary'
      >
        Create new club
      </Button>
    </div>
  )
}
