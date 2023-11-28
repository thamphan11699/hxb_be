import React, { useEffect, useState } from 'react'
import { NavLink } from 'react-router-dom'
import {
  CContainer,
  CHeader,
  CHeaderDivider,
  CHeaderNav,
  CNavLink,
  CNavItem,
  CButton,
} from '@coreui/react'
import Register from 'src/views/auth/Register'
import { useDispatch, useSelector } from 'react-redux'
import { AppHeaderDropdown } from './header'
import Login from 'src/views/auth/Login'
import { httpGetRequest } from 'src/axiosHeper'
import Booking from 'src/views/auth/Booking'
// import { AppHeaderDropdown } from './header/index'

const AppHeader = () => {
  const isAuthentication = useSelector((state) => state.isAuthentication)
  const user = useSelector((state) => state.user)
  const [open, setOpen] = useState(false)
  const [openLogin, setOpenLogin] = useState(false)
  const [openBooking, setOpenBooking] = useState(false)
  const dispatch = useDispatch()

  const handleCloseModal = () => {
    setOpen(false)
    setOpenLogin(false)
    setOpenBooking(false)
  }

  const handleOpenModal = () => {
    setOpen(true)
  }

  useEffect(() => {
    let token = localStorage.getItem('token')
    console.log(token)
    if (token) {
      httpGetRequest('/user/current-user', null, { Authorization: 'Bearer ' + token })
        .then(({ data }) => {
          dispatch({ type: 'get-current-user', isAuthentication: true, user: data.user })
        })
        .catch((err) => {
          dispatch({ type: 'logout', isAuthentication: false, user: null })
          localStorage.removeItem('token')
        })
    } else {
      dispatch({ type: 'logout', isAuthentication: false, user: null })
    }
  }, [dispatch])

  return (
    <>
      {openBooking && <Booking open={openBooking} handleCloseModal={handleCloseModal} />}
      {open && <Register open={open} handleCloseModal={handleCloseModal} />}
      {openLogin && <Login open={openLogin} handleCloseModal={handleCloseModal} />}
      <CHeader position="sticky" className="mb-4">
        <CContainer>
          <CHeaderNav className="d-none d-md-flex me-auto">
            <CNavItem style={{}} className="test">
              <CNavLink to="/home" component={NavLink}>
                <h5 style={{ margin: 0 }} className="main_color">
                  AN THU Website
                </h5>
              </CNavLink>
            </CNavItem>
            <CNavItem>
              <CNavLink to="/home" component={NavLink}>
                <p className="main_color">Trang chủ</p>
              </CNavLink>
            </CNavItem>
            <CNavItem>
              <CNavLink to="/service" component={NavLink}>
                <p className="main_color">Dịch vụ</p>
              </CNavLink>
            </CNavItem>
            <CNavItem>
              <CNavLink to="/contact" component={NavLink}>
                <p className="main_color">Liên hệ</p>
              </CNavLink>
            </CNavItem>
          </CHeaderNav>
          <CHeaderNav>
            <CNavItem className="test1">
              <CButton
                color="danger"
                variant="outline"
                className="main_color"
                onClick={() => setOpenBooking(true)}
              >
                Đặt lịch
              </CButton>
            </CNavItem>
            {!isAuthentication && (
              <>
                <CNavItem className="test1">
                  <CButton
                    color="danger"
                    variant="outline"
                    className="main_color"
                    onClick={() => handleOpenModal()}
                  >
                    Đăng ký
                  </CButton>
                </CNavItem>
                <CNavItem>
                  <CButton
                    color="danger"
                    variant="outline"
                    className="main_color"
                    onClick={() => {
                      setOpenLogin(true)
                    }}
                  >
                    Đăng nhập
                  </CButton>
                </CNavItem>
              </>
            )}
          </CHeaderNav>
          {isAuthentication && (
            <>
              <CHeaderNav className="ms-3">
                <AppHeaderDropdown user={user} />
              </CHeaderNav>
            </>
          )}
        </CContainer>
        <CHeaderDivider />
      </CHeader>
    </>
  )
}

export default AppHeader
