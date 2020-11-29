import React from 'react'
import { AuthRoles } from '@enums'
import { BooleanFilterSelect, DashboardFilterDrawer } from '@components/common'

import {
  createStyles,
  FormControl,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
  TextField
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  field: {
    marginBottom: theme.spacing(2),
    width: '100%'
  },
  wrapper: {
    margin: theme.spacing(0, 3, 2)
  }
}))

interface Props {
  isList: boolean,
  search: string,
  location: string,
  role: AuthRoles,
  showDeleted: boolean | null,
  setShowDeleted: (set: boolean | null) => void,
  setSearch: (set: string) => void,
  setLocation: (set: string) => void,
  setRole: (set: AuthRoles) => void,
  toggleList: (set: boolean) => () => void
}

export const UsersFilterDrawer = ({
  isList,
  search,
  location,
  role,
  showDeleted,
  setShowDeleted,
  setSearch,
  setLocation,
  setRole,
  toggleList
}: Props) => {
  const classes = useStyles()

  return (
    <DashboardFilterDrawer
      title='Search activites'
      showViewChanger
      isList={isList}
      toggleView={toggleList}
    >
      <div className={classes.wrapper}>
        <TextField
          label='Search'
          type='text'
          fullWidth
          className={classes.field}
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
        <TextField
          label='Location search'
          type='text'
          fullWidth
          className={classes.field}
          value={location}
          onChange={e => setLocation(e.target.value)}
        />
        <FormControl fullWidth className={classes.field}>
          <InputLabel id='activity-type-field'>Filter By Role</InputLabel>
          <Select
            labelId='activity-type-field'
            value={role}
            onChange={e => setRole(e.target.value as AuthRoles)}
            fullWidth
          >
            <MenuItem value={AuthRoles.UNSET}>Unset</MenuItem>
            <MenuItem value={AuthRoles.USER}>User role only</MenuItem>
            <MenuItem value={AuthRoles.ADMIN}>Admin role only</MenuItem>
          </Select>
        </FormControl>
        <BooleanFilterSelect
          className={classes.field}
          label='Filter by deleted'
          setValue={setShowDeleted}
          value={showDeleted}
          falseLabel='Show only active users'
          trueLabel='Show only deleted users'
        />
      </div>
    </DashboardFilterDrawer>
  )
}
