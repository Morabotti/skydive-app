import React from 'react'
import { DashboardContainer } from '@components/common'
import { ClubNavigation, ClubViewSelector } from '@components/club'
import { useClub } from '@hooks'

const ClubView = () => {
  const {
    tab,
    club,
    isMember,
    isOwner,
    onChangeTab
  } = useClub()

  return (
    <>
      <ClubNavigation
        loading={club.isLoading}
        club={club.data}
        tab={tab}
        onChangeTab={onChangeTab}
        isMember={isMember}
        isOwner={isOwner}
      />
      <DashboardContainer>
        <ClubViewSelector
          loading={club.isLoading}
          tab={tab}
          club={club.data}
          isMember={isMember}
        />
      </DashboardContainer>
    </>
  )
}

export default ClubView
