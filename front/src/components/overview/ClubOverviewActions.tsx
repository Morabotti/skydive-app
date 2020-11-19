import React from 'react'
import { Button } from '@material-ui/core'
import { Skeleton } from '@material-ui/lab'

interface Props {
  loading: boolean,
  showClubProfile: boolean
}

export const ClubOverviewActions = ({
  loading,
  showClubProfile
}: Props) => {
  if (loading) {
    return (
      <>
        <Skeleton variant='rect' width={144} height={39} />
        <Skeleton variant='rect' width={166} height={39} />
      </>
    )
  }

  return (
    <>
      {showClubProfile && (
        <Button color='secondary'>
          Open club profile
        </Button>
      )}
      <Button color='primary'>
        Show all clubs
      </Button>
    </>
  )
}
