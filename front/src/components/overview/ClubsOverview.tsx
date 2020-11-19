import React from 'react'
import { ClubAccount } from '@types'
import { Actions, DashboardSection } from '@components/common'
import { ClubOverviewList, ClubOverviewActions } from '@components/overview'

import {
  makeStyles,
  createStyles
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  wrapper: {
    display: 'flex',
    flexDirection: 'column',
    height: '100%'
  },
  fullHeight: {
    flexGrow: 1
  },
  padding: {
    padding: theme.spacing(1, 2)
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
        <Actions align='right' className={classes.padding} withDivider>
          <ClubOverviewActions
            loading={loading}
            showClubProfile={mainClub !== undefined}
          />
        </Actions>
      </div>
    </DashboardSection>
  )
}
