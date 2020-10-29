import React, { memo } from 'react'
import { useAuthLayer } from '@hooks'
import { Typography as T, createStyles, makeStyles } from '@material-ui/core'
import { customPalette } from '@theme'
import { Redirect } from 'react-router-dom'
import { PrimaryLoader } from '@components/common'

const useStyles = makeStyles(() => createStyles({
  bg: {
    backgroundColor: customPalette.common.background
  }
}))

interface Props {
  children: JSX.Element
}

export const DashboardAuthLayer = memo(({ children }: Props) => {
  const { loading, auth, pathname } = useAuthLayer()
  const classes = useStyles()

  if (loading && auth === null) {
    return (
      <PrimaryLoader text='Loading dashboard' />
    )
  }

  if (!loading && auth === null) {
    return (
      <Redirect
        to={`/login${pathname !== '/dashboard' ? `?search=${pathname}` : ''}`}
      />
    )
  }

  if (auth === null) {
    return (
      <T variant='body1'>Error</T>
    )
  }

  return (
    <div className={classes.bg}>
      {children}
    </div>
  )
})
