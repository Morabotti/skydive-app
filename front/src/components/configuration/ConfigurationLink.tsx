import React, { memo } from 'react'
import { createStyles, makeStyles, SvgIconTypeMap, Typography as T } from '@material-ui/core'
import { customPalette } from '@theme'
import { Skeleton } from '@material-ui/lab'
import { OverridableComponent } from '@material-ui/core/OverridableComponent'

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
      color: customPalette.header.primaryColor,
      '& > div > $icon': {
        color: customPalette.header.primaryColor
      }
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
  icon: {
    width: '170px',
    height: '170px',
    color: theme.palette.text.secondary,
    transition: `color 300ms ${theme.transitions.easing.easeInOut}`
  }
}))

interface Props {
  icon: OverridableComponent<SvgIconTypeMap<{}, 'svg'>>,
  loading?: boolean,
  amount?: number,
  primaryText?: string,
  secondaryText?: string,
  onClick: () => void,
  onMouseEnter: () => void
}

export const ConfigurationLink = memo(({
  icon: Icon,
  amount,
  loading,
  primaryText,
  secondaryText,
  onClick,
  onMouseEnter
}: Props) => {
  const classes = useStyles()

  if (loading || amount === undefined) {
    return (
      <div className={classes.flex}>
        <Skeleton variant='circle' width={170} height={170} />
        {amount && (
          <Skeleton variant='text' width={80} height={60} />
        )}
        {primaryText && (
          <Skeleton variant='text' width='40%' height={24} />
        )}
        {secondaryText && (
          <Skeleton variant='text' width='60%' height={20} />
        )}
      </div>
    )
  }

  return (
    <button
      className={classes.button}
      onClick={onClick}
      onMouseEnter={onMouseEnter}
    >
      <div className={classes.flex}>
        <Icon className={classes.icon} />
        {amount && (
          <T variant='h1' component='p'>{amount}</T>
        )}
        {primaryText && (
          <T variant='body1' gutterBottom>{primaryText}</T>
        )}
        {secondaryText && (
          <T variant='body2' className={classes.color}>{secondaryText}</T>
        )}
      </div>
    </button>
  )
})
