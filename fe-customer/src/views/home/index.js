import {
  CCard,
  CCardBody,
  CCardImage,
  CCardTitle,
  CCarousel,
  CCarouselItem,
  CCol,
  CContainer,
  CHeaderDivider,
  CImage,
  CRow,
} from '@coreui/react'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { httpGetRequest } from 'src/axiosHeper'

function Home() {
  const navigate = useNavigate()

  const [services, setServices] = useState([])
  useEffect(() => {
    httpGetRequest('/customer/home')
      .then(({ data }) => {
        // console.log(data)
        setServices(data.services)
      })
      .catch((err) => {
        toast.warning(err?.response?.data?.message)
      })
  }, [])

  return (
    <div>
      <CContainer fluid>
        <CContainer>
          <CCarousel controls indicators>
            <CCarouselItem>
              <CImage
                className="d-block w-100"
                src={'https://mailisa.com/uploads/logo/da-Tiếng việt.jpg'}
                alt="slide 1"
              />
            </CCarouselItem>
            <CCarouselItem>
              <CImage
                className="d-block w-100"
                src={'https://mailisa.com/uploads/logo/69moi-Tiếng việt.jpg'}
                alt="slide 2"
              />
            </CCarouselItem>
            <CCarouselItem>
              <CImage
                className="d-block w-100"
                src={'https://mailisa.com/uploads/logo/96may-Tiếng việt.jpg'}
                alt="slide 3"
              />
            </CCarouselItem>
          </CCarousel>
        </CContainer>
        <div style={{ display: 'flex', justifyContent: 'center', marginTop: 40, marginBottom: 40 }}>
          <h3 className="main_color">Các dịch vụ nổi bật</h3>
        </div>
        <CContainer>
          <CRow xs={{ cols: 1 }} sm={{ cols: 2 }} md={{ cols: 3 }}>
            {services.map((value, index) => (
              <CCol className="mb-10" key={value.id}>
                <CCard onClick={() => navigate('/service/' + value.id)}>
                  <CCardImage className="p-16" orientation="top" src={value.image} />
                  <CCardBody>
                    <CCardTitle className="main_color text-center">{value.name}</CCardTitle>
                  </CCardBody>
                </CCard>
              </CCol>
            ))}
          </CRow>
        </CContainer>
        <CHeaderDivider />
        <CContainer style={{ marginTop: 50, marginBottom: 40 }}>
          <CCard>
            <iframe
              title="test"
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1107.5060639882036!2d105.79918179422202!3d20.984958776757715!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135acc6bdc7f95f%3A0x58ffc66343a45247!2sUniversity%20of%20Transport%20Technology!5e0!3m2!1sen!2s!4v1697027679684!5m2!1sen!2s"
              width="100%"
              height="450"
              style={{ border: 0 }}
              allowFullScreen=""
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
            ></iframe>
          </CCard>
        </CContainer>
      </CContainer>
    </div>
  )
}

export default Home
