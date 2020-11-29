import React, { useCallback } from 'react'
import { DashboardContainer, DashboardSection } from '@components/common'
import { UpdateClubForm } from '@components/configuration'
import { useMutation, useQueryCache } from 'react-query'
import { createClub } from '@client'
import { Client } from '@enums'
import { Club, ClubRequest } from '@types'
import { useHistory } from 'react-router-dom'

const CreateClubView = () => {
  const queryCache = useQueryCache()
  const { push } = useHistory()
  const [ mutate ] = useMutation(createClub, {
    onSuccess: (data: Club) => {
      queryCache.invalidateQueries(Client.GET_CLUBS)
      queryCache.setQueryData([Client.GET_CLUB_BY_ID, data.id], data)
      queryCache.setQueryData([Client.GET_CLUB_BY_SLUG, data.slug], data)
    }
  })

  const onSubmit = useCallback(async (set: ClubRequest) => {
    await mutate(set)
    push('/dashboard/configuration')
  }, [mutate, push])

  return (
    <DashboardContainer>
      <DashboardSection title='Create New Club'>
        <UpdateClubForm onSubmit={onSubmit} />
      </DashboardSection>
    </DashboardContainer>
  )
}

export default CreateClubView
