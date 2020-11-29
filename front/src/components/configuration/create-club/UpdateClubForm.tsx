import React, { useCallback } from 'react'
import { Field, Form, Formik, FormikHelpers } from 'formik'
import { ClubRequest } from '@types'
import { stringToSlug } from '@utils/slugify'
import { Grid, Typography as T, makeStyles, FormControlLabel, Button } from '@material-ui/core'
import { Checkbox, TextField } from 'formik-material-ui'
import { Actions } from '@components/common'
import { useApplicationNavigation } from '@hooks'
import { clubUpdateSchema } from '@utils/validation'

const useStyles = makeStyles(() => ({
  title: {
    fontWeight: 600
  },
  titleRow: {
    paddingTop: '0 !important',
    paddingBottom: '0 !important'
  }
}))

const defaultForm: ClubRequest = {
  address: '',
  city: '',
  description: '',
  isPublic: false,
  name: '',
  slug: '',
  phone: '',
  zipCode: ''
}

interface Props {
  onSubmit: (set: ClubRequest) => Promise<void>
}

export const UpdateClubForm = ({
  onSubmit
}: Props) => {
  const classes = useStyles()
  const { onNavigation } = useApplicationNavigation()

  const handleSubmit = useCallback(async (
    form: ClubRequest,
    { setSubmitting }: FormikHelpers<ClubRequest>
  ) => {
    setSubmitting(true)

    const data: ClubRequest = {
      ...form,
      slug: stringToSlug(form.name)
    }

    try {
      await onSubmit(data)
    }
    catch (e) {
      setSubmitting(false)
    }
  }, [onSubmit])

  return (
    <Formik
      initialValues={defaultForm}
      onSubmit={handleSubmit}
      validationSchema={clubUpdateSchema}
      validateOnMount
    >
      {({ isSubmitting, values, isValid }) => (
        <Form>
          <Grid container spacing={3}>
            <Grid item xs={12}>
              <Field
                fullWidth
                component={TextField}
                label='Club name'
                name='name'
                type='text'
                required
                disabled={isSubmitting}
                helperText={values.name !== ''
                  ? `Club slug: ${stringToSlug(values.name)}`
                  : 'Enter a club name'
                }
              />
            </Grid>
            <Grid item xs={12}>
              <Field
                fullWidth
                component={TextField}
                label='Club description'
                name='description'
                type='text'
                multiline
                disabled={isSubmitting}
              />
            </Grid>
            <Grid item xs={12}>
              <T variant='body1' className={classes.title}>Basic information:</T>
            </Grid>
            <Grid item xs={12}>
              <Field
                fullWidth
                component={TextField}
                label='Location address'
                name='address'
                type='text'
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
                disabled={isSubmitting}
              />
            </Grid>
            <Grid item xs={12}>
              <FormControlLabel
                label='Mark group as public (can be changed later)'
                control={
                  <Field
                    component={Checkbox}
                    color='primary'
                    name='isPublic'
                    type='checkbox'
                  />
                }
              />
            </Grid>
            <Grid item xs={12}>
              <Actions>
                <Button
                  onClick={onNavigation('/dashboard/configuration')}
                  color='secondary'
                  disableElevation
                  variant='outlined'
                  disabled={isSubmitting}
                >
                  Cancel
                </Button>
                <Button
                  type='submit'
                  color='primary'
                  autoFocus
                  disableElevation
                  variant='outlined'
                  disabled={!isValid}
                >
                  Create club
                </Button>
              </Actions>
            </Grid>
          </Grid>
        </Form>
      )}
    </Formik>
  )
}
