<template>
    <div class="Placard">
        <!-- 添加 -->
        <el-row :gutter="20" class="Placard-Info">
            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2" :offset="1">
                <el-button type="primary" class="Placard-Info-li" size="small" @click="PlacardShow = true">添加
                </el-button>
            </el-col>
        </el-row>
        <!-- 查询列表 -->
        <el-row class="Placard-list">
            <el-col :span="24">
                <el-table
                        :data="this.informationList.slice((queryInfo.page - 1) * queryInfo.pageSize, queryInfo.page * queryInfo.pageSize)"
                        border
                        style="width: 100%">
                    <el-table-column
                            type="index"
                            label="序"
                            width="50">
                    </el-table-column>
                    <el-table-column
                            prop="keyword"
                            label="关键词"
                            width="220">
                    </el-table-column>
                    <el-table-column
                            prop="content"
                            label="公告内容"
                            width="220">
                    </el-table-column>
                    <el-table-column
                            prop="address"
                            label="链接地址"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="person"
                            label="操作者"
                            width="100">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="创建时间"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="endTime"
                            label="截止时间"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="updateTime"
                            label="更新时间">
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <!-- 分页 -->
        <el-row :gutter="20" class="goodsindex-list" style="float: right">
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

        <!-- 添加或编辑关键词 -->
        <el-dialog title="添加公告" :visible.sync="PlacardShow">
            <el-form :model="PlacardForm" :rules="PlacardRules" ref="PlacardForm">
                <el-form-item label="关键词" :label-width="PlacardWidth" prop="keyword">
                    <el-input v-model="PlacardForm.keyword" autocomplete="off" placeholder="请输入关键词"></el-input>
                </el-form-item>
                <el-form-item label="公告内容" :label-width="PlacardWidth" prop="content">
                    <el-input v-model="PlacardForm.content" autocomplete="off" placeholder="请输入备注"></el-input>
                </el-form-item>
                <el-form-item label="链接地址/备注" :label-width="PlacardWidth" prop="address">
                    <el-input v-model="PlacardForm.address" autocomplete="off" placeholder="请输入备注"></el-input>
                </el-form-item>
                <el-form-item label="截止时间" :label-width="PlacardWidth" prop="endTime">
                    <el-date-picker size="small" type="datetime" v-model="PlacardForm.endTime" placeholder="选择时间"
                                    value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm">
                    </el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="PlacardSubmit('PlacardForm'),PlacardShow = false" type="primary">添 加</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {

        created() {
            this.$axios
                .post("/consumer/information/getAllAnnounce", this.PlacardForm)
                .then(response => {
                    this.informationList = response.data
                    this.queryInfo.total = this.informationList.length
                })
        },


        data() {

            return {
                informationList: [],
                //搜索框分页
                queryInfo: {
                    name: '',
                    type: '',
                    type2: '',
                    page: 1,
                    pageSize: 10,
                    total: 0,
                },

                // tableData: [],
                // 弹出层
                PlacardShow: false,
                PlacardForm: {
                    keyword: '',
                    content: '',
                    address: '', //address
                    endTime: '', //截止时间
                },
                PlacardRules: {
                    keyword: [
                        {required: true, message: '请输入关键词', trigger: 'blur'},
                        {min: 2, max: 10, message: '长度为 2-15 个字符', trigger: 'blur'}
                    ],
                    content: [
                        {required: true, message: '请输入关键词', trigger: 'blur'},
                        {min: 2, max: 20, message: '长度为 2~20 个字符', trigger: 'blur'}
                    ],
                },
                PlacardWidth: '120px'
            }
        },
        methods: {
            //分页methods
            handleSizeChange(val) {
                this.queryInfo.pageSize = val;
            },
            handleCurrentChange(val) {
                this.queryInfo.page = val
            },

            PlacardSubmit() {
                this.$axios
                    .post("/consumer/information/publishAnnounce", this.PlacardForm)
                    .then(response => {
                    })
                this.$message.success("发布成功!")

            }
        }
    }
</script>

<style scoped>
    .Placard {
        width: 100%;
        min-height: 100%;
        padding: 15px;
        box-sizing: border-box;
    }

    .Placard-Info {
        width: 100%;
        margin-bottom: 15px;
    }

    .Placard-Info-li {
        width: 100%;
    }

    .Placard-list {
        width: 100%;
        margin-bottom: 20px;
    }

    .Placard-page-box {
        display: flex;
        justify-content: flex-end;
    }
</style>