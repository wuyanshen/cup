export const saveInLocal = store => {
    console.log('store初始化了')
    if (sessionStorage.state) store.replaceState(JSON.parse(sessionStorage.state))
    store.subscribe((mutation, state) => {
        sessionStorage.state = JSON.stringify(state)
    })
}
