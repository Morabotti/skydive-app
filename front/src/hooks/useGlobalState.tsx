import React, { createContext, useContext, useReducer, useMemo } from 'react'
import { Club, Activity } from '@types'

interface State {
  myClubs: null | Club[],
  myActivities: null | Activity[]
}

const actionCreators = {
  setMyClubs: (myClubs: Club[] | null) => ({
    type: 'my-clubs-set' as const,
    myClubs
  }),
  setMyActivities: (myActivities: Activity[] | null) => ({
    type: 'my-activities-set' as const,
    myActivities
  })
}

type ActionCreators = typeof actionCreators
type ActionCreatorParams = Parameters<ActionCreators[keyof ActionCreators]>
type Action = ReturnType<ActionCreators[keyof ActionCreators]>
export type Actions = {
  [K in keyof ActionCreators]: (...params: ActionCreatorParams) => void
}
type Context = State & Actions

const initialState: State = {
  myClubs: null,
  myActivities: null
}

const initialContext: Context = {
  ...initialState,
  ...Object.keys(actionCreators)
    .reduce(
      (actions, action): Actions => ({
        ...actions,
        [action]: () => {}
      }),
      {} as Actions
    )
}

const reducer = (state: State, action: Action) => {
  const payload = { ...action }
  // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
  // @ts-ignore
  delete payload.type

  return { ...state, ...payload as Omit<Action, 'type'> }
}

const StateContext = createContext<Context>(initialContext)

interface Props {
  children: React.ReactNode
}

export const StateContextProvider = ({ children }: Props) => {
  const [state, dispatch] = useReducer(reducer, initialState)

  const actions = useMemo(
    () => (Object.keys(actionCreators) as (keyof ActionCreators)[])
      .reduce((actions: Actions, action) => ({
        ...actions,
        [action] (...args: Omit<Actions, 'type'>[]) {
          dispatch((actionCreators[action] as Function).apply(this, args))
        }
      }), {} as Actions),
    []
  )

  const value = useMemo((): Context => ({
    ...state,
    ...actions
  }), [state, actions])

  return (
    <StateContext.Provider value={value}>
      {children}
    </StateContext.Provider>
  )
}

export const useGlobalState = (): Context => useContext(StateContext)
