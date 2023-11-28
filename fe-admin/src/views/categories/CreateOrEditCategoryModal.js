import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import {
  CButton,
  CCol,
  CForm,
  CFormCheck,
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

function CreateOrEditCategoryModal({ open, closeModal, id, refreshPage }) {
  const [item, setItem] = useState({ name: '', isHome: false })

  const [validated, setValidated] = useState(false)

  useEffect(() => {
    if (id !== 0) {
      httpGetRequest('/category/edit/' + id)
        .then(({ data }) => {
          console.log(data)
          setItem(data)
        })
        .catch((err) => {
          toast.error('Có lỗi')
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

  const handleSubmit = (event) => {
    event.preventDefault()
    const form = event.currentTarget
    if (form.checkValidity() === false) {
      event.preventDefault()
      event.stopPropagation()
      setValidated(true)
    } else {
      if (id !== 0) {
        httpPostRequest('/category/edit/' + id, item)
          .then((res) => {
            toast.success('Sửa danh mục thành công')
            closeModal(false, 0)
            refreshPage(null)
            setItem({ name: '', isHome: false })
          })
          .catch((err) => {
            console.log(err)
            toast.warn('Sửa danh mục không thành công')
          })
      } else {
        httpPostRequest('/category/create', item)
          .then((res) => {
            toast.success('Thêm danh mục thành công')
            closeModal(false, 0)
            refreshPage(null)
            setItem({ name: '', isHome: false })
          })
          .catch((err) => {
            console.log(err)
            toast.warn('Thêm danh mục không thành công')
          })
      }
    }
  }

  const handleChangeCheckBox = (event) => {
    let value = event.target.checked
    console.log(value)

    setItem((pre) => {
      return {
        ...pre,
        isHome: value,
      }
    })
  }

  const handleCloseModal = () => {
    setItem({ name: '', isHome: false })
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
            <CModalTitle id="LiveDemoExampleLabel">{id ? 'Sửa ' : 'Thêm '} danh mục</CModalTitle>
          </CModalHeader>
          <CModalBody>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="email" className="col-sm-2 col-form-label">
                Tên danh mục
              </CFormLabel>
              <CCol sm={10}>
                <CFormInput
                  type="text"
                  id="text"
                  name="name"
                  value={item.name}
                  onChange={handleChange}
                  required
                />
              </CCol>
            </CInputGroup>
            <CInputGroup className="mb-3 has-validation">
              <CFormLabel htmlFor="email" className="col-sm-2 col-form-label">
                Hiển thị
              </CFormLabel>
              <CCol sm={10}>
                <div style={{ marginTop: 8 }}>
                  <CFormCheck
                    id="flexCheckDefault"
                    label="Có"
                    checked={item.isHome}
                    onChange={handleChangeCheckBox}
                  />
                </div>
              </CCol>
            </CInputGroup>
          </CModalBody>
          <CModalFooter>
            <CButton color="secondary" onClick={() => handleCloseModal()}>
              Đóng
            </CButton>
            <CButton color="primary" type="submit">
              Xác nhận
            </CButton>
          </CModalFooter>
        </CForm>
      </div>
    </CModal>
  )
}

export default CreateOrEditCategoryModal

CreateOrEditCategoryModal.propTypes = {
  open: PropTypes.bool.isRequired,
  closeModal: PropTypes.func.isRequired,
  refreshPage: PropTypes.func.isRequired,
  id: PropTypes.number.isRequired,
}
