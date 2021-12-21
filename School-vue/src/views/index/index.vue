<template>
    <div class="index">
        <!-- 头部 -->
        <div class="index-head">
            <div class="index-head-title">
                工作台
            </div>
            <el-row>
                <el-col :xs="24" :sm="24" :md="14" :lg="12" :xl="12">
                    <div class="index-head-centent-left">
                        <el-avatar style="margin-right: 10px;" :size="56" :src="circleUrl"></el-avatar>
                        <div class="index-head-centent-left-text">
                            <p style="font-weight: bold;">hello,{{user.name}},祝你新的一天购物愉快!</p>
                            <!--                            <p></p>-->
                            <iframe id="tianqi" scrolling="no" frameborder="0" allowtransparency="true"
                                    src="http://i.tianqi.com/index.php?c=code&a=getcode&id=34&h=25&w=280">
                            </iframe>


                        </div>
                    </div>
                </el-col>
                <el-col :xs="24" :sm="24" :md="10" :lg="12" :xl="12">
                    <div class="index-head-centent-right">
                        <div class="index-head-centent-right-list" @click="toAllMessage">
                            <div class="index-head-centent-right-list-icon">
                                <daiban style="color: #FD7F07; background: #FBEEE1;"
                                        class="index-head-centent-right-list-icon-is"/>
                                已处理卖单数
                            </div>
                            <div v-if="wait != 0" class="index-head-centent-right-list-text">
                                {{all-wait}}&nbsp;/&nbsp;{{all}}
                            </div>
                        </div>
                        <div class="index-head-centent-right-list">
                            <div class="index-head-centent-right-list-icon">
                                <github style="color: #2294FC; background: #DDEDFD;"
                                        class="index-head-centent-right-list-icon-is"/>
                                开源项目
                            </div>
                            <div class="index-head-centent-right-list-text">
                                7
                            </div>
                        </div>
                        <div class="index-head-centent-right-list" @click="toAllMessage2">
                            <div class="index-head-centent-right-list-icon">
                                <daiban class="index-head-centent-right-list-icon-is"/>
                                我的买单
                            </div>
                            <div class="index-head-centent-right-list-text">
                                {{mytotal}}
                            </div>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <!-- 图标 -->
        <div class="index-centent">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="16" :xl="16">
                    <!-- 项目 -->
                    <div class="grid-content">
                        <div class="index-centent-title">
                            <div class="index-centent-title-left">
                                <sort class="index-centent-title-left-icon"/>
                                我的项目
                            </div>
                            <div class="index-centent-title-right">
                                全部项目
                            </div>
                        </div>
                        <div class="index-centent-box">
                            <Project/>
                        </div>
                    </div>
                    <!-- 动态 -->
                    <div class="grid-content">
                        <div class="index-centent-title">
                            <div class="index-centent-title-left">
                                <dynamic class="index-centent-title-left-icontwo"></dynamic>
                                与我相关
                            </div>
                            <div class="index-centent-title-right" @click="toAllMessage">
                                全部动态
                            </div>
                        </div>
                        <div class="index-centent-box">
                             <span v-if="flag">
                                 <DynamicBox :dynamicList="dynamicList"></DynamicBox>
                             </span>
                        </div>
                    </div>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="8" :xl="8">
                    <div class="grid-content">
                        <div class="index-centent-title">
                            <div class="index-centent-title-left">
                                <operating class="index-centent-title-left-iconthree"/>
                                快捷操作
                            </div>
                            <div class="index-centent-title-right">
                                <!-- 全部动态 -->
                            </div>
                        </div>
                        <div class="index-centent-box">
                            <Fast/>
                        </div>
                    </div>
                    <div class="grid-content">
                        <div class="index-centent-title">
                            <div class="index-centent-title-left">
                                <group class="index-centent-title-left-iconfour"/>
                                沟通过的人 ({{number}})
                            </div>
                            <div class="index-centent-title-right">
                                <!-- 全部动态 -->
                            </div>
                        </div>
                        <div class="index-centent-box">
                            <span v-if="flag2">
                                <Colleague :friendList="friendList"></Colleague>
                            </span>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
    import daiban from '../../assets/icon/daiban.svg'
    import github from '../../assets/icon/github.svg'
    import sort from '../../assets/icon/sort.svg'
    import dynamic from '../../assets/icon/dynamic.svg'
    import operating from '../../assets/icon/operating.svg'
    import group from '../../assets/icon/group.svg'
    import Project from '../../components/Index/project' // 项目
    import DynamicBox from '../../components/Index/dynamicBox' //动态
    import Fast from '../../components/Index/fast' //快捷操作
    import Colleague from '../../components/Index/colleague'

    export default {
        methods: {
            //跳转全部动态 / 我的卖单
            toAllMessage() {
                this.$router.push({
                    path: this.$route.query.redirect || '/home/Orders2',
                })
            },
            //跳转我的买单
            toAllMessage2() {
                this.$router.push({
                    path: this.$route.query.redirect || '/home/Orders',
                })
            },
        },

        beforeCreate() {


            var userId = localStorage.getItem("token")
            //我的买单
            this.$axios.get("/consumer/user/getMyOrder?userid=" + userId).then((response) => {
                this.mytotal = response.data.length
            }).catch(() => {
            })

            this.$axios
                .post("/consumer/user/getUser?userid=" + userId)
                .then(response => {

                    this.user = response.data
                    this.circleUrl = response.data.headpicture
                    //获取左侧沟通过的用户,colleague
                    this.$axios
                        .post("/consumer/chatmessage/getMyMessage?myId=" + this.user.id)
                        .then(response => {
                            //沟通过人的数量
                            this.number = response.data.length
                            for (let i = 0; i < response.data.length; i++) {
                                this.friend.id = response.data[i].id
                                this.friend.name = response.data[i].name
                                this.friend.img = response.data[i].headpicture
                                this.friend.signature = response.data[i].signature
                                this.friend.status = response.data[i].status

                                //先把defaultObject转换成字符串，然后再转换成对象赋值给newObject,可防止引用
                                var usernow = JSON.parse(JSON.stringify(this.friend));
                                this.friendList[i] = usernow
                            }
                            this.flag2 = true
                        })
                    //dynamicBox，我的卖单信息
                    this.$axios
                        .post("/consumer/user/getAllUserMessage?userid=" + userId)
                        .then(response => {
                            this.dynamicList = response.data
                            this.flag = true
                            //待办事项
                            if (this.flag && this.flag2) {
                                //计算待办事项和总事项数量
                                var ok = 0
                                for (let i = 0; i < this.dynamicList.length; i++) {
                                    if (this.dynamicList[i].type == "下单了") {
                                        ok++
                                    }
                                }
                                this.wait = ok
                                this.all = this.dynamicList.length
                            }

                        })
                    //js删除固定位置下表元素
                    // this.friendList.splice(0,1)

                })


        },
        mounted() {

        },


        data() {
            return {

                //我自己的订单数
                mytotal: '',


                ////////////
                flag: false,
                flag2: false,
                user: {
                    id: "",
                    userId: "",
                    name: "",
                    headpicture: "",
                    root: ""
                },
                //后期改成数据库传递过来，用域名的方式展示图片，本地不能加载到图片
                circleUrl: "",
                //单个好友模块
                friend: {
                    id: '',
                    img: '',
                    name: '',
                    dept: '',
                    signature: '',
                    status: '',
                },
                //好友列表
                friendList: [],
                //沟通过的人数量
                number: 0,


                dynamicList: [],

                //待办事项
                wait: '0',//未办事项,就是他人下的订单状态是未支付的
                all: '0',//总事项

                //我的订单，我自己下的订单
                ordersNum: '',
            }


        },
        components: {
            daiban,
            github,
            sort,
            dynamic,
            operating,
            group,
            Project,
            DynamicBox,
            Fast,
            Colleague
        }
    }
