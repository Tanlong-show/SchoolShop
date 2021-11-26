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
    const img = 'https://www.baidu.com/img/flexible/logo/pc/result.png'

    //聊天信息框
    const listData = [
        {
            "date": "2020/04/25 21:19:07",
            "text": {
                "text": "<i class='el-icon-document-checked' style='font-size:2rem;'/>",
                "subLink": {
                    "text": "a.txt",
                    "prop": {
                        underline: false
                    }
                },

            },
            "mine": false,
            "name": "留恋人间不羡仙",
            "img": "https://img0.baidu.com/it/u=3066115177,3339701526&fm=26&fmt=auto&gp=0.jpg"
        },
        {
            "date": "",
            "text": {"text": "起床不"},
            "mine": false,
            "name": "留恋人间不羡仙",
            "img": "https://img0.baidu.com/it/u=3066115177,3339701526&fm=26&fmt=auto&gp=0.jpg"
        },
        {
            "text": "2020/04/25 21:19:07",
            "type": 'tip'
        },
        {
            "date": "2020/04/25 21:19:07",
            "text": {"text": "<audio data-src='https://www.w3school.com.cn/i/horse.mp3'/>"},
            "mine": false,
            "name": "只盼流星不盼雨",
            "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
        },
        {
            "date": "2020/04/25 21:19:07",
            "text": {"text": "<img data-src='" + img + "'/>"},
            "mine": false,
            "name": "只盼流星不盼雨",
            "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
        },
        {
            "date": "2020/04/16 21:19:07",
            "text": {"text": "<video data-src='https://www.w3school.com.cn/i/movie.mp4' controls='controls' />"},
            "mine": true,
            "name": "JwChat",
            "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
        }, {
            "date": "2021/03/02 13:14:21",
            "mine": false,
            "name": "留恋人间不羡仙",
            "img": "https://img0.baidu.com/it/u=3066115177,3339701526&fm=26&fmt=auto&gp=0.jpg",
            "text": {
                system: {
                    title: '在接入人工前，智能助手将为您首次应答。',
                    subtitle: '猜您想问:',
                    content: [
                        {
                            id: `system1`,
                            text: '组件如何使用'
                        },
                        {
                            id: `system2`,
                            text: '组件参数在哪里查看'
                        },
                        {
                            id: 'system',
                            text: '我可不可把组件用在商业'
                        }
                    ]
                }
            }
        }
    ]

    function getListArr(size) {
        const listSize = listData.length
        if (!size) {
            size = listSize
        }
        let result = []
        for (let i = 0; i < size; i++) {
            const item = listData[Math.random() * listSize >> 0]
            item.id = Math.random().toString(16).substr(-6)
            result.push(item)
        }
        return result
    }

    export default {
        components: {},
        data() {
            return {
                inputMsg: '',
                list: [],
                tool: {
                    // show: ['file', 'history', 'img', ['文件1', '', '美图']],
                    // showEmoji: false,
                    callback: this.toolEvent
                },
                rightConfig: {
                    listTip: '当前在线',
                    // notice: '【公告】这是一款高度自由的聊天组件，基于AVue、Vue、Element-ui开发。点个赞再走吧 ',
                    list: [
                        {
                            name: 'JwChat',
                            "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg",
                            id: 1,
                        },
                        {
                            id: 2,
                            name: 'JwChat',
                            "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
                        },
                        {
                            id: 3,
                            name: 'JwChat',
                            "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
                        },
                        {
                            id: 4,
                            name: '留恋人间不羡仙',
                            "img": "https://img0.baidu.com/it/u=3066115177,3339701526&fm=26&fmt=auto&gp=0.jpg"
                        },
                        {
                            name: '只盼流星不盼雨',
                            "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
                        }
                    ]
                },
                rightConfig2: {
                    listTip: '当前在线',
                    // notice: '【公告】这是一款高度自由的聊天组件，基于AVue、Vue、Element-ui开发。点个赞再走吧 ',
                    list: [
                        {
                            name: '公共聊天区',
                            "img": "https://img1.baidu.com/it/u=2109725846,3376113789&fm=26&fmt=auto&gp=0.jpg"
                        },
                        {
                            name: '留恋人间不羡仙',
                            "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
                        },
                        {
                            name: '只盼流星不盼雨',
                            "img": "https://img1.baidu.com/it/u=2109725846,3376113789&fm=26&fmt=auto&gp=0.jpg"
                        }
                    ]
                },
                quickConfig: {
                    nav: ['快捷回复', '超级回复'],
                    showAdd: true,
                    maxlength: 200,
                    showHeader: true,
                    showDeleteBtn: true,
                },
                talk: [
                    '快捷回复1',
                    '快捷回复2',
                    '快捷回复3',
                    '快捷回复4',
                    '快捷回复5',
                    '快捷回复6',
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
                        tip: '加载更多',
                        callback: this.bindLoadHistory,
                    },
                    quickList: [
                        {text: '这里是jwchat，您想了解什么问题。', id: 3},
                        {text: 'jwchat是最好的聊天组件', id: 4},
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
                    active: 'win01',
                    width: '160px',
                    listHeight: '60px',
                    list: [{
                        id: 'win00',
                        img: 'https://ts1.cn.mm.bing.net/th?id=OIP-C.cVzduBzpN28tTaUWRRlA_AAAAA&w=144&h=170&c=8&rs=1&qlt=90&o=6&dpr=1.25&pid=3.1&rm=2',
                        name: '公共聊天区',
                        dept: '文明交流',
                        readNum: 1
                    },
                        {
                            id: 'win01',
                            img: 'https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg',
                            name: '阳光明媚爱万物',
                            dept: '沙拉黑油',
                            readNum: 12
                        },
                        {
                            id: 'win02',
                            img: 'https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg',
                            name: '只盼流星不盼雨',
                            dept: '最后说的话',
                            readNum: 12
                        },
                        {
                            id: 'win03',
                            img: 'https://img0.baidu.com/it/u=3066115177,3339701526&fm=26&fmt=auto&gp=0.jpg',
                            name: '留恋人间不羡仙',
                            dept: '这里可以放万物',
                            readNum: 0
                        },
                        {
                            id: 'win04',
                            img: 'https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg',
                            name: '阳光明媚爱万物',
                            dept: '官方客服'
                        }],
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
                const history = new Array(3).fill().map((i, j) => {
                    return {
                        "date": "2020/05/20 23:19:07",
                        "text": {"text": j + new Date()},
                        "mine": false,
                        "name": "公共聊天区",
                        "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
                    }
                })
                let list = history.concat(this.list)
                this.list = list
                console.log('加载历史', list, history)
            },
            talkEvent(play) {
                console.log(play)
                alert(1)
            },
            bindEnter(str) {
                const msg = this.inputMsg
                console.log(msg, str);
                if (!msg) return;
                const msgObj = {
                    "date": "2020/05/20 23:19:07",
                    "text": {"text": msg},
                    "mine": true,
                    "name": "公共聊天区",
                    "img": "https://img1.baidu.com/it/u=31094377,222380373&fm=26&fmt=auto&gp=0.jpg"
                }
                this.list.push(msgObj)
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
            },
            bindTalk(play) {
                console.log('talk', play)
                const {key, value} = play
                if (key === 'navIndex')
                    this.talk = [1, 1, 1, 1, 1, 1, 1, 1].reduce((p) => {
                        p.push('随机修改颜色 #' + Math.random().toString(16).substr(-6))
                        return p
                    }, [])
                if (key === 'select') {
                    this.inputMsg = value
                    this.bindEnter()
                }
                if (key === 'delIndex') {
                    this.talk.splice(value, 1)
                }
            },
            bindWinBar(play = {}) {
                const {type, data = {}} = play
                console.log(play);
                if (type === 'winBar') {
                    const {id, dept, name, img} = data
                    this.config = {...this.config, id, dept, name, img}
                    this.winBarConfig.active = id
                    if (id === 'win00') {
                        this.list = getListArr()
                    } else
                        this.list = getListArr(Math.random() * 4 >> 0)
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
            }
        },
        mounted() {
            this.list = getListArr()
        }
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