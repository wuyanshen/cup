import axios from './index'

// 查询待办任务
const getTask = params => {
    return axios.get(`/act/`, params)
}

// 查询部署的工作流
const getProcessList = params => {
    return axios.get(`/act/deps`, params)
}

// 挂起/激活流程
const suspend = params => {
    return axios.put(`/act/suspend`, params)
}

// 删除流程
const deleteProcess = key => {
    return axios.delete(`/act/${key}`)
}
// 通过xml部署工作流
const publishByXml = params => {
    return axios.post(`/act/publishByXml`, params)
}

// 通过zip部署工作流
const publishByZip = params => {
    return axios.post(`/act/publishByZip`, params)
}

// 完成任务
const completeTask = params => {
    return axios.put(`/act/complete`, params)
}

export default {
    getTask,
    getProcessList,
    suspend,
    deleteProcess,
    publishByXml,
    publishByZip,
    completeTask
}