</script>

<style scoped>
    .index {
        width: 100%;
        min-height: 100%;
    }

    .index-head {
        width: 100%;
        /* height: 133px; */
        padding: 16px 32px 0 32px;
        box-sizing: border-box;
        background: rgba(255, 255, 255, .7);
        border-bottom: 1px solid #dcdfe6;
    }

    .index-head-title {
        width: 100%;
        height: 30px;
        line-height: 30px;
        font-size: 20px;
        color: #292929;
        margin-bottom: 16px;
    }

    .index-head-centent-left {
        width: 100%;
        height: 70px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
    }

    .index-head-centent-left-text {
        height: 56px;
    }

    .index-head-centent-left-text p:nth-child(1) {
        margin-bottom: 7px;
        font-size: 20px;
        color: #292929;
        letter-spacing: 0.1em;

    }

    .index-head-centent-left-text p:nth-child(2) {
        font-size: 12px;
        color: #999999;
        letter-spacing: 0.1em;
    }

    .index-head-centent-right {
        width: 100%;
        height: 70px;
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }

    .index-head-centent-right-list {
        height: 56px;
        cursor: pointer;
        margin-left: 20px;
    }

    .index-head-centent-right-list-icon {
        font-size: 14px;
        display: flex;
        margin-bottom: 5px;
        color: #808695;
    }

    .index-head-centent-right-list-icon-is {
        width: 14px;
        height: 14px;
        fill: currentColor;
        margin-right: 5px;
        padding: 5px;
        border-radius: 12px;
    }

    .index-head-centent-right-list-text {
        height: 28px;
        line-height: 28px;
        font-weight: bold;
        text-align: right;
        font-size: 20px;
    }

    .index-centent {
        width: 100%;
        min-height: 100%;
        padding: 25px;
        box-sizing: border-box;


    }


    .el-col {
        border-radius: 4px;
    }

    .grid-content {
        border-radius: 4px;
        min-height: 36px;
        background: #ffffff;
        width: 100%;
        margin-bottom: 25px;
    }

    .index-centent-title {
        width: 100%;
        height: 52px;
        border-bottom: 1px solid #dcdfe6;
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        padding: 0 15px;
        box-sizing: border-box;
        justify-content: space-between;
        font-size: 14px;
    }

    .index-centent-title-left {
        display: flex;
        justify-content: flex-start;
        font-size: 14px;
        /* border-radius: 11px;
        overflow: hidden; */
    }

    .index-centent-title-left-icon {
        width: 12px;
        height: 12px;
        font-size: 12px;
        color: #1890FF;
        padding: 5px;
        background: #E6F3FE;
        /* border-radius: 11px; */
        fill: currentColor;
        margin-right: 10px;
    }

    .index-centent-title-left-icontwo {
        width: 12px;
        height: 12px;
        font-size: 12px;
        color: red;
        padding: 5px;
        background: pink;
        /* border-radius: 11px; */
        fill: currentColor;
        margin-right: 10px;
    }

    .index-centent-title-left-iconthree {
        width: 12px;
        height: 12px;
        font-size: 12px;
        color: #FC1D1D;
        padding: 5px;
        background: #FDF4FD;
        /* border-radius: 11px; */
        fill: currentColor;
        margin-right: 10px;
    }

    .index-centent-title-left-iconfour {
        width: 12px;
        height: 12px;
        font-size: 12px;
        color: #18B2FA;
        padding: 5px;
        background: #FDF4FD;
        /* border-radius: 11px; */
        fill: currentColor;
        margin-right: 10px;
    }

    .index-centent-title-right {
        color: #3CA0FD;
        cursor: pointer;
    }

    .index-centent-box {
        width: 100%;
        padding: 15px 15px 0;
        box-sizing: border-box;
    }
</style>