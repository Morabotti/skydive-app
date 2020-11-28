import React, { useCallback } from 'react'

import {
  FormControl,
  InputLabel,
  MenuItem,
  Select
} from '@material-ui/core'

interface Props {
  value: null | boolean,
  setValue: (set: null | boolean) => void,
  label: string,
  className?: string,
  nullLabel?: string,
  falseLabel?: string,
  trueLabel?: string
}

export const BooleanFilterSelect = ({
  value,
  setValue,
  label,
  className,
  nullLabel,
  falseLabel,
  trueLabel
}: Props) => {
  const onChange = useCallback((event: React.ChangeEvent<{ value: unknown }>) => {
    const value = event.target.value
    setValue(value === 'null' ? null : value === 'true')
  }, [setValue])

  const modifiedValue = value === null ? 'null' : value ? 'true' : 'false'

  return (
    <FormControl fullWidth className={className}>
      <InputLabel>{label}</InputLabel>
      <Select
        value={modifiedValue}
        onChange={onChange}
        fullWidth
      >
        <MenuItem value='null'>{nullLabel || 'Unset'}</MenuItem>
        <MenuItem value='false'>{falseLabel || 'false'}</MenuItem>
        <MenuItem value='true'>{trueLabel || 'true'}</MenuItem>
      </Select>
    </FormControl>
  )
}
