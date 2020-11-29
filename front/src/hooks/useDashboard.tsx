import React, {
  createContext,
  ReactNode,
  useContext,
  useCallback,
  useState
} from 'react'

import { useSnackbar } from 'notistack'
import { NotificationType } from '@enums'

interface DashboardContext {
  loading: boolean,
  setLoading: (set: boolean) => void,
  createNotification: (message: string, type?: NotificationType) => void
}

interface Props {
  children: ReactNode
}

export const __DashboardContext = createContext<DashboardContext>({
  loading: false,
  createNotification: () => {},
  setLoading: () => {}
})

export const DashboardProvider = ({ children }: Props) => {
  const [loading, setLoading] = useState(false)
  const { enqueueSnackbar } = useSnackbar()

  const createNotification = useCallback((message: string, type?: NotificationType) => {
    enqueueSnackbar(message, { variant: type || 'default' })
  }, [enqueueSnackbar])

  return (
    <__DashboardContext.Provider
      value={{
        createNotification,
        loading,
        setLoading
      }}
    >
      {children}
    </__DashboardContext.Provider>
  )
}

export const useDashboard = (): DashboardContext => useContext(__DashboardContext)
