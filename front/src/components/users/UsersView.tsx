import React from 'react'
import { ConfirmationDialog, DashboardContainer, PaginationControls } from '@components/common'
import { UsersFilterDrawer, UsersViewSelector } from '@components/users'
import { useUsers } from '@hooks'
import { createStyles, makeStyles, Menu, MenuItem } from '@material-ui/core'

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

const UsersView = () => {
  const classes = useStyles()

  const {
    users,
    isList,
    search,
    location,
    showDeleted,
    role,
    anchor,
    select,
    deleting,
    setSearch,
    setRole,
    setLocation,
    setShowDeleted,
    toggleList,
    toggleDeleteDialog,
    onResetFilters,
    onCloseMenu,
    onSelectSettings,
    onNavigation,
    onDelete
  } = useUsers()

  return (
    <DashboardContainer variant='no-max-width'>
      <UsersFilterDrawer
        isList={isList}
        search={search}
        location={location}
        showDeleted={showDeleted}
        role={role}
        setLocation={setLocation}
        setSearch={setSearch}
        setRole={setRole}
        setShowDeleted={setShowDeleted}
        toggleList={toggleList}
      />
      <div className={classes.container}>
        <UsersViewSelector
          users={users.data?.result || []}
          isList={isList}
          loading={users.isLoading}
          onResetFilters={onResetFilters}
          onSelectSettings={onSelectSettings}
        />
        <PaginationControls
          length={users.data?.length || 0}
          loading={users.isLoading}
        />
      </div>
      <Menu
        anchorEl={anchor}
        open={Boolean(anchor)}
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
        onClose={onCloseMenu}
      >
        <MenuItem onClick={onNavigation}>Open profile</MenuItem>
        <MenuItem onClick={toggleDeleteDialog(select)}>Delete user</MenuItem>
      </Menu>
      <ConfirmationDialog
        onClose={toggleDeleteDialog(null)}
        onConfirm={onDelete}
        open={deleting !== null}
        title='Confirm Action'
        children={`Are you sure that you want to delete ${select?.username} account?`}
      />
    </DashboardContainer>
  )
}

export default UsersView
