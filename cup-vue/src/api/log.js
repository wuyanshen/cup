import axios from './index.js'

const logPage = params => {
	return axios.get('/logs/page',params)
}

const deleteLogs = params => {
	return axios.delete('/logs/delete', params);
}

export default{
	logPage,
	deleteLogs,
}