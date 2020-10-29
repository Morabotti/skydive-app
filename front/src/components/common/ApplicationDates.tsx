import React from 'react'
import { MuiPickersUtilsProvider } from '@material-ui/pickers'
import MomentUtils from '@date-io/moment'
import moment from 'moment'

interface Props {
  children: React.ReactNode
}

export const ApplicationDates = ({ children }: Props) => {
  return (
    <MuiPickersUtilsProvider
      locale='en'
      libInstance={moment}
      utils={MomentUtils}
    >
      {children}
    </MuiPickersUtilsProvider>
  )
}
