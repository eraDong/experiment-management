import request from '@/utils/request'

// 修改 Vue 接口调用以支持分页
export const equipmentRenderService = (page = 0, size = 10) =>
  request.get(`/experiment/equipments?page=${page}&size=${size}`)

export const equipmentDeleteService = (id) =>
  request.delete(`/experiment/delete-equipment/${id}`)

export const equipmentSearchService = (params) => {
  const queryParams = Object.entries(params)
    .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
    .join('&')

  return request.get(`/experiment/search?${queryParams}`)
}
