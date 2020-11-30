import React from 'react'
import { QueryCache, ReactQueryCacheProvider } from 'react-query'
import { MuiPickersUtilsProvider } from '@material-ui/pickers'
import MomentUtils from '@date-io/moment'
import moment from 'moment'
import theme from '@theme'

import {
  MuiThemeProvider,
  CssBaseline,
  StylesProvider,
  createGenerateClassName
} from '@material-ui/core'

const queryCache = new QueryCache({
  defaultConfig: {
    queries: {
      refetchOnWindowFocus: false,
      staleTime: 5 * 60 * 1000,
      cacheTime: 5 * 60 * 1000
    },
    mutations: {
      throwOnError: true
    }
  }
})

const className = createGenerateClassName({
  productionPrefix: 'r'
})

interface Props {
  children: React.ReactNode
}

export const ApplicationProviders = ({ children }: Props) => {
  return (
    <StylesProvider injectFirst generateClassName={className}>
      <MuiThemeProvider theme={theme}>
        <CssBaseline />
        <MuiPickersUtilsProvider
          locale='en'
          libInstance={moment}
          utils={MomentUtils}
        >
          <ReactQueryCacheProvider queryCache={queryCache}>
            {children}
          </ReactQueryCacheProvider>
        </MuiPickersUtilsProvider>
      </MuiThemeProvider>
    </StylesProvider>
  )
}
