<template>
    <div class="jwchat__body wrapper">
        <JwChat-index
                :style="tanlong"
                :config="config"
                :taleList="list"
                @enter="bindEnter"
                v-model="inputMsg"
                :showRightBox='true'
                scrollType="scroll"
                :winBarConfig="winBarConfig"
                :quickList="config.quickList"
                @clickTalk="talkEvent"
        >
            <!-- :toolConfig="tool" -->
            <JwChat-rightbox style="overflow-y: scroll" class="rightSlot" :config="rightConfig" @click="rightClick"/>
            <JwChat-talk class="rightSlot" :Talelist="talk" :config="quickConfig" @event="bindTalk"/>
            <template slot="tools">
                <div style="width:20rem;text-align:right;" @click="toolEvent(12)">
                    <JwChat-icon type="icon-lishi" title="自定义"/>
                </div>
            </template>
        </JwChat-index>
    </div>
</template>

<script>


    //聊天信息框
    var listData = []

    function getListArr(size) {
        const listSize = listData.length
        if (!size) {
            size = listSize
        }
        let result = []
        for (let i = 0; i < size; i++) {
            var item = listData[i]
            result.push(item)
        }
        return result
    }

    export default {
        components: {},

        beforeMount() {
            var getId = this.$route.query.id
            if (getId != undefined) {
                this.toUserId = getId;
                this.list = getListArr()
                this.winBarConfig.active = getId
            }
        },

        //获取当前用户信息
        created() {

            var userId = localStorage.getItem("token")
            //获取当前账号用户
            this.$axios
                .post("/consumer/user/getUser?userid=" + userId)
                .then(response => {
                    this.user = response.data

                    //获取左侧沟通过的用户
                    this.$axios
                        .post("/consumer/chatmessage/getMyMessage?myId=" + this.user.id)
                        .then(response => {
                            for (let i = 0, j = 1; i < response.data.length; i++, j++) {
                                this.friend.id = response.data[i].id
                                this.friend.name = response.data[i].name
                                this.friend.img = response.data[i].headpicture
                                this.friend.dept = response.data[i].signature
                                this.friend.readNum = 0
                                //先把defaultObject转换成字符串，然后再转换成对象赋值给newObject,可防止引用
                                var usernow = JSON.parse(JSON.stringify(this.friend));
                                this.winBarConfig.list.push(usernow)
                            }
                            this.winBarConfig.active = "-1"

                        })
                })

            //获取右侧所有用户
            this.$axios
                .post("/consumer/user/getAllUser")
                .then(response => {
                    for (let i = 0; i < response.data.length; i++) {
                        this.userone.img = response.data[i].headpicture
                        this.userone.name = response.data[i].name
                        this.userone.id = response.data[i].id

                        //先把defaultObject转换成字符串，然后再转换成对象赋值给newObject,可防止引用
                        var usernow = JSON.parse(JSON.stringify(this.userone));
                        this.rightConfig.list[i] = usernow
                        // this.rightConfig2.list[i] = usernow
                    }
                    this.rightConfig.listTip = "大厅用户"
                })


        },


        data() {
            return {
                //之前消息存数
                messageNowNum:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],

                //当前用户
                user: {},
                //好友用户
                friendList: {},
                //发送给的用户id,0代表公共聊天,默认进去是0
                toUserId: "0",
                //单个信息块
                message: {
                    "date": "",
                    "text": {"text": ""},
                    "mine": false,
                    "name": "",
                    "img": ""
                },
                //单个用户模块
                userone: {
                    name: '',
                    img: "",
                    id: 1,
                },
                //单个好友模块
                friend: {
                    id: '',
                    img: '',
                    name: '',
                    dept: '',
                    readNum: '0'
                },

                inputMsg: '',
                list: [],
                tool: {
                    // show: ['file', 'history', 'img', ['文件1', '', '美图']],
                    // showEmoji: false,
                    callback: this.toolEvent
                },
                rightConfig: {
                    listTip: '用户 Space+BackSpace',
                    // notice: '【公告】这是一款高度自由的聊天组件，基于AVue、Vue、Element-ui开发。点个赞再走吧 ',

                    list: [],
                    callback: this.bindWinBar,

                },
                rightConfig2: {
                    listTip: '当前在线',
                    // notice: '【公告】这是一款高度自由的聊天组件，基于AVue、Vue、Element-ui开发。点个赞再走吧 ',
                    list: []
                },
                quickConfig: {
                    nav: ['快捷回复'],
                    showAdd: true,
                    maxlength: 200,
                    showHeader: true,
                    showDeleteBtn: true,
                },
                talk: [
                    '我有好货，快私我！',
                    '衣衣不舍，年末再惠。',
                    '谁说好货不便宜，不信你来瞧。',
                    '品牌甩卖，只有更低',
                    '该出手就出手，让美丽不再冻人',
                    '大发，大发，花一件钱买到二件啦！',
                ],
                tanlong: {
                    width: "1100px",
                    height: "660px"
                },
                config: {
                    img: 'https://ts1.cn.mm.bing.net/th?id=OIP-C.cVzduBzpN28tTaUWRRlA_AAAAA&w=144&h=170&c=8&rs=1&qlt=90&o=6&dpr=1.25&pid=3.1&rm=2',
                    name: '公共聊天区',
                    dept: '注意语言，文明公共聊天区',
                    callback: this.bindCover,
                    historyConfig: {
                        show: true,
                        tip: '————————TOP————————',
                        callback: this.bindLoadHistory,
                    },
                    quickList: [
                        {text: '大甩卖！！！限时特惠', id: 3},
                        {text: '大发，大发，花一件钱买到二件啦！', id: 4},
                        {text: '谁将烟焚散，散了纵横的牵绊；听弦断，断那三千痴缠。', id: 5},
                        {text: '长夏逝去。山野间的初秋悄然涉足。', id: 6},
                        {text: '江南风骨，天水成碧，天教心愿与身违。', id: 7},
                        {text: '总在不经意的年生。回首彼岸。纵然发现光景绵长。', id: 8},
                        {text: '外面的烟花奋力的燃着，屋里的人激情的说着情话', id: 10},
                        {text: '假如你是云，我就是雨，一生相伴，风风雨雨；', id: 11},
                        {text: '即使泪水在眼中打转，我依旧可以笑的很美，这是你学不来的坚强。', id: 12},
                        {text: ' 因为不知来生来世会不会遇到你，所以今生今世我会加倍爱你。', id: 13},
                    ]
                },

                //左侧好友栏
                winBarConfig: {
                    active: 'win00',
                    width: '160px',
                    listHeight: '60px',
                    list: [{
                        id: 'win00',
                        img: 'https://ts1.cn.mm.bing.net/th?id=OIP-C.cVzduBzpN28tTaUWRRlA_AAAAA&w=144&h=170&c=8&rs=1&qlt=90&o=6&dpr=1.25&pid=3.1&rm=2',
                        name: '公共聊天区',
                        dept: '注意语言，文明公共聊天区',
                        readNum: 1
                    },
                    ],
                    callback: this.bindWinBar,

                }
            }
        },
        methods: {
            /**
             * @description: 点击加载更多的回调函数
             * @param {*}
             * @return {*}
             */
            bindLoadHistory() {

                this.$message.warning("已经到顶啦！")
            },
            talkEvent(play) {
                console.log(play)
                alert(1)
            },
            bindEnter(str) {
                const msg = this.inputMsg
                var s = this.getNowFormatTime;
                if (!msg) return;
                var tl = this
                const msgObj = {
                    "date": tl.getNowTime(),
                    "text": {"text": msg},
                    "mine": true,
                    "name": this.user.name,
                    "img": this.user.headpicture
                }
                this.list.push(msgObj)

                //发送到后端，进入redis存储
                this.$axios
                    .post("/consumer/chatmessage/sendMessage?toUserId=" + this.toUserId + "&content=" + msg + "&fromUserId=" + this.user.id)
                    .then(response => {
                    })
            },
            /**
             * @description:
             * @param {*} type 当前点击的按钮
             * @param {*} plyload 附加文件或者需要处理的数据
             * @return {*}
             */
            toolEvent(type, plyload) {
                console.log('tools', type, plyload)
            },
            bindCover(event) {
                console.log('header', event)
            },
            rightClick(type) {
                console.log('rigth', type)
                var id = type.value.id
                if (type.key == "name") {
                    this.winBarConfig.active = id
                    if (id === 'win00') {
                        //如果是点击的公共聊天室，则将toid设为0传到后端识别
                        this.toUserId = 0;
                        this.list = getListArr()
                    } else {
                        this.toUserId = id;
                        this.list = getListArr()
                    }

                }
            },
            bindTalk(play) {
                console.log('talk', play)
                const {key, value} = play
                if (key === 'navIndex')
                    return p;
                // this.talk = [1, 1, 1, 1, 1, 1, 1, 1].reduce((p) => {
                //     // p.push('暂未开放')
                //     return p
                // }, [])
                if (key === 'select') {
                    this.inputMsg = value
                    this.bindEnter()
                }
                if (key === 'delIndex') {
                    this.talk.splice(value, 1)
                }
                if (key === 'addTalk') {
                    this.talk.push(value)
                }
            },
            //点击左侧头像的回调函数
            bindWinBar(play = {}) {
                const {type, data = {}} = play
                console.log(play);
                if (type === 'winBar') {
                    const {id, dept, name, img} = data
                    this.config = {...this.config, id, dept, name, img}
                    this.winBarConfig.active = id
                    if (id === 'win00') {
                        //如果是点击的公共聊天室，则将toid设为0传到后端识别
                        this.toUserId = 0;
                        this.list = getListArr()
                    } else {
                        this.toUserId = id;
                        this.list = getListArr()
                    }


                    //清空红点
                    for (let i = 0; i < this.winBarConfig.list.length; i++) {

                        if (this.winBarConfig.list[i].id == this.toUserId) {
                            //无效效果
                            this.winBarConfig.list[i].readNum = "now"
                        } else if (this.toUserId == 0 && this.winBarConfig.list[0].readNum != "now") {
                            console.log(this.winBarConfig.list[0])
                            this.winBarConfig.list[0].readNum = "now"
                        } else {
                            this.winBarConfig.list[i].readNum = "0"
                        }
                    }

                }
                if (type === 'winBtn') {
                    const {target: {id} = {}} = data
                    const {list} = this.winBarConfig
                    this.winBarConfig.list = list.reduce((p, i) => {
                        if (id != i.id)
                            p.push(i)
                        return p
                    }, [])
                }
            },

            //获取当前时间并格式化
            getNowTime() {
                var date = new Date();
                var year = date.getFullYear(); //月份从0~11，所以加一
                var dateArr = [date.getMonth() + 1, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds()];
                for (var i = 0; i < dateArr.length; i++) {
                    if (dateArr[i] >= 1 && dateArr[i] <= 9) {
                        dateArr[i] = "0" + dateArr[i];
                    }
                }
                var strDate = year + '-' + dateArr[0] + '-' + dateArr[1] + ' ' + dateArr[2] + ':' + dateArr[3] + ':' + dateArr[4];
                return strDate
            },
            getMyMessage() {
                //获取==大厅==的对应对话框聊天信息！！！
                this.$axios
                    .post("/consumer/chatmessage/getMessage?toUserId=" + this.toUserId + "&fromUserId=" + this.user.id)
                    .then(response => {

                        // for (let i = 0; i < this.winBarConfig.list.length; i++) {
                        //
                        //     if (this.winBarConfig.list[i].id == this.toUserId) {
                        //         if(this.winBarConfig.list[i].readNum == 0){
                        //             this.winBarConfig.list[i].readNum = 0
                        //         }
                        //     }
                        // }
                        //获取动态信息数量变动

                        for (let i = 0; i < this.winBarConfig.list.length; i++) {

                            if(i == 0){
                                this.$axios
                                    .post("/consumer/chatmessage/getMessage?toUserId=" + 0 + "&fromUserId=" + this.user.id)
                                    .then(response => {
                                        if(response.data.length != this.messageNowNum[i]){
                                            this.winBarConfig.list[i].readNum = response.data.length - this.messageNowNum[i]
                                            this.messageNowNum[i] = response.data.length
                                        }
                                    })
                            }else{
                                this.$axios
                                    .post("/consumer/chatmessage/getMessage?toUserId=" + this.winBarConfig.list[i].id + "&fromUserId=" + this.user.id)
                                    .then(response => {
                                        if(response.data.length != this.messageNowNum[i]){
                                            this.winBarConfig.list[i].readNum = response.data.length - this.messageNowNum[i]
                                            this.messageNowNum[i] = response.data.length
                                        }
                                    })
                            }

                        }


                        var num = 0;
                        for (let i = 0; i < response.data.length; i++, num++) {
                            //先把defaultObject转换成字符串，然后再转换成对象赋值给newObject,可防止引用
                            var message = JSON.parse(JSON.stringify(this.message));
                            message.name = response.data[i].userName
                            message.date = response.data[i].updateTime
                            message.text.text = response.data[i].content
                            message.img = response.data[i].userHeadpic

                            //判断信息是对方还是自己
                            if (response.data[i].fromId == this.user.id) {
                                message.mine = true
                            } else {
                                message.mine = false
                            }

                            listData[i] = message

                        }
                        //如果是大厅消息，则没有清空操作
                        if (this.toUserId == 0) {

                        } else {
                            //好友消息，每次清空原数组重新加载新的对话消息
                            listData.length = num;
                        }

                        this.list = getListArr()

                    })
            },

            // getChange() {
            //
            //
            // }


        }
        ,
        mounted() {

            var my = this
            this.intervalid1 = setInterval(() => {
                my.getMyMessage()
            }, 1500);

            // this.intervalid2 = setInterval(() => {
            //     my.getChange()
            // }, 2000);
        },
        //当聊天页面关闭时停止刷新
        beforeDestroy() {
            clearInterval(this.intervalid1)
            // clearInterval(this.intervalid2)

        },
    }
</script>
<style scoped>
    .rightSlot {
        width: 300px;
        height: 50%;
        overflow: hidden;
        flex-direction: column;
    }
</style>
<style>
    .active {
        color: red;
    }

    .jwchat__body {
        margin-left: 170px;
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }
</style>