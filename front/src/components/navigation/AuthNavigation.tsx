import React, { ReactNode, memo, Fragment } from 'react'
import { useAuth, useAuthNavigation } from '@hooks'
import { createStyles, makeStyles } from '@material-ui/core'
import { customPalette } from '@theme'

import { AuthNavigationHeader } from '@components/navigation'

const useStyles = makeStyles(() => createStyles({
  '@global': {
    body: {
      backgroundColor: customPalette.common.background
    }
  },
  root: {
    display: 'flex',
    minHeight: '100vh'
  },
  application: {
    flex: 1,
    display: 'flex',
    width: '100%',
    flexDirection: 'column'
  },
  main: {
    flex: 1,
    backgroundColor: customPalette.common.background
  }
}))

interface Props {
  children: ReactNode
}

const AuthNavigation = memo(({ children }: Props) => {
  const classes = useStyles()
  const { auth, revokeAuth } = useAuth()
  const { currentRoute } = useAuthNavigation()

  if (auth === null) {
    return <Fragment />
  }

  return (
    <div className={classes.root}>
      <div className={classes.application}>
        <AuthNavigationHeader
          auth={auth}
          currentRoute={currentRoute}
          onRevokeAuth={revokeAuth}
        />
        <main className={classes.main}>
          {children}
        </main>
      </div>
    </div>
  )
})

export default AuthNavigation
