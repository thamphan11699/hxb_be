import { CButton, CModal, CModalBody, CModalFooter, CModalHeader, CModalTitle } from '@coreui/react'
import React from 'react'
import PropTypes from 'prop-types'

export default function DeleteModal({ open, closeModal, deleteItem, id }) {
  return (
    <CModal
      visible={open}
      onClose={() => closeModal(false, 0)}
      aria-labelledby="LiveDemoExampleLabel"
      alignment="center"
    >
      <CModalHeader onClose={() => closeModal(false, 0)}>
        <CModalTitle id="LiveDemoExampleLabel">Xóa tài khoản</CModalTitle>
      </CModalHeader>
      <CModalBody>
        <p>Bạn muốn xóa tài khoản này?</p>
      </CModalBody>
      <CModalFooter>
        <CButton color="secondary" onClick={() => closeModal(false, 0)}>
          Đóng
        </CButton>
        <CButton color="danger" onClick={() => deleteItem(id)}>
          Xác nhận
        </CButton>
      </CModalFooter>
    </CModal>
  )
}

DeleteModal.propTypes = {
  open: PropTypes.bool.isRequired,
  closeModal: PropTypes.func.isRequired,
  deleteItem: PropTypes.func.isRequired,
  id: PropTypes.number.isRequired,
}
