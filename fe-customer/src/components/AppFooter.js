import React from 'react'
import { CCol, CContainer, CFooter, CRow } from '@coreui/react'

const AppFooter = () => {
  return (
    <CFooter style={{ height: 250, background: '#CC0E74' }}>
      <CContainer>
        <CRow className="align-items-center">
          <CCol style={{ textAlign: 'center', color: 'white' }}>
            <h3>AN THU Website</h3>
            <p>Giấy phép kinh doanh số: 0305 201 737</p>
            <p>Tư vấn (24/7): 028 7106 9999</p>
            <p>Di động: 0932 699 299</p>
            <p>Email: tuvan@mailisa.com</p>
          </CCol>
        </CRow>
      </CContainer>
    </CFooter>
  )
}

export default React.memo(AppFooter)
