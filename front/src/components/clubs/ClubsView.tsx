import React from 'react'
import { DashboardContainer, PaginationControls } from '@components/common'
import { ClubsFilterDrawer, ClubsViewSelector } from '@components/clubs'
import { useAuth, useClubs } from '@hooks'
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

const ClubsView = () => {
  const classes = useStyles()
  const { auth } = useAuth()

  const {
    isList,
    toggleList,
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
        toggleList={toggleList}
        setIsPublic={setIsPublic}
        setCity={setCity}
        setSearch={setSearch}
      />
      <div className={classes.container}>
        <ClubsViewSelector
          clubs={clubs.data?.result || []}
          isList={isList}
          loading={clubs.isLoading}
          role={auth?.user.role}
          onResetFilters={onResetFilters}
        />
        <PaginationControls length={clubs.data?.length || 0} />
      </div>
    </DashboardContainer>
  )
}

export default ClubsView
