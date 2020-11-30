import React from 'react'
import { OverridableComponent } from '@material-ui/core/OverridableComponent'

import {
  Typography as T,
  makeStyles,
  createStyles,
  SvgIconTypeMap,
  Button
} from '@material-ui/core'
import clsx from 'clsx'

const useStyles = makeStyles(theme => createStyles({
  container: {
    width: '100%',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    flexDirection: 'column',
    height: '100%'
  },
  wrapper: {
    maxWidth: '350px',
    width: '100%',
    padding: theme.spacing(1),
    textAlign: 'center',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center'
  },
  text: {
    margin: theme.spacing(2, 0)
  },
  icon: {
    width: '86px',
    height: '86px',
    color: theme.palette.text.secondary
  }
}))

interface Props {
  icon: OverridableComponent<SvgIconTypeMap<{}, 'svg'>>,
  text: string,
  buttonText?: string,
  className?: string,
  onClick?: () => void,
  onMouseEnter?: () => void
}

export const CenterMessage = ({
  icon: Icon,
  text,
  buttonText,
  className,
  onClick,
  onMouseEnter
}: Props) => {
  const classes = useStyles()

  return (
    <div className={clsx(classes.container, className)}>
      <div className={classes.wrapper}>
        <Icon className={classes.icon} />
        <T variant='body2' className={classes.text}>
          {text}
        </T>
        {onClick && (
          <Button
            variant='contained'
            color='primary'
            disableElevation
            onClick={onClick}
            onMouseEnter={onMouseEnter}
          >
            {buttonText}
          </Button>
        )}
      </div>
    </div>
  )
}
