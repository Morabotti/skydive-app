import React, {
  createContext,
  ReactNode,
  useContext,
  useCallback
} from 'react'

import { useSnackbar } from 'notistack'
import { NotificationType } from '@enums'

interface DashboardContext {
  createNotification: (message: string, type?: NotificationType) => void
}

interface Props {
  children: ReactNode
}

export const __DashboardContext = createContext<DashboardContext>({
  createNotification: () => {}
})

export const DashboardProvider = ({ children }: Props) => {
  const { enqueueSnackbar } = useSnackbar()

  const createNotification = useCallback((message: string, type?: NotificationType) => {
    enqueueSnackbar(message, { variant: type || 'default' })
  }, [enqueueSnackbar])

  return (
    <__DashboardContext.Provider
      value={{
        createNotification
      }}
    >
      {children}
    </__DashboardContext.Provider>
  )
}

export const useDashboard = (): DashboardContext => useContext(__DashboardContext)
