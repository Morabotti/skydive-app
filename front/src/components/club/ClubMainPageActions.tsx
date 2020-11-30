import React from 'react'
import { Button, createStyles, makeStyles, Typography as T } from '@material-ui/core'
import { DashboardSection } from '@components/common'
import { customPalette } from '@theme'
import { Skeleton } from '@material-ui/lab'

const useStyles = makeStyles(theme => createStyles({
  wrapper: {
    width: '100%',
    '&:children:not(:last-child)': {
      marginBottom: theme.spacing(2)
    }
  },
  text: {
    fontWeight: theme.typography.fontWeightBold,
    color: customPalette.header.primaryText
  },
  center: {
    display: 'flex',
    alignItems: 'center',
    flexDirection: 'column'
  }
}))

interface Props {
  loading: boolean,
  isMember: boolean,
  isOwner: boolean,
  accepted: boolean,
  onLeave: () => void,
  onJoin: () => void
}

export const ClubMainPageActions = ({
  loading,
  isMember,
  isOwner,
  accepted,
  onLeave,
  onJoin
}: Props) => {
  const classes = useStyles()

  if (loading) {
    return (
      <DashboardSection >
        <div className={classes.center}>
          <Skeleton variant='text' height={24} width='60%' />
          <div className={classes.wrapper}>
            <Skeleton variant='rect' height={36} width='100%' />
          </div>
        </div>
      </DashboardSection>
    )
  }

  return (
    <DashboardSection>
      <T variant='body1' align='center' className={classes.text} gutterBottom>Actions</T>
      <div className={classes.wrapper}>
        {isMember ? (
          <>
            <Button
              variant='outlined'
              disabled={loading || isOwner}
              onClick={onLeave}
              color='secondary'
              fullWidth
            >
              {accepted ? 'Leave Club' : 'Cancel Request To Join'}
            </Button>
          </>
        ) : (
          <>
            <Button
              variant='outlined'
              disabled={loading}
              onClick={onJoin}
              color='primary'
              fullWidth
            >
              Request To Join
            </Button>
          </>
        )}
      </div>
    </DashboardSection>
  )
}
