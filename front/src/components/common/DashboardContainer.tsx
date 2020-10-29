import React, { memo } from 'react'

import {
  Container,
  makeStyles,
  createStyles
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  container: {
    padding: theme.spacing(0)
  }
}))

interface Props {
  variant?: 'default' | 'no-max-width',
  children: JSX.Element | JSX.Element[]
}

export const DashboardContainer = memo(({
  variant = 'default',
  children
}: Props) => {
  const classes = useStyles()

  if (variant === 'no-max-width') {
    return (
      <>
        {children}
      </>
    )
  }

  return (
    <Container className={classes.container} maxWidth='md'>
      {children}
    </Container>
  )
})
