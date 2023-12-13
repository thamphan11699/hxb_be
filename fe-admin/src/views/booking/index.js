import CIcon from '@coreui/icons-react'
import {
  CBadge,
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CFormInput,
  CInputGroup,
  CInputGroupText,
  CRow,
  CTabPane,
  CTable,
  CTableBody,
  CTableDataCell,
  CTableHead,
  CTableHeaderCell,
  CTableRow,
} from '@coreui/react'
import React, { useEffect, useState } from 'react'
import { httpGetRequestNoHeader } from 'src/axiosHeper'
import { cilPen, cilSearch } from '@coreui/icons'
import ReactPaginate from 'react-paginate'
import BoolingDetailModal from './BoolingDetailModal'

function Booking() {
  const [items, setItems] = useState([])

  const [id, setId] = useState(0)

  const [totalElement, setTotalElement] = useState(0)

  const [openCreateOrUpdate, setOpenCreateOrUpdate] = useState(false)

  const [searchWord, setSearchWord] = useState('')

  const [search, setSearch] = useState({
    pageSize: 10,
    pageIndex: 1,
    searchWord: '',
  })

  useEffect(() => {
    loadPage(search)
  }, [search])

  const handlePageChange = (data) => {
    console.log(data.selected)
    setSearch((pre) => {
      return {
        ...pre,
        pageIndex: Number(data.selected) + 1,
      }
    })
  }

  const loadPage = (search) => {
    httpGetRequestNoHeader('/private/booking', search)
      .then(({ data }) => {
        setTotalElement(data.totalElements)
        setItems(data.items)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  const handleChangeSearchText = (event) => {
    event.persist()
    let value = event.target.value
    setSearchWord(value)
  }

  const handleSearch = (event) => {
    event.persist()
    setSearch({
      pageSize: 10,
      pageIndex: 1,
      searchWord: searchWord,
    })
  }

  const handleOpenModal = (open, id) => {
    setOpenCreateOrUpdate(open)
    setId(0)
    if (open) {
      setId(id)
    } else {
      loadPage(search)
    }
  }

  const renderStatus = (status) => {
    switch (status) {
      case '01':
        return <CBadge color="success">Mới đặt</CBadge>
      case '02':
        return <CBadge color="success">Xác nhận</CBadge>
      case '03':
        return <CBadge color="danger">Hủy</CBadge>
      default:
        return <CBadge color="success">Hoàn thành</CBadge>
    }
  }

  return (
    <>
      {openCreateOrUpdate && (
        <BoolingDetailModal open={openCreateOrUpdate} closeModal={handleOpenModal} id={id} />
      )}

      <CRow>
        <CCol xs={12}>
          <CCard className="mb-4">
            <CCardHeader style={{ display: 'flex', flexDirection: 'row-reverse' }}>
              <CInputGroup className="" style={{ width: 400, marginRight: 20 }}>
                <CInputGroupText>
                  <CIcon icon={cilSearch} style={{ cursor: 'pointer' }} onClick={handleSearch} />
                </CInputGroupText>
                <CFormInput
                  placeholder="Tìm kiếm..."
                  autoComplete="search"
                  name="search"
                  onChange={handleChangeSearchText}
                />
              </CInputGroup>
            </CCardHeader>
            <CCardBody>
              <CCard>
                <CTabPane className="p-2" visible>
                  <CTable color="dark" striped>
                    <CTableHead>
                      <CTableRow>
                        <CTableHeaderCell scope="col">ID</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Tên khách hàng</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Tên dịch vụ</CTableHeaderCell>
                        <CTableHeaderCell scope="col" className="text-center">
                          Trạng thái
                        </CTableHeaderCell>
                        <CTableHeaderCell scope="col">Ngày đặt lịch</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Mã khuyến mãi</CTableHeaderCell>
                        <CTableHeaderCell scope="col" className="text-center">
                          Giá gốc
                        </CTableHeaderCell>
                        <CTableHeaderCell scope="col" className="text-center">
                          Giá khuyến mãi
                        </CTableHeaderCell>
                        <CTableHeaderCell scope="col" className="text-center">
                          Số điện thoại
                        </CTableHeaderCell>
                        <CTableHeaderCell scope="col" className="text-center">
                          Chức năng
                        </CTableHeaderCell>
                      </CTableRow>
                    </CTableHead>
                    <CTableBody>
                      {items.map((value, index) => (
                        <CTableRow key={value.id}>
                          <CTableHeaderCell scope="row">{value.id}</CTableHeaderCell>
                          <CTableDataCell>
                            {value.userType === '1'
                              ? value.userFirstName + ' ' + value.userLastName
                              : value.customerFirstName + ' ' + value.customerLastName}
                          </CTableDataCell>
                          <CTableDataCell className="text">{value.serviceName}</CTableDataCell>
                          <CTableDataCell className="text-center">
                            {renderStatus(value.status)}
                          </CTableDataCell>
                          <CTableDataCell>{value.bookingDateString}</CTableDataCell>
                          <CTableDataCell>{value.discountName}</CTableDataCell>
                          <CTableDataCell className="text-center">
                            {value.cost.toLocaleString('it-IT', {
                              style: 'currency',
                              currency: 'VND',
                            })}
                          </CTableDataCell>
                          <CTableDataCell className="text-center">
                            {value.promotionalPrice.toLocaleString('it-IT', {
                              style: 'currency',
                              currency: 'VND',
                            })}
                          </CTableDataCell>
                          <CTableDataCell className="text-center">
                            {value.userType === '1'
                              ? value.userPhoneNumber
                              : value.customerPhoneNumber}
                          </CTableDataCell>
                          <CTableDataCell colSpan={2} className="text-center">
                            <CButton
                              color="info"
                              variant="outline"
                              style={{ marginRight: 10 }}
                              onClick={() => {
                                handleOpenModal(true, value.id)
                              }}
                            >
                              <CIcon icon={cilPen} />
                            </CButton>
                          </CTableDataCell>
                        </CTableRow>
                      ))}
                    </CTableBody>
                  </CTable>
                  <div style={{ display: 'flex', flexDirection: 'row-reverse' }}>
                    <ReactPaginate
                      previousLabel="<"
                      nextLabel=">"
                      breakLabel="..."
                      breakClassName="page-item"
                      breakLinkClassName="page-link"
                      pageCount={Math.floor((totalElement - 1) / search.pageSize) + 1}
                      pageRangeDisplayed={4}
                      marginPagesDisplayed={2}
                      onPageChange={handlePageChange}
                      containerClassName="pagination justify-content-center"
                      pageClassName="page-item"
                      pageLinkClassName="page-link"
                      previousClassName="page-item"
                      previousLinkClassName="page-link"
                      nextClassName="page-item"
                      nextLinkClassName="page-link"
                      activeClassName="active"
                      hrefAllControls
                      forcePage={search.pageIndex - 2}
                      onClick={(clickEvent) => {
                        console.log('onClick', clickEvent)
                      }}
                    />
                  </div>
                </CTabPane>
              </CCard>
            </CCardBody>
          </CCard>
        </CCol>
      </CRow>
    </>
  )
}

export default Booking
