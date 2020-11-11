import React from 'react'
import { useApplicationNavigation, useLogin } from '@hooks'
import { customPalette } from '@theme'

import {
  Container,
  createStyles,
  makeStyles,
  Button,
  Paper,
  Grid,
  Typography as T,
  TextField,
  InputAdornment,
  IconButton,
  FormControlLabel,
  Checkbox
} from '@material-ui/core'

import {
  ArrowRight,
  EmailOutline,
  EyeOffOutline,
  EyeOutline,
  LockOutline
} from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  container: {
    height: '100vh',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  },
  center: {
    width: '100%'
  },
  paper: {
    padding: theme.spacing(2),
    height: '100%',
    animation: `$startUpAnimation 500ms ${theme.transitions.easing.easeInOut}`
  },
  '@keyframes startUpAnimation': {
    'from': {
      opacity: 0,
      marginTop: '-50px'
    },
    'to': {
      opacity: 1,
      marginTop: '0px'
    }
  },
  submit: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center'
  },
  main: {
    display: 'flex',
    flexDirection: 'column'
  },
  content: {
    padding: theme.spacing(1),
    height: '100%'
  },
  spacer: {
    marginBottom: theme.spacing(3)
  },
  fit: {
    objectFit: 'contain',
    width: '100%'
  },
  loginWrapper: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    height: '100%',
    flexDirection: 'column',
    [theme.breakpoints.up('sm')]: {
      margin: theme.spacing(0, 4)
    }
  },
  form: {
    width: '100%'
  },
  iconButton: {
    padding: theme.spacing(0.5)
  },
  half: {
    marginBottom: theme.spacing(1.5)
  },
  registerUrl: {
    marginTop: theme.spacing(3),
    textDecoration: 'none',
    color: theme.palette.text.primary,
    display: 'flex',
    alignItems: 'center',
    transition: 'color 300ms ease',
    position: 'relative',
    '& > svg': {
      position: 'absolute',
      right: 0,
      top: '50%',
      transform: 'translate(24px, -50%)',
      transition: 'transform 500ms ease'
    },
    '&:hover': {
      color: customPalette.header.primaryColor,
      '& > svg': {
        transform: 'translate(28px, -50%)'
      }
    }
  }
}))

const LoginView = () => {
  const classes = useStyles()
  const { onNavigation } = useApplicationNavigation()

  const {
    loading,
    error,
    remember,
    showPassword,
    handleChange,
    login,
    togglePassword,
    toggleRemember,
    onSubmit,
    onKeyDown
  } = useLogin()

  return (
    <Container className={classes.container} maxWidth='md'>
      <div className={classes.center}>
        <Paper elevation={2} className={classes.paper}>
          <Grid container spacing={3}>
            <Grid item sm={12} md={6}>
              <div className={classes.content}>
                <div className={classes.spacer}>
                  <T
                    variant='h2'
                    component='h1'
                    align='center'
                  >Skydive-app</T>
                </div>
                <div>
                  <img
                    src='/assets/placeholder.png'
                    alt='placeholder'
                    className={classes.fit}
                  />
                </div>
              </div>
            </Grid>
            <Grid item sm={12} md={6}>
              <div className={classes.content}>
                <div className={classes.loginWrapper}>
                  <form className={classes.form} onSubmit={onSubmit}>
                    <T
                      variant='h5'
                      component='h3'
                      className={classes.half}
                      align='center'
                    >Login</T>
                    <TextField
                      label='Email'
                      type='text'
                      name='username'
                      fullWidth
                      autoComplete='username'
                      error={error}
                      disabled={loading}
                      className={classes.spacer}
                      value={login.username}
                      onChange={handleChange('username')}
                      onKeyDown={onKeyDown}
                      variant='standard'
                      InputProps={{
                        startAdornment: (
                          <InputAdornment position='start'>
                            <EmailOutline />
                          </InputAdornment>
                        )
                      }}
                    />
                    <TextField
                      label='Password'
                      name='password'
                      fullWidth
                      type={!showPassword ? 'text' : 'password'}
                      autoComplete='current-password'
                      error={error}
                      className={classes.half}
                      value={login.password}
                      disabled={loading}
                      onChange={handleChange('password')}
                      variant='standard'
                      onKeyDown={onKeyDown}
                      InputProps={{
                        startAdornment: (
                          <InputAdornment position='start'>
                            <LockOutline />
                          </InputAdornment>
                        ),
                        endAdornment: (
                          <IconButton
                            onClick={togglePassword}
                            className={classes.iconButton}
                          >
                            {showPassword ? <EyeOffOutline /> : <EyeOutline />}
                          </IconButton>
                        )
                      }}
                    />
                    <FormControlLabel
                      control={<Checkbox
                        checked={remember}
                        onChange={toggleRemember}
                        name='remember'
                        color='primary'
                      />}
                      label='Remember me'
                      className={classes.half}
                    />
                    <Button
                      variant='outlined'
                      color='primary'
                      fullWidth
                      disableElevation
                      disabled={loading}
                      autoFocus
                      type='submit'
                    >
                      Login
                    </Button>
                  </form>
                  <T
                    variant='body1'
                    component='a'
                    href='/register'
                    className={classes.registerUrl}
                    onClick={onNavigation('/register')}
                  >Create your account <ArrowRight /></T>
                </div>
              </div>
            </Grid>
          </Grid>
        </Paper>
      </div>
    </Container>
  )
}

export default LoginView
