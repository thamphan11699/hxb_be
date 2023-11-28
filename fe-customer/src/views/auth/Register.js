import {
  CButton,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CImage,
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
import defaultAvatar from '../../assets/images/avatars/1.jpg'
import { useDispatch } from 'react-redux'

function Register({ open, handleCloseModal }) {
  const dispatch = useDispatch()

  const [item, setItem] = useState({
    email: '',
    password: '',
    passwordConfirm: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    roleIds: [3],
    image: '',
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
      if (item.password !== item.passwordConfirm) {
        toast.warning('Mật khẩu không trùng khớp')
        return
      }
      httpPostRequest('/auth/register', item)
        .then(({ data }) => {
          toast.success('Đăng ký thành công')
          setItem({
            email: '',
            password: '',
            passwordConfirm: '',
            firstName: '',
            lastName: '',
            phoneNumber: '',
            roleIds: [3],
            image: '',
          })
          localStorage.setItem('token', data.token)
          dispatch({ type: 'register', isAuthentication: true, user: data.user })
          handleCloseModal()
        })
        .catch((err) => {
          console.log(err)
          toast.warn('Đăng ký không thành công')
        })
    }
  }

  const handleUploadImage = (e) => {
    let file = e.target.files[0]
    if (file) {
      let formData = new FormData()
      formData.append('file', e.target.files[0])
      httpPostRequest('/image/upload', formData)
        .then(({ data }) => {
          console.log(data)
          setItem((pre) => {
            return {
              ...pre,
              image: data.uri,
            }
          })
        })
        .catch((err) => {
          console.log(err)
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
            <CModalTitle id="LiveDemoExampleLabel">Đăng ký</CModalTitle>
          </CModalHeader>
          <CModalBody>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="firstName" className="col-sm-3 col-form-label">
                Họ
              </CFormLabel>
              <CCol sm={9}>
                <CFormInput
                  type="text"
                  id="firstName"
                  name="firstName"
                  value={item.firstName}
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="lastName" className="col-sm-3 col-form-label">
                Tên
              </CFormLabel>
              <CCol sm={9}>
                <CFormInput
                  type="text"
                  id="lastName"
                  name="lastName"
                  value={item.lastName}
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
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
              <CFormLabel htmlFor="phoneNumber" className="col-sm-3 col-form-label">
                Số điện thoại
              </CFormLabel>
              <CCol sm={9}>
                <CFormInput
                  type="text"
                  id="phoneNumber"
                  name="phoneNumber"
                  value={item.phoneNumber}
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
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="passwordConfirm" className="col-sm-3 col-form-label">
                Xác nhận mật khẩu
              </CFormLabel>
              <CCol sm={9}>
                <CFormInput
                  type="password"
                  id="passwordConfirm"
                  name="passwordConfirm"
                  value={item.passwordConfirm}
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <div className="clearfix">
              <CImage
                align="center"
                rounded
                src={item.image ? item.image : defaultAvatar}
                width={200}
                height={200}
                className="mb-3"
              />
              <div style={{ display: 'flex', justifyContent: 'center' }}>
                <CButton color="info" className="text-center" variant="outline">
                  <CFormLabel htmlFor="file" className="mb-0" style={{ cursor: 'pointer' }}>
                    Tải hình ảnh
                  </CFormLabel>
                </CButton>
                <CFormInput
                  type="file"
                  id="file"
                  name="file"
                  hidden
                  onChange={handleUploadImage}
                  accept="image/*"
                />
              </div>
            </div>
          </CModalBody>
          <CModalFooter>
            <CButton color="danger" onClick={() => handleCloseModal()} variant="outline">
              Đóng
            </CButton>
            <CButton color="success" type="submit" variant="outline">
              Đăng ký
            </CButton>
          </CModalFooter>
        </CForm>
      </CModal>
    </>
  )
}

export default Register

Register.propTypes = {
  open: PropTypes.bool.isRequired,
  handleCloseModal: PropTypes.func.isRequired,
}
