import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
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
} from '@coreui/react'
import { httpGetRequest, httpPostRequest } from 'src/axiosHeper'
import { toast } from 'react-toastify'

function BoolingDetailModal({ open, closeModal, id }) {
  const [item, setItem] = useState({
    id: '',
    userType: '',
    customerLastName: '',
    userLastName: '',
    serviceName: '',
    status: '',
    bookingDate: '',
    bookingDateString: '',
    discountName: '',
    cost: '',
    promotionalPrice: '',
    customerFirstName: '',
    userFirstName: '',
    customerPhoneNumber: '',
    userPhoneNumber: '',
  })

  useEffect(() => {
    if (id !== 0) {
      httpGetRequest('/private/booking/' + id)
        .then(({ data }) => {
          setItem(data)
        })
        .catch((err) => {
          toast.warning(err?.response?.data?.message)
        })
    }
  }, [id])

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

  const handleChangeStatus = (event) => {
    event.persist()
    let value = event.target.value

    setItem((pre) => {
      return {
        ...pre,
        status: value,
      }
    })

    httpPostRequest('/private/booking/update-status/' + id, { status: value })
      .then(({ data }) => {
        toast.success('Cập nhật trạng thái thành công.')
      })
      .catch((err) => {
        toast.warning(err?.response?.data?.message)
      })
  }

  const handleCloseModal = () => {
    setItem({
      id: '',
      userType: '',
      customerLastName: '',
      userLastName: '',
      serviceName: '',
      status: '',
      bookingDate: '',
      bookingDateString: '',
      discountName: '',
      cost: '',
      promotionalPrice: '',
      customerFirstName: '',
      userFirstName: '',
      customerPhoneNumber: '',
      userPhoneNumber: '',
    })
    closeModal(false, 0)
  }

  return (
    <CModal
      visible={open}
      onClose={() => handleCloseModal()}
      aria-labelledby="LiveDemoExampleLabel"
      alignment="center"
      size="lg"
    >
      <div>
        <CForm className="needs-validation">
          <CModalHeader onClose={() => closeModal(false, 0)}>
            <CModalTitle id="LiveDemoExampleLabel">Thông tin đặt lịch</CModalTitle>
          </CModalHeader>
          <CModalBody>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="customer_name" className="col-sm-2 col-form-label">
                Tên khách hàng
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="customer_name"
                  name="customer_name"
                  value={
                    item.userType === '1'
                      ? item.userFirstName + ' ' + item.userLastName
                      : item.customerFirstName + ' ' + item.customerLastName
                  }
                  readOnly
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="servicename" className="col-sm-2 col-form-label">
                Tên dịch vụ
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="servicename"
                  name="servicename"
                  value={item.serviceName}
                  readOnly
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="role" className="col-sm-2 col-form-label">
                Trạng thái
              </CFormLabel>
              <CCol sm={10}>
                <CFormSelect
                  onChange={handleChangeStatus}
                  required
                  name="role"
                  value={item.status}
                  aria-label="Default select example"
                  options={[
                    { label: 'Mới tạo', value: '01' },
                    { label: 'Xác nhận', value: '02' },
                    { label: 'Hủy', value: '03' },
                    { label: 'Hoàn tất', value: '04' },
                  ]}
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="date" className="col-sm-2 col-form-label">
                Ngày đặt lịch
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="date"
                  name="date"
                  value={item.bookingDateString}
                  readOnly
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="code" className="col-sm-2 col-form-label">
                Mã khuyến mãi
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="code"
                  name="code"
                  value={item.discountName || ''}
                  readOnly
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>

            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="cost" className="col-sm-2 col-form-label">
                Giá gốc
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="cost"
                  name="cost"
                  value={item.cost.toLocaleString('it-IT', {
                    style: 'currency',
                    currency: 'VND',
                  })}
                  readOnly
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="price" className="col-sm-2 col-form-label">
                Giá khuyến mãi
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="price"
                  name="price"
                  value={item.promotionalPrice.toLocaleString('it-IT', {
                    style: 'currency',
                    currency: 'VND',
                  })}
                  readOnly
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="phone" className="col-sm-2 col-form-label">
                Số điện thoại
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="phone"
                  name="phone"
                  value={item.userType === '1' ? item.userPhoneNumber : item.customerPhoneNumber}
                  readOnly
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
          </CModalBody>
          <CModalFooter>
            <CButton color="danger" onClick={() => handleCloseModal()} variant="outline">
              Đóng
            </CButton>
          </CModalFooter>
        </CForm>
      </div>
    </CModal>
  )
}

export default BoolingDetailModal

BoolingDetailModal.propTypes = {
  open: PropTypes.bool.isRequired,
  closeModal: PropTypes.func.isRequired,
  id: PropTypes.number.isRequired,
}
