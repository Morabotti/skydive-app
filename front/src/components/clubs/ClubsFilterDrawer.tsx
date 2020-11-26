import React from 'react'
import { useAuth } from '@hooks'
import { AuthRoles } from '@enums'
import { DashboardFilterDrawer } from '@components/common'
import { customPalette } from '@theme'

import {
  Checkbox,
  createStyles,
  FormControlLabel,
  makeStyles,
  TextField,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  field: {
    marginBottom: theme.spacing(2)
  },
  wrapper: {
    margin: theme.spacing(0, 3, 2)
  },
  title: {
    color: customPalette.header.primaryText,
    fontWeight: theme.typography.fontWeightBold
  },
  titleWrapper: {
    marginBottom: theme.spacing(2)
  }
}))

interface Props {
  isList: boolean,
  search: string,
  city: string,
  isPublic: boolean | null,
  setSearch: (set: string) => void,
  setCity: (set: string) => void,
  toggleList: (set: boolean) => () => void,
  toggleIsPublic: () => void
}

export const ClubsFilterDrawer = ({
  isList,
  city,
  search,
  isPublic,
  setCity,
  setSearch,
  toggleList,
  toggleIsPublic
}: Props) => {
  const classes = useStyles()
  const { auth } = useAuth()

  const isAdmin = auth && auth.user && auth.user.role === AuthRoles.ADMIN

  return (
    <DashboardFilterDrawer
      title='Search clubs'
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
          <TextField
            label='City name'
            type='text'
            fullWidth
            className={classes.field}
            value={city}
            onChange={e => setCity(e.target.value)}
          />
          <FormControlLabel
            control={
              <Checkbox color='primary' />
            }
            disabled
            label='Show only my clubs'
          />
        </div>
        {isAdmin && (
          <div className={classes.wrapper}>
            <div>
              <T variant='body1' className={classes.title}>Admin options</T>
            </div>
            <FormControlLabel
              control={
                <Checkbox color='primary' />
              }
              value={isPublic === null ? false : isPublic}
              onChange={toggleIsPublic}
              label='Show only private clubs'
            />
          </div>
        )}
      </>
    </DashboardFilterDrawer>
  )
}
