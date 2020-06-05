import axios from './index.js'

const logPage = params => {
	return axios.get('/logs/page',params)
}

export default{
	logPage
}