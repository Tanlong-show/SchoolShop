<template>
    <div class="Ad">
        <!-- 搜索条件 -->
        <el-row :gutter="20" class="goodsindex-queryInfo">
            <!-- 商品名称搜索 -->
            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                <el-input class="goodsindex-queryInfo-li" v-model="Order.name" clearable size="small"
                          placeholder="请输入产品名称"></el-input>
            </el-col>
            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                <el-input class="goodsindex-queryInfo-li" v-model="Order.code" clearable size="small"
                          placeholder="请输入订单编号"></el-input>
            </el-col>

            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="primary" @click="selectAdvance" class="goodsindex-queryInfo-li" size="small">多级搜索
                </el-button>
            </el-col>
            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="danger" @click="clearAdvanced" class="goodsindex-queryInfo-li" size="small">重置
                </el-button>
            </el-col>

        </el-row>
        <br>
        <!-- 查询列表 -->
        <el-row class="Ad-list">
            <el-col :span="24">
                <el-table
                        :data="this.dynamicList.slice((queryInfo.page - 1) * queryInfo.pageSize, queryInfo.page * queryInfo.pageSize)"
                        border
                        style="width: 100%">
                    <el-table-column
                            type="index"
                            label="序"
                            width="50">
                    </el-table-column>
                    <el-table-column
                            prop="ordNumber"
                            label="订单编号"
                            width="160">
                    </el-table-column>
                    <el-table-column
                            prop="goodName"
                            label="商品名称"
                            width="240">
                    </el-table-column>

                    <el-table-column
                            prop="buyerName"
                            label="买家名称"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="time"
                            label="订单更新时间"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="type"
                            label="买家操作"
                            width="170">
                    </el-table-column>
                    <el-table-column
                            prop="type"
                            label="状态"
                            width="170">
                        <template slot-scope="scope">
                            <i style="font-size: 20px;color: red" v-if="scope.row.type == '下单了'"
                               class="el-icon-question">待办</i>

                            <i style="font-size: 20px; color: green" v-else class="el-icon-s-claim">已解决</i>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="buyerMainId"
                            label="买家id"
                            width="160">
                        　
                        <template slot-scope="scope">
                            　
                            <el-button type="primary" @click="contactBuyer(scope.row.buyerMainId)">联系买家</el-button>
                        </template>
                    </el-table-column>

                </el-table>
            </el-col>
        </el-row>
        <!-- 分页 -->
        <el-row :gutter="20" class="goodsindex-list">
            <el-col :span="24" class="goodsindex-page-box">
                <el-pagination
                        :hide-on-single-page="false"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="queryInfo.page"
                        :page-sizes="[10, 20, 50, 100]"
                        :page-size="queryInfo.pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="queryInfo.total">
                </el-pagination>
            </el-col>
        </el-row>

    </div>
</template>

<script>
    export default {

        mounted() {
            var userId = localStorage.getItem("token")

            this.$axios
                .post("/consumer/user/getAllUserMessage?userid=" + userId)
                .then(response => {
                    this.dynamicList = response.data
                    this.dynamicList2 = response.data
                    this.queryInfo.total = this.dynamicList.length
                })
        },

        data() {

            return {
                Order: {
                    name: '',
                    code: '',
                },

                dynamicList: [],
                dynamicList2: [], //备用
                dynamicList3: [], //备用

                //搜索框分页
                queryInfo: {
                    name: '',
                    type: '',
                    type2: '',
                    page: 1,
                    pageSize: 10,
                    total: 0,
                },


            }
        },
        methods: {
            handleSizeChange(val) {
                this.queryInfo.pageSize = val;
            },
            handleCurrentChange(val) {
                this.queryInfo.page = val
            },
            //联系买家按钮
            contactBuyer(id) {
                this.$router.push({
                    path: this.$route.query.redirect || '/coupon/AddCoupon',
                    query: {id: id}
                })
            },

            selectAdvance() {
                var j = 0
                if (this.Order.code != '') {
                    if (this.Order.name != '') {
                        for (let i = 0; i < this.dynamicList2.length; i++) {
                            if(this.dynamicList2[i].ordNumber == this.Order.code && this.dynamicList2[i].goodName == this.Order.name){
                                this.dynamicList3[j++] = this.dynamicList2[i];
                            }
                        }
                        if(j != 0){
                            this.dynamicList = JSON.parse(JSON.stringify(this.dynamicList3));
                            this.dynamicList3.length = 0
                            j = 0;
                        }else{
                            this.$message.warning('未查询到数据')
                        }
                    }else{
                        for (let i = 0; i < this.dynamicList2.length; i++) {
                            if(this.dynamicList2[i].ordNumber == this.Order.code){
                                this.dynamicList3[j++] = this.dynamicList2[i];
                            }
                        }
                        if(j != 0){
                            this.dynamicList = JSON.parse(JSON.stringify(this.dynamicList3));
                            this.dynamicList3.length = 0
                            j = 0;
                        }else{
                            this.$message.warning('未查询到数据')
                        }
                    }
                } else if (this.Order.name != '') {
                    for (let i = 0; i < this.dynamicList2.length; i++) {
                        if(this.dynamicList2[i].goodName == this.Order.name){
                            this.dynamicList3[j++] = this.dynamicList2[i];
                        }
                    }
                    if(j != 0){
                        this.dynamicList = JSON.parse(JSON.stringify(this.dynamicList3));
                        this.dynamicList3.length = 0
                        j = 0;
                    }else{
                        this.$message.warning('未查询到数据')
                    }
                }else{
                    this.$message.error('查询条件未空')
                }


            },
            clearAdvanced() {
                this.Order.name = ''
                this.Order.code = ''
                this.dynamicList = JSON.parse(JSON.stringify(this.dynamicList2));
            },

        }
    }
</script>

<style scoped>
    .Ad {
        width: 100%;
        min-height: 100%;
        padding: 15px;
        box-sizing: border-box;
    }

    .Ad-Info {
        width: 100%;
        margin-bottom: 15px;
    }

    .Ad-Info-li {
        width: 100%;
    }

    .Ad-list {
        width: 100%;
        margin-bottom: 20px;
    }

    .Ad-page-box {
        display: flex;
        justify-content: flex-end;
    }
</style>