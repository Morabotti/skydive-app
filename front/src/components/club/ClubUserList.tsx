import React from 'react'
import { CenterMessage, DashboardSection } from '@components/common'
import { Skeleton } from '@material-ui/lab'
import { ClubUserListRow } from '@components/club'
import { ClubAccount } from '@types'

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow
} from '@material-ui/core'
import { AccountCancel } from 'mdi-material-ui'

interface Props {
  isOwner: boolean,
  isAdmin: boolean,
  loading?: boolean,
  members?: ClubAccount[],
  onAccept: (set: ClubAccount) => () => void,
  onDecline: (set: ClubAccount) => () => void,
  onRemove: (set: ClubAccount) => () => void,
  onResetFilters: () => void
}

export const ClubUserList = ({
  isOwner,
  isAdmin,
  loading,
  members,
  onAccept,
  onDecline,
  onRemove,
  onResetFilters
}: Props) => {
  if (loading || !members) {
    return (
      <DashboardSection variant='no-padding'>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell><Skeleton variant='text' width='60%' height={23} /></TableCell>
              {(isOwner || isAdmin) && (
                <TableCell>
                  <Skeleton variant='text' width='40%' height={23} />
                </TableCell>
              )}
              <TableCell><Skeleton variant='text' width='40%' height={23} /></TableCell>
              <TableCell><Skeleton variant='text' width='60%' height={23} /></TableCell>
              {(isOwner || isAdmin) && (
                <TableCell />
              )}
            </TableRow>
          </TableHead>
          <TableBody>
            {[...Array(16)].map((e, i) => (
              <ClubUserListRow key={i} loading isOwner={(isOwner || isAdmin)} />
            ))}
          </TableBody>
        </Table>
      </DashboardSection>
    )
  }

  if (members.length === 0) {
    return (
      <CenterMessage
        icon={AccountCancel}
        text='No accounts found with selected filters.'
        onClick={onResetFilters}
        buttonText='Reset filters'
      />
    )
  }

  return (
    <DashboardSection variant='no-padding'>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            {(isOwner || isAdmin) && (
              <TableCell>Username</TableCell>
            )}
            <TableCell>Role</TableCell>
            <TableCell>Join Date</TableCell>
            {(isOwner || isAdmin) && (
              <TableCell />
            )}
          </TableRow>
        </TableHead>
        <TableBody>
          {members.map(member => (
            <ClubUserListRow
              key={member.id}
              member={member}
              isOwner={(isOwner || isAdmin)}
              loading={loading}
              onAccept={onAccept(member)}
              onDecline={onDecline(member)}
              onRemove={onRemove(member)}
            />
          ))}
        </TableBody>
      </Table>
    </DashboardSection>
  )
}
