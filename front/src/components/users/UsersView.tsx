import React from 'react'
import { useUsers } from '@hooks'
import { createStyles, makeStyles, Menu, MenuItem } from '@material-ui/core'
import clsx from 'clsx'

import {
  ConfirmationDialog,
  DashboardContainer,
  PaginationControls
} from '@components/common'

import {
  UsersFilterDrawer,
  UsersViewSelector,
  CreateUserDialog
} from '@components/users'

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

const UsersView = () => {
  const classes = useStyles()

  const {
    users,
    extended,
    isList,
    search,
    location,
    showDeleted,
    role,
    anchor,
    select,
    creating,
    deleting,
    setSearch,
    setRole,
    setLocation,
    setShowDeleted,
    toggleList,
    toggleDeleteDialog,
    toggleCreateDialog,
    toggleExtended,
    onResetFilters,
    onCloseMenu,
    onSelectSettings,
    onNavigation,
    onDelete,
    onCreate
  } = useUsers()

  return (
    <DashboardContainer variant='no-max-width'>
      <UsersFilterDrawer
        isList={isList}
        extended={extended}
        search={search}
        location={location}
        showDeleted={showDeleted}
        role={role}
        setLocation={setLocation}
        setSearch={setSearch}
        setRole={setRole}
        setShowDeleted={setShowDeleted}
        toggleList={toggleList}
        toggleExtended={toggleExtended}
        onCreate={toggleCreateDialog(true)}
      />
      <div className={clsx(classes.container, { [classes.extended]: extended })}>
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
      <CreateUserDialog
        onClose={toggleCreateDialog(false)}
        onConfirm={onCreate}
        open={creating}
      />
    </DashboardContainer>
  )
}

export default UsersView
