import React from 'react'
import { Actions, DashboardSection } from '@components/common'

import {
  makeStyles,
  createStyles,
  Button
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  wrapper: {
    display: 'flex',
    flexDirection: 'column',
    height: '100%'
  },
  fullHeight: {
    flexGrow: 1
  },
  padding: {
    padding: theme.spacing(1, 2)
  }
}))

interface Props {
  loading: boolean
}

export const JumpsOverview = ({
  loading
}: Props) => {
  const classes = useStyles()

  return (
    <DashboardSection
      title='My Jumps'
      loading={loading}
      variant='no-padding'
    >
      <div className={classes.wrapper}>
        <div className={classes.fullHeight} />
        <Actions align='right' withDivider className={classes.padding}>
          <Button color='primary'>
            Show my jumps
          </Button>
        </Actions>
      </div>
    </DashboardSection>
  )
}
