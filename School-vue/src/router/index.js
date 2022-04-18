import Vue from 'vue'
import VueRouter from 'vue-router'

import Layout from '@/layout' //布局页


Vue.use(VueRouter)

// 通用页面, 这里的配置不需要权限
export const constRouter = [
    {
        path: '/login',
        component: () => import('@/views/login/Login'),
        hidden: true //导航菜单忽略选项
    },
    {
        path: '/register',
        component: () => import('@/views/register/register.vue'),
        hidden: true //导航菜单忽略选项
    },
    {
        path: '',
        component: Layout, //应用布局页
        redirect: '/index',
        hidden: true,
    },
    {
        path: '/index',
        component: Layout, //应用布局页
        name: 'index',
        meta: {
            title: "首页", //导航菜单项标题
            icon: 'el-icon-s-home' //导航菜单图标
        },
        children: [
            {
                path: '',
                component: () => import('@/views/index/index.vue'),
                name: 'indexs',
                meta: {
                    title: "工作台",
                    icon: 'el-icon-s-home',
                    //哪些用户能看的数组
                    // roles: ['admin','jerry']
                }
            }
        ]
    }
]

// 动态路由 communication
export const asyncRoutes = [
    {
        path: '/analyze',
        component: Layout,
        redirect: '/goods/index',
        meta: {
            title: "数据分析",
            icon: 'el-icon-s-marketing',
            hidden: false
        },
        children: [
            // {
            //     path: 'analyzeindex',
            //     component: () => import('@/views/analyze/Index.vue'),
            //     name: 'analyzeindex',
            //     meta: {
            //         title: "主控数据",
            //         icon: 'el-icon-tickets',
            //         hidden: false,
            //         //哪些用户能看的数组
            //         roles: ['admin', 'jerry']
            //     }
            // },
            {
                path: 'analyzemonitor',
                component: () => import('@/views/analyze/Monitor.vue'),
                name: 'analyzemonitor',
                meta: {
                    title: "监控数据",
                    icon: 'el-icon-tickets',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
        ]
    },
    {
        path: '/goods',
        component: Layout,
        redirect: '/goods/index',
        meta: {
            title: "产品管理",
            icon: 'el-icon-s-goods',
            hidden: false,
        },
        children: [
            {
                path: 'index',
                component: () => import('@/views/goods/Index.vue'),
                name: 'goodsindex',
                meta: {
                    title: "产品管理",
                    icon: 'el-icon-tickets',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
            {
                path: 'fromgood',
                component: () => import('@/views/goods/FromGood.vue'),
                name: 'fromgood',
                meta: {
                    title: "添加(编辑产品)",
                    icon: 'el-icon-tickets',
                    hidden: true,
                    roles: ['admin', 'jerry'],

                }
            },
            {
                path: 'audit',
                component: () => import('@/views/goods/Audit.vue'),
                name: 'audit',
                meta: {
                    title: "商品审计",
                    icon: 'el-icon-tickets',
                    hidden: true,
                    roles: ['admin', 'jerry'],

                }
            },
            {
                path: 'sort',
                component: () => import('@/views/goods/Sort.vue'),
                name: 'sort',
                meta: {
                    title: "产品分类",
                    icon: 'el-icon-tickets',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
        ]
    },
    {
        path: '/marketing',
        component: Layout,
        redirect: '/marketing/spike',
        meta: {
            title: "营销控制",
            icon: 'el-icon-s-help',
            hidden: false,
        },
        children: [
            {
                path: 'spike',
                component: () => import('@/views/marketing/Spike.vue'),
                name: 'spike',
                meta: {
                    title: "秒杀添加",
                    icon: 'el-icon-message',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
            {
                path: 'produce',
                component: () => import('@/views/marketing/Produce.vue'),
                name: 'produce',
                meta: {
                    title: "秒杀产品",
                    icon: 'el-icon-message',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },

        ]
    },
    {
        path: '/coupon',
        component: Layout,
        redirect: '/coupon/addCoupon',
        meta: {
            title: "商品大厅",
            icon: 'el-icon-lollipop',
            hidden: false,
        },
        children: [
            {
                path: 'addCoupon',
                component: () => import('@/views/coupon/AddCoupon.vue'),
                name: 'addCoupon',
                meta: {
                    title: "交流大厅",
                    icon: 'el-icon-chat-line-round',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
            {
                path: 'couponlist',
                component: () => import('@/views/coupon/CouponList.vue'),
                name: 'couponlist',
                meta: {
                    title: "商品列表",
                    icon: 'el-icon-platform-eleme',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
        ]
    },
    {
        path: '/communication',
        component: Layout,
        redirect: '/communication/index',
        meta: {
            title: "反馈",
            icon: 'el-icon-s-help',
            hidden: false,
        },
        children: [
            {
                path: 'index',
                component: () => import('@/views/communication/Index.vue'),
                name: 'communicationindex',
                meta: {
                    title: "邮件",
                    icon: 'el-icon-message',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
            // {
            //     path: 'wechat',
            //     component: () => import('@/views/communication/WeChat.vue'),
            //     name: 'communicationwechat',
            //     meta: {
            //         title: "客服",
            //         icon: 'el-icon-service',
            //         hidden: false,
            //         roles: ['admin', 'jerry']
            //     }
            // },
        ]
    },
    {
        path: '/control',
        component: Layout,
        redirect: '/control/keycode',
        meta: {
            title: "管理员",
            icon: 'el-icon-s-platform',
            hidden: false,
        },
        children: [
            {
                path: 'keycode',
                component: () => import('@/views/control/KeyCode.vue'),
                name: 'keycode',
                meta: {
                    title: "商品审核",
                    icon: 'el-icon-message',
                    hidden: false,
                    roles: ['jerry']
                }
            },
            {
                path: 'placard',
                component: () => import('@/views/control/Placard.vue'),
                name: 'placard',
                meta: {
                    title: "实时公告",
                    icon: 'el-icon-message',
                    hidden: false,
                    roles: ['jerry']
                }
            },

            {
                path: 'nav',
                component: () => import('@/views/control/Nav.vue'),
                name: 'nav',
                meta: {
                    title: "申请管理员",
                    icon: 'el-icon-message',
                    hidden: false,
                    roles: ['admin']
                }
            },
        ]
    },
    {
        path: '/home',
        component: Layout,
        redirect: '/home/index',
        meta: {
            title: "个人中心",
            icon: 'el-icon-user-solid',
            hidden: false,
        },
        children: [
            {
                path: 'index',
                component: () => import('@/views/home/Index.vue'),
                name: 'homeindex',
                meta: {
                    title: "个人设置",
                    icon: 'el-icon-user-solid',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
            // {
            //     path: 'Informationall',
            //     component: () => import('@/views/home/Informationall.vue'),
            //     name: 'homeinformationall',
            //     meta: {
            //         title: "个人消息",
            //         icon: 'el-icon-message-solid',
            //         hidden: false,
            //         roles: ['admin', 'jerry']
            //     }
            // },
            {
                path: 'Orders',
                component: () => import('@/views/home/Orders.vue'),
                name: 'homeOrders',
                meta: {
                    title: "我的买单",
                    icon: 'el-icon-coin',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
            {
                path: 'Orders2',
                component: () => import('@/views/home/Orders2.vue'),
                name: 'homeOrders2',
                meta: {
                    title: "我的卖单",
                    icon: 'el-icon-wallet',
                    hidden: false,
                    roles: ['admin', 'jerry']
                }
            },
        ]
    }
]

const router = new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes: constRouter
})

export default router

