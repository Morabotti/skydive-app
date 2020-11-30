import React from 'react'
import { DashboardContainer, DashboardSection } from '@components/common'
import { LinearProgress, Typography as T } from '@material-ui/core'
import { useActivity } from '@hooks'

const ActivityView = () => {
  const { activity } = useActivity()

  return (
    <DashboardContainer>
      <DashboardSection>
        <div>
          {(activity.isLoading || !activity.data) ? (
            <LinearProgress />
          ) : (
            <div>
              <div>
                <T variant='h5' gutterBottom>{activity.data.title}</T>
                <T variant='body1' gutterBottom>{activity.data.description}</T>
                <T variant='body2' gutterBottom>Type: {activity.data.type}</T>
                <T variant='body2' gutterBottom>Access: {activity.data.access}</T>
                <T variant='body2' gutterBottom>Start Date: {activity.data.startDate}</T>
                <T variant='body2' gutterBottom>End Date: {activity.data.endDate}</T>
              </div>
            </div>
          )}
        </div>
      </DashboardSection>
    </DashboardContainer>
  )
}

export default ActivityView
