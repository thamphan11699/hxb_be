import { CBreadcrumb, CBreadcrumbItem, CCallout, CCard, CContainer } from '@coreui/react'
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { httpGetRequest } from 'src/axiosHeper'

function SerivceDetail() {
  let { id } = useParams()
  const [service, setService] = useState({
    name: '',
    price: null,
    description: null,
    introduction: '',
  })

  useEffect(() => {
    httpGetRequest('/customer/service/' + id)
      .then(({ data }) => {
        // console.log(data)
        setService(data)
      })
      .catch((err) => {
        console.log(err)
      })
  }, [id])

  // useEffect(() => {
  //   if (service.description) {
  //     let html = document.getElementById('desc')
  //     html.innerHTML = service.description
  //   }
  // }, [service.description])
  return (
    <CContainer>
      <CCard className="p-20" style={{ marginBottom: 40 }}>
        <CBreadcrumb style={{ '--cui-breadcrumb-divider': "'>'" }}>
          <CBreadcrumbItem href="#/home">Trang chủ</CBreadcrumbItem>
          <CBreadcrumbItem href="#/service">Dịch vụ</CBreadcrumbItem>
          <CBreadcrumbItem active>{service.name}</CBreadcrumbItem>
        </CBreadcrumb>
        <hr />
        <h4 className="main_color mb-10">{service.name}</h4>
        <h5 className="main_color mb-10">
          Giá dự kiến:{' '}
          {service.price
            ? service.price.toLocaleString('it-IT', {
                style: 'currency',
                currency: 'VND',
              })
            : 'Liên hệ'}
        </h5>
        <CCallout color="danger">
          <strong>{service.introduction}</strong>
        </CCallout>
        <div id="desc" dangerouslySetInnerHTML={{ __html: service.description }}></div>
      </CCard>
    </CContainer>
  )
}

export default SerivceDetail
