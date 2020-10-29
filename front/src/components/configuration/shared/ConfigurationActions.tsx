import React, { memo } from 'react'
import { makeStyles, createStyles, IconButton } from '@material-ui/core'
import { Pencil, Close, ContentSave } from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  actions: {
    '& > button:not(:last-child)': {
      marginRight: theme.spacing(1)
    }
  },
  button: {
    padding: theme.spacing(1)
  }
}))

interface Props {
  editing?: boolean,
  loading?: boolean,
  onEdit?: () => void,
  onCancel?: () => void,
  onSave?: () => void
}

export const ConfigurationActions = memo(({
  editing,
  loading,
  onEdit,
  onCancel,
  onSave
}: Props) => {
  const classes = useStyles()

  if (editing) {
    return (
      <div className={classes.actions}>
        <IconButton className={classes.button} onClick={onCancel} disabled={loading}>
          <Close />
        </IconButton>
        <IconButton className={classes.button} onClick={onSave} disabled={loading}>
          <ContentSave />
        </IconButton>
      </div>
    )
  }
  return (
    <IconButton className={classes.button} onClick={onEdit} disabled={loading}>
      <Pencil />
    </IconButton>
  )
})
