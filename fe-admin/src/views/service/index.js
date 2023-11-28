import React, { useEffect, useState } from 'react'
import { toast } from 'react-toastify'
import { httpGetRequestNoHeader, httpPostRequest } from 'src/axiosHeper'
import DeleteModal from './DeleteModal'
import {
  CButton,
  CCard,
  CCardBody,
  CCardHeader,
  CCol,
  CFormInput,
  CImage,
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
import CIcon from '@coreui/icons-react'
import { cilPen, cilDelete, cilLibraryAdd, cilSearch } from '@coreui/icons'
import ReactPaginate from 'react-paginate'
import defaultAvatar from '../../assets/images/react.jpg'
import { useNavigate } from 'react-router-dom'

function Service() {
  const navigate = useNavigate()

  const [items, setItems] = useState([])

  const [id, setId] = useState(0)

  const [totalElement, setTotalElement] = useState(0)

  const [open, setOpen] = useState(false)

  const [searchWord, setSearchWord] = useState('')

  const [search, setSearch] = useState({
    pageSize: 10,
    pageIndex: 1,
    searchWord: '',
  })

  const handleChangeModal = (open, id) => {
    setOpen(open)
    setId(0)
    if (open) {
      setId(id)
    }
  }

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
    httpGetRequestNoHeader('/service', search)
      .then(({ data }) => {
        setTotalElement(data.totalElements)
        setItems(data.items)
      })
      .catch((err) => {
        console.log(err)
      })
  }

  const handleDelete = (id) => {
    httpPostRequest('/service/delete/' + id)
      .then(({ data }) => {
        toast.success('Xoá thành công')
        loadPage(search)
        handleChangeModal(false, 0)
      })
      .catch((err) => {
        toast.warn('Xoá không thành công')
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
      <DeleteModal open={open} closeModal={handleChangeModal} deleteItem={handleDelete} id={id} />
      <CRow>
        <CCol xs={12}>
          <CCard className="mb-4">
            <CCardHeader style={{ display: 'flex', flexDirection: 'row-reverse' }}>
              <CButton
                color="success"
                style={{ marginRight: 10 }}
                variant="outline"
                onClick={() => {
                  navigate('/service/create')
                }}
              >
                <CIcon icon={cilLibraryAdd} />
                Thêm dịch vụ
              </CButton>
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
                        <CTableHeaderCell scope="col">Tên dịch vụ</CTableHeaderCell>
                        <CTableHeaderCell scope="col">Tiêu đề</CTableHeaderCell>
                        <CTableHeaderCell scope="col" className="text-center">
                          Hình ảnh
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
                          <CTableDataCell className="text">{value.name}</CTableDataCell>
                          <CTableDataCell className="text">{value.sortDescription}</CTableDataCell>
                          <CTableDataCell className="text-center">
                            <CImage
                              align="center"
                              style={{ width: 40, height: 40 }}
                              rounded
                              src={value.image ? value.image : defaultAvatar}
                              width={200}
                              height={200}
                              // className="mb-3"
                            />
                          </CTableDataCell>
                          <CTableDataCell colSpan={2} className="text-center">
                            <CButton
                              color="info"
                              variant="outline"
                              style={{ marginRight: 10 }}
                              onClick={() => {
                                navigate('/service/edit/' + value.id)
                              }}
                            >
                              <CIcon icon={cilPen} />
                            </CButton>
                            <CButton
                              variant="outline"
                              color="danger"
                              onClick={() => handleChangeModal(true, value.id)}
                            >
                              <CIcon icon={cilDelete} />
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

export default Service
