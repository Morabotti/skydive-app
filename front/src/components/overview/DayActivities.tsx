import React from 'react'
import { Activity, Participation } from '@types'
import moment from 'moment'
import { CenterMessage } from '@components/common'
import { Airplane, CalendarRemove, Cloud } from 'mdi-material-ui'
import { ActivityType } from '@enums'
import { customPalette } from '@theme'
import { useApplicationNavigation } from '@hooks'

import {
  Avatar,
  colors,
  createStyles,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  makeStyles,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  wrapping: {
    padding: theme.spacing(1, 2),
    height: '100%'
  },
  title: {
    padding: theme.spacing(1, 2),
    '& > p': {
      fontWeight: theme.typography.fontWeightBold,
      color: customPalette.header.primaryText
    }
  },
  participation: {
    backgroundColor: colors.blue[400]
  },
  activity: {
    backgroundColor: colors.blueGrey[400]
  }
}))

interface Props {
  loading: boolean,
  participation: Participation[],
  activities: Activity[],
  selected: string
}

export const DayActivities = ({
  loading,
  participation,
  activities,
  selected
}: Props) => {
  const classes = useStyles()
  const { onNavigation, onRoutePreload } = useApplicationNavigation()

  if (loading) {
    return (
      <div />
    )
  }

  if (participation.length === 0 && activities.length === 0) {
    return (
      <div className={classes.wrapping}>
        <CenterMessage
          text={`No activities on ${moment(selected).format('dddd')}`}
          icon={CalendarRemove}
        />
      </div>
    )
  }

  return (
    <>
      <div className={classes.title}>
        <T variant='body1' align='center'>Activities on {moment(selected).format('dddd')}</T>
      </div>
      <List component='nav'>
        {participation.map(participation => (
          <ListItem
            button
            key={participation.id}
            onClick={onNavigation(`/dashboard/activity/${participation.activity?.token}`)}
            onMouseEnter={onRoutePreload(`/dashboard/activity/${participation.activity?.token}`)}
          >
            <ListItemAvatar>
              <Avatar className={classes.participation}>
                {participation.activity?.type === ActivityType.JUMP ? (
                  <Airplane />
                ) : (
                  <Cloud />
                )}
              </Avatar>
            </ListItemAvatar>
            <ListItemText
              primary={participation.activity?.title}
              secondary={moment(participation.activity?.startDate).format('DD.MM.YYYY')}
            />
          </ListItem>
        ))}
        {activities.map(activity => (
          <ListItem
            button
            key={activity.id}
            onClick={onNavigation(`/dashboard/activity/${activity.token}`)}
            onMouseEnter={onRoutePreload(`/dashboard/activity/${activity.token}`)}
          >
            <ListItemAvatar>
              <Avatar className={classes.activity}>
                {activity.type === ActivityType.JUMP ? (
                  <Airplane />
                ) : (
                  <Cloud />
                )}
              </Avatar>
            </ListItemAvatar>
            <ListItemText
              primary={activity.title}
              secondary={moment(activity.startDate).format('DD.MM.YYYY')}
            />
          </ListItem>
        ))}
      </List>
    </>
  )
}
