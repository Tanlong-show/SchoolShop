<template>
    <div class="CouponList">

        <!-- 搜索条件 -->
        <el-row :gutter="20" class="goodsindex-queryInfo">
            <!-- 商品名称搜索 -->
            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                <el-input class="goodsindex-queryInfo-li" v-model="queryInfo.name" clearable size="small" placeholder="请输入产品名称"></el-input>
            </el-col>
            <!-- 商品分类搜索 一级分类-->
            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                <el-select @change="changeSelectOne"  class="goodsindex-queryInfo-li" v-model="queryInfo.type" size="small" clearable placeholder="请选择一级分类">
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.value"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-col>

            <!-- 商品分类搜索 二级分类-->
            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                <el-select  class="goodsindex-queryInfo-li" v-model="queryInfo.type2" size="small" clearable placeholder="二级分类">
                    <el-option
                            v-for="item in options2"
                            :key="item.value"
                            :label="item.value"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-col>

            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="primary" @click="selectAdvance" class="goodsindex-queryInfo-li" size="small" >多级搜索</el-button>
            </el-col>
            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="danger" @click="clearAdvanced" class="goodsindex-queryInfo-li" size="small" >重置</el-button>
            </el-col>

        </el-row>
        <hr>

        <div class="box">
            <ul>
                <li v-for="(v, index) in json.list">
                    <img v-bind:src="v.pictureUrl" alt="">
                    <h3 style="font-weight: bolder;color: #606266">{{v.name}}</h3>
                    <nobr style="color: red;font-weight: bold">价格：{{v.price}}</nobr>
                    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                    <nobr style="font-weight: bold">剩余：</nobr>
                    {{v.number}}
                    <p style="font-weight: bold">发布时间：{{v.createTime}}</p>
                    <p style="font-weight: bold">交易地点：{{v.address}}</p>
                    <p style="font-weight: bold">产品分类：{{v.sortone}} - {{v.sorttwo}}</p>
                    <br>
                    <el-row :gutter="5">
                        <el-button style="width: 45%;margin-left: 7px;" icon="el-icon-circle-plus" type="primary" plain class="CouponList-Info-li" size="small"
                                   @click="addShop(index,v.price)">购物车
                        </el-button>
                        <el-button style="width: 45%;" type="success" icon="el-icon-bell" class="CouponList-Info-li" size="small"
                                   @click="">联系卖家
                        </el-button>
                    </el-row>
                </li>
            </ul>
        </div>


        <div style="position: fixed; width:1336px; height: 70px; right: 0px; top: 651px;">
            <div style="float: left; width: 50%;background-color: gold;height: 100%">
                <svg t="1634624238256" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5237" width="64" height="64"><path d="M522.24 104.448q2.048 0 5.12-2.048 6.144-4.096 16.384-14.336 12.288-11.264 24.576-16.384t32.768 3.072q10.24 3.072 16.384 8.704t11.776 10.752 12.288 8.704 16.896 3.584 17.408-3.072 13.824-5.632 13.312-3.584 15.872 2.048q10.24 4.096 13.824 11.776t2.048 15.872-7.168 16.384-13.824 13.312q-19.456 12.288-29.696 25.088t-20.48 27.136q-5.12 7.168-11.264 16.384t-11.264 18.432q-6.144 11.264-13.312 21.504l-211.968 0q-7.168-12.288-14.336-23.552-6.144-10.24-13.312-20.48t-12.288-18.432q-10.24-15.36-23.04-27.136t-25.088-20.992q-13.312-9.216-18.432-25.088t9.216-27.136q7.168-5.12 16.384-4.096t19.456 4.096 19.968 6.144 17.92 1.024q14.336-3.072 21.504-10.752t24.576-18.944q13.312-8.192 29.696-5.632t24.576 10.752q4.096 5.12 8.704 8.704t9.728 6.656q5.12 4.096 11.264 7.168zM637.952 309.248q12.288 16.384 30.72 35.84 15.36 17.408 37.888 39.424t52.224 46.592q24.576 21.504 50.688 50.688t45.568 68.096 27.136 89.6-2.56 113.152q-9.216 51.2-36.352 96.768t-72.192 79.36-107.008 53.76-140.8 19.968q-80.896 0-143.36-23.04t-105.984-59.904-69.12-82.432-33.792-90.624q-9.216-50.176-2.56-96.256t24.064-86.528 43.52-74.24 56.832-60.416q26.624-23.552 48.128-44.544t35.84-38.4q17.408-19.456 30.72-36.864l230.4 0zM529.408 623.616l-27.648-27.648-21.504-21.504q-10.24-10.24-14.336-15.36-8.192-7.168-21.504-11.776t-22.528 5.632q-8.192 11.264-4.096 22.016t9.216 16.896l15.36 14.336q11.264 12.288 24.576 26.624 15.36 16.384 33.792 34.816l-1.024 21.504-69.632 0q-9.216 0-15.872 7.168t-6.656 15.36 6.144 14.336 16.384 6.144l69.632 0 0 35.84-69.632 0q-9.216 0-15.872 5.632t-6.656 14.848q-1.024 8.192 5.632 13.824t16.896 5.632l69.632 0 0 20.48q1.024 14.336 9.216 19.968t20.48 5.632q12.288 1.024 19.968-7.68t7.68-17.92l0-20.48 71.68 0q10.24 0 16.896-4.608t5.632-14.848q0-21.504-28.672-21.504l-65.536 0 1.024-34.816 66.56-1.024q14.336 1.024 20.48-5.632t6.144-15.872q0-8.192-6.656-14.848t-20.992-6.656l-66.56 0 0-19.456q17.408-16.384 31.744-31.744 12.288-12.288 24.576-25.088t18.432-17.92q23.552-20.48 10.24-35.84-7.168-9.216-18.432-9.216t-20.48 8.192q-5.12 4.096-17.408 14.848t-24.576 22.016q-14.336 13.312-31.744 29.696z" p-id="5238"></path></svg>
                <span style="height: 100%; float: right; margin-top: 40px;margin-right: 150px; font-weight: bolder; font-size: 20px;">💴预估价格:￥{{totalPrice()}}</span>
            </div>
            <div style="float: left; width: 50%;background-color: black;height: 100%">
                <svg t="1634623424862" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4193" width="64" height="64"><path d="M774.7 812.9H362c-35.9 0-65.2-29.2-65.2-65.1v-27c0-35.9 29.2-65.2 65.2-65.2h412.8c12.1 0 21.9 9.8 21.9 21.9s-9.8 21.9-21.9 21.9H362c-11.8 0-21.4 9.6-21.4 21.4v27c0 11.8 9.6 21.3 21.4 21.3h412.8c12.1 0 21.9 9.8 21.9 21.9-0.1 12.1-9.9 21.9-22 21.9z" fill="#436DAA" p-id="4194"></path><path d="M772.8 699.4c-12.1 0-21.9-9.8-21.9-21.9s9.8-21.9 21.9-21.9c19.4 0 36-13.2 40.4-32l56.7-242c3-12.6 0-25.6-8-35.7-8-10.1-20-15.9-33-15.9h-546c-20.9 0-40.7-10.2-52.9-27.2l-118.1-168c-7-9.9-4.6-23.5 5.3-30.5 9.9-7 23.5-4.5 30.5 5.3l117.9 167.7c3.9 5.4 10.4 8.7 17.2 8.7h546c26.4 0 50.9 11.8 67.3 32.5s22.4 47.3 16.3 73l-56.7 242c-9 38.8-43.1 65.9-82.9 65.9zM422.8 928.3c-30 0-54.3-24.4-54.3-54.3 0-30 24.4-54.3 54.3-54.3 30 0 54.3 24.4 54.3 54.3s-24.4 54.3-54.3 54.3z m0-64.9c-5.8 0-10.5 4.7-10.5 10.5s4.7 10.5 10.5 10.5 10.5-4.7 10.5-10.5-4.7-10.5-10.5-10.5zM670.6 928.3c-30 0-54.3-24.4-54.3-54.3 0-30 24.4-54.3 54.3-54.3S725 844 725 873.9c0 30-24.4 54.4-54.4 54.4z m0-64.9c-5.8 0-10.5 4.7-10.5 10.5s4.7 10.5 10.5 10.5 10.5-4.7 10.5-10.5c0.1-5.8-4.7-10.5-10.5-10.5z" fill="#436DAA" p-id="4195"></path><path d="M731.5 465.9H340.8c-12.1 0-21.9-9.8-21.9-21.9 0-12.1 9.8-21.9 21.9-21.9h390.7c12.1 0 21.9 9.8 21.9 21.9 0 12.2-9.8 21.9-21.9 21.9zM660 590.3H412.2c-12.1 0-21.9-9.8-21.9-21.9 0-12.1 9.8-21.9 21.9-21.9H660c12.1 0 21.9 9.8 21.9 21.9 0 12.1-9.8 21.9-21.9 21.9z" fill="#FFBC00" p-id="4196"></path></svg>

                <span style="height: 100%; float: right; margin-top: 20px;margin-right: 150px; font-weight: bolder; font-size: 20px;">
                   <el-button icon="el-icon-milk-tea" type="success" plain  size="big"
                              @click="CouponListShow = true">结算
                   </el-button>
                </span>

            </div>

        </div>


        <!-- 添加或编辑关键词 -->

        <el-dialog title="购物车列表" :visible.sync="CouponListShow" style="height: 700px; font-weight: bolder">
            <el-scrollbar style="height:100%">
                <el-form>


                    <el-form-item v-for="(v,index) in json.list2" :label-width="CouponListWidth" prop="name">
                        <img style="float:left; width: 80px;height: 80px;" v-bind:src="v.pictureUrl" alt="">
                        <span style="float: right">￥{{v.price}}</span>
                        &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
                        <span style="float: right">{{v.name}}</span>
                        <el-button type="warning" size="small" style="float: right;margin-right: 20px;" @click="clearOne(index)">删除</el-button>

                    </el-form-item>

                    <span style="float: right;color: gold">总预估：￥{{totalPrice()}}</span>

                </el-form>
            </el-scrollbar>

            <div slot="footer" class="dialog-footer">
                <el-button type="danger" style="float: left;" @click="clearList">清空</el-button>
                <el-button @click="CouponListSubmit()" :disabled="totalPrice()==0 ? true : false" type="success">提交</el-button>
            </div>
        </el-dialog>
    </div>


