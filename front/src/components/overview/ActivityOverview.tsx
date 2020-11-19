import React from 'react'
import { Participation } from '@types'
import { DashboardSection } from '@components/common'

import {
  makeStyles,
  createStyles
} from '@material-ui/core'

const useStyles = makeStyles(() => createStyles({
  wrapper: {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column'
  },
  fullHeight: {
    flexGrow: 1
  }
}))

interface Props {
  loading: boolean,
  activities?: Participation[]
}

export const ActivityOverview = ({
  loading,
  activities
}: Props) => {
  const classes = useStyles()
  console.log(loading, activities)

  return (
    <DashboardSection
      title='Activities'
      loading={loading}
      variant='no-padding'
    >
      <div className={classes.wrapper}>
        Activities
      </div>
    </DashboardSection>
  )
}
