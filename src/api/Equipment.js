import request from '@/utils/request'

// 修改 Vue 接口调用以支持分页
export const equipmentRenderService = (page = 0, size = 10) =>
  request.get(`/experiment/equipments?page=${page}&size=${size}`)
