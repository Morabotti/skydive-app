import React from 'react'
import { ClubAccount } from '@types'
import { MapMarker, Phone, Update } from 'mdi-material-ui'
import { Skeleton } from '@material-ui/lab'
import moment from 'moment'

import {
  makeStyles,
  createStyles,
  Typography as T,
  Avatar,
  colors
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  container: {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column'
  },
  top: {
    display: 'flex',
    width: '100%',
    marginBottom: theme.spacing(2)
  },
  rounded: {
    color: '#fff',
    backgroundColor: colors.blue[500],
    transition: `background-color 300ms ${theme.transitions.easing.easeInOut}`,
    width: 76,
    height: 76,
    fontSize: theme.typography.h3.fontSize
  },
  heading: {
    flexGrow: 1,
    marginLeft: theme.spacing(1.5),
    justifyContent: 'center',
    display: 'flex',
    flexDirection: 'column'
  },
  desc: {
    height: '60px',
    maxHeight: '60px',
    overflow: 'hidden',
    marginBottom: theme.spacing(2.5)
  },
  withLogo: {
    display: 'flex',
    alignItems: 'center',
    '& > svg': {
      width: 28,
      paddingRight: theme.spacing(0.75)
    }
  },
  texts: {
    '& > p:not(:last-child)': {
      paddingBottom: theme.spacing(1.5)
    },
    '& > span:not(:last-child)': {
      marginBottom: theme.spacing(1.5)
    }
  }
}))

interface Props {
  loading?: boolean,
  clubAccount?: ClubAccount
}

export const MainClubOverview = ({
  loading,
  clubAccount
}: Props) => {
  const classes = useStyles()

  if (!clubAccount || loading) {
    return (
      <div className={classes.container}>
        <div className={classes.top}>
          <div>
            <Skeleton variant='rect' width={76} height={76} />
          </div>
          <div className={classes.heading}>
            <Skeleton variant='text' width='70%' height={31} />
            <Skeleton variant='text' width='40%' height={24} />
          </div>
        </div>
        <div className={classes.desc}>
          <T variant='body2'>
            <Skeleton variant='rect' width='100%' height={60} />
          </T>
        </div>
        <div className={classes.texts}>
          <Skeleton variant='rect' width='60%' height={24} />
          <Skeleton variant='rect' width='72%' height={24} />
          <Skeleton variant='rect' width='60%' height={24} />
        </div>
      </div>
    )
  }

  const profile = clubAccount.club?.clubProfile

  return (
    <div className={classes.container}>
      <div className={classes.top}>
        <div>
          <Avatar variant='rounded' className={classes.rounded}>
            {clubAccount.club?.name.charAt(0).toUpperCase()}
          </Avatar>
        </div>
        <div className={classes.heading}>
          <T variant='h5'>{clubAccount.club?.name}</T>
          <T variant='body1' color='textSecondary'>{profile?.city}</T>
        </div>
      </div>
      <div className={classes.desc}>
        <T variant='body2'>
          {profile?.description}
        </T>
      </div>
      <div className={classes.texts}>
        <T variant='body1' className={classes.withLogo} color='textSecondary'>
          <MapMarker />
          {profile?.address}, {profile?.zipCode} {profile?.city}
        </T>
        <T variant='body1' className={classes.withLogo} color='textSecondary'>
          <Phone />
          {profile?.phone}
        </T>
        <T variant='body1' className={classes.withLogo} color='textSecondary'>
          <Update />
          {clubAccount.accepted
            ? `Member since ${moment(clubAccount.createdAt).format('DD.MM.YYYY')}`
            : `Not yet a member (requested on ${moment(clubAccount.createdAt).format('DD.MM.YYYY')})`
          }
        </T>
      </div>
    </div>
  )
}
