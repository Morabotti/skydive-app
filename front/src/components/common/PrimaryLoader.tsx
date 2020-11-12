import React, { memo } from 'react'
import { makeStyles, createStyles, Typography as T } from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  wrap: {
    height: '100vh',
    overflow: 'hidden',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  },
  text: {
    fontWeight: 600,
    color: '#333333'
  },
  center: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  },
  loader: {
    display: 'inline-block',
    position: 'relative',
    width: '80px',
    height: '35px',
    '& > div': {
      position: 'absolute',
      top: '10px',
      width: '13px',
      height: '13px',
      borderRadius: '50%',
      background: theme.palette.primary.dark,
      animationTimingFunction: 'cubic-bezier(0, 1, 1, 0)',

      '&:nth-child(1)': {
        left: '8px',
        animation: '$ellipsis1 0.6s infinite'
      },
      '&:nth-child(2)': {
        left: '8px',
        animation: '$ellipsis2 0.6s infinite'
      },
      '&:nth-child(3)': {
        left: '32px',
        animation: '$ellipsis2 0.6s infinite'
      },
      '&:nth-child(4)': {
        left: '56px',
        animation: '$ellipsis3 0.6s infinite'
      }
    }
  },
  '@keyframes ellipsis1': {
    '0%': { transform: 'scale(0)' },
    '100%': { transform: 'scale(1)' }
  },
  '@keyframes ellipsis2': {
    '0%': { transform: 'translate(0, 0)' },
    '100%': { transform: 'translate(24px, 0)' }
  },
  '@keyframes ellipsis3': {
    '0%': { transform: 'scale(1)' },
    '100%': { transform: 'scale(0)' }
  }
}))

interface Props {
  text?: string | null
}

export const PrimaryLoader = memo(({
  text = null
}: Props) => {
  const classes = useStyles()

  return (
    <div className={classes.wrap}>
      <div>
        <T variant='h2'>SKYDIVE APP</T>
        <div className={classes.center}>
          <div className={classes.loader}>
            <div />
            <div />
            <div />
            <div />
          </div>
        </div>
        {text && (
          <T
            variant='body1'
            align='center'
            className={classes.text}
          >{text}</T>
        )}
      </div>
    </div>
  )
})
