<template>
    <div class="goodsindex">
        <!-- 搜索条件 -->
        <el-row :gutter="20" class="goodsindex-queryInfo">
            <!-- 商品名称搜索 -->
            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                <el-input class="goodsindex-queryInfo-li" v-model="queryInfo.name" clearable size="small"
                          placeholder="请输入产品名称"></el-input>
            </el-col>
            <!-- 商品分类搜索 一级分类-->
            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                <el-select @change="changeSelectOne" class="goodsindex-queryInfo-li" v-model="queryInfo.type"
                           size="small" clearable placeholder="请选择一级分类">
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
                <el-select class="goodsindex-queryInfo-li" v-model="queryInfo.type2" size="small" clearable
                           placeholder="二级分类">
                    <el-option
                            v-for="item in options2"
                            :key="item.value"
                            :label="item.value"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-col>

            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="primary" @click="selectAdvance" class="goodsindex-queryInfo-li" size="small">多级搜索
                </el-button>
            </el-col>
            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="danger" @click="clearAdvanced" class="goodsindex-queryInfo-li" size="small">重置
                </el-button>
            </el-col>

            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2">
                <el-button type="success" class="goodsindex-queryInfo-li" size="small" @click="addFromGood">添加
                </el-button>
            </el-col>
        </el-row>

        <hr>


        <!-- 检索结果 -->
        <el-row :gutter="20" class="goodsindex-list">
            <el-col :span="24">
                <el-table
                        :data="this.json.list.slice((queryInfo.page - 1) * queryInfo.pageSize, queryInfo.page * queryInfo.pageSize)"
                        border
                        size='small'
                        style="width: 100%">
                    <el-table-column
                            type="index"
                            label="序"
                            width="50">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="产品名称"
                            width="150">
                    </el-table-column>
                    <el-table-column
                            prop="pictureUrl"
                            label="产品图片"
                            width="160">
                        　
                        <template slot-scope="scope">
                            　　　　<img :src="scope.row.pictureUrl" width="60" height="60"/>
                            　
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="sortone"
                            label="一级分类"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="sorttwo"
                            label="二级分类"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="price"
                            label="价格(元)"
                            width="80">
                    </el-table-column>
                    <el-table-column
                            prop="address"
                            label="交易地点"
                            width="80">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="创建时间"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="下架"
                            width="150">
                        <template slot-scope="scope">　　
                            <el-button type="danger"
                                       v-if="scope.row.state == 0 ? false : true && scope.row.state != 2 && scope.row.state != 3 && scope.row.state != 4"
                                       @click="lowerShelfById(scope.row.id),scope.row.state = 0"
                                       class="goodsindex-queryInfo-li"
                                       size="small">下架
                            </el-button>
                            <el-button type="success"
                                       v-if="scope.row.state == 1 ? false : true && scope.row.state != 2 && scope.row.state != 3 && scope.row.state != 4"
                                       @click="lowerShelfById(scope.row.id),scope.row.state = 1"
                                       class="goodsindex-queryInfo-li"
                                       size="small">上架
                            </el-button>
                            <el-button :disabled="true" type="warning" v-if="scope.row.state == 2"
                                       class="goodsindex-queryInfo-li"
                                       size="small">创建审核中
                            </el-button>
                            <el-button :disabled="true" type="warning" v-if="scope.row.state == 3"
                                       class="goodsindex-queryInfo-li"
                                       size="small">修改审核中
                            </el-button>
                            <el-button style="color: red" v-if="scope.row.state == 4">
                                <svg t="1637656350008" class="icon" viewBox="0 0 1024 1024"
                                     version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1942" width="32" height="32">

                                    <path d="M86.016 0L2.048 70.656c149.504 111.616 288.768 239.616 411.136 367.616C225.792 627.2 78.848 813.056 1.536 887.808L161.28 1021.44c56.832-117.248 180.224-294.912 345.6-481.28C672.256 727.552 796.16 906.24 852.992 1024c0 0 155.648-165.376 169.472-139.776C962.56 816.64 816.128 620.032 619.52 418.816c112.64-115.712 239.104-230.4 374.272-331.264l-36.864-68.608c-153.088 76.288-299.008 189.44-430.08 309.76C394.752 203.264 245.248 83.968 86.016 0z"
                                          p-id="1943" fill="#d81e06"></path>
                                </svg>
                                被驳回
                            </el-button>


                        </template>

                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="修改信息"
                            width="150">
                        <template slot-scope="scope">　　
                            <el-button type="primary" @click="modifyGood(scope.row.id)" class="goodsindex-queryInfo-li"
                                       size="small">修改信息
                            </el-button>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="修改信息"
                            width="100">
                        <template slot-scope="scope">　　
                            <el-button type="primary" @click="checkGood(scope.row.id)" class="goodsindex-queryInfo-li"
                                       size="small">审计
                            </el-button>
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
            var validatelinl = (rule, value, callback) => {
                let linlRG = /^(((ht|f)tps?):\/\/)?[\w-]+(\.[\w-]+)+([\w.,@?^=%&:/~+#-]*[\w@?^=%&/~+#-])?$/
                if (value === '') {
                    callback(new Error('请输入链接地址'));
                } else if (!linlRG.test(value)) {
                    callback(new Error('请输入正确的网址'));
                } else {
                    callback();
                }
            };


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
            }
        },
        methods: {
            handleSizeChange(val) {
                this.queryInfo.pageSize = val;
            },
            handleCurrentChange(val) {
                this.queryInfo.page = val
            },

            //修改产品跳转
            modifyGood(val) {
                this.$router.push({
                    path: this.$route.query.redirect || '/goods/fromgood',
                    query: {id: val}
                })
            },

            //审计跳转
            checkGood(val) {
                this.$router.push({
                    path: this.$route.query.redirect || '/goods/audit',
                    query: {id: val}
                })
            },

            //添加产品跳转
            addFromGood() {
                this.$router.push({
                    path: this.$route.query.redirect || '/goods/fromgood',
                    query: {id: 0}
                })
            },


            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            },

            /////////一二级分类
            //一级分类改变条件触发
            changeSelectOne(data) {
                this.options2.length = 0;
                this.queryInfo.type2 = "";
                //一级分类数据处理
                for (let i = 0; i < this.json.list.length; i++) {
                    if (this.json.list[i].sortone == data) {
                        this.sortOne.value = this.json.list[i].sorttwo
                        //一旦有重复改变z值
                        var z = true;
                        //防止对象引用，重新生成对象
                        for (let j = 0; j < this.options2.length; j++) {
                            //防止重复添加相同名称
                            if (this.options2[j].value == this.sortOne.value) {
                                z = false;
                                break;
                            }
                        }
                        var s = JSON.parse(JSON.stringify(this.sortOne))

                        if (z && i != 0) {
                            this.options2.push(s)
                        }
                        if (i == 0) {
                            this.options2.push(s)
                        }
                    }
                }

            },
            selectAdvance() {
                this.queryInfo.type = this.queryInfo.type
                this.$axios
                    .post("/consumer/goods/getGoodsListByUserId?name="
                        + this.queryInfo.name + "&sortOneName=" + this.queryInfo.type + "&sortTwoName=" + this.queryInfo.type2)
                    .then(response => {
                        if (response.data.length == 0) {
                            this.$message.warning('暂无对应数据');
                        } else {
                            this.$message.success('查询成功');
                            this.json.list = response.data;
                        }
                    })
                this.queryInfo.page = 1;
            },
            clearAdvanced() {
                this.queryInfo.name = this.queryInfo.type = this.queryInfo.type2 = '';
                this.selectAdvance();
            },
            //上下架通用
            lowerShelfById(id) {
                this.$axios
                    .post("/consumer/goods/lowerShelfById?id=" + id)
                this.$message.success("修改成功");

            },


        }
    }
</script>

<style scoped>
    .goodsindex {
        width: 100%;
        min-height: 100%;
        padding: 15px;
        box-sizing: border-box;
    }

    /* 搜索 */
    .goodsindex-queryInfo {
        margin-bottom: 10px;
    }

    .goodsindex-queryInfo-li {
        width: 100%;
        height: auto;
    }

    /* 列表 */
    .goodsindex-list {
        width: 100%;
        height: auto;
        margin-bottom: 20px;
    }

    /* 分页 */
    .goodsindex-page-box {
        width: 100%;
        height: auto;
        display: flex;
        justify-content: flex-end;
    }
</style>