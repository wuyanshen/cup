module.exports = {
    devServer: {
        port: 9999,
        host: 'localhost',
        open: false,
        proxy: 'http://localhost:3001'
    }
}