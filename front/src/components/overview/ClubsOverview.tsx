import React from 'react'
import { ClubAccount } from '@types'
import { Actions } from '@components/common'
import { ClubOverviewList } from '@components/overview'

import {
  makeStyles,
  createStyles,
  Paper,
  Typography as T,
  Button,
  IconButton
} from '@material-ui/core'
import { Skeleton } from '@material-ui/lab'
import { DotsVertical } from 'mdi-material-ui'

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
  },
  title: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center'
  },
  padding: {
    padding: theme.spacing(1)
  }
}))

interface Props {
  loading: boolean,
  clubs: ClubAccount[] | undefined,
  mainClub: ClubAccount | undefined
}

export const ClubsOverview = ({
  loading,
  clubs,
  mainClub
}: Props) => {
  const classes = useStyles()

  return (
    <Paper square className={classes.paper}>
      <div className={classes.wrapper}>
        <div className={classes.title}>
          {loading ? (
            <>
              <Skeleton variant='rect' width='40%' height={40} />
              <Skeleton variant='circle' width={40} height={40} />
            </>
          ) : (
            <>
              <T variant='h4'>{mainClub ? 'My Club' : 'My Clubs'}</T>
              {mainClub && (
                <IconButton className={classes.padding}>
                  <DotsVertical />
                </IconButton>
              )}
            </>
          )}
        </div>
        <div className={classes.fullHeight}>
          <ClubOverviewList
            loading={loading}
            clubs={clubs}
            mainClub={mainClub}
          />
        </div>
        <Actions align='right'>
          {loading ? (
            <>
              <Skeleton variant='rect' width={144} height={39} />
              <Skeleton variant='rect' width={166} height={39} />
            </>
          ) : (
            <>
              {mainClub && (
                <Button color='secondary'>
                  Open club profile
                </Button>
              )}
              <Button color='primary'>
                Show all clubs
              </Button>
            </>
          )}
        </Actions>
      </div>
    </Paper>
  )
}
