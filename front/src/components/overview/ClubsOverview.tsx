import React from 'react'
import { ClubAccount } from '@types'
import { DashboardSection } from '@components/common'
import { ClubOverviewList, ClubOverviewActions } from '@components/overview'
import { makeStyles, createStyles } from '@material-ui/core'

const useStyles = makeStyles(() => createStyles({
  wrapper: {
    display: 'flex',
    flexDirection: 'column',
    height: '100%'
  },
  fullHeight: {
    flexGrow: 1
  }
}))

interface Props {
  loading: boolean,
  clubs: ClubAccount[] | undefined,
  mainClub: ClubAccount | undefined
}

export const ClubsOverview = ({
  loading,
  clubs,
  mainClub
}: Props) => {
  const classes = useStyles()

  return (
    <DashboardSection
      title={mainClub ? 'My Club' : 'My Clubs'}
      loading={loading}
      variant='no-padding'
    >
      <div className={classes.wrapper}>
        <div className={classes.fullHeight}>
          <ClubOverviewList
            loading={loading}
            clubs={clubs}
            mainClub={mainClub}
          />
        </div>
        <ClubOverviewActions
          loading={loading}
          clubAccount={mainClub}
        />
      </div>
    </DashboardSection>
  )
}
