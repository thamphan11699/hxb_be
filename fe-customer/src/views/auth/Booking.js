import {
  CButton,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CFormSelect,
  CInputGroup,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
  CRow,
} from '@coreui/react'
import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import { httpGetRequest, httpPostRequest } from 'src/axiosHeper'
import { toast } from 'react-toastify'
import { useSelector } from 'react-redux'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { LocalizationProvider, StaticDateTimePicker } from '@mui/x-date-pickers'
import moment from 'moment'

function Booking({ open, handleCloseModal }) {
  const user = useSelector((state) => state.user)

  const [services, setServices] = useState([])

  useEffect(() => {
    httpGetRequest('/booking/create-booking')
      .then(({ data }) => {
        setServices(data)
        setItem((pre) => {
          return { ...pre, serviceId: data[0].value, price: data[0].price }
        })
      })
      .catch((err) => {
        toast.warning(err?.response?.data?.message)
      })
  }, [])

  const [item, setItem] = useState({
    userType: user ? '1' : '2',
    userId: user?.id,
    serviceId: '',
    bookingDate: new Date().getTime(),
    email: '',
    firstName: '',
    lastName: '',
    phoneNumber: '',
    discountId: '',
    discountCode: '',
    price: '',
    discountPrice: 0,
    message: '',
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
      httpPostRequest('/booking/create-booking', item)
        .then(({ data }) => {
          toast.success('Đặt lịch thành công')
          setItem({
            userType: user ? '1' : '2',
            userId: user?.id,
            serviceId: '',
            bookingDate: new Date().getTime(),
            email: '',
            firstName: '',
            lastName: '',
            phoneNumber: '',
            discountId: '',
            discountCode: '',
            price: '',
            discountPrice: 0,
            message: '',
          })
          handleCloseModal()
        })
        .catch((err) => {
          toast.warning(err?.response?.data?.message)
        })
    }
  }

  const handleCheckDiscount = () => {
    httpGetRequest('/customer/check-discount', {
      code: item.discountCode,
      totalPrice: item.price,
    })
      .then(({ data }) => {
        setItem((pre) => {
          return {
            ...pre,
            message: '',
            discountId: data.id,
            discountPrice: data.price,
          }
        })
      })
      .catch((err) => {
        setItem((pre) => {
          return {
            ...pre,
            message: err.response.data.message,
          }
        })
      })
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
            <CModalTitle id="LiveDemoExampleLabel">Đặt lịch dịch vụ</CModalTitle>
          </CModalHeader>
          <CModalBody>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="service" className="col-sm-12 col-form-label">
                Chọn dịch vụ
              </CFormLabel>
              <CCol sm={12}>
                <CFormSelect
                  onChange={(e) =>
                    setItem((pre) => {
                      return {
                        ...pre,
                        serviceId: Number(e.target.value),
                        price: services.filter((v, i) => {
                          return v.value === Number(e.target.value)
                        })[0].price,
                      }
                    })
                  }
                  required
                  name="service"
                  value={item.serviceId}
                  aria-label="Default select example"
                  options={services}
                />
              </CCol>
            </CInputGroup>
            {!user && (
              <>
                <CInputGroup className="mb-3 has-validation">
                  <CFormLabel htmlFor="firstName" className="col-sm-12 col-form-label">
                    Họ
                  </CFormLabel>
                  <CCol sm={12}>
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
                  <CFormLabel htmlFor="lastName" className="col-sm-12 col-form-label">
                    Tên
                  </CFormLabel>
                  <CCol sm={12}>
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
                  <CFormLabel htmlFor="email" className="col-sm-12 col-form-label">
                    Email
                  </CFormLabel>
                  <CCol sm={12}>
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
                  <CFormLabel htmlFor="phoneNumber" className="col-sm-12 col-form-label">
                    Số điện thoại
                  </CFormLabel>
                  <CCol sm={12}>
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
              </>
            )}
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <StaticDateTimePicker
                orientation="portrait"
                minDate={moment.now()}
                openTo="day"
                value={item.bookingDate}
                onChange={(newValue) => {
                  setItem((pre) => {
                    return { ...pre, bookingDate: newValue.getTime() }
                  })
                }}
              />
            </LocalizationProvider>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="discountCode" className="col-sm-12 col-form-label">
                Mã khuyến mãi
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="discountCode"
                  name="discountCode"
                  value={item.discountCode}
                  onChange={handleChange}
                />
              </CCol>
              <CCol sm={2} style={{ display: 'flex', justifyContent: 'center' }}>
                <CButton
                  color="success"
                  variant="outline"
                  onClick={handleCheckDiscount}
                  disabled={!item.discountCode}
                >
                  Kiểm tra
                </CButton>
              </CCol>
              {item.message && <span>{item.message}</span>}
            </CInputGroup>
            <hr />
            <CRow>
              <CCol sm={8}>
                <h5>Giá: </h5>
              </CCol>
              <CCol sm={4} style={{ display: 'flex', justifyContent: 'end' }}>
                <h5>
                  {item.price
                    ? item.price.toLocaleString('it-IT', {
                        style: 'currency',
                        currency: 'VND',
                      })
                    : 'Liên hệ'}
                </h5>
              </CCol>
            </CRow>
            <CRow>
              <CCol sm={8}>
                <h5>Khuyến mãi: </h5>
              </CCol>
              <CCol sm={4} style={{ display: 'flex', justifyContent: 'end' }}>
                <h5>
                  {item.discountPrice
                    ? item.discountPrice.toLocaleString('it-IT', {
                        style: 'currency',
                        currency: 'VND',
                      })
                    : '0 VND'}
                </h5>
              </CCol>
            </CRow>
            <CRow>
              <CCol sm={8}>
                <h5>Thành tiền: </h5>
              </CCol>
              <CCol sm={4} style={{ display: 'flex', justifyContent: 'end' }}>
                <h5>
                  {item.price
                    ? (item.price - item.discountPrice).toLocaleString('it-IT', {
                        style: 'currency',
                        currency: 'VND',
                      })
                    : 'Liên hệ'}
                </h5>
              </CCol>
            </CRow>
            <hr />
          </CModalBody>
          <CModalFooter>
            <CButton color="danger" onClick={() => handleCloseModal()} variant="outline">
              Đóng
            </CButton>
            <CButton color="success" type="submit" variant="outline">
              Đặt lịch
            </CButton>
          </CModalFooter>
        </CForm>
      </CModal>
    </>
  )
}

export default Booking

Booking.propTypes = {
  open: PropTypes.bool.isRequired,
  handleCloseModal: PropTypes.func.isRequired,
}
