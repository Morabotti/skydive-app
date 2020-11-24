import React from 'react'
import { Button, createStyles, makeStyles } from '@material-ui/core'
import { Skeleton } from '@material-ui/lab'
import { Actions } from '@components/common'
import { useApplicationNavigation } from '@hooks'
import { ClubAccount } from '@types'

const useStyles = makeStyles(theme => createStyles({
  padding: {
    padding: theme.spacing(1, 2)
  }
}))

interface Props {
  loading: boolean,
  clubAccount: ClubAccount | undefined
}

export const ClubOverviewActions = ({
  loading,
  clubAccount
}: Props) => {
  const classes = useStyles()
  const { onRoutePreload, onNavigation } = useApplicationNavigation()

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
        {clubAccount && clubAccount.club && (
          <Button
            color='secondary'
            onClick={onNavigation(`/dashboard/club/${clubAccount.club.slug}`)}
            onMouseEnter={onRoutePreload(`/dashboard/club/${clubAccount.club.slug}`)}
          >
            Open club profile
          </Button>
        )}
      </>
      <Button
        color='primary'
        onClick={onNavigation('/dashboard/clubs')}
        onMouseEnter={onRoutePreload('/dashboard/clubs')}
      >
        Show all clubs
      </Button>
    </Actions>
  )
}
