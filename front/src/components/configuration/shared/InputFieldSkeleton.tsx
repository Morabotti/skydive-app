import React, { memo } from 'react'
import { Skeleton } from '@material-ui/lab'
import { makeStyles, createStyles } from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  field: {
    '&:not(:last-child)': {
      marginBottom: theme.spacing(3)
    }
  }
}))

interface Props {
  rows?: number
}

export const InputFieldSkeleton = memo(({
  rows = 1
}: Props) => {
  const classes = useStyles()

  return (
    <div className={classes.field}>
      <Skeleton
        variant='rect'
        animation='wave'
        height={((rows * 19) - 19) + 56}
        width='100%'
      />
    </div>
  )
})
