import React from 'react'
import { DashboardContainer } from '@components/common'
import { ActivitiesFilterDrawer } from '@components/activities'
import { useActivities } from '@hooks'

const ActivitiesView = () => {
  const {
    access,
    activities,
    from,
    to,
    isList,
    isVisible,
    search,
    type,
    setAccess,
    setFrom,
    setSearch,
    setTo,
    setType,
    toggleIsVisible,
    toggleList
  } = useActivities()

  console.log(activities)

  return (
    <DashboardContainer variant='no-max-width'>
      <ActivitiesFilterDrawer
        access={access}
        from={from}
        to={to}
        isList={isList}
        isVisible={isVisible}
        search={search}
        type={type}
        setAccess={setAccess}
        setSearch={setSearch}
        setType={setType}
        setFrom={setFrom}
        setTo={setTo}
        toggleIsVisible={toggleIsVisible}
        toggleList={toggleList}
      />
      <div>
        activities
      </div>
    </DashboardContainer>
  )
}

export default ActivitiesView
