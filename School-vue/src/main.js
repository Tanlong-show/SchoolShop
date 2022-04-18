import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

// 聊天室-基于element
import Chat from 'jwchat';
Vue.use(Chat)




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

axios.defaults.baseURL = 'http://127.0.0.1:9527';//后端开发环境地址,进入网关
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';//配置请求头信息。
//axios.defaults.headers.common['accessToken'] = 'FA4C308D5E8F6409E01344ADDAEB4C71';



Vue.use(dataV)


Vue.config.productionTip = false

Vue.use(HappyScroll)

Vue.use(scroll)

Vue.use(VueParticles)


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


// 添加请求拦截器
//将token放入header中
axios.interceptors.request.use((config) => {
  // console.log(config)
  if (localStorage.getItem('token') != null) {
    config.headers.curUserid = window.localStorage.getItem('token')

  }

  return config
})


// axios.interceptors.request.use(config => {
//   // 在发送请求之前做些什么
//   //判断是否存在token，如果存在将每个页面header都添加token
//   //if中的值为登陆时存入vuex中的token值
// //如果存在token值，将token放入请求头‘Authorization’中
//   if(store.state.token){
//     config.headers.common['Authorization']=store.state.token
//   }
//
//   return config;
// }, error => {
//   // 对请求错误做些什么
//   return Promise.reject(error);
// });

