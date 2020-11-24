import React from 'react'
import { customPalette } from '@theme'
import { FormatListBulletedSquare, ViewList } from 'mdi-material-ui'
import clsx from 'clsx'

import {
  createStyles,
  Drawer,
  IconButton,
  makeStyles,
  Toolbar,
  Tooltip,
  Typography as T
} from '@material-ui/core'

const drawerWidth = 320

const useStyles = makeStyles(theme => createStyles({
  drawer: {
    width: drawerWidth,
    flexShrink: 0
  },
  drawerPaper: {
    width: drawerWidth
  },
  container: {
    overflow: 'auto'
  },
  title: {
    color: customPalette.header.primaryText,
    fontWeight: theme.typography.fontWeightBold
  },
  view: {
    margin: theme.spacing(3, 3, 2),
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center'
  },
  spacing: {
    '& > *:not(:last-child)': {
      marginRight: theme.spacing(0.5)
    }
  },
  active: {
    color: customPalette.header.primaryColor,
    cursor: 'default'
  }
}))

interface Props {
  title?: string,
  children: JSX.Element | JSX.Element[],
  showViewChanger?: boolean,
  isList?: boolean,
  toggleView?: (set: boolean) => () => void
}

export const DashboardFilterDrawer = ({
  title,
  children,
  showViewChanger,
  isList,
  toggleView
}: Props) => {
  const classes = useStyles()

  return (
    <Drawer
      className={classes.drawer}
      variant='permanent'
      classes={{
        paper: classes.drawerPaper
      }}
    >
      <Toolbar />
      <div className={classes.container}>
        {title && (
          <div className={classes.view}>
            <T variant='body1' className={classes.title}>{title}</T>
            {showViewChanger && (
              <div className={classes.spacing}>
                <Tooltip title='Show as list'>
                  <IconButton
                    size='small'
                    className={clsx({ [classes.active]: isList })}
                    onClick={toggleView ? toggleView(true) : undefined}
                  >
                    <FormatListBulletedSquare />
                  </IconButton>
                </Tooltip>
                <Tooltip title='Show as blocks'>
                  <IconButton
                    size='small'
                    className={clsx({ [classes.active]: !isList })}
                    onClick={toggleView ? toggleView(false) : undefined}
                  >
                    <ViewList />
                  </IconButton>
                </Tooltip>
              </div>
            )}
          </div>
        )}
        {children}
      </div>
    </Drawer>
  )
}
