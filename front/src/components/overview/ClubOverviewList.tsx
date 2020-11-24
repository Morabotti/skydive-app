import React, { useCallback } from 'react'
import { Club, ClubAccount } from '@types'
import { ClubOverviewTile, MainClubOverview } from '@components/overview'
import { createStyles, makeStyles } from '@material-ui/core'
import { useApplicationNavigation } from '@hooks'
import { useQueryCache } from 'react-query'
import { useHistory } from 'react-router-dom'
import { Client } from '@enums'

const useStyles = makeStyles(() => createStyles({
  container: {
    height: '100%'
  }
}))

interface Props {
  clubs?: ClubAccount[],
  loading?: boolean,
  mainClub?: ClubAccount
}

export const ClubOverviewList = ({
  clubs,
  loading,
  mainClub
}: Props) => {
  const classes = useStyles()
  const queryCache = useQueryCache()
  const { onRoutePreload } = useApplicationNavigation()
  const { push } = useHistory()

  const onClubSelect = useCallback((set: Club | null) => () => {
    if (set === null) {
      return
    }

    queryCache.setQueryData([Client.GET_CLUB_BY_SLUG, set.slug], set)
    push(`/dashboard/club/${set.slug}`)
  }, [push, queryCache])

  if (loading || !clubs) {
    return (
      <MainClubOverview loading />
    )
  }

  if (mainClub) {
    return (
      <MainClubOverview
        clubAccount={mainClub}
        loading={loading}
      />
    )
  }

  return (
    <div className={classes.container}>
      {clubs.filter((i, j) => j < 3).map((clubAccount, index, array) => (
        <ClubOverviewTile
          key={clubAccount.id}
          clubAccount={clubAccount}
          last={index === array.length - 1}
          onClick={onClubSelect(clubAccount.club)}
          onMouseEnter={onRoutePreload(`/dashboard/club/${clubAccount.club?.slug}`)}
        />
      ))}
    </div>
  )
}
