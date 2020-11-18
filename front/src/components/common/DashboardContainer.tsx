import React, { memo } from 'react'
import clsx from 'clsx'

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
  children: JSX.Element | JSX.Element[],
  className?: string
}

export const DashboardContainer = memo(({
  variant = 'default',
  children,
  className
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
    <Container
      className={clsx(classes.container, className)}
      maxWidth='md'
    >
      {children}
    </Container>
  )
})
