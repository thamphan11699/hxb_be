import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import {
  CButton,
  CCard,
  CCardBody,
  CCardGroup,
  CCol,
  CContainer,
  CForm,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
} from '@coreui/react'
import CIcon from '@coreui/icons-react'
import { cilLockLocked, cilUser } from '@coreui/icons'
import { httpPostRequest, logout } from '../../../axiosHeper'
import { useDispatch, useSelector } from 'react-redux'

const Login = () => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const isAuthentication = useSelector((state) => state.isAuthentication)
  const [user, setUser] = useState({ email: '', password: '' })
  const [validated, setValidated] = useState(false)

  const handleChange = (event) => {
    event.persist()
    let value = event.target.value
    let name = event.target.name
    setUser((pre) => {
      return {
        ...pre,
        [name]: value,
      }
    })
  }

  useEffect(() => {
    let token = localStorage.getItem('token')
    console.log(token)
    if (token) {
      localStorage.removeItem('token')
      dispatch({ type: 'logout', isAuthentication: false, user: null })
      logout()
    }
  }, [dispatch])

  const handleSubmit = (event) => {
    event.preventDefault()
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
      setValidated(true)
    } else {
      httpPostRequest('/auth/authentication', user)
        .then(({ data }) => {
          localStorage.setItem('token', data.token)
          dispatch({ type: 'login', isAuthentication: true, user: data.user })
          navigate('/')
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }

  return (
    <div className="bg-light min-vh-100 d-flex flex-row align-items-center">
      <CContainer>
        <CRow className="justify-content-center">
          <CCol md={8}>
            <CCardGroup>
              <CCard className="p-4">
                <CCardBody>
                  <CForm
                    className="needs-validation"
                    noValidate
                    onSubmit={handleSubmit}
                    validated={validated}
                  >
                    <h1>{isAuthentication ? 'Logined' : 'Login'}</h1>
                    <p className="text-medium-emphasis">Sign In to your account</p>
                    <CInputGroup className="mb-3 has-validation">
                      <CInputGroupText>
                        <CIcon icon={cilUser} />
                      </CInputGroupText>
                      <CFormInput
                        placeholder="Username"
                        autoComplete="username"
                        name="email"
                        value={user.email}
                        onChange={handleChange}
                        required
                      />
                    </CInputGroup>
                    <CInputGroup className="mb-4 has-validation">
                      <CInputGroupText>
                        <CIcon icon={cilLockLocked} />
                      </CInputGroupText>
                      <CFormInput
                        type="password"
                        placeholder="Password"
                        autoComplete="current-password"
                        name="password"
                        value={user.password}
                        onChange={handleChange}
                        required
                      />
                    </CInputGroup>
                    <CRow>
                      <CCol xs={6}>
                        <CButton color="primary" className="px-4" type="submit">
                          Login
                        </CButton>
                      </CCol>
                      <CCol xs={6} className="text-right">
                        <CButton color="link" className="px-0">
                          Forgot password?
                        </CButton>
                      </CCol>
                    </CRow>
                  </CForm>
                </CCardBody>
              </CCard>
              <CCard className="text-white bg-primary py-5" style={{ width: '44%' }}>
                <CCardBody className="text-center">
                  <div>
                    <h2>Sign up</h2>
                    <p>
                      Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                      tempor incididunt ut labore et dolore magna aliqua.
                    </p>
                    <Link to="/register">
                      <CButton color="primary" className="mt-3" active tabIndex={-1}>
                        Register Now!
                      </CButton>
                    </Link>
                  </div>
                </CCardBody>
              </CCard>
            </CCardGroup>
          </CCol>
        </CRow>
      </CContainer>
    </div>
  )
}

export default Login
