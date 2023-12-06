import request from '@/utils/request'

// 修改 Vue 接口调用以支持分页
export const equipmentRenderService = (page = 0, size = 10) =>
  request.get(`/experiment/equipments?page=${page}&size=${size}`)

export const equipmentGetByIdService = (id) =>
  request.get(`/experiment/equipment/${id}`)

export const equipmentEditService = (id, updateAttributes) => {
  const formData = new FormData()
  formData.append('name', updateAttributes.name)
  formData.append('category', updateAttributes.category)
  formData.append('status', updateAttributes.status)
  formData.append('description', updateAttributes.description)

  // 如果有 image 文件，也添加到 formData
  if (updateAttributes.image) {
    formData.append('image', updateAttributes.image)
  }

  return request.put(`experiment/edit-equipment/${id}`, formData)
}

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
  formData.append('image', null || image)

  return request.post(`/experiment/create-equipment`, formData)
}
