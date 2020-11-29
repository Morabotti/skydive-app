import React from 'react'
import { DashboardContainer, PaginationControls } from '@components/common'
import { ActivitiesFilterDrawer, ActivitiesViewSelector } from '@components/activities'
import { useActivities, useAuth } from '@hooks'
import { createStyles, makeStyles } from '@material-ui/core'
import clsx from 'clsx'

const useStyles = makeStyles(theme => createStyles({
  container: {
    padding: theme.spacing(3),
    position: 'relative',
    [theme.breakpoints.down(425)]: {
      padding: theme.spacing(1.5)
    }
  },
  extended: {
    marginLeft: 320
  }
}))

const ActivitiesView = () => {
  const classes = useStyles()
  const { auth } = useAuth()

  const {
    access,
    activities,
    extended,
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
    toggleExtended,
    onResetFilters
  } = useActivities()

  return (
    <DashboardContainer variant='no-max-width'>
      <ActivitiesFilterDrawer
        access={access}
        extended={extended}
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
        toggleExtended={toggleExtended}
      />
      <div className={clsx(classes.container, { [classes.extended]: extended })}>
        <ActivitiesViewSelector
          activities={activities.data?.result || []}
          isList={isList}
          loading={activities.isLoading}
          role={auth?.user.role}
          onResetFilters={onResetFilters}
        />
        <PaginationControls
          length={activities.data?.length || 0}
          loading={activities.isLoading}
        />
      </div>
    </DashboardContainer>
  )
}

export default ActivitiesView
