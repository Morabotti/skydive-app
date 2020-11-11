import * as Yup from 'yup'

export const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})/
export const phoneRegex = /^\+?[0-9]\d{1,14}$/
export const zipCodeRegex = /^\d{5}$/

export const baseUserSchema = Yup.object().shape({
  firstName: Yup.string()
    .min(1, 'First name is too short')
    .max(255, 'First name is too long')
    .required('Enter first name'),
  lastName: Yup.string()
    .min(1, 'Last name is too short')
    .max(255, 'Last name is too long')
    .required('Enter last name'),
  address: Yup.string()
    .max(255, 'Address is too long')
    .required('Enter address'),
  city: Yup.string()
    .max(255, 'City name is too long')
    .required('Enter city name'),
  zipCode: Yup.string()
    .required('Enter zip code')
    .matches(zipCodeRegex, 'Entered zip code is not in correct format'),
  phone: Yup.string()
    .required('Enter phone numbere')
    .matches(phoneRegex, 'Entered phone number is not in correct format')
})

export const userRegisterSchema = Yup.object().concat(baseUserSchema).shape({
  password: Yup.string()
    .required('Enter a password')
    .max(64, 'Entered password is too long')
    .matches(
      passwordRegex,
      'Password must contain atleast 8 characters, atleast 1 small and capatilized character and 1 number'
    ),
  rePassword: Yup.string()
    .required('Enter given password again')
    .oneOf([Yup.ref('password')], 'Given password does not match with original one'),
  username: Yup.string()
    .email('Given email is not valid')
    .max(255, 'Given email is too long')
    .required('Enter a email')
})
