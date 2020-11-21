import React from 'react'
import { OverridableComponent } from '@material-ui/core/OverridableComponent'

import {
  Typography as T,
  makeStyles,
  createStyles,
  SvgIconTypeMap
} from '@material-ui/core'

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
  text: string
}

export const CenterMessage = ({
  icon: Icon,
  text
}: Props) => {
  const classes = useStyles()

  return (
    <div className={classes.container}>
      <div className={classes.wrapper}>
        <Icon className={classes.icon} />
        <T variant='body2' className={classes.text}>
          {text}
        </T>
      </div>
    </div>
  )
}
