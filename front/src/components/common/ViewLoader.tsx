import React, { memo } from 'react'
import { AuthRoles } from '@enums'
import { useAuth } from '@hooks'
import { NoAccess } from '@components/common'

export interface Props {
  access?: AuthRoles[],
  children: React.ReactNode
}

export const ViewLoader = memo(({
  access,
  children
}: Props) => {
  const { auth } = useAuth()
  const hasAccess = auth !== null && access?.indexOf(auth.user.role) !== -1

  if (access && (!hasAccess || !auth)) {
    return (
      <NoAccess />
    )
  }

  return (
    <>
      {children}
    </>
  )
})
