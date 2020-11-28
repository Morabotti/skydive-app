import React from 'react'
import { useAuth } from '@hooks'
import { ActivityAccess, ActivityType, AuthRoles } from '@enums'
import { BooleanFilterSelect, DashboardFilterDrawer } from '@components/common'
import { customPalette } from '@theme'
import { DatePicker } from '@material-ui/pickers'
import moment from 'moment'

import {
  createStyles,
  FormControl,
  InputLabel,
  makeStyles,
  MenuItem,
  Select,
  TextField,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  field: {
    marginBottom: theme.spacing(2),
    width: '100%'
  },
  wrapper: {
    margin: theme.spacing(0, 3, 2)
  },
  title: {
    color: customPalette.header.primaryText,
    fontWeight: theme.typography.fontWeightBold,
    marginBottom: theme.spacing(2)
  }
}))

interface Props {
  isList: boolean,
  search: string,
  to: string,
  from: string,
  type: ActivityType,
  access: ActivityAccess,
  isVisible: boolean | null,
  setSearch: (set: string) => void,
  setFrom: (set: string) => void,
  setTo: (set: string) => void,
  setType: (set: ActivityType) => void,
  setAccess: (set: ActivityAccess) => void,
  setIsVisible: (set: boolean | null) => void,
  toggleList: (set: boolean) => () => void
}

export const ActivitiesFilterDrawer = ({
  isList,
  search,
  type,
  access,
  to,
  from,
  isVisible,
  setSearch,
  setType,
  setAccess,
  setFrom,
  setTo,
  setIsVisible,
  toggleList
}: Props) => {
  const classes = useStyles()
  const { auth } = useAuth()

  const isAdmin = auth && auth.user && auth.user.role === AuthRoles.ADMIN

  return (
    <DashboardFilterDrawer
      title='Search activites'
      showViewChanger
      isList={isList}
      toggleView={toggleList}
    >
      <>
        <div className={classes.wrapper}>
          <TextField
            label='Search'
            type='text'
            fullWidth
            className={classes.field}
            value={search}
            onChange={e => setSearch(e.target.value)}
          />
          <DatePicker
            label='Activities from'
            className={classes.field}
            value={from}
            fullWidth
            onChange={date => setFrom(date?.toISOString() || moment().toISOString())}
            animateYearScrolling
            showTodayButton
          />
          <DatePicker
            label='Activities to'
            value={to}
            className={classes.field}
            fullWidth
            onChange={date => setTo(date?.toISOString() || moment().toISOString())}
            animateYearScrolling
            showTodayButton
          />
          <FormControl fullWidth className={classes.field}>
            <InputLabel id='activity-type-field'>Filter By Type</InputLabel>
            <Select
              labelId='activity-type-field'
              value={type}
              onChange={e => setType(e.target.value as ActivityType)}
              fullWidth
            >
              <MenuItem value={ActivityType.UNSET}>Unset</MenuItem>
              <MenuItem value={ActivityType.JUMP}>Jump Activity</MenuItem>
              <MenuItem value={ActivityType.MISC}>Misc Activity</MenuItem>
            </Select>
          </FormControl>
          <FormControl fullWidth className={classes.field}>
            <InputLabel id='activity-type-field'>Filter By Access</InputLabel>
            <Select
              labelId='activity-type-field'
              value={access}
              onChange={e => setAccess(e.target.value as ActivityAccess)}
              fullWidth
            >
              <MenuItem value={ActivityType.UNSET}>Unset</MenuItem>
              <MenuItem value={ActivityAccess.OPEN}>Open only</MenuItem>
              <MenuItem value={ActivityAccess.INVITE}>Invite only</MenuItem>
            </Select>
          </FormControl>
        </div>
        {isAdmin && (
          <div className={classes.wrapper}>
            <div>
              <T variant='body1' className={classes.title}>Admin options</T>
            </div>
            <BooleanFilterSelect
              className={classes.field}
              label='Filter by visibility'
              setValue={setIsVisible}
              value={isVisible}
              falseLabel='Show only visible activites'
              trueLabel='Show only invisible activities'
            />
          </div>
        )}
      </>
    </DashboardFilterDrawer>
  )
}
