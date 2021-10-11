module.exports = {
    chainWebpack: (config) => {
        const svgRule = config.module.rule('svg');

        svgRule.uses.clear();

        svgRule
            .use('babel-loader')
            .loader('babel-loader')
            .end()
            .use('vue-svg-loader')
            .loader('vue-svg-loader');
    },
    devServer: {
        /* 自动打开浏览器 */
        open: false,
        /* 设置为0.0.0.0则所有的地址均能访问 */
        host: '0.0.0.0',
        port: 9528,
        https: false,
        hotOnly: false,
        /* 使用代理 */
        //     proxy: {
        //         '/api': {
        //             target: '/localhost:8181',
        //             secure: false,  // 如果是https接口，需要配置这个参数
        //             ws: true,//是否代理websockets
        //             changeOrigin: true,
        //             pathRewrite: {
        //                 '^/api': '/localhost:8282'
        //             }
        //         }
        //     }
        },

}