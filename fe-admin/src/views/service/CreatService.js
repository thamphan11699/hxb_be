import {
  CButton,
  CCard,
  CCardBody,
  CCol,
  CForm,
  CFormInput,
  CFormLabel,
  CFormSelect,
  CFormTextarea,
  CImage,
  CInputGroup,
  CRow,
} from '@coreui/react'
import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { toast } from 'react-toastify'
import { httpGetRequest, httpPostRequest } from 'src/axiosHeper'
import defaultAvatar from '../../assets/images/react.jpg'
import { CKEditor } from '@ckeditor/ckeditor5-react'
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'

function CreatService() {
  const [item, setItem] = useState({
    name: '',
    sortDescription: '',
    description: '',
    image: '',
    categoryId: null,
    price: null,
    introduction: '',
  })

  const [categories, setCategories] = useState([])
  const [validated, setValidated] = useState(false)

  const navigate = useNavigate()

  const handleUploadImage = (e) => {
    let file = e.target.files[0]
    if (file) {
      let formData = new FormData()
      formData.append('file', e.target.files[0])
      httpPostRequest('/image/upload', formData)
        .then(({ data }) => {
          console.log(data)
          setItem((pre) => {
            return {
              ...pre,
              image: data.uri,
            }
          })
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }

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
      console.log(item)
      httpPostRequest('/service/create', item)
        .then((res) => {
          toast.success('Thêm dịch vụ thành công')
          navigate('/service')
        })
        .catch((err) => {
          console.log(err)
          toast.warn('Thêm dịch vụ không thành công')
        })
    }
  }

  useEffect(() => {
    httpGetRequest('/service/create')
      .then(({ data }) => {
        setCategories(data.categories)

        setItem((pre) => {
          return {
            ...pre,
            categoryId: data?.categories[0]?.value,
          }
        })
      })
      .catch((err) => {
        console.log(err)
      })
  }, [])

  const handleChangeCategory = (event) => {
    event.persist()
    let value = event.target.value
    setItem((pre) => {
      return {
        ...pre,
        categoryId: Number(value),
      }
    })
  }

  return (
    <CCard className="mb-4">
      <CCardBody>
        <CRow>
          <CCol xs={3}>
            <div className="clearfix">
              <CImage
                align="center"
                rounded
                src={item.image ? item.image : defaultAvatar}
                width={200}
                height={200}
                className="mb-3"
              />
              <div style={{ display: 'flex', justifyContent: 'center' }}>
                <CButton color="light" className="text-center">
                  <CFormLabel htmlFor="file" className="mb-0" style={{ cursor: 'pointer' }}>
                    Thêm hình ảnh
                  </CFormLabel>
                </CButton>
                <CFormInput
                  type="file"
                  id="file"
                  name="file"
                  hidden
                  onChange={handleUploadImage}
                  accept="image/*"
                />
              </div>
            </div>
          </CCol>
          <CCol xs={9}>
            <CForm
              validated={validated}
              noValidate
              className="needs-validation"
              onSubmit={handleSubmit}
            >
              <CInputGroup className="mb-3 has-validation">
                <CFormLabel htmlFor="email" className="col-sm-2 col-form-label">
                  Tên dịch vụ
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
                  Giá dự kiến
                </CFormLabel>
                <CCol sm={10}>
                  <CFormInput
                    type="number"
                    id="number"
                    name="price"
                    value={item.price}
                    onChange={handleChange}
                    required
                  />
                </CCol>
              </CInputGroup>
              <CInputGroup className="mb-3 has-validation">
                <CFormLabel htmlFor="email" className="col-sm-2 col-form-label">
                  Lời giới thiệu
                </CFormLabel>
                <CCol sm={10}>
                  <CFormTextarea
                    type="text"
                    id="introduction"
                    name="introduction"
                    value={item.introduction}
                    onChange={handleChange}
                    required
                  />
                </CCol>
              </CInputGroup>
              <CInputGroup className="mb-3 has-validation">
                <CFormLabel htmlFor="password" className="col-sm-2 col-form-label">
                  Tiêu đề
                </CFormLabel>
                <CCol sm={10}>
                  <CFormTextarea
                    type="text"
                    id="text"
                    name="sortDescription"
                    value={item.sortDescription}
                    onChange={handleChange}
                    style={{ height: 150 }}
                    required
                  />
                </CCol>
              </CInputGroup>
              <CInputGroup className="mb-3 has-validation">
                <CFormLabel htmlFor="role" className="col-sm-2 col-form-label">
                  Danh mục
                </CFormLabel>
                <CCol sm={10}>
                  <CFormSelect
                    onChange={handleChangeCategory}
                    required
                    name="role"
                    value={item.categoryId}
                    aria-label="Default select example"
                    options={categories}
                  />
                </CCol>
              </CInputGroup>
              <CInputGroup className="mb-3 has-validation">
                <CFormLabel htmlFor="firstName" className="col-sm-2 col-form-label">
                  Mô tả
                </CFormLabel>
                <CCol sm={10}>
                  <CKEditor
                    config={{
                      ckfinder: {
                        // Upload the images to the server using the CKFinder QuickUpload command
                        // You have to change this address to your server that has the ckfinder php connector
                        uploadUrl: 'http://localhost:9999/api/v1/image/upload-ck',
                      },
                    }}
                    editor={ClassicEditor}
                    data={item.description}
                    onReady={(editor) => {
                      // You can store the "editor" and use when it is needed.
                      console.log('Editor is ready to use!', editor)
                    }}
                    onChange={(event, editor) => {
                      const data = editor.getData()
                      setItem((pre) => {
                        return {
                          ...pre,
                          description: data,
                        }
                      })
                    }}
                  />
                </CCol>
              </CInputGroup>
              <CRow>
                <div style={{ display: 'flex', flexDirection: 'row-reverse' }}>
                  <CButton color="success" className="text-center" type="submit">
                    Lưu
                  </CButton>
                </div>
              </CRow>
            </CForm>
          </CCol>
        </CRow>
      </CCardBody>
    </CCard>
  )
}

export default CreatService
