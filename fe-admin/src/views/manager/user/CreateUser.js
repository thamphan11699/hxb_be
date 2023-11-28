import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CFormSelect,
  CImage,
  CInputGroup,
  CRow,
} from '@coreui/react'
import React, { useEffect, useState } from 'react'
import defaultAvatar from '../../../assets/images/avatars/1.jpg'
import { httpGetRequest, httpPostRequest } from 'src/axiosHeper'
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom'

export default function CreateUser() {
  const [listRole, setListRole] = useState([])
  const [item, setItem] = useState({
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    avatar: '',
    roleId: null,
  })
  const [validated, setValidated] = useState(false)

  const navigate = useNavigate()

  useEffect(() => {
    httpGetRequest('/user/create-user')
      .then(({ data }) => {
        setListRole(
          data.roles.map((e) => {
            const role = {}
            role['label'] = e.roleName
            role['value'] = e.roleId
            return role
          }),
        )
        setItem((pre) => {
          return {
            ...pre,
            roleId: data?.roles[0]?.roleId,
          }
        })
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])

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

  const handleChangeRole = (event) => {
    event.persist()
    let value = event.target.value
    setItem((pre) => {
      return {
        ...pre,
        roleId: Number(value),
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
      console.log(item)
      httpPostRequest('/user/create-user', item)
        .then((res) => {
          toast.success('Thêm tài khoản thành công')
          navigate('/management/account')
        })
        .catch((err) => {
          console.log(err)
          toast.warn('Thêm tài khoản không thành công')
        })
    }
  }

  return (
    <CCard className="mb-4">
      <CCardBody>
        <CRow>
          <CCol xs={3}>
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
                <CButton color="light" className="text-center">
                  <CFormLabel htmlFor="file" className="mb-0" style={{ cursor: 'pointer' }}>
                    Thêm hình ảnh
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
          </CCol>
          <CCol xs={9}>
            <CForm
              validated={validated}
              noValidate
              className="needs-validation"
              onSubmit={handleSubmit}
            >
              <CInputGroup className="mb-3 has-validation">
                <CFormLabel htmlFor="email" className="col-sm-2 col-form-label">
                  Email
                </CFormLabel>
                <CCol sm={10}>
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
                <CFormLabel htmlFor="password" className="col-sm-2 col-form-label">
                  Password
                </CFormLabel>
                <CCol sm={10}>
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
                <CFormLabel htmlFor="firstName" className="col-sm-2 col-form-label">
                  Họ
                </CFormLabel>
                <CCol sm={10}>
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
                <CFormLabel htmlFor="lastName" className="col-sm-2 col-form-label">
                  Tên
                </CFormLabel>
                <CCol sm={10}>
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
                <CFormLabel htmlFor="phoneNumber" className="col-sm-2 col-form-label">
                  Số điện thoại
                </CFormLabel>
                <CCol sm={10}>
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
                <CFormLabel htmlFor="role" className="col-sm-2 col-form-label">
                  Quyền
                </CFormLabel>
                <CCol sm={10}>
                  <CFormSelect
                    onChange={handleChangeRole}
                    required
                    name="role"
                    value={item.roleId}
                    aria-label="Default select example"
                    options={listRole}
                  />
                </CCol>
              </CInputGroup>
              <CRow>
                <div style={{ display: 'flex', flexDirection: 'row-reverse' }}>
                  <CButton color="success" className="text-center" type="submit">
                    Lưu
                  </CButton>
                </div>
              </CRow>
            </CForm>
          </CCol>
        </CRow>
      </CCardBody>
    </CCard>
  )
}
