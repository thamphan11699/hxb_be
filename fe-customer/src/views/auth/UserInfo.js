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
import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { httpGetRequestWithHeader, httpPostRequest } from 'src/axiosHeper'
import { toast } from 'react-toastify'
import defaultAvatar from '../../assets/images/avatars/1.jpg'

function UserInfo({ open, handleCloseModal, user }) {
  const [item, setItem] = useState({
    id: '',
    email: '',
    password: '',
    passwordConfirm: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    roleIds: [3],
    avatar: '',
  })

  useEffect(() => {
    httpGetRequestWithHeader('/user/get-user-by-email/' + user.email).then(({ data }) => {
      setItem((pre) => {
        return { ...pre, ...data }
      })
    })
  }, [user.email])

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
      httpPostRequest('/user/edit-user/' + item.id, item)
        .then(({ data }) => {
          toast.success('Cập nhật thành công')
          handleCloseModal()
        })
        .catch((err) => {
          console.log(err)
          toast.warn('Cập nhật không thành công')
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
              avatar: data.uri,
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
            <CModalTitle id="LiveDemoExampleLabel">Thông tin người dùng</CModalTitle>
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
                  readOnly
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
                  readOnly
                  value={item.phoneNumber}
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>

            <div className="clearfix">
              <CImage
                align="center"
                rounded
                src={item.avatar ? item.avatar : defaultAvatar}
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
              Cập nhật
            </CButton>
          </CModalFooter>
        </CForm>
      </CModal>
    </>
  )
}

export default UserInfo

UserInfo.propTypes = {
  open: PropTypes.bool.isRequired,
  handleCloseModal: PropTypes.func.isRequired,
  user: PropTypes.object.isRequired,
}
