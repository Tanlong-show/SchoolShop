<template>
    <el-tabs class="dropdowns" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="实时" name="first" style="padding:0; " class="dropdowns-dropBox">
            <div class="dropdowns-list-box">
                <Drop v-for="item in nowList" :keyword="item.keyword" :text="item.content"
                      :time="item.updateTime" :end-time="item.endTime" :person="item.person"/>

                <el-button class="dropdowns-more" :loading="false" type="text" disabled>End</el-button>
            </div>

            <!-- <Empy type='Horn' text='你还没有新的通知' /> -->
        </el-tab-pane>
        <el-tab-pane label="消息" name="second" style="padding:0" class="dropdowns-dropBox">
            <div class="dropdowns-list-box">
                <NewsBox v-for="item in friendList" :headpicture="item.headpicture" :text='item.name+"给你发了一条消息"'
                         :time='item.createTime'/>
                <el-button class="dropdowns-more" :loading="false" type="text" disabled>End</el-button>
            </div>

            <!-- <Empy type='News' text='你还没有新的消息' /> -->
        </el-tab-pane>
        <el-tab-pane label="代办" name="third" style="padding:0">
            <div class="dropdowns-list-box">
                <Matter v-for="item in dynamicList" :type='item.type2' :text='item.buyerName+item.type+item.goodName'
                        :time='item.time'/>
                <el-button class="dropdowns-more" :loading="false" type="text" disabled>End</el-button>
            </div>

            <!-- <Empy type='Flag' text='你还没有新的代办事项' /> -->
        </el-tab-pane>
    </el-tabs>
</template>

<script>
    // 通知 每一栏
    import Drop from './drop'
    // 消息 每一栏
    import NewsBox from './newsBox'
    // 代办 每一栏
    import Matter from './matter'
    // 清空图标
    import Clear from '@/assets/icon/clear.svg'

    // 空
    import Empy from './empy'

    //导入后，实时接收rabbitMQ消息
    import Stomp from 'stompjs'


    export default {

        created() {
            this.connect();
        },

        beforeCreate() {
            var userId = localStorage.getItem("token")
            //我的卖单信息
            this.$axios
                .post("/consumer/user/getAllUserMessage?userid=" + userId)
                .then(response => {
                    this.dynamicList = response.data
                    //待办事项
                    //计算待办事项和总事项数量
                    var ok = 0
                    for (let i = 0; i < this.dynamicList.length; i++) {
                        if (this.dynamicList[i].type == "下单了") {
                            this.dynamicList[i].type2 = "primary"
                        } else if (this.dynamicList[i].type == "取消了订单产品") {
                            this.dynamicList[i].type2 = "warning"
                        } else {
                            this.dynamicList[i].type2 = "success"
                        }
                    }

                }),

                //获取左侧沟通过的用户,colleague
                this.$axios
                    .post("/consumer/user/getUser?userid=" + userId)
                    .then(response => {
                        //获取左侧沟通过的用户,colleague
                        this.id = response.data.id
                        this.$axios
                            .post("/consumer/chatmessage/getMyMessage?myId=" + this.id)
                            .then(response => {
                                this.friendList = response.data
                            })
                    })
        },


        data() {
            return {
                //小红点数量，子组件中
                LENGTH:'',
                client: null,
                tag: 0,
                id: '',
                activeName: 'first',
                dropOff: false,
                //代办中信息
                dynamicList: [],

                friendList: [],

                nowList: [], //实时消息,公告
            };
        },
        components: {
            Drop,
            Clear,
            NewsBox,
            Empy,
            Matter
        },
        methods: {
            handleClick(tab, event) {
                // console.log(tab, event);
            },

            //实时接收rabbitMQ的消息
            ///////////////////////////////////////////////////////////
            //webSocket连接成功后回调函数
            onConnected(frame) {
                // console.log("Connected: " + frame);
                var exchange = "/exchange/tanlong/tl";

                //创建随机队列用上面的路由key绑定交换机,放入收到消息后的回调函数和失败的回调函数
                this.client.subscribe(exchange, this.responseCallback, this.onFailed);
                // console.log(frame)
            },
            onFailed(frame) {
                // console.log("Failed: " + frame);
            },
            responseCallback(frame) {
                //把json数据转换为对象
                var pp = JSON.parse(frame.body);

                this.nowList.push(pp)
                // this.LENGTH = this.nowList.length
                //子组件给父组件传值并进行绑定
                this.$emit('changeSize', this.nowList.length)

                //接收到服务器推送消息，向服务器定义的接收消息routekey路由rk_recivemsg发送确认消息
                // this.client.send("/exchange/exchange_topics_inform/rk_recivemsg", {"content-type": "text/plain"}, frame.body);
            },
            connect() {
                //这里填你rabbitMQ的连接ip地址直接替换localhost:15674就好其它的不用改
                this.client = Stomp.client("ws://localhost:15674/ws")
                //填写你rabbitMQ登录的用户名和密码
                var headers = {
                    "login": "guest",
                    "passcode": "guest",
                    //虚拟主机，默认“/”
                    "host": "/"
                };
                //创建连接,放入连接成功和失败回调函数
                this.client.connect(headers, this.onConnected, this.onFailed);

            }

        },
      
    }
</script>

<style>
    .el-tabs__nav-scroll {
        display: flex;
        justify-content: center;
    }

    .el-tabs__nav {
        /* width: 320px!important; */
        display: flex !important;
        justify-content: center !important;
    }

    .el-tabs__header {
        margin-bottom: 0 !important;
    }

    .dropdowns-dropBox {
        width: 100%;
        /* min-height: 330px;
        max-height: 450px; */
        max-height: 450px;
        overflow: hidden;
        position: relative;
    }

    .dropdowns-list-box {
        max-height: 450px;
        overflow-x: hidden;
        overflow-y: auto;
        -ms-overflow-style: none;
        overflow: '-moz-scrollbars-none';
        scrollbar-width: none; /*  火狐   */
        position: relative;
        padding-bottom: 40px;
        box-sizing: border-box;
    }

    .dropdowns-list-box::-webkit-scrollbar {
        display: none; /*  Chrome  */
        width: 0 !important; /*  Chrome  */
    }

    .dropdowns-list-box .drop:nth-last-child(1) {
        border-bottom: transparent;
    }

    .dropdowns-dropBox-scroll {
        width: 300px;
        /* height: 328px; */

    }

    .happy-scroll-container {
        width: 300px !important;
    }

    .happy-scroll-container .happy-scroll-content {
        /* padding-bottom: 38px !important; */
        box-sizing: border-box !important;
        width: 300px !important;
    }

    .happy-scroll-container {
        height: 290px !important;
    }

    .dropdowns-more {
        width: 100%;
        height: 30px;
        color: #2D8CF0;
        font-size: 14px;
        text-align: center;
        line-height: 30px;

    }

    .dropdowns-foots {
        width: 100%;
        height: 38px;
        background: #f7f7f7;
        font-size: 14px;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #515A6E;
        border-top: 1px solid #dcdfe6;
        position: absolute;
        bottom: -2px;
        left: 0;
    }

    .dropdowns-foots-iconsClear {
        width: 16px;
        height: 16px;
        margin-right: 5px;
        color: #515A6E;
        fill: currentColor;
    }

    .dropdowns-foots:hover {
        /* color: #2AB4FB!important; */
        background: #E8F6FD;
    }

    .disableds {
        pointer-events: none;
    }
</style>