<template>
    <div class="produce" style="background-color: seashell">
        <div class="box">
            <ul>
                <li v-for="(v, index) in json.list">
                    <img v-bind:src="v.goods.pictureUrl" alt="">
                    <h3 style="font-weight: bolder;color: #606266;overflow:hidden;white-space:nowrap;
                    width: 160px;text-overflow:ellipsis; ">{{v.goods.name}}</h3>
                    <nobr style="color: red;font-weight: bold">价格：{{v.goods.price}}</nobr>
                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                    <nobr style="font-weight: bold">剩余：</nobr>
                    {{v.goods.number}}
                    <p style="font-weight: bold">发布时间：{{v.goods.createTime}}</p>
                    <p style="font-weight: bold">交易地点：{{v.goods.address}}</p>
                    <p style="font-weight: bold">产品分类：{{v.goods.sortone}} - {{v.goods.sorttwo}}</p>
                    <p style="font-weight: bold">秒杀介绍：{{v.content}}</p>
                    <p style="font-weight: bold; color: red">秒杀倒计时：{{timeList[index]}}</p>

                    <br>
                    <el-row :gutter="5">
                        <el-button style="width: 45%;margin-left: 7px;" icon="el-icon-circle-plus" type="primary" plain
                                   :disabled="timeList[index] != '已结束'" class="CouponList-Info-li" size="small"
                                   @click="addShop(index,v.price)">秒买
                        </el-button>
                        <el-button style="width: 45%;" type="success" icon="el-icon-bell" class="CouponList-Info-li"
                                   size="small"
                                   @click="">联系卖家
                        </el-button>
                    </el-row>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
    export default {

        mounted() {
            this.$axios
                .post("/consumer/flashsale/getAllFlashSale")
                .then(response => {
                    this.json.list = response.data
                    for (let i = 0; i < this.json.list.length; i++) {
                        var t = this.countTime(this.json.list[i].startTime)
                        this.timeList.push(t)
                    }
                })
            var my = this
            this.intervalid1 = setInterval(() => {
                my.cutTime()
            }, 1000);

        },
        data() {
            return {
                json: {
                    list: '',
                },
                timeList:[],
            }
        },

        methods: {
            countTime(start) {
                var d, h, m, s;
                //获取当前时间
                var date = new Date();
                var now = date.getTime();
                //设置截止时间
                var endDate = new Date(start);
                var end = endDate.getTime();
                //时间差
                var leftTime = end - now;
                //定义变量 d,h,m,s保存倒计时的时间
                if (leftTime >= 0) {
                    this.d = Math.floor(leftTime / 1000 / 60 / 60 / 24);
                    this.h = Math.floor(leftTime / 1000 / 60 / 60 % 24);
                    this.m = Math.floor(leftTime / 1000 / 60 % 60);
                    this.s = Math.floor(leftTime / 1000 % 60);
                    return this.d+"天"+this.h+"时"+this.m+"分"+this.s+"秒"
                }else{
                    return "已结束"
                }

            },

            cutTime(){
                this.timeList.length = 0
                for (let i = 0; i < this.json.list.length; i++) {
                    var t = this.countTime(this.json.list[i].startTime)
                    this.timeList.push(t)
                }
            }
        },
        //当聊天页面关闭时停止刷新
        beforeDestroy() {
            clearInterval(this.intervalid1)

        },

    }


</script>
<style>
    .produce {
        width: 100%;
        min-height: 100%;
        padding: 15px;
        box-sizing: border-box;
    }

    .CouponList-Info {
        width: 100%;
        margin-bottom: 15px;
    }

    .CouponList-Info-li {
        width: 100%;
    }

    .CouponList-list {
        width: 100%;
        margin-bottom: 20px;
    }

    .CouponList-page-box {
        display: flex;
        justify-content: flex-end;
    }

    .box ul {
        display: flex;
        flex-wrap: wrap;
    }

    .box li {
        padding: 3px;
        list-style: none;
        margin-right: 15px;
        border: 1px solid #eee;
    }

    .box img {
        width: 200px;
        height: 150px;
    }
</style>