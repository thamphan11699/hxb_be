import { createStore } from 'redux'

const initialState = {
  sidebarShow: true,
  isAuthentication: false,
  user: null,
}

const changeState = (state = initialState, { type, ...rest }) => {
  console.log('Type', type)
  console.log('Rest', rest)
  switch (type) {
    case 'set':
      return { ...state, ...rest }
    case 'login':
      return { ...state, ...rest }
    case 'logout':
      return { ...state, ...rest }
    case 'get-current-user':
      return { ...state, ...rest }
    default:
      return state
  }
}

const store = createStore(changeState)
export default store
