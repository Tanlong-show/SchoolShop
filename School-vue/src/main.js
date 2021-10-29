import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

// 引入进度条样式
import 'nprogress/nprogress.css'

//引入自定义滚动条
import HappyScroll from 'vue-happy-scroll'
import 'vue-happy-scroll/docs/happy-scroll.css'

//自动滚动插件
import scroll from 'vue-seamless-scroll'

// 炫酷的粒子动效
import  VueParticles  from   'vue-particles'

// 引入图表
import VCharts from 'v-charts'

Vue.use(VCharts)

// 制作地图
import echarts from 'echarts'
Vue.prototype.$echarts = echarts
import china from 'echarts/map/json/china.json'
echarts.registerMap('china', china)

// 炫酷图标
import dataV from '@jiaminghi/data-view'

//import axiox
import axios from 'axios'

Vue.prototype.$axios = axios

axios.defaults.baseURL = 'http://localhost:8181';//后端开发环境地址
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';//配置请求头信息。
//axios.defaults.headers.common['accessToken'] = 'FA4C308D5E8F6409E01344ADDAEB4C71';



Vue.use(dataV)


Vue.config.productionTip = false

Vue.use(HappyScroll)

Vue.use(scroll)

Vue.use(VueParticles)

Vue.use(ElementUI);

// 全局路由守卫
import './router/permission'

// 全局注册指令
import vPermission from './directive/permission'
Vue.directive('permission',vPermission)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')



//监听，在页面刷新时将vuex里的信息保存到sessionStorage里
window.addEventListener("beforeunload", () => {
  //刷新保存状态
  if (sessionStorage.getItem("store")) {
    store.replaceState(
        Object.assign({},
            store.state,
            JSON.parse(sessionStorage.getItem("store"))
        )
    );
    sessionStorage.removeItem("store")
  }
  sessionStorage.setItem("store", JSON.stringify(store.state));
});



