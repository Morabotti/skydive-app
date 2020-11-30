import React, { Fragment } from 'react'
import { ClubRole } from '@enums'
import { DashboardSection } from '@components/common'
import { Skeleton } from '@material-ui/lab'

import {
  Checkbox,
  createStyles,
  FormControl,
  FormControlLabel,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
  TextField
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  field: {
    marginBottom: theme.spacing(2)
  },
  bottom: {
    marginBottom: theme.spacing(3)
  }
}))

interface Props {
  search: string,
  role: ClubRole,
  accepted: boolean,
  loading: boolean,
  isOwner: boolean,
  isAdmin: boolean,
  setSearch: (set: string) => void,
  setRole: (set: ClubRole) => void,
  toggleAccepted: () => void
}

export const ClubUserSearchFilters = ({
  loading,
  isOwner,
  isAdmin,
  search,
  role,
  accepted,
  setSearch,
  setRole,
  toggleAccepted
}: Props) => {
  const classes = useStyles()

  if (loading) {
    return (
      <DashboardSection loading title='Search members' className={classes.bottom}>
        <>
          <Skeleton width='100%' height={48} variant='rect' className={classes.field} />
          <Skeleton width='100%' height={48} variant='rect' className={classes.field} />
          {(isAdmin || isOwner) && (
            <Skeleton width='40%' height={42} variant='text' />
          )}
        </>
      </DashboardSection>
    )
  }

  return (
    <DashboardSection
      title='Search members'
      loading={loading}
      className={classes.bottom}
    >
      <>
        <TextField
          label='Search'
          type='text'
          fullWidth
          className={classes.field}
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
        <FormControl fullWidth className={classes.field}>
          <InputLabel>Filter by role</InputLabel>
          <Select
            value={role}
            onChange={e => setRole(e.target.value as ClubRole)}
            fullWidth
          >
            <MenuItem value={ClubRole.UNSET}>Unset</MenuItem>
            <MenuItem value={ClubRole.CLUB}>Club owners</MenuItem>
            <MenuItem value={ClubRole.PILOT}>Club pilots</MenuItem>
            <MenuItem value={ClubRole.USER}>Club users</MenuItem>
          </Select>
        </FormControl>
        {(isAdmin || isOwner) && (
          <FormControlLabel
            control={
              <Checkbox
                color='primary'
                checked={accepted}
                onChange={toggleAccepted}
              />
            }
            label='Show only members'
          />
        )}
      </>
    </DashboardSection>
  )
}
