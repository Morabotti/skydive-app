import React from 'react'

import theme from '@theme'

import {
  MuiThemeProvider,
  CssBaseline,
  StylesProvider,
  createGenerateClassName
} from '@material-ui/core'

interface Props {
  children: React.ReactNode
}

const className = createGenerateClassName({
  productionPrefix: 'r'
})

export const ApplicationTheme = ({ children }: Props) => (
  <StylesProvider injectFirst generateClassName={className}>
    <MuiThemeProvider theme={theme}>
      <CssBaseline />
      {children}
    </MuiThemeProvider>
  </StylesProvider>
)
