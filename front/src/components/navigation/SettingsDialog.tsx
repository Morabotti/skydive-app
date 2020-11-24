import React, { memo } from 'react'
import { Cog } from 'mdi-material-ui'

import {
  Dialog,
  DialogActions,
  Button,
  makeStyles,
  createStyles,
  DialogContent,
  DialogTitle
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  actions: {
    padding: theme.spacing(1, 3, 2)
  },
  title: {
    '& > h2': {
      display: 'flex',
      alignItems: 'center',
      '& > svg': {
        marginRight: theme.spacing(1)
      }
    }
  }
}))

interface Props {
  open: boolean,
  onClose: () => void
}

export const SettingsDialog = memo(({
  open,
  onClose
}: Props) => {
  const classes = useStyles()

  return (
    <Dialog
      open={open}
      onClose={onClose}
      fullWidth
      maxWidth='sm'
      aria-labelledby='settings-dialog-title'
    >
      <DialogTitle
        className={classes.title}
        id='settings-dialog-title'
      ><Cog />Settings</DialogTitle>
      <DialogContent>
        TODO
      </DialogContent>
      <DialogActions className={classes.actions}>
        <Button
          onClick={onClose}
          color='primary'
          disableElevation
          variant='contained'
        >Close</Button>
      </DialogActions>
    </Dialog>
  )
})
