module.exports = {
    //项目访问基本路径
    publicPath: process.env.NODE_ENV === 'production'?'/':'/',
    //输出文件目录
    outputDir: 'cup-dist',
    devServer: {
        port: 9999,
        host: 'localhost',
        open: false,
        proxy: 'http://localhost:3001'
    }
}