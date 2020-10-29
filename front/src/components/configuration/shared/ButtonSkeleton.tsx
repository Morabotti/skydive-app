import React, { memo } from 'react'
import { Skeleton } from '@material-ui/lab'

interface Props {
  width?: string | number
}

export const ButtonSkeleton = memo(({
  width = '200px'
}: Props) => (
  <Skeleton
    variant='rect'
    animation='wave'
    height={36}
    width={width}
  />
))
