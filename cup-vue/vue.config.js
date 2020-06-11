module.exports = {
    //输出文件目录
    outputDir: 'cup-dist',
    devServer: {
        port: 9999,
        host: 'localhost',
        open: false,
        proxy: 'http://localhost:3001'
    }
}