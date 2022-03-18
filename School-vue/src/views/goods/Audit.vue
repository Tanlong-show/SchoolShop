<template>
    <div class="WeChat">
        <div class="tl" id="tanlong">
            <ul class="timeline">
                <li class="timeline__line"></li>
                <li class="timeline__item start">
                    <div class="info">
                        <div class="dot"></div>
                        <time class="time">08:30</time>
                        <h4 class="title">检索...</h4>
                    </div>
                </li>
                <li v-for="audit in goodFrom" class="timeline__item">
                    <div class="info">
                        <div class="dot"></div>
                        <time class="time">{{audit.time}}</time>
                        <icon class="el-icon-star-on"></icon>
                        <h4 class="title">
                            {{audit.people}}
                        </h4>
                    </div>
                    <div class="content">
                        {{audit.content}}
                        <el-button v-if="audit.content.indexOf('审核')!=-1">
                            加急
                        </el-button>
                    </div>
                </li>

                <li class="timeline__item end">
                    <div class="info">
                        <div class="dot"></div>
                        <time class="time">18:30</time>
                        <h4 class="title">结束</h4>
                    </div>
                </li>
            </ul>

        </div>

        <div style="margin-left: 20px;">
            <span style="margin-left: 20px;font-weight: bold">商品名称: {{good.name}}</span>
            <img v-bind:src="good.pictureUrl" style="width: 100px; height: 100px;" alt="">
        </div>


        <hr>

        <div style="margin-left: 20px;">
            <span style="margin-left: 20px;font-weight: bold">查询起始时间：
                <el-date-picker size="small" type="datetime" v-model="timeStart" placeholder="选择时间"
                                value-format="yyyy-MM-dd&HH:mm" format="yyyy-MM-dd&HH:mm">
                </el-date-picker>
            </span>
            <hr>


            <hr style="width: 50px; color: #1AB8B8; border-width: 10px;">
            <span style="margin-left: 20px;font-weight: bold" icon="el-icon-search">查询截止时间：
                <el-date-picker size="small" type="datetime" v-model="timeEnd" placeholder="选择时间"
                                value-format="yyyy-MM-dd&HH:mm" format="yyyy-MM-dd&HH:mm">
                </el-date-picker>
            </span>
            <hr>
            <span style="margin-top:20px;margin-left: 20px;font-weight: bold">
                <el-button icon="el-icon-caret-right" type="primary">{{timeStart}}</el-button>
            </span>
            <br>
            <span style="margin-left: 100px">~</span>
            <br>
            <span style="margin-top:20px;margin-left: 20px;font-weight: bold">
                <el-button icon="el-icon-caret-right" type="primary">{{timeEnd}}</el-button>
            </span>

            <span style="margin-top:20px;margin-left: 20px;font-weight: bold">
                <el-button :disabled="timeEnd == '' || timeStart == ''" icon="el-icon-search"
                           @click="findbyTime" style="color:white;background-color: #1AB8B8">查询</el-button>
            </span>
            <span style="margin-top:20px;margin-left: 10px;font-weight: bold">
                <el-button icon="el-icon-warning"
                           @click="initTime" style="color:white;background-color: salmon">重置</el-button>
            </span>

            <hr style="width: 50px; color: #1AB8B8; border-width: 10px;">
            <el-button style="margin-left: 20px;font-weight: bold" icon="el-icon-info">
                <el-switch
                        style=""
                        v-model="choice"
                        active-color="#13ce66"
                        inactive-color="#ff4949"
                        active-text="启用"
                        inactive-text="高级查询：禁用">
                </el-switch>
            </el-button>

            <div style="margin-left: 20px;font-weight: bold">
                <el-button v-for="(keyWord, i) in highList" style="margin-top:20px;margin-left: 20px;font-weight: bold"
                           icon="el-icon-search">
                    <el-input
                            placeholder="高亮关键字"
                            v-model="highList[i]"
                            :disabled="!choice">
                    </el-input>
                    <input type="color" v-model="colorList[i]">

                </el-button>
            </div>

            <el-button icon="el-icon-s-open" @click="onAdd"
                       style="margin-left: 20px; margin-top: 20px; font-weight: bold;" type="success"
                       v-if="choice">
                添加
            </el-button>
            <el-button icon="el-icon-remove" @click="onSub"
                       style="margin-left: 20px; margin-top: 20px; font-weight: bold;" type="danger"
                       v-if="choice">
                清除
            </el-button>
            <el-button icon="el-icon-s-tools" @click="onHigh"
                       style="margin-left: 20px; margin-top: 20px; font-weight: bold;" type="warning"
                       v-if="choice">
                设置
            </el-button>
            <el-button icon="el-icon-loading" @click="onInit"
                       style="margin-left: 20px; margin-top: 20px; font-weight: bold;" type="primary"
                       v-if="choice">
                刷新
            </el-button>
        </div>
    </div>

</template>

