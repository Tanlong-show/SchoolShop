<template>
    <div class="Ad">
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
                            <i style="font-size: 20px;color: red" v-if="scope.row.type == '下单了'" class="el-icon-question">待办</i>

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
                    this.queryInfo.total = this.dynamicList.length
                })
        },

        data() {

            return {
                dynamicList: [],
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