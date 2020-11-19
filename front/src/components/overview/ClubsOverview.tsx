import React from 'react'
import { ClubAccount } from '@types'
import { Actions } from '@components/common'
import { ClubOverviewList } from '@components/overview'

import {
  makeStyles,
  createStyles,
  Paper,
  Typography as T,
  Button
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  paper: {
    width: '100%',
    height: '100%'
  },
  wrapper: {
    padding: theme.spacing(2),
    display: 'flex',
    flexDirection: 'column',
    height: '100%'
  },
  fullHeight: {
    flexGrow: 1,
    padding: theme.spacing(1, 0)
  }
}))

interface Props {
  loading: boolean,
  clubs: ClubAccount[] | undefined
}

export const ClubsOverview = ({
  loading,
  clubs
}: Props) => {
  const classes = useStyles()

  return (
    <Paper square className={classes.paper}>
      <div className={classes.wrapper}>
        <div>
          <T variant='h4'>My Clubs</T>
        </div>
        <div className={classes.fullHeight}>
          <ClubOverviewList
            loading={loading}
            clubs={clubs}
          />
        </div>
        <Actions align='right'>
          <Button color='primary'>
            Show all clubs
          </Button>
        </Actions>
      </div>
    </Paper>
  )
}
