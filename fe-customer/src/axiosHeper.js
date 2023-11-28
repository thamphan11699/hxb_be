import axios from 'axios'

axios.defaults.baseURL = 'http://localhost:9999/api/v1'

export const httpGetRequest = (url, data, headers) => {
  return axios.get(url, { params: data, headers: headers })
}

export const httpPostRequest = (url, data) => {
  axios.defaults.headers.common['Authorization'] = localStorage.getItem('token')
    ? 'Bearer ' + localStorage.getItem('token')
    : ''
  return axios.post(url, data)
}

export const httpGetRequestWithHeader = (url, data) => {
  axios.defaults.headers.common['Authorization'] = localStorage.getItem('token')
    ? 'Bearer ' + localStorage.getItem('token')
    : ''
  return axios.get(url, { params: data })
}

export const logout = () => {
  delete axios.defaults.headers.common['Authorization']
}
