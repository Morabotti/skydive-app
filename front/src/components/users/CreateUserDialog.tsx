import React, { memo, useCallback, useState } from 'react'
import { CreateUserForm, CreateUser } from '@types'
import { AuthRoles } from '@enums'
import { userRegisterSchema } from '@utils/validation'
import { Select, TextField } from 'formik-material-ui'
import { Actions } from '@components/common'
import { Formik, Form, Field, FormikHelpers } from 'formik'
import { CheckCircle, EyeOffOutline, EyeOutline } from 'mdi-material-ui'
import { Alert } from '@material-ui/lab'

import {
  Dialog,
  DialogTitle,
  DialogContent,
  Button,
  Grid,
  makeStyles,
  createStyles,
  Typography as T,
  IconButton,
  Zoom,
  FormControl,
  InputLabel,
  MenuItem
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  title: {
    fontWeight: 600
  },
  dialog: {
    paddingBottom: theme.spacing(3)
  },
  iconButton: {
    padding: theme.spacing(0.5)
  },
  checkPassword: {
    color: 'green'
  },
  error: {
    animation: `$animation 400ms ${theme.transitions.easing.easeInOut}`
  },
  '@keyframes animation': {
    'from': {
      opacity: 0,
      marginTop: '-50px'
    },
    'to': {
      opacity: 1,
      marginTop: '0px'
    }
  }
}))

const emptyForm: CreateUserForm = ({
  firstName: '',
  lastName: '',
  address: '',
  city: '',
  zipCode: '',
  phone: '',
  password: '',
  username: '',
  role: AuthRoles.USER,
  rePassword: '',
  showPassword: true
})

interface Props {
  open: boolean,
  onClose: () => void,
  onConfirm: (set: CreateUser) => void
}
export const CreateUserDialog = memo(({
  open,
  onClose,
  onConfirm
}: Props) => {
  const classes = useStyles()
  const [error, setError] = useState<null | string>(null)

  const handleSubmit = useCallback(async (
    form: CreateUserForm,
    { setSubmitting }: FormikHelpers<CreateUserForm>
  ) => {
    setError(null)

    const user: CreateUser = {
      address: form.address,
      city: form.city,
      firstName: form.firstName,
      lastName: form.lastName,
      password: form.password,
      phone: form.phone,
      role: form.role,
      username: form.username,
      zipCode: form.zipCode
    }

    try {
      await onConfirm(user)
    }
    catch (e) {
      setError(e)
    }
    setSubmitting(false)
  }, [onConfirm, setError])

  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth='md'
      aria-labelledby='create-user-dialog-title'
    >
      <DialogTitle id='create-user-dialog-title'>Create new user</DialogTitle>
      <DialogContent className={classes.dialog}>
        <Formik
          initialValues={emptyForm}
          onSubmit={handleSubmit}
          validationSchema={userRegisterSchema}
        >
          {({ values, isSubmitting, setFieldValue, isValid }) => (
            <Form>
              <Grid container spacing={3}>
                <Grid item xs={12}>
                  <FormControl fullWidth>
                    <InputLabel htmlFor='account-role'>Account role</InputLabel>
                    <Field
                      component={Select}
                      name='role'
                      fullWidth
                    >
                      <MenuItem value={AuthRoles.USER}>User</MenuItem>
                      <MenuItem value={AuthRoles.ADMIN}>Admin</MenuItem>
                    </Field>
                  </FormControl>
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
                    name='username'
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
                      onClick={onClose}
                      color='secondary'
                      disableElevation
                      variant='outlined'
                      disabled={isSubmitting}
                    >
                      Close
                    </Button>
                    <Button
                      type='submit'
                      color='primary'
                      autoFocus
                      disableElevation
                      variant='outlined'
                      disabled={!isValid}
                    >
                      Create User
                    </Button>
                  </Actions>
                </Grid>
              </Grid>
            </Form>
          )}
        </Formik>
      </DialogContent>
    </Dialog>
  )
})
