import React from 'react'
import { ClubTab } from '@enums'
import { Club } from '@types'

interface Props {
  tab: ClubTab,
  club?: Club,
  loading: boolean,
  isMember: boolean
}

export const ClubViewSelector = ({
  tab,
  club,
  loading,
  isMember
}: Props) => {
  console.log(club, loading, isMember)

  if (tab === ClubTab.ACTIVITY) {
    return (
      <div>
        ACTIVITY
      </div>
    )
  }

  if (tab === ClubTab.CONFIG) {
    return (
      <div>
        CONFIG
      </div>
    )
  }

  if (tab === ClubTab.USERS) {
    return (
      <div>
        USERS
      </div>
    )
  }

  return (
    <div>
      MAIN
    </div>
  )
}
