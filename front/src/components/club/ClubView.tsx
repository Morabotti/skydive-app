import React from 'react'
import { DashboardContainer } from '@components/common'
import { ClubNavigation, ClubViewSelector } from '@components/club'
import { useClub } from '@hooks'

const ClubView = () => {
  const {
    tab,
    club,
    myClubs,
    isMember,
    isOwner,
    clubRole,
    accepted,
    onChangeTab,
    onJoin,
    onLeave
  } = useClub()

  return (
    <>
      <ClubNavigation
        loading={club.isLoading || myClubs.isLoading}
        club={club.data}
        tab={tab}
        onChangeTab={onChangeTab}
        isMember={isMember}
        isOwner={isOwner}
        accepted={accepted}
        clubRole={clubRole}
      />
      <DashboardContainer>
        <ClubViewSelector
          loading={club.isLoading || myClubs.isLoading}
          tab={tab}
          club={club.data}
          isMember={isMember}
          isOwner={isOwner}
          accepted={accepted}
          onJoin={onJoin}
          onLeave={onLeave}
        />
      </DashboardContainer>
    </>
  )
}

export default ClubView
