import { CBreadcrumb, CBreadcrumbItem, CCard, CContainer } from '@coreui/react'
import React from 'react'

function Contact() {
  return (
    <CContainer>
      <CCard className="p-20" style={{ marginBottom: 40 }}>
        <CBreadcrumb style={{ '--cui-breadcrumb-divider': "'>'" }}>
          <CBreadcrumbItem href="#">Trang chủ</CBreadcrumbItem>
          <CBreadcrumbItem active>Liên hệ</CBreadcrumbItem>
        </CBreadcrumb>
        <hr />
        <h4 className="main_color">LIÊN HỆ</h4>
        <div style={{ padding: '10px 5px ', marginTop: 30 }}>
          <h4 className="main_color">ĐỊA CHỈ CỬA HÀNG SPA AN THU</h4>
          <h6 className="main_color mt-10">
            Nơi lựa chọn hàng đầu gửi gắm nhan sắc của phụ nữ Việt
          </h6>
          <p className="mb-10">
            ♦Hotline: <span className="main_color">09871232132121</span>
          </p>
          <p className="mb-10">
            ♦Website: <span className="main_color">http://localhost:3000/#/contact</span>
          </p>
          <p className="mb-10">
            ♦E-mail: <span className="main_color">anhtq@test.com </span>
          </p>
          <p className="mb-10">
            ♦FB: <span className="main_color">https://www.facebook.com/anh1bestyasuo20pgg</span>
          </p>
          <p className="mb-10">
            ♦Địa chỉ:{' '}
            <span className="main_color">https://www.facebook.com/anh1bestyasuo20pgg</span>
          </p>
          <p style={{ marginTop: 25 }} className="mb-10">
            Khi thương hiệu An Thu nổi tiếng kéo theo nhiều cơ sở thẩm mỹ luôn nói là chi nhánh của
            An Thu, một số cá nhân thì nói là nhân viên của An Thu. Để bảo vệ quyền lợi và tránh rủi
            ro tiền mất tật mang cho khách hàng khi đi làm đẹp, An Thu công bố thông tin những địa
            chỉ sau đây là chi nhánh chính thức trực thuộc hệ thống Thẩm mỹ viện An Thu.
          </p>
        </div>
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
  )
}

export default Contact
