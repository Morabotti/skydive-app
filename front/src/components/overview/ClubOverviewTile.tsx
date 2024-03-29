import React from 'react'
import { ClubAccount } from '@types'
import { Skeleton } from '@material-ui/lab'
import { ChevronRight } from 'mdi-material-ui'
import clsx from 'clsx'
import moment from 'moment'

import {
  makeStyles,
  createStyles,
  Avatar,
  colors,
  Typography as T,
  Divider
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  paper: {
    padding: theme.spacing(1.5),
    height: '50%',
    display: 'flex',
    width: '100%',
    alignItems: 'center',
    [theme.breakpoints.down('sm')]: {
      height: 'auto'
    }
  },
  avatarWrapper: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center'
  },
  textWrapper: {
    marginLeft: theme.spacing(1.5),
    display: 'flex',
    justifyContent: 'center',
    flexDirection: 'column',
    flexGrow: 1,
    textAlign: 'left'
  },
  rounded: {
    color: '#fff',
    backgroundColor: colors.blue[500],
    transition: `background-color 300ms ${theme.transitions.easing.easeInOut}`,
    width: 52,
    height: 52,
    fontSize: theme.typography.h3.fontSize,
    [theme.breakpoints.down('sm')]: {
      width: 36,
      height: 36,
      fontSize: theme.typography.h5.fontSize
    }
  },
  seeMore: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    opacity: 0,
    transition: `opacity 300ms ${theme.transitions.easing.easeInOut}`
  },
  noOpacity: {
    opacity: 1
  },
  button: {
    border: 'none',
    cursor: 'pointer',
    backgroundColor: '#fff',
    '&:hover': {
      backgroundColor: colors.grey[50]
    },
    '&:hover $seeMore': {
      opacity: 1
    },
    '&:hover $rounded': {
      backgroundColor: colors.blue[600]
    }
  }
}))

interface Props {
  clubAccount?: ClubAccount,
  loading?: boolean,
  last?: boolean,
  onClick?: () => void,
  onMouseEnter?: () => void
}

export const ClubOverviewTile = ({
  clubAccount,
  loading,
  last,
  onClick,
  onMouseEnter
}: Props) => {
  const classes = useStyles()

  if (loading || !clubAccount || !clubAccount.club) {
    return (
      <>
        <div className={classes.paper}>
          <div className={classes.avatarWrapper}>
            <Skeleton variant='rect' width={55} height={55} />
          </div>
          <div className={classes.textWrapper}>
            <Skeleton variant='text' width='50%' height={31} />
            <Skeleton variant='text' width='80%' height={24} />
          </div>
          <div className={clsx(classes.seeMore, classes.noOpacity)}>
            <Skeleton variant='circle' width={24} height={24} />
          </div>
        </div>
        {!last && (
          <Divider />
        )}
      </>
    )
  }

  return (
    <>
      <button
        className={clsx(classes.paper, classes.button)}
        onClick={onClick}
        onMouseEnter={onMouseEnter}
      >
        <div className={classes.avatarWrapper}>
          <Avatar variant='rounded' className={classes.rounded}>
            {clubAccount.club.name.charAt(0).toUpperCase()}
          </Avatar>
        </div>
        <div className={classes.textWrapper}>
          <T variant='h5'>{clubAccount.club.name}</T>
          <T
            variant='body1'
            color='textSecondary'
          >
            {clubAccount.club.clubProfile.city}
            {clubAccount.accepted !== null
              ? `, Member since ${moment(clubAccount.accepted).format('DD.MM.YYYY')}`
              : `, Not yet a member`
            }
          </T>
        </div>
        <div className={classes.seeMore}>
          <ChevronRight />
        </div>
      </button>
      {!last && (
        <Divider />
      )}
    </>
  )
}
