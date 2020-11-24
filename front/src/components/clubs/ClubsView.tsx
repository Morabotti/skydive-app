import React from 'react'
import { DashboardContainer, DummyComponent } from '@components/common'
import { ClubsFilterDrawer } from '@components/clubs'
import { useClubs } from '@hooks'

const ClubsView = () => {
  const {
    isList,
    toggleList,
    city,
    search,
    isPublic,
    toggleIsPublic,
    setCity,
    setSearch
  } = useClubs()

  return (
    <DashboardContainer variant='no-max-width'>
      <ClubsFilterDrawer
        isList={isList}
        city={city}
        isPublic={isPublic}
        search={search}
        toggleList={toggleList}
        toggleIsPublic={toggleIsPublic}
        setCity={setCity}
        setSearch={setSearch}
      />
      <div style={{ marginLeft: 320 }}>
        <DummyComponent />
      </div>
    </DashboardContainer>
  )
}

export default ClubsView
