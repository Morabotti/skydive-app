import React from 'react'
import { Club } from '@types'
import { ClubUserListView } from '@components/club'
import { CircularProgress } from '@material-ui/core'

interface Props {
  club?: Club,
  loading: boolean,
  isOwner: boolean
}

export const ClubUserListWrapper = ({
  club,
  loading,
  isOwner
}: Props) => {
  if (!club) {
    return (
      <CircularProgress />
    )
  }

  return (
    <ClubUserListView
      club={club}
      loading={loading}
      isOwner={isOwner}
    />
  )
}
