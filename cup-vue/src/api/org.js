import axios from './index.js'

const orgTree = () => {
    return axios.get('/orgs/tree')
}


export default {
    orgTree,
}