<script>


    export default {
        mounted() {
            var id = this.$route.query.id
            this.id = id;
            this.$axios
                .post("/consumer/goods/getAuditShow?id=" + this.id)
                .then(response => {
                    this.goodFrom = response.data

                })
            //查找商品信息
            this.$axios
                .post("/consumer/goods/getGoodsById?id=" + this.id)
                .then(response => {
                    this.good = response.data
                })

        },
        data() {
            return {
                id: '',
                good: '',
                goodFrom: [],
                goodFrom2: [], //用来存查询时间后的数据
                //存储关键高亮字词
                highList: [],
                colorList: [],
                timeStart: '',
                timeEnd: '',
                choice: 'false'
            }
        },
        methods: {
            onAdd() {
                this.highList.push('')
            },

            onSub() {
                this.highList.pop();
            },

            onHigh() {
                for (let i = 0; i < this.highList.length; i++) {
                    if (this.highList[i] != '') {
                        var str = this.highList[i]
                        this.onHighFind(str, this.colorList[i])
                    }
                }
            },
            onInit() {
                location.reload()

            },

            //高亮设置
            onHighFind(searchVal, bgColor) {
                var oDiv = document.getElementById("tanlong");
                var sText = oDiv.innerHTML;
                var reg1 = /<script[^>]*>(.|\n)*<\/script>/gi; //去掉script标签
                sText = sText.replace(reg1, "");
                var bgColor = bgColor || "orange";
                var num = -1;
                var rStr = new RegExp(searchVal, "gi"); //匹配传入的搜索值不区分大小写 i表示不区分大小写，g表示全局搜索
                var rHtml = new RegExp("\<.*?\>", "ig");//匹配html元素
                var aHtml = sText.match(rHtml); //存放html元素的数组
                var arr = sText.match(rStr);
                var a = -1;
                sText = sText.replace(rHtml, '{~}');  //替换html标签
                sText = sText.replace(rStr, function () {
                    a++;
                    return "<span name='addSpan' style='background-color: " + bgColor + ";'>" + arr[a] + "</span>"
                }); //替换key
                sText = sText.replace(/{~}/g, function () {  //恢复html标签
                    num++;
                    return aHtml[num];
                });
                oDiv.innerHTML = sText;
            },

            //select时间大于等于now则返回true
            myCompareTime(select, now) {
                var yearone = select.substring(0, 4);
                var monthone = select.substring(5, 7);
                var dayone = select.substring(8, 10);
                var hourone = select.substring(11, 13);
                var minone = select.substring(14);

                var yeartwo = now.substring(0, 4);
                var monthtwo = now.substring(5, 7);
                var daytwo = now.substring(8, 10);
                var hourtwo = now.substring(11, 13);
                var mintwo = now.substring(14);
                console.log(yearone + " " + monthone + " " + dayone + " " + hourone + " " + minone)
                if (yearone > yeartwo) {
                    return true
                } else if (yearone == yeartwo) {
                    if (monthone > monthtwo) {
                        return true
                    } else if (monthone == monthtwo) {
                        if (dayone > daytwo) {
                            return true
                        } else if (dayone == daytwo) {
                            if (hourone > hourtwo) {
                                return true
                            } else if (hourone == hourtwo) {
                                if (minone > mintwo) {
                                    return true
                                } else if (minone == mintwo) {
                                    return true;
                                } else {
                                    return false
                                }
                            } else {
                                return false
                            }
                        } else {
                            return false
                        }
                    } else {
                        return false
                    }
                } else {
                    return false;
                }

            },

            //必须精确到分钟
            findbyTime() {
                var flag = true
                var s
                if (this.goodFrom2.length != 0) {
                    //深拷贝
                    this.goodFrom = JSON.parse(JSON.stringify(this.goodFrom2))
                }
                this.goodFrom2.length = 0

                for (let i = 0; i < this.goodFrom.length; i++) {
                    var str = this.goodFrom[i].time.substring(5, 21)
                    //时间位于两个端点间就添加进来
                    if (this.myCompareTime(this.timeEnd, str) && this.myCompareTime(str, this.timeStart)) {
                        this.goodFrom2.push(this.goodFrom[i])
                    }
                }
                var z = this.goodFrom;
                this.goodFrom = this.goodFrom2;
                this.goodFrom2 = z;
            },
            initTime() {
                if (this.goodFrom2.length != 0) {
                    this.goodFrom = this.goodFrom2;
                    this.goodFrom2 = '';
                }
                this.timeEnd = '';
                this.timeStart = '';
            },


        }


    }

</script>


<style scoped>


    .tl {
        float: left;
        margin-left: 10px;
        width: 60%;
        height: 110%;

        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
        background: black;
    }

    .timeline {
        position: relative;
        display: grid;
        gap: 40px;
        padding: 0;
        margin: 0;
        max-width: 600px;
        font-size: 0.8rem;
        line-height: 1;
        color: white;
        list-style-type: none;
        clip-path: inset(0 0 100% 0);
        animation: expand 3s forwards linear;
    }

    .timeline .timeline__line {
        position: absolute;
        top: 0;
        left: 6px;
        width: 4px;
        height: 100%;
        background: white;
    }

    .timeline .timeline__item .info {
        display: grid;
        grid-template-columns: repeat(3, auto) 1fr;
        align-items: center;
        gap: 0.3rem;
    }

    .timeline .timeline__item .info h4 {
        margin: 0;
    }

    .timeline .timeline__item .info a {
        text-decoration: none;
        color: #3498db;
    }

    .timeline .timeline__item .info .dot {
        position: relative;
        width: 16px;
        height: 16px;
        background: #1a1e23;
        border-radius: 50%;
    }

    .timeline .timeline__item .info .dot::before {
        position: absolute;
        content: "";
        top: -4px;
        left: -4px;
        right: -4px;
        bottom: -4px;
        background: #1a1e23;
    }

    .timeline .timeline__item .info .dot::after {
        position: absolute;
        content: "";
        top: 0;
        left: 0;
        width: 8px;
        height: 8px;
        border: 4px solid white;
        border-radius: inherit;
    }

    .timeline .timeline__item .info .time {
        margin-right: 8px;
    }

    .timeline .timeline__item .content {
        margin: 1rem 0 0 3.9rem;
        line-height: 1.5;
    }

    @keyframes expand {
        to {
            clip-path: inset(0 0 0 0);
        }
    }


</style>