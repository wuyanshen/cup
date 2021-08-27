const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
    //项目访问基本路径
    publicPath: process.env.NODE_ENV === 'production' ? '/' : '/',
    //输出文件目录
    outputDir: 'cup-dist',
    devServer: {
        port: 9999,
        host: 'localhost',
        open: true,
        proxy: 'http://localhost:3001'
    },
    configureWebpack: config => {
        config.plugins.forEach(val => {
            if (val instanceof HtmlWebpackPlugin) {
                // 设置浏览器页签名称
                val.options.title = 'cup管理系统'
            }
        })
    }
}
