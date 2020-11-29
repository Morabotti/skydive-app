import React from 'react'
import { useControls } from '@hooks'
import { Skeleton } from '@material-ui/lab'
import clsx from 'clsx'

import {
  makeStyles,
  createStyles,
  Button,
  Typography as T
} from '@material-ui/core'

import {
  ChevronDoubleLeft,
  ChevronDoubleRight,
  ChevronLeft,
  ChevronRight
} from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  fixed: {
    position: 'fixed',
    bottom: theme.spacing(3),
    right: theme.spacing(3),
    display: 'flex',
    width: 'auto',
    [theme.breakpoints.down(425)]: {
      bottom: theme.spacing(1.5),
      right: theme.spacing(1.5)
    }
  },
  buttonGroup: {
    display: 'inline-flex',
    [theme.breakpoints.down('xs')]: {
      display: 'inline-block'
    },
    '& > button': {
      backgroundColor: theme.palette.background.paper
    }
  },
  text: {
    margin: theme.spacing('auto', 1)
  },
  button: {
    margin: theme.spacing(0, 0.5),
    [theme.breakpoints.down(425)]: {
      minWidth: 40
    }
  },
  last: {
    marginRight: theme.spacing(0)
  }
}))

interface Props {
  length: number,
  loading?: boolean,
  fetching?: boolean
}

export const PaginationControls = ({
  length,
  loading,
  fetching
}: Props) => {
  const classes = useStyles()

  const {
    limit,
    offset,
    onChangeOffset,
    onNextPage,
    onPrevPage
  } = useControls()

  const min = offset + 1
  const maxPages = Math.ceil(length / limit)
  const currentPageMax = offset + limit > length ? length : offset + limit

  if (fetching) {
    return (
      <div className={classes.fixed}>
        <div className={classes.buttonGroup}>
          <Skeleton variant='rect' width={64} height={32} className={classes.button} />
          <Skeleton variant='rect' width={64} height={32} className={classes.button} />
        </div>
        <Skeleton
          variant='text'
          width={55}
          height={28}
          className={classes.text}
        />
        <div className={classes.buttonGroup}>
          <Skeleton variant='rect' width={64} height={32} className={classes.button} />
          <Skeleton
            variant='rect'
            width={64}
            height={32}
            className={clsx(classes.button, classes.last)}
          />
        </div>
      </div>
    )
  }

  if (length === 0) {
    return (
      <React.Fragment />
    )
  }

  return (
    <div className={classes.fixed}>
      <div className={classes.buttonGroup}>
        <Button
          variant='outlined'
          disabled={loading || offset <= 0}
          className={classes.button}
          onClick={() => onChangeOffset(0)}
          size='small'
        >
          <ChevronDoubleLeft />
        </Button>
        <Button
          variant='outlined'
          disabled={loading || offset <= 0}
          className={classes.button}
          onClick={onPrevPage}
          size='small'
        >
          <ChevronLeft />
        </Button>
      </div>
      <T
        component='span'
        className={classes.text}
        color='textSecondary'
      >{min}-{currentPageMax} of {length}</T>
      <div className={classes.buttonGroup}>
        <Button
          variant='outlined'
          className={classes.button}
          disabled={loading || (offset + limit + 1) >= length}
          onClick={onNextPage}
          size='small'
        >
          <ChevronRight />
        </Button>
        <Button
          variant='outlined'
          className={clsx(classes.button, classes.last)}
          disabled={loading || (offset + limit + 1) >= length}
          onClick={() => onChangeOffset(maxPages)}
          size='small'
        >
          <ChevronDoubleRight />
        </Button>
      </div>
    </div>
  )
}
