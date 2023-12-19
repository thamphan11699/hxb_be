import {
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
import { useNavigate } from 'react-router-dom'
import { httpGetRequest } from 'src/axiosHeper'
import 'moment/locale/vi'
import { toast } from 'react-toastify'

moment().tz()

function Category() {
  const navigate = useNavigate()
  const [services, setServices] = useState([])
  const [count, setCount] = useState(0)
  useEffect(() => {
    httpGetRequest('/customer/service')
      .then(({ data }) => {
        // console.log(data)
        setServices(data.services)
        setCount(data.count)
      })
      .catch((err) => {
        toast.warning(err?.response?.data?.message)
      })
  }, [])

  const getMoreService = () => {
    httpGetRequest('/customer/service', { start: services.length })
      .then(({ data }) => {
        // console.log(data)
        setServices((pre) => {
          return [...pre, ...data.services]
        })
        setCount(data.count)
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
          <CBreadcrumbItem active>Dịch vụ</CBreadcrumbItem>
        </CBreadcrumb>
        <hr />
        <h4 className="main_color">DỊCH VỤ</h4>
        <CRow xs={{ cols: 1 }} sm={{ cols: 1 }} md={{ cols: 1 }}>
          {services.map((value, index) => (
            <CCol key={value.id} style={{ cursor: 'pointer' }}>
              <CCard
                className="mb-3"
                style={{ height: '250px' }}
                onClick={() => {
                  navigate('/service/' + value.id)
                }}
              >
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
                      src={value.image}
                    />
                  </CCol>
                  <CCol md={9}>
                    <CCardBody>
                      <CCardTitle>{value.name}</CCardTitle>
                      <CCardText>{value.sortDescription}</CCardText>
                      <CCardText>
                        Giá dự kiến:{' '}
                        {value.price
                          ? value.price.toLocaleString('it-IT', {
                              style: 'currency',
                              currency: 'VND',
                            })
                          : 'Liên hệ'}
                      </CCardText>
                      <CCardText>
                        <small className="text-medium-emphasis">
                          Cập nhật gần nhất:{' '}
                          {moment(value.lastUpdated)
                            .locale('vi')
                            .local()
                            .startOf('minute')
                            .fromNow()}
                        </small>
                      </CCardText>
                    </CCardBody>
                  </CCol>
                </CRow>
              </CCard>
            </CCol>
          ))}
        </CRow>
      </CCard>
      <div style={{ display: 'flex', justifyContent: 'center', marginTop: 40, marginBottom: 40 }}>
        {services.length < count && (
          <CButton
            color="danger"
            variant="outline"
            className="main_color"
            onClick={() => getMoreService()}
          >
            Xem thêm
          </CButton>
        )}
      </div>
    </CContainer>
  )
}

export default Category
