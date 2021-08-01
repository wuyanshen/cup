import axios from './index.js'

// 新增请假
const leaveAdd = params => {
    return axios.post(`/leave`,params)
}
// 查询请假列表
const leavePage = params => {
    return axios.get(`/leave/page`,params)
}

// 删除请假
const leaveDelete = id => {
    return axios.delete(`/leave/${id}`)
}

// 修改请假
const leaveUpdate = params => {
    return axios.put(`/leave/update`,params)
}
export default {
    leaveAdd,
    leavePage,
    leaveDelete,
    leaveUpdate,
}