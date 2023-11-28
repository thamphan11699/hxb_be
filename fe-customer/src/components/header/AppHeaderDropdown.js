import React, { useState } from 'react'
import {
  CDropdown,
  CDropdownDivider,
  CDropdownHeader,
  CDropdownItem,
  CDropdownMenu,
  CDropdownToggle,
} from '@coreui/react'
import { cilBell, cilLockLocked, cilUser } from '@coreui/icons'
import CIcon from '@coreui/icons-react'

import avatar8 from './../../assets/images/avatars/8.jpg'
import { useDispatch } from 'react-redux'
import PropTypes from 'prop-types'
import UserInfo from 'src/views/auth/UserInfo'
import { useNavigate } from 'react-router-dom'

const AppHeaderDropdown = ({ user }) => {
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const [open, setOpen] = useState(false)
  const logOut = () => {
    localStorage.removeItem('token')
    dispatch({ type: 'logout', isAuthentication: false, user: null })
  }
  const handleCloseModal = () => {
    setOpen(false)
  }

  const handleOpenModal = () => {
    console.log(user)
    setOpen(true)
  }

  return (
    <>
      <UserInfo open={open} handleCloseModal={() => handleCloseModal()} user={user} />
      <CDropdown variant="nav-item">
        <CDropdownToggle placement="bottom-end" className="py-0" caret={false}>
          <img
            width={'40px'}
            height={'40px'}
            src={user?.avatar ? user.avatar : avatar8}
            alt="avatar"
            style={{ borderRadius: '50%' }}
          />
        </CDropdownToggle>
        <CDropdownMenu className="pt-0" placement="bottom-end">
          <CDropdownHeader className="bg-light fw-semibold py-2">Account</CDropdownHeader>
          <CDropdownItem style={{ cursor: 'pointer' }} onClick={() => navigate('/my-booking')}>
            <CIcon icon={cilBell} className="me-2" />
            Lịch đã đặt
          </CDropdownItem>
          <CDropdownHeader className="bg-light fw-semibold py-2">Settings</CDropdownHeader>
          <CDropdownItem style={{ cursor: 'pointer' }}>
            <CIcon icon={cilUser} className="me-2" onClick={() => handleOpenModal()} />
            Thông tin
          </CDropdownItem>
          <CDropdownDivider />
          <CDropdownItem onClick={() => logOut()} style={{ cursor: 'pointer' }}>
            <CIcon icon={cilLockLocked} className="me-2" />
            Đăng xuất
          </CDropdownItem>
        </CDropdownMenu>
      </CDropdown>
    </>
  )
}

export default AppHeaderDropdown
AppHeaderDropdown.propTypes = {
  user: PropTypes.object,
}
