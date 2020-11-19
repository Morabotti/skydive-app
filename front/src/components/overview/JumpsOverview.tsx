import React from 'react'
import { Actions } from '@components/common'

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
  loading: boolean
}

export const JumpsOverview = ({
  loading
}: Props) => {
  const classes = useStyles()

  console.log(loading)

  return (
    <Paper square className={classes.paper}>
      <div className={classes.wrapper}>
        <div>
          <T variant='h4'>My Jumps</T>
        </div>
        <div className={classes.fullHeight} />
        <Actions align='right'>
          <Button color='primary'>
            Show my jumps
          </Button>
        </Actions>
      </div>
    </Paper>
  )
}