</template>

<script>

    export default {

        created() {

        },

        mounted() {

            this.$axios
                .post("/consumer/goods/getGoodsList")
                .then(response => {
                    this.json.list = response.data
                    // this.json.list[0].pictureUrl='https://img0.baidu.com/it/u=3481486975,4218348512&fm=26&fmt=auto'

                    //一级分类数据处理
                    for (let i = 0; i < this.json.list.length; i++) {
                        this.sortOne.value = this.json.list[i].sortone
                        //一旦有重复改变z值
                        var z = true;
                        //防止对象引用，重新生成对象
                        for (let j = 0; j < this.options.length; j++) {
                            //防止重复添加相同名称
                            if(this.options[j].value == this.sortOne.value){
                               z = false;
                               break;
                            }
                        }
                        var s = JSON.parse(JSON.stringify(this.sortOne))

                        if(z && i != 0){
                            this.options.push(s)
                        }
                        if(i == 0){
                            this.options.push(s)
                        }
                    }

                })

        },




        data() {


            return {
                tableData: [],
                // 弹出层
                CouponListShow: false,

                CouponListWidth: '120px',
                json: {

                    list: [],
                    list2: [],
                    totalmoney: 0
                },

                //搜索框
                queryInfo:{
                    name: '',
                    type: '',
                    type2:'',
                    page: 1 ,
                    pageSize: 10
                },


                //一二级对象公用即可
                sortOne :{
                    value:""
                },
                //一级分类数据
                options: [

                ],
                //二级分类数据
                options2: [

                ],


            }
        },
        methods: {
            CouponListSubmit() {
                this.$axios
                    .post("/consumer/ordering/addOrdering",this.json.list2)
                    .then(response => {

                    })
                this.clearList();
                this.$message.success('已进入订单');

            },

            addShop(id, money){
                this.json.totalmoney += money;
                this.json.list2.push(this.json.list[id]);
            },

            totalPrice(){

                return this.json.totalmoney;
            },

            clearList(){
                this.json.totalmoney = 0;
                this.json.list2.length=0;
            },
            clearOne(index){
                // for (let i = index; i < this.json.list2.length; i++) {
                //     this.json.list2[i] = this.json.list2[i+1];
                // }
                // this.json.list2.reduce();
                this.json.totalmoney -= this.json.list2[index].price;

                this.json.list2.splice(index,1)
            },

            //一级分类改变条件触发
            changeSelectOne(data){
                this.options2.length = 0;
                this.queryInfo.type2 = "";
                //一级分类数据处理
                for (let i = 0; i < this.json.list.length; i++) {
                    if(this.json.list[i].sortone == data){
                        this.sortOne.value = this.json.list[i].sorttwo
                        //一旦有重复改变z值
                        var z = true;
                        //防止对象引用，重新生成对象
                        for (let j = 0; j < this.options2.length; j++) {
                            //防止重复添加相同名称
                            if(this.options2[j].value == this.sortOne.value){
                                z = false;
                                break;
                            }
                        }
                        var s = JSON.parse(JSON.stringify(this.sortOne))

                        if(z && i != 0){
                            this.options2.push(s)
                        }
                        if(i == 0){
                            this.options2.push(s)
                        }
                    }
                }

            },
            selectAdvance(){
                this.queryInfo.type = this.queryInfo.type
                this.$axios
                    .post("/consumer/goods/getGoodsAdvanced?name="
                        +this.queryInfo.name+"&sortOneName="+this.queryInfo.type+"&sortTwoName="+this.queryInfo.type2)
                    .then(response => {
                        if(response.data.length == 0){
                            this.$message.warning('暂无对应数据');
                        }else{
                            this.$message.success('查询成功');
                            this.json.list = response.data;
                        }
                    })
            },
            clearAdvanced(){
                this.queryInfo.name = this.queryInfo.type = this.queryInfo.type2 = '';
                this.selectAdvance();
            },

        },

    }
</script>

<style scoped>

    .CouponList {
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
