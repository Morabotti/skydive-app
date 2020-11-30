import React, { useCallback, useState } from 'react'
import { Club } from '@types'
import { createStyles, Grid, makeStyles } from '@material-ui/core'
import { ClubMainPageInformation, ClubMainPageActions } from '@components/club'
import { CenterMessage, ConfirmationDialog } from '@components/common'
import { Cancel } from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  relative: {
    position: 'relative',
    display: 'flex',
    [theme.breakpoints.down('xs')]: {
      flexDirection: 'column'
    }
  },
  height: {
    height: '400px'
  }
}))

interface Props {
  club?: Club,
  loading: boolean,
  isMember: boolean,
  isOwner: boolean,
  accepted: boolean,
  onJoin: () => void,
  onLeave: () => void
}

export const ClubMainPageView = ({
  club,
  loading,
  isMember,
  isOwner,
  accepted,
  onLeave,
  onJoin
}: Props) => {
  const classes = useStyles()
  const [joinConfirmation, setJoinConfirmation] = useState(false)
  const [leaveConfirmation, setLeaveConfirmation] = useState(false)

  const toggleJoin = useCallback((set: boolean) => () => {
    setJoinConfirmation(set)
  }, [setJoinConfirmation])

  const toggleLeave = useCallback((set: boolean) => () => {
    setLeaveConfirmation(set)
  }, [setLeaveConfirmation])

  const handleLeave = useCallback(() => {
    onLeave()
    setLeaveConfirmation(false)
  }, [onLeave, setLeaveConfirmation])

  const handleJoin = useCallback(() => {
    onJoin()
    setJoinConfirmation(false)
  }, [onJoin, setJoinConfirmation])

  return (
    <div>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={8} className={classes.relative}>
          <ClubMainPageInformation
            isMember={isMember}
            loading={loading}
            club={club}
          />
        </Grid>
        <Grid item xs={12} sm={4}>
          <ClubMainPageActions
            isMember={isMember}
            isOwner={isOwner}
            loading={loading}
            accepted={accepted}
            onLeave={toggleLeave(true)}
            onJoin={toggleJoin(true)}
          />
        </Grid>
      </Grid>
      <CenterMessage
        className={classes.height}
        icon={Cancel}
        text='Not implemented yet'
      />
      <ConfirmationDialog
        onClose={toggleJoin(false)}
        onConfirm={handleJoin}
        open={joinConfirmation}
        title='Confirm Joining'
        children={`Are you sure that you want to request to join this club?`}
      />
      <ConfirmationDialog
        onClose={toggleLeave(false)}
        onConfirm={handleLeave}
        open={leaveConfirmation}
        title='Confirm Leaving'
        children={`Are you sure that you want to leave this club?`}
      />
    </div>
  )
}
