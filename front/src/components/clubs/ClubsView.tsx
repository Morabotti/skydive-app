import React from 'react'
import { DashboardContainer, PaginationControls } from '@components/common'
import { ClubsFilterDrawer, ClubsViewSelector } from '@components/clubs'
import { useAuth, useClubs } from '@hooks'
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

const ClubsView = () => {
  const classes = useStyles()
  const { auth } = useAuth()

  const {
    isList,
    extended,
    toggleList,
    toggleExtended,
    city,
    search,
    clubs,
    isPublic,
    setIsPublic,
    setCity,
    setSearch,
    onResetFilters
  } = useClubs()

  return (
    <DashboardContainer variant='no-max-width'>
      <ClubsFilterDrawer
        isList={isList}
        city={city}
        isPublic={isPublic}
        search={search}
        extended={extended}
        toggleList={toggleList}
        toggleExtended={toggleExtended}
        setIsPublic={setIsPublic}
        setCity={setCity}
        setSearch={setSearch}
      />
      <div className={clsx(classes.container, { [classes.extended]: extended })}>
        <ClubsViewSelector
          clubs={clubs.data?.result || []}
          isList={isList}
          loading={clubs.isLoading}
          role={auth?.user.role}
          onResetFilters={onResetFilters}
        />
        <PaginationControls
          length={clubs.data?.length || 0}
          fetching={clubs.isLoading}
        />
      </div>
    </DashboardContainer>
  )
}

export default ClubsView
