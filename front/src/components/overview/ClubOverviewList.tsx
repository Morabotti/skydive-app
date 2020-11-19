import React from 'react'
import { ClubAccount } from '@types'
import { ClubOverviewTile } from '@components/overview'
import { createStyles, makeStyles } from '@material-ui/core'

const useStyles = makeStyles(() => createStyles({
  container: {
    height: '100%'
  }
}))

interface Props {
  clubs?: ClubAccount[],
  loading?: boolean
}

export const ClubOverviewList = ({
  clubs,
  loading
}: Props) => {
  const classes = useStyles()

  if (loading || !clubs) {
    return (
      <div className={classes.container}>
        {[...Array(3)].map((e, i) => (
          <ClubOverviewTile key={i} loading />
        ))}
      </div>
    )
  }

  return (
    <div className={classes.container}>
      {clubs.map(club => (
        <ClubOverviewTile
          key={club.id}
          clubAccount={club}
        />
      ))}
    </div>
  )
}
