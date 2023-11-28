import CIcon from '@coreui/icons-react'
import {
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
import { cilSearch } from '@coreui/icons'
import ReactPaginate from 'react-paginate'

function Customer() {
  const [items, setItems] = useState([])

  const [totalElement, setTotalElement] = useState(0)

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
    httpGetRequestNoHeader('/private-customer', search)
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

  return (
    <>
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
                        <CTableHeaderCell scope="col">Họ</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Tên</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Email</CTableHeaderCell>
                        <CTableHeaderCell scope="col" className="text-center">
                          Số điện thoại
                        </CTableHeaderCell>
                      </CTableRow>
                    </CTableHead>
                    <CTableBody>
                      {items.map((value, index) => (
                        <CTableRow key={value.id}>
                          <CTableHeaderCell scope="row">{value.id}</CTableHeaderCell>
                          <CTableDataCell>{value.firstName}</CTableDataCell>
                          <CTableDataCell>{value.lastName}</CTableDataCell>
                          <CTableDataCell>{value.email}</CTableDataCell>
                          <CTableDataCell className="text-center">
                            {value.phoneNumber}
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
                      forcePage={search.pageIndex - 1}
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

export default Customer
