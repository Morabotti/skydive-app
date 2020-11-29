import React from 'react'
import { customPalette } from '@theme'
import { ChevronLeft, ChevronRight, FormatListBulletedSquare, ViewList } from 'mdi-material-ui'
import clsx from 'clsx'

import {
  Button,
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
    flexShrink: 0,
    position: 'relative'
  },
  drawerPaper: {
    overflow: 'visible'
  },
  notExtended: {
    width: 0
  },
  extended: {
    width: drawerWidth
  },
  container: {
    overflow: 'auto',
    display: 'flex',
    flexDirection: 'column',
    height: '100%'
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
  },
  closeOpen: {
    position: 'absolute',
    bottom: theme.spacing(3),
    right: -(theme.spacing(3) + 32),
    [theme.breakpoints.down(425)]: {
      bottom: theme.spacing(1.5),
      right: -(theme.spacing(1.5) + 32)
    }
  },
  button: {
    margin: theme.spacing(0),
    padding: '3px',
    backgroundColor: theme.palette.background.paper,
    minWidth: 32
  },
  fullHeight: {
    flexGrow: 1
  }
}))

interface Props {
  title?: string,
  children: JSX.Element | JSX.Element[],
  actions?: JSX.Element | JSX.Element[],
  actionsTitle?: string,
  showViewChanger?: boolean,
  isList?: boolean,
  extended: boolean,
  toggleView?: (set: boolean) => () => void,
  toggleExtended: (set: boolean) => () => void
}

export const DashboardFilterDrawer = ({
  title,
  children,
  showViewChanger,
  actions,
  actionsTitle,
  isList,
  extended,
  toggleView,
  toggleExtended
}: Props) => {
  const classes = useStyles()

  return (
    <Drawer
      className={clsx(classes.drawer, {
        [classes.extended]: extended,
        [classes.notExtended]: !extended
      })}
      variant='permanent'
      classes={{
        paper: clsx(classes.drawerPaper, {
          [classes.extended]: extended,
          [classes.notExtended]: !extended
        })
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
        <div className={classes.fullHeight}>
          {children}
        </div>
        {actions && (
          <div>
            {actionsTitle && (
              <div className={classes.view}>
                <T variant='body1' className={classes.title}>{actionsTitle}</T>
              </div>
            )}
            {actions}
          </div>
        )}
      </div>
      <div className={classes.closeOpen}>
        <Button
          variant='outlined'
          className={classes.button}
          onClick={toggleExtended(!extended)}
          size='small'
        >
          {extended ? <ChevronLeft /> : <ChevronRight />}
        </Button>
      </div>
    </Drawer>
  )
}
