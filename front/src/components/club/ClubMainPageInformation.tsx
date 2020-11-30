import React from 'react'
import { Club } from '@types'
import { AccountMultiple, MapMarker } from 'mdi-material-ui'
import { DashboardSection } from '@components/common'
import { Skeleton } from '@material-ui/lab'
import clsx from 'clsx'

import {
  Avatar,
  Chip,
  colors,
  createStyles,
  makeStyles,
  Tooltip,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  paper: {
    width: '100%'
  },
  avatar: {
    backgroundColor: colors.lightBlue[500],
    color: '#fff',
    position: 'absolute',
    top: '50%',
    transform: 'translateY(-50%)',
    width: '85px',
    height: '85px',
    borderColor: theme.palette.background.default,
    borderWidth: theme.spacing(1),
    borderStyle: 'solid',
    fontSize: theme.typography.h3.fontSize,
    [theme.breakpoints.down('xs')]: {
      display: 'none'
    }
  },
  withIcon: {
    width: theme.spacing(7),
    height: '100%',
    [theme.breakpoints.down('xs')]: {
      width: 'unset',
      height: 'unset'
    }
  },
  centerer: {
    paddingLeft: theme.spacing(3),
    height: '80px',
    marginBottom: theme.spacing(1),
    [theme.breakpoints.down('xs')]: {
      height: 'auto',
      paddingLeft: 0
    }
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
  },
  title: {
    marginBottom: theme.spacing(1)
  },
  spaceBetween: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between'
  },
  chip: {
    color: '#fff',
    fontWeight: theme.typography.fontWeightBold
  },
  private: {
    backgroundColor: colors.red[600]
  },
  public: {
    backgroundColor: colors.green[600]
  }
}))

interface Props {
  club?: Club,
  loading: boolean,
  isMember: boolean
}

export const ClubMainPageInformation = ({
  club,
  loading,
  isMember
}: Props) => {
  const classes = useStyles()

  if (loading || !club) {
    return (
      <>
        <div className={classes.withIcon} />
        <DashboardSection className={classes.paper}>
          <div className={classes.title}>
            <div className={classes.spaceBetween}>
              <Skeleton variant='text' width='50%' height={39} />
              <Skeleton variant='rect' width={75} height={32} />
            </div>
            <Skeleton variant='text' width='70%' height={31} />
          </div>
          <div className={classes.centerer}>
            <Skeleton variant='rect' width='100%' height={75} />
          </div>
          <div className={classes.texts}>
            <Skeleton variant='rect' width='60%' height={24} />
            <Skeleton variant='rect' width='50%' height={24} />
          </div>
        </DashboardSection>
      </>
    )
  }

  return (
    <>
      <Avatar className={classes.avatar}>
        {club.name.charAt(0)}
      </Avatar>
      <div className={classes.withIcon} />
      <DashboardSection className={classes.paper}>
        <div className={classes.title}>
          <div className={classes.spaceBetween}>
            <T variant='h4'>{club.name}</T>
            <Tooltip
              title={club.isPublic
                ? 'This club is currently public'
                : 'This club is currently private'}
            >
              <Chip
                className={clsx(classes.chip, {
                  [classes.public]: club.isPublic,
                  [classes.private]: !club.isPublic
                })}
                label={club.isPublic ? 'PUBLIC' : 'PRIVATE'}
              />
            </Tooltip>
          </div>
          <T variant='h5'>
            {isMember
              ? `${club.clubProfile.address}, ${club.clubProfile.zipCode} ${club.clubProfile.city}`
              : club.clubProfile.city
            }
          </T>
        </div>
        <div className={classes.centerer}>
          <T variant='body1'>{club.clubProfile.description}</T>
          <T variant='body1'>Description</T>
          <T variant='body1'>Description</T>
        </div>
        <div className={classes.texts}>
          <T variant='body1' className={classes.withLogo} color='textSecondary'>
            <AccountMultiple /> 2
          </T>
          <T variant='body1' className={classes.withLogo} color='textSecondary'>
            <MapMarker /> 12
          </T>
        </div>
      </DashboardSection>
    </>
  )
}
