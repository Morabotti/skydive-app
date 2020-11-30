import React from 'react'
import { Club } from '@types'
import { useAuth, useClubUserList } from '@hooks'
import { PaginationControls } from '@components/common'
import { ClubUserSearchFilters, ClubUserList } from '@components/club'
import { AuthRoles } from '@enums'

interface Props {
  club: Club,
  loading: boolean,
  isOwner: boolean
}

export const ClubUserListView = ({
  club,
  loading,
  isOwner
}: Props) => {
  const { auth } = useAuth()

  const {
    members,
    accepted,
    role,
    search,
    setRole,
    setSearch,
    toggleAccepted,
    onAccept,
    onDecline,
    onRemove,
    onResetFilters
  } = useClubUserList(club.id)

  return (
    <>
      <ClubUserSearchFilters
        accepted={accepted}
        isOwner={isOwner}
        isAdmin={auth?.user.role === AuthRoles.ADMIN}
        loading={loading || members.isLoading}
        role={role}
        search={search}
        setRole={setRole}
        setSearch={setSearch}
        toggleAccepted={toggleAccepted}
      />
      <ClubUserList
        isOwner={isOwner}
        isAdmin={auth?.user.role === AuthRoles.ADMIN}
        loading={loading || members.isLoading}
        members={members.data?.result}
        onAccept={onAccept}
        onDecline={onDecline}
        onRemove={onRemove}
        onResetFilters={onResetFilters}
      />
      <PaginationControls
        length={members.data?.length || 0}
        fetching={members.isLoading}
      />
    </>
  )
}
