import React from 'react'
import { Redirect } from 'react-router-dom'

const LanderView = () => {
  return (
    <Redirect from='/' to='/login' />
  )
}

export default LanderView
