const state = {
    username: 'yanfa1',
    password: '123456',
}

const actions = {
    updateUsername({ commit, state, rootState, dispatch }) {
        commit('SET_USERNAME', 'superAdmin')
    },
    updateRootAppName({ commit }) {
        commit('UPDATE_APPNAME', '后台系统')
    },

}

const getters = {
    usernameWithVersion(state) {
        return `${state.username}v1.0`
    }
}

const mutations = {
    SET_USERNAME(state, value) {
        state.username = value
    }
}

export default {
    // namespaced: true,
    getters,
    state,
    actions,
    mutations
}