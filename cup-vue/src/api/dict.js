import axios from './index'

// 查询字典类型分页
const dictTypePage = params => {
    return axios.get('/dicts/type/page', params)
};

// 修改字典类型
const updateDictType = params => {
    return axios.put('/dicts/type/update', params)
};

// 新增字典类型
const addDictType = params => {
    return axios.post('/dicts/type/add', params)
};

// 删除字典类型
const deleteDictType = id => {
    return axios.delete(`/dicts/type/${id}`)
};

// 查询字典类型详情
const dictTypeDetail = id => {
    return axios.get(`/dicts/type/${id}`)
};

// 查询字典数据
const dictDataPage = params => {
    return axios.get('/dicts/data/page', params)
};

// 查询字典数据详情
const dictDataDetail = id => {
    return axios.get(`/dicts/data/${id}`)
};

// 查询字典数据list
const dictDataList = params => {
    return axios.get('/dicts/data/list', params)
};

// 修改字典数据
const updateDictData = params => {
    return axios.put('/dicts/data/update', params)
};

// 新增字典数据
const addDictData = params => {
    return axios.post('/dicts/data/add', params)
};

// 删除字典数据
const deleteDictData = id => {
    return axios.delete(`/dicts/data/${id}`)
};

export default {
    dictTypePage,
    dictDataList,
    dictDataDetail,
    dictTypeDetail,
    updateDictType,
    addDictType,
    deleteDictType,
    dictDataPage,
    updateDictData,
    addDictData,
    deleteDictData
}
