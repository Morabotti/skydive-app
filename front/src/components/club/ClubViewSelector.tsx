import React from 'react'
import { ClubTab } from '@enums'
import { Club } from '@types'
import { ClubMainPageView, ClubUserListWrapper } from '@components/club'
import { CenterMessage } from '@components/common'
import { Cancel } from 'mdi-material-ui'

interface Props {
  tab: ClubTab,
  club?: Club,
  loading: boolean,
  isMember: boolean,
  isOwner: boolean,
  accepted: boolean,
  onJoin: () => void,
  onLeave: () => void
}

export const ClubViewSelector = ({
  tab,
  club,
  loading,
  isMember,
  isOwner,
  accepted,
  onJoin,
  onLeave
}: Props) => {
  if (tab === ClubTab.ACTIVITY) {
    return (
      <CenterMessage
        icon={Cancel}
        text='Not implemented yet'
      />
    )
  }

  if (tab === ClubTab.CONFIG) {
    return (
      <CenterMessage
        icon={Cancel}
        text='Not implemented yet'
      />
    )
  }

  if (tab === ClubTab.USERS) {
    return (
      <ClubUserListWrapper
        loading={loading}
        isOwner={isOwner}
        club={club}
      />
    )
  }

  return (
    <ClubMainPageView
      isMember={isMember}
      isOwner={isOwner}
      loading={loading}
      accepted={accepted}
      club={club}
      onJoin={onJoin}
      onLeave={onLeave}
    />
  )
}
