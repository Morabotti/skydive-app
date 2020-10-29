import React, { memo } from 'react'
import clsx from 'clsx'
import { customPalette } from '@theme'
import { Skeleton } from '@material-ui/lab'

import {
  Paper,
  makeStyles,
  createStyles,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  padding: {
    padding: theme.spacing(3),
    [theme.breakpoints.down(425)]: {
      padding: theme.spacing(1.5)
    }
  },
  root: {
    flexGrow: 1
  },
  headerWrapper: {
    margin: theme.spacing(1, 0),
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'flex-end'
  },
  header: {
    ...theme.typography.body1,
    color: customPalette.header.secondaryText
  },
  section: {
    marginBottom: theme.spacing(3),
    [theme.breakpoints.down(425)]: {
      marginBottom: theme.spacing(2)
    }
  }
}))

interface Props {
  variant?: 'default' | 'no-padding',
  paper?: boolean,
  title?: string,
  sectionLoading?: boolean,
  actions?: JSX.Element | JSX.Element[],
  children: JSX.Element | JSX.Element[]
}

export const DashboardSection = memo(({
  variant = 'default',
  paper = true,
  sectionLoading = false,
  title,
  actions,
  children
}: Props) => {
  const classes = useStyles()

  return (
    <section className={classes.section}>
      {(title || actions) && (
        <div className={classes.headerWrapper}>
          {sectionLoading ? (
            <Skeleton variant='text' width={240} height={28} />
          ) : (
            <T variant='h2' className={classes.header}>{title}</T>
          )}
          {actions && (
            <div>
              {actions}
            </div>
          )}
        </div>
      )}
      {paper ? (
        <Paper
          className={clsx(classes.root, {
            [classes.padding]: variant === 'default'
          })}
        >
          {children}
        </Paper>
      ) : (
        <div
          className={clsx(classes.root, {
            [classes.padding]: variant === 'default'
          })}
        >
          {children}
        </div>
      )}
    </section>
  )
})
