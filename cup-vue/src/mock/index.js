import Mock from 'mockjs'
import { getUserInfo } from './response/user'
const Random = Mock.Random

Mock.mock(/\/getUserInfo/, getUserInfo)

Mock.setup({
    // timeout: 3000 //3秒后才会返回接口响应
    // timeout: '0-3000', //随机0-3秒返回接口响应
    timeout: 0
})

Random.extend({
    fruit() {
        const fruit = ['apple', 'peach', 'lemon']
        return this.pick(fruit)
    }
})

export default Mock