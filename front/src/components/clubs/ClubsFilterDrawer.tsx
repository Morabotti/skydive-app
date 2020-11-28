import React from 'react'
import { useAuth } from '@hooks'
import { AuthRoles } from '@enums'
import { BooleanFilterSelect, DashboardFilterDrawer } from '@components/common'
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
    fontWeight: theme.typography.fontWeightBold,
    marginBottom: theme.spacing(2)
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
  setIsPublic: (set: null | boolean) => void,
  toggleList: (set: boolean) => () => void
}

export const ClubsFilterDrawer = ({
  isList,
  city,
  search,
  isPublic,
  setCity,
  setSearch,
  setIsPublic,
  toggleList
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
            <BooleanFilterSelect
              className={classes.field}
              label='Filter by public'
              setValue={setIsPublic}
              value={isPublic}
              falseLabel='Show only public clubs'
              trueLabel='Show only private clubs'
            />
          </div>
        )}
      </>
    </DashboardFilterDrawer>
  )
}
