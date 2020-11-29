import React from 'react'
import { createStyles, makeStyles, Typography as T } from '@material-ui/core'
import { DashboardSection } from '@components/common'
import { AccountGroup } from 'mdi-material-ui'
import { customPalette } from '@theme'
import { Skeleton } from '@material-ui/lab'

const useStyles = makeStyles(theme => createStyles({
  color: {
    color: theme.palette.text.secondary
  },
  button: {
    border: 'none',
    cursor: 'pointer',
    backgroundColor: '#fff',
    padding: 0,
    margin: 0,
    display: 'flex',
    width: '100%',
    height: '100%',
    textAlign: 'left',
    alignItems: 'normal',
    transition: `color 300ms ${theme.transitions.easing.easeInOut}`,
    '&:hover': {
      color: customPalette.header.primaryColor
    }
  },
  flex: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: '100%',
    flexDirection: 'column'
  },
  upper: {
    width: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  },
  icon: {
    width: '170px',
    height: '170px'
  }
}))

interface Props {
  loading: boolean,
  amount?: number,
  onClick: () => void,
  onMouseEnter: () => void
}

export const PlatformUsersTile = ({
  loading,
  amount,
  onClick,
  onMouseEnter
}: Props) => {
  const classes = useStyles()

  if (loading || amount === undefined) {
    return (
      <DashboardSection
        title='Manage users'
        loading
        variant='no-padding'
      >
        <div className={classes.flex}>
          <Skeleton variant='circle' width={170} height={170} />
          <Skeleton variant='text' width={80} height={60} />
          <Skeleton variant='text' width='40%' height={24} />
          <Skeleton variant='text' width='60%' height={20} />
        </div>
      </DashboardSection>
    )
  }

  return (
    <DashboardSection
      title='Manage users'
      loading={loading}
      variant='no-padding'
    >
      <button
        className={classes.button}
        onClick={onClick}
        onMouseEnter={onMouseEnter}
      >
        <div className={classes.flex}>
          <AccountGroup className={classes.icon} />
          <T variant='h1' component='p'>{amount}</T>
          <T variant='body1' gutterBottom>Users in platform</T>
          <T variant='body2' className={classes.color}>Open users management</T>
        </div>
      </button>
    </DashboardSection>
  )
}
