import React from 'react'
import { ClubAccount } from '@types'
import { ClubOverviewTile, MainClubOverview } from '@components/overview'
import { createStyles, makeStyles } from '@material-ui/core'

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
      {clubs.filter((i, j) => j < 3).map((club, index, array) => (
        <ClubOverviewTile
          key={club.id}
          clubAccount={club}
          last={index === array.length - 1}
        />
      ))}
    </div>
  )
}
