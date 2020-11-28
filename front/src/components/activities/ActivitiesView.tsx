import React from 'react'
import { DashboardContainer, PaginationControls } from '@components/common'
import { ActivitiesFilterDrawer, ActivitiesViewSelector } from '@components/activities'
import { useActivities, useAuth } from '@hooks'
import { createStyles, makeStyles } from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  container: {
    marginLeft: 320,
    padding: theme.spacing(3),
    position: 'relative',
    [theme.breakpoints.down(425)]: {
      padding: theme.spacing(1.5)
    }
  }
}))

const ActivitiesView = () => {
  const classes = useStyles()
  const { auth } = useAuth()

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
    setIsVisible,
    toggleList,
    onResetFilters
  } = useActivities()

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
        setIsVisible={setIsVisible}
        toggleList={toggleList}
      />
      <div className={classes.container}>
        <ActivitiesViewSelector
          activities={activities.data?.result || []}
          isList={isList}
          loading={activities.isLoading}
          role={auth?.user.role}
          onResetFilters={onResetFilters}
        />
        <PaginationControls length={activities.data?.length || 0} />
      </div>
    </DashboardContainer>
  )
}

export default ActivitiesView
