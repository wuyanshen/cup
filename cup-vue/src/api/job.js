import axios from './index.js'

const jobPage = params => {
    return axios.get('/jobs/page', params)
}

const addJob = params => {
    return axios.post('/jobs', params)
}

const deleteJob = params => {
    console.log(params)
    return axios.delete('/jobs', params)
}

const trigger = params => {
    return axios.put('/jobs/trigger', params)
}

const pauseJob = params => {
    return axios.put('/jobs/pause', params)
}

const resumeJob = params => {
    return axios.put('/jobs/resume', params)
}

const check = params => {
    return axios.get('/jobs/check', params)
}

export default {
    jobPage,
    addJob,
    deleteJob,
    pauseJob,
    resumeJob,
    check,
    trigger
}
