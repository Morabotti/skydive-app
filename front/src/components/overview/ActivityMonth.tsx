import React, { useCallback } from 'react'
import { ChevronLeft, ChevronRight } from 'mdi-material-ui'
import { Skeleton } from '@material-ui/lab'
import moment from 'moment'

import {
  makeStyles,
  createStyles,
  IconButton,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  container: {
    padding: theme.spacing(3, 7),
    [theme.breakpoints.down('xs')]: {
      padding: theme.spacing(2, 2)
    }
  },
  wrapper: {
    display: 'flex',
    alignItems: 'center'
  },
  width: {
    width: '100%'
  }
}))

interface Props {
  loading?: boolean,
  disabled?: boolean,
  date: string,
  onChangeDate: (date: string) => void
}

export const ActivityMonth = ({
  loading,
  disabled,
  date,
  onChangeDate
}: Props) => {
  const classes = useStyles()

  const onChangeMonth = useCallback((month: number) => () => {
    onChangeDate(moment(date).add(month, 'month').toISOString())
  }, [onChangeDate, date])

  if (loading) {
    <div className={classes.container}>
      <div className={classes.wrapper}>
        <Skeleton
          variant='circle'
          width={48}
          height={48}
        />
        <div className={classes.width}>
          <Skeleton
            variant='rect'
            width='70%'
            height={31}
          />
        </div>
        <Skeleton
          variant='circle'
          width={48}
          height={48}
        />
      </div>
    </div>
  }

  return (
    <div className={classes.container}>
      <div className={classes.wrapper}>
        <IconButton disabled={disabled} onClick={onChangeMonth(-1)}>
          <ChevronLeft />
        </IconButton>
        <div className={classes.width}>
          <T
            variant='h5'
            align='center'
          >{moment(date).format('MMMM, YYYY')}</T>
        </div>
        <IconButton disabled={disabled} onClick={onChangeMonth(1)}>
          <ChevronRight />
        </IconButton>
      </div>
    </div>
  )
}
