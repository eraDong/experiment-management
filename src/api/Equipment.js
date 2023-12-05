import request from '@/utils/request'

// 修改 Vue 接口调用以支持分页
export const equipmentRenderService = (page = 0, size = 10) =>
  request.get(`/experiment/equipments?page=${page}&size=${size}`)

export const equipmentDeleteService = (id) =>
  request.delete(`/experiment/delete-equipment/${id}`)

export const equipmentSearchService = (params, page = 0, size = 10) => {
  const queryParams = Object.entries(params)
    .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
    .join('&')

  return request.get(
    `/experiment/search?${queryParams}&page=${page}&size=${size}`
  )
}

export const equipmentAddService = ({
  name,
  category,
  status,
  description,
  image
}) => {
  const formData = new FormData()
  formData.append('name', name)
  formData.append('category', category)
  formData.append('status', status)
  formData.append('description', description)
  formData.append('image', image)

  return request.post(`/experiment/create-equipment`, formData)
}
