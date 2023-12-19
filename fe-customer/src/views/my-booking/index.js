import {
  CBadge,
  CBreadcrumb,
  CBreadcrumbItem,
  CButton,
  CCard,
  CCardBody,
  CCardText,
  CCardTitle,
  CCol,
  CContainer,
  CRow,
} from '@coreui/react'
import moment from 'moment-timezone'
import React, { useEffect, useState } from 'react'
import { httpGetRequest, httpPostRequest } from 'src/axiosHeper'
import 'moment/locale/vi'
import { useSelector } from 'react-redux'
import { toast } from 'react-toastify'

moment().tz()

const renderStatus = (status) => {
  switch (status) {
    case '01':
      return <CBadge color="success">Mới đặt</CBadge>
    case '02':
      return <CBadge color="success">Xác nhận</CBadge>
    case '03':
      return <CBadge color="danger">Hủy</CBadge>
    default:
      return <CBadge color="success">Hoàn thành</CBadge>
  }
}

function MyBooking() {
  const [bookings, setMyBookings] = useState([])

  const userId = useSelector((state) => state.user?.id)

  useEffect(() => {
    loadPage(userId)
  }, [userId])

  const loadPage = (id) => {
    if (id) {
      httpGetRequest('/private/booking/user/' + id)
        .then(({ data }) => {
          console.log(data)
          setMyBookings(data)
        })
        .catch((err) => {
          toast.warning(err?.response?.data?.message)
        })
    }
  }

  const onCancelBooking = (id) => {
    httpPostRequest('/private/booking/update-status/' + id, { status: '03' })
      .then(({ data }) => {
        loadPage(userId)
        toast.success('Hủy thành công')
      })
      .catch((err) => {
        toast.warning(err?.response?.data?.message)
      })
  }

  return (
    <CContainer>
      <CCard className="p-20" style={{ marginBottom: 40 }}>
        <CBreadcrumb style={{ '--cui-breadcrumb-divider': "'>'" }}>
          <CBreadcrumbItem href="#/home">Trang chủ</CBreadcrumbItem>
          <CBreadcrumbItem active>Lịch sử đặt lịch</CBreadcrumbItem>
        </CBreadcrumb>
        <hr />
        <h4 className="main_color">LỊCH SỬ ĐẶT LỊCH</h4>
        <CRow xs={{ cols: 1 }} sm={{ cols: 1 }} md={{ cols: 1 }}>
          {bookings.map((value, index) => (
            <CCol key={value.id} style={{ cursor: 'pointer' }}>
              <CCard className="mb-3" style={{ height: '250px' }}>
                <CRow className="g-0" style={{ height: '250px !important' }}>
                  <CCol md={3}>
                    <img
                      alt="test"
                      className="card-img"
                      style={{
                        display: 'block',
                        height: 240,
                        width: 250,
                        marginTop: 5,
                        marginLeft: 5,
                      }}
                      src={value.serviceImg}
                    />
                  </CCol>
                  <CCol md={9} style={{ position: 'relative' }}>
                    <CCardBody style={{ padding: 2 }}>
                      <CCardTitle>{value.name}</CCardTitle>
                      <CCardText>{value.sortDescription}</CCardText>
                      <CCardText>
                        Giá gốc:{' '}
                        {value.cost
                          ? value.cost.toLocaleString('it-IT', {
                              style: 'currency',
                              currency: 'VND',
                            })
                          : 'Liên hệ'}
                      </CCardText>
                      <CCardText>
                        Giá dự kiến:{' '}
                        {value.promotionalPrice
                          ? value.promotionalPrice.toLocaleString('it-IT', {
                              style: 'currency',
                              currency: 'VND',
                            })
                          : 'Liên hệ'}
                      </CCardText>
                      <CCardText>Ngày đặt lịch: {value.bookingDateString}</CCardText>
                      <CCardText>Trạng tháu: {renderStatus(value.status)}</CCardText>
                      <CButton
                        variant="outline"
                        color="danger"
                        style={{ position: 'absolute', right: 20, bottom: 15 }}
                        disabled={'01' !== value.status}
                        onClick={() => onCancelBooking(value.id)}
                      >
                        Hủy
                      </CButton>
                    </CCardBody>
                  </CCol>
                </CRow>
              </CCard>
            </CCol>
          ))}
        </CRow>
      </CCard>
    </CContainer>
  )
}

export default MyBooking
