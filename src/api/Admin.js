import request from '@/utils/request'

export const AdminLoginService = (password) =>
  request.post('/admin/login', { password })
