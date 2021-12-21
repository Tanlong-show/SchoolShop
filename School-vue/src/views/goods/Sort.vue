<template>
    <div class="sort">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
                <!-- 一级分类 -->
                <div class="sort-one">
                    <!-- 添加按钮 -->
                    <el-row class="sort-one-head">
                        <el-col :span="6">
                            <div class="sort-one-name">一级分类</div>
                        </el-col>
<!--                        <el-col :xs="12" :sm="8" :md="6" :lg="4" :xl="4" class="sort-one-add">-->
<!--                            <el-button type="primary" class="sort-one-add-submit" size="small" @click="addOneSort"> 添加-->
<!--                            </el-button>-->
<!--                        </el-col>-->
                    </el-row>
                    <!-- 一级分类列表 -->
                    <el-row class="sort-one-list">
                        <el-col :span="24">
                            <el-table
                                    :data="this.json.list.slice((queryInfo.page - 1) * queryInfo.pageSize, queryInfo.page * queryInfo.pageSize)"
                                    border
                                    style="width: 100%">
                                <el-table-column
                                        type="index"
                                        label="序"
                                        width="50">
                                </el-table-column>
                                <el-table-column
                                        prop="sortone"
                                        label="一级分类名称">
                                </el-table-column>
                                <el-table-column
                                        prop="address"
                                        label="售卖点">
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
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="12" :xl="12">
                <!-- 二级分类 -->
                <div class="sort-one">
                    <!-- 添加按钮 -->
                    <el-row class="sort-one-head">
                        <el-col :span="6">
                            <div class="sort-one-name">二级分类</div>
                        </el-col>
<!--                        <el-col :xs="12" :sm="8" :md="6" :lg="4" :xl="4" class="sort-one-add">-->
<!--                            <el-button type="primary" class="sort-one-add-submit" size="small" @click="addTwoSort"> 添加-->
<!--                            </el-button>-->
<!--                        </el-col>-->
                    </el-row>
                    <!-- 二级分类列表 -->
                    <el-row class="sort-one-list">
                        <el-col :span="24">
                            <el-table
                                    :data="this.json.list.slice((queryInfo.page - 1) * queryInfo.pageSize, queryInfo.page * queryInfo.pageSize)"
                                    border
                                    style="width: 100%">
                                <el-table-column
                                        type="index"
                                        label="序"
                                        width="50">
                                </el-table-column>
                                <el-table-column
                                        prop="sortone"
                                        label="所属一级分类">
                                </el-table-column>
                                <el-table-column
                                        prop="sorttwo"
                                        label="二级分类名称">
                                </el-table-column>
                                <el-table-column
                                        prop="address"
                                        label="售卖点">
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
            </el-col>
        </el-row>

    </div>
</template>

<script>
    export default {

        mounted() {

            this.$axios
                .post("/consumer/goods/getGoodsListByUserId?name="
                    + this.queryInfo.name + "&sortOneName=" + this.queryInfo.type + "&sortTwoName=" + this.queryInfo.type2)
                .then(response => {
                    this.json.list = response.data
                    // this.json.list[0].pictureUrl='https://img0.baidu.com/it/u=3481486975,4218348512&fm=26&fmt=auto'
                    this.queryInfo.total = response.data.length
                    //一级分类数据处理
                    for (let i = 0; i < this.json.list.length; i++) {
                        this.sortOne.value = this.json.list[i].sortone
                        //一旦有重复改变z值
                        var z = true;
                        //防止对象引用，重新生成对象
                        for (let j = 0; j < this.options.length; j++) {
                            //防止重复添加相同名称
                            if (this.options[j].value == this.sortOne.value) {
                                z = false;
                                break;
                            }
                        }
                        var s = JSON.parse(JSON.stringify(this.sortOne))

                        if (z && i != 0) {
                            this.options.push(s)
                        }
                        if (i == 0) {
                            this.options.push(s)
                        }
                    }

                })

        },

        data() {
            return {

                json: {

                    list: [],
                },

                //搜索框
                queryInfo: {
                    name: '',
                    type: '',
                    type2: '',
                    page: 1,
                    pageSize: 10,
                    total: 0,
                },

                //一二级对象公用即可
                sortOne: {
                    value: ""
                },
                //一级分类数据
                options: [],
                //二级分类数据
                options2: [],

                tableData: [],

            }
        },
        methods: {

            handleSizeChange(val) {
                this.queryInfo.pageSize = val;
            },
            handleCurrentChange(val) {
                this.queryInfo.page = val
            },
        }
    }
</script>

<style scoped>
    .sort {
        width: 100%;
        min-height: 100%;
        padding: 15px;
        box-sizing: border-box;
    }

    /* 一级分类 */
    .sort-one {
        width: 100%;
        height: auto;
        padding: 10px;
        box-sizing: border-box;
        border-radius: 5px;
        border: 1px solid #eeeeee;
    }

    .sort-one-name {
        width: 100%;
        height: 50px;
        line-height: 50px;
        padding-left: 20px;
        font-size: 16px;
        font-weight: bold;
        letter-spacing: 2px
    }

    .sort-one-add {
        display: flex;
        height: 50px;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .sort-one-add-submit {
        width: 100%;
    }

    .sort-one-head {
        width: 100%;
        margin-bottom: 15px;
    }

    .sort-one-list {
        width: 100%;
        margin-bottom: 20px;
    }

    .sort-one-page-line {
        width: 100%;
        display: flex;
        justify-content: flex-end;
    }
</style>