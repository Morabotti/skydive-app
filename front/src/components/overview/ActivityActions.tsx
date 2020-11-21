import React from 'react'
import { Button, makeStyles } from '@material-ui/core'
import { Skeleton } from '@material-ui/lab'
import { Actions } from '@components/common'

interface Props {
  loading: boolean
}

const useStyles = makeStyles(theme => ({
  padding: {
    padding: theme.spacing(1, 2)
  }
}))

export const ActivityActions = ({
  loading
}: Props) => {
  const classes = useStyles()

  if (loading) {
    return (
      <Actions align='right' withDivider className={classes.padding}>
        <Skeleton variant='rect' width={166} height={39} />
      </Actions>
    )
  }

  return (
    <Actions align='right' withDivider className={classes.padding}>
      <Button color='primary'>
        Search activities
      </Button>
    </Actions>
  )
}
