import React from 'react'
import { useLogin } from '@hooks'

import {
  Container,
  createStyles,
  makeStyles,
  Button,
  Paper,
  CircularProgress
} from '@material-ui/core'

const useStyles = makeStyles(theme =>
  createStyles({
    container: {
      color: theme.palette.text.primary,
      marginTop: theme.spacing(2),
      [theme.breakpoints.down('xs')]: {
        marginTop: theme.spacing(0)
      }
    },
    paper: {
      padding: theme.spacing(2)
    },
    submit: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center'
    },
    main: {
      display: 'flex',
      flexDirection: 'column'
    }
  })
)

const LoginView = () => {
  const classes = useStyles()
  const { loading, onSubmit } = useLogin()

  return (
    <Container className={classes.container} maxWidth='xs'>
      <Paper elevation={2} className={classes.paper}>
        <main className={classes.main}>
          <div className={classes.submit}>
            <Button
              variant='contained'
              color='primary'
              onClick={onSubmit}
            >
              {loading ? (
                <CircularProgress
                  color='inherit'
                  size={20}
                />
              ) : 'Login'}
            </Button>
          </div>
        </main>
      </Paper>
    </Container>
  )
}

export default LoginView
