import React from 'react'
import { Button, createStyles, makeStyles } from '@material-ui/core'
import { Skeleton } from '@material-ui/lab'
import { Actions } from '@components/common'

const useStyles = makeStyles(theme => createStyles({
  padding: {
    padding: theme.spacing(1, 2)
  }
}))

interface Props {
  loading: boolean,
  showClubProfile: boolean
}

export const ClubOverviewActions = ({
  loading,
  showClubProfile
}: Props) => {
  const classes = useStyles()

  if (loading) {
    return (
      <Actions align='right' className={classes.padding} withDivider>
        <Skeleton variant='rect' width={144} height={39} />
        <Skeleton variant='rect' width={166} height={39} />
      </Actions>
    )
  }

  return (
    <Actions align='right' className={classes.padding} withDivider>
      <>
        {showClubProfile && (
          <Button color='secondary'>
            Open club profile
          </Button>
        )}
      </>
      <Button color='primary'>
        Show all clubs
      </Button>
    </Actions>
  )
}
