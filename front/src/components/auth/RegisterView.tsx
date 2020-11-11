import React, { useCallback, useState } from 'react'
import { useApplicationNavigation } from '@hooks'
import { userRegisterSchema } from '@utils/validation'
import { TextField } from 'formik-material-ui'
import { useHistory } from 'react-router-dom'
import { RegisterUserForm, RegisterUser } from '@types'
import { Field, Form, Formik, FormikHelpers } from 'formik'
import { Alert } from '@material-ui/lab'
import { Actions } from '@components/common'
import { EyeOffOutline, EyeOutline, CheckCircle } from 'mdi-material-ui'

import {
  Container,
  createStyles,
  makeStyles,
  Button,
  Paper,
  Grid,
  Typography as T,
  IconButton,
  Zoom
} from '@material-ui/core'
import { userRegister } from '@client'

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
    padding: theme.spacing(3),
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
  title: {
    fontWeight: 600
  },
  titleRow: {
    paddingTop: '0 !important',
    paddingBottom: '0 !important'
  },
  iconButton: {
    padding: theme.spacing(0.5)
  },
  checkPassword: {
    color: 'green'
  },
  primaryTitle: {
    marginBottom: theme.spacing(3)
  },
  error: {
    animation: `$startUpAnimation 400ms ${theme.transitions.easing.easeInOut}`
  },
  '@keyframes popUp': {
    'from': {
      opacity: 0
    },
    'to': {
      opacity: 1
    }
  }
}))

const defaultForm: RegisterUserForm = ({
  firstName: '',
  lastName: '',
  address: '',
  city: '',
  zipCode: '',
  phone: '',
  password: '',
  rePassword: '',
  email: '',
  showPassword: true
})

const RegisterView = () => {
  const classes = useStyles()
  const { push } = useHistory()
  const { onNavigation } = useApplicationNavigation()
  const [error, setError] = useState<null | string>(null)

  const handleSubmit = useCallback(async (
    form: RegisterUserForm,
    { setSubmitting }: FormikHelpers<RegisterUserForm>
  ) => {
    setSubmitting(true)
    setError(null)

    try {
      const register: RegisterUser = {
        address: form.address,
        firstName: form.firstName,
        lastName: form.lastName,
        city: form.city,
        password: form.password,
        phone: form.phone,
        zipCode: form.zipCode,
        email: form.email
      }

      await userRegister(register)
      push('/register/success')
    }
    catch (e) {
      setError(e)
      setSubmitting(false)
    }
  }, [push, setError])

  return (
    <Container className={classes.container} maxWidth='sm'>
      <div className={classes.center}>
        <Paper elevation={2} className={classes.paper}>
          <Formik
            initialValues={defaultForm}
            onSubmit={handleSubmit}
            validationSchema={userRegisterSchema}
            validateOnMount
          >
            {({ isSubmitting, values, isValid, setFieldValue }) => (
              <Form>
                <T
                  variant='h5'
                  component='h3'
                  align='center'
                  className={classes.primaryTitle}
                >Register into service</T>
                <Grid container spacing={3}>
                  <Grid item xs={12} classes={{ item: classes.titleRow }}>
                    <T variant='body1' className={classes.title}>General information:</T>
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      fullWidth
                      component={TextField}
                      label='First Name'
                      name='firstName'
                      type='text'
                      required
                      disabled={isSubmitting}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      fullWidth
                      component={TextField}
                      label='Last Name'
                      name='lastName'
                      type='text'
                      required
                      disabled={isSubmitting}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <Field
                      fullWidth
                      component={TextField}
                      label='Home Address'
                      name='address'
                      type='text'
                      required
                      disabled={isSubmitting}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      fullWidth
                      component={TextField}
                      label='City'
                      name='city'
                      type='text'
                      required
                      disabled={isSubmitting}
                    />
                  </Grid>
                  <Grid item xs={6}>
                    <Field
                      fullWidth
                      component={TextField}
                      label='Zip Code'
                      name='zipCode'
                      type='text'
                      required
                      disabled={isSubmitting}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <Field
                      fullWidth
                      component={TextField}
                      label='Phone Number'
                      name='phone'
                      type='text'
                      required
                      disabled={isSubmitting}
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <T variant='body1' className={classes.title}>Login information: </T>
                  </Grid>
                  <Grid item xs={12}>
                    <Field
                      fullWidth
                      component={TextField}
                      label='Email address'
                      name='email'
                      type='email'
                      required
                      disabled={isSubmitting}
                      autoComplete='username'
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <Field
                      component={TextField}
                      label='Password'
                      name='password'
                      fullWidth
                      type={!values.showPassword ? 'text' : 'password'}
                      autoComplete='new-password'
                      disabled={isSubmitting}
                      required
                      InputProps={{
                        endAdornment: (
                          <IconButton
                            onClick={() => setFieldValue(
                              'showPassword',
                              !values.showPassword,
                              false
                            )}
                            className={classes.iconButton}
                          >
                            {values.showPassword ? <EyeOffOutline /> : <EyeOutline />}
                          </IconButton>
                        )
                      }}
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <Field
                      component={TextField}
                      label='Enter password again'
                      name='rePassword'
                      fullWidth
                      type='password'
                      autoComplete='new-password'
                      disabled={isSubmitting}
                      required
                      InputProps={{
                        endAdornment: (
                          <Zoom in={values.password !== '' && values.password === values.rePassword}>
                            <CheckCircle className={classes.checkPassword} />
                          </Zoom>
                        )
                      }}
                    />
                  </Grid>
                  {error && (
                    <Grid item xs={12}>
                      <Alert
                        severity='error'
                        className={classes.error}
                      >Given information is invalid. Please check your information again.</Alert>
                    </Grid>
                  )}
                  <Grid item xs={12}>
                    <Actions>
                      <Button
                        onClick={onNavigation('/login')}
                        color='secondary'
                        disableElevation
                        variant='outlined'
                        disabled={isSubmitting}
                      >
                        Back to login
                      </Button>
                      <Button
                        type='submit'
                        color='primary'
                        autoFocus
                        disableElevation
                        variant='outlined'
                        disabled={!isValid}
                      >
                        Register
                      </Button>
                    </Actions>
                  </Grid>
                </Grid>
              </Form>
            )}
          </Formik>
        </Paper>
      </div>
    </Container>
  )
}

export default RegisterView
