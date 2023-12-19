import {
  CButton,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CInputGroup,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
} from '@coreui/react'
import React, { useState } from 'react'
import PropTypes from 'prop-types'
import { httpPostRequest } from 'src/axiosHeper'
import { toast } from 'react-toastify'
import { useDispatch } from 'react-redux'

function Login({ open, handleCloseModal }) {
  const dispatch = useDispatch()

  const [item, setItem] = useState({
    email: '',
    password: '',
  })

  const [validated, setValidated] = useState(false)
  const handleChange = (event) => {
    event.persist()
    let value = event.target.value
    let name = event.target.name

    setItem((pre) => {
      return {
        ...pre,
        [name]: value,
      }
    })
  }
  const handleSubmit = (event) => {
    event.preventDefault()
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
      setValidated(true)
    } else {
      httpPostRequest('/auth/authentication-customer', item)
        .then(({ data }) => {
          toast.success('Đăng nhập thành công')
          setItem({
            email: '',
            password: '',
          })
          localStorage.setItem('token', data.token)
          dispatch({ type: 'login', isAuthentication: true, user: data.user })
          handleCloseModal()
        })
        .catch((err) => {
          toast.warning(err?.response?.data?.message)
        })
    }
  }

  return (
    <>
      <CModal
        visible={open}
        onClose={() => handleCloseModal()}
        aria-labelledby="LiveDemoExampleLabel"
        alignment="center"
        size="lg"
      >
        <CForm
          validated={validated}
          noValidate
          className="needs-validation"
          onSubmit={handleSubmit}
        >
          <CModalHeader onClose={() => handleCloseModal(false)}>
            <CModalTitle id="LiveDemoExampleLabel">Đăng nhập</CModalTitle>
          </CModalHeader>
          <CModalBody>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="email" className="col-sm-3 col-form-label">
                Email
              </CFormLabel>
              <CCol sm={9}>
                <CFormInput
                  type="email"
                  id="email"
                  name="email"
                  value={item.email}
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="password" className="col-sm-3 col-form-label">
                Mật khẩu
              </CFormLabel>
              <CCol sm={9}>
                <CFormInput
                  type="password"
                  id="password"
                  name="password"
                  value={item.password}
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
          </CModalBody>
          <CModalFooter>
            <CButton color="danger" onClick={() => handleCloseModal()} variant="outline">
              Đóng
            </CButton>
            <CButton color="success" type="submit" variant="outline">
              Đăng nhập
            </CButton>
          </CModalFooter>
        </CForm>
      </CModal>
    </>
  )
}

export default Login

Login.propTypes = {
  open: PropTypes.bool.isRequired,
  handleCloseModal: PropTypes.func.isRequired,
}
