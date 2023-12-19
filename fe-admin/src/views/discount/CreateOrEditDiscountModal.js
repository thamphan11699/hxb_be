import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import {
  CButton,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CInputGroup,
  CModal,
  CModalBody,
  CModalFooter,
  CModalHeader,
  CModalTitle,
} from '@coreui/react'
import { httpGetRequest, httpPostRequest } from 'src/axiosHeper'
import { toast } from 'react-toastify'
import moment from 'moment/moment'

function CreateOrEditDiscountModal({ open, closeModal, id, refreshPage }) {
  const [item, setItem] = useState({
    id: '',
    code: '',
    percent: '',
    expiredAtString: moment(Date.now()).format('yyyy/MM/DD'),
    minimumOrder: '',
    maximumMoney: '',
    expiredAt: '',
  })

  const [validated, setValidated] = useState(false)

  useEffect(() => {
    if (id !== 0) {
      httpGetRequest('/discount/' + id)
        .then(({ data }) => {
          console.log(data)
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

  console.log(item)

  const handleChangeDate = (event) => {
    event.persist()
    let value = event.target.value

    setItem((pre) => {
      return {
        ...pre,
        expiredAtString: value,
        expiredAt: Date.parse(value),
      }
    })

    console.log(item)
  }

  const handleSubmit = (event) => {
    const submit = {
      id: item.id,
      code: item.code,
      percent: item.percent,
      expiredAtString: item.expiredAtString,
      minimumOrder: item.minimumOrder,
      maximumMoney: item.maximumMoney,
      expiredAt: Date.parse(item.expiredAtString),
    }

    event.preventDefault()
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
      setValidated(true)
    } else {
      if (id !== 0) {
        httpPostRequest('/discount/update/' + id, submit)
          .then((res) => {
            toast.success('Sửa khuyến mãi thành công')
            closeModal(false, 0)
            refreshPage(null)
            setItem({
              id: '',
              code: '',
              percent: '',
              expiredAtString: moment(Date.now()).format('yyyy/MM/DD'),
              minimumOrder: '',
              maximumMoney: '',
              expiredAt: '',
            })
          })
          .catch((err) => {
            toast.warning(err?.response?.data?.message)
          })
      } else {
        httpPostRequest('/discount/create', submit)
          .then((res) => {
            toast.success('Thêm khuyến mãi thành công')
            closeModal(false, 0)
            refreshPage(null)
            setItem({
              id: '',
              code: '',
              percent: '',
              expiredAtString: moment(Date.now()).format('yyyy/MM/DD'),
              minimumOrder: '',
              maximumMoney: '',
              expiredAt: '',
            })
          })
          .catch((err) => {
            toast.warning(err?.response?.data?.message)
          })
      }
    }
  }

  const handleCloseModal = () => {
    setItem({
      id: '',
      code: '',
      percent: '',
      expiredAtString: moment(Date.now()).format('yyyy/MM/DD'),
      minimumOrder: '',
      maximumMoney: '',
      expiredAt: '',
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
        <CForm
          validated={validated}
          noValidate
          className="needs-validation"
          onSubmit={handleSubmit}
        >
          <CModalHeader onClose={() => closeModal(false, 0)}>
            <CModalTitle id="LiveDemoExampleLabel">{id ? 'Sửa ' : 'Thêm '} khuyến mãi</CModalTitle>
          </CModalHeader>
          <CModalBody>
            {id !== 0 && (
              <>
                <CInputGroup className="mb-3 has-validation">
                  <CFormLabel htmlFor="code" className="col-sm-2 col-form-label">
                    Mã khuyến mãi
                  </CFormLabel>
                  <CCol sm={10}>
                    <CFormInput
                      type="text"
                      id="code"
                      name="code"
                      value={item.code}
                      readOnly
                      onChange={handleChange}
                      required
                    />
                  </CCol>
                </CInputGroup>
              </>
            )}

            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="percent" className="col-sm-2 col-form-label">
                Phần trăm
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="number"
                  id="percent"
                  name="percent"
                  value={item.percent}
                  onChange={handleChange}
                  max={100}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="expiredAtString" className="col-sm-2 col-form-label">
                Ngày hết hạn
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="date"
                  id="expiredAtString"
                  name="expiredAtString"
                  value={item.expiredAtString}
                  onChange={handleChangeDate}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="minimumOrder" className="col-sm-2 col-form-label">
                Đơn hàng tối thiểu
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="number"
                  id="minimumOrder"
                  name="minimumOrder"
                  value={item.minimumOrder}
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="maximumMoney" className="col-sm-2 col-form-label">
                Số tiền tối đa
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="number"
                  id="maximumMoney"
                  name="maximumMoney"
                  value={item.maximumMoney}
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
            <CButton color="success" type="submit" variant="outline">
              Xác nhận
            </CButton>
          </CModalFooter>
        </CForm>
      </div>
    </CModal>
  )
}

export default CreateOrEditDiscountModal

CreateOrEditDiscountModal.propTypes = {
  open: PropTypes.bool.isRequired,
  closeModal: PropTypes.func.isRequired,
  refreshPage: PropTypes.func.isRequired,
  id: PropTypes.number.isRequired,
}
