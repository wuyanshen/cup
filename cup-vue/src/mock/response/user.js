import Mock from 'mockjs'

const Random = Mock.Random

export const getUserInfo = (options) => {
    const template = {
        'str|2-4': 'lison', //随机显示lison2-4次
        'age|+2': 18, //数组的时候，age每次+2
        'num|2-6': 0, //随机显示[2-6]的数字
        'float|2-8.2-3': 0, //随机显示[2-8]小数点随机2位或者三位
        'boo|1': true, //true的概率是二分之一
        'boo2|1-9': true, //min/(min+max) true的概率是十分之一
        'obj|1': { //随机将1个数据放在结果中
            name: 'xiaom',
            age: 22,
            address: '北京'
        },
        'obj2|1-2': { //随机将1-2个数据放在结果中
            name: 'xiaom',
            age: 22,
            address: '北京'
        },
        'arr|2': [1, 2, 3], //指定循环显示2次数组
        'arr2|1-2': ['a', 'b'], //随机循环数组1-2次
        'func': () => { //返回方法中return的值
            return 'this is a function'
        },
        'reg': /.*\.(js|html|css|jpg)/, //根据正则表达式的内容生成结果
        email: Random.email(), //随机生成邮箱
        email2: Mock.mock('@email'), //随机生成邮箱
        range: Random.range(3, 7, 2), //随机返回数组包括3不包括7，相差2
        date: Random.date('yyyy-MM-dd'), //随机返回日期
        time: Random.time('hh:mm'), //随机返回时间
        datetime: Random.datetime(), //随机返回日期+时间
        now: Random.now('second', 'yyyy-MM-dd hh:mm:ss'), //返回当前时间，精确到秒
        img: Random.image('100x100', '#00f000', '#fff', 'test'), //随机返回图片地址
        img_base64: Random.dataImage(), //返回base64格式的图片
        color: Random.color(), //返回随机颜色
        cword: Random.cword('我是中文汉字', 2, 5), //随机返回2-5个汉字
        cname: Random.cname(), //随机返回中文名字
        email3: Random.email('qq.com'), //随机返回qq.com的邮箱
        region: Random.region(), //随机返回中国地区
        province: Random.province(), //随机返回中国省份
        city: Random.city(), //随机返回中国城市
        county: Random.county(true), //随机返回县级
        zip: Random.zip(), //随机返回邮政编码
        upperFirstLetter: Random.capitalize('apple'), //第一个首字母大写
        upper: Random.upper('i love china'), //整体大写
        lower: Random.lower('I AM CODING'), //整体小写
        pick: Random.pick(), //从数组中随机挑选一个
        shuffle: Random.shuffle([1, 2, 3, 4, 5, 6]), //随机打乱顺序
        guid: Random.guid(), //随机生成了guid
        id: Random.id(), //随机生成身份证
        fruit: Random.fruit(), //自定义mock
        fruit2: '@fruit', //自定义mock2
    }

    return Mock.mock(template)
}