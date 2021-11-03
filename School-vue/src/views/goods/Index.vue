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

            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2" :offset="1">
                <el-button type="primary" class="Carousel-Info-li" size="small" @click="CarouselShow = true">添加
                </el-button>
            </el-col>
        </el-row>

        <hr>


        <!-- 添加或编辑关键词 -->
        <el-dialog title="添加轮播图" :visible.sync="CarouselShow">
            <el-form :model="CarouselForm" :rules="CarouselRules" ref="CarouselForm">
                <el-form-item label="名称" :label-width="CarouselWidth" prop="name">
                    <el-input v-model="CarouselForm.name" autocomplete="off" placeholder="请输入关键词"></el-input>
                </el-form-item>
                <el-form-item label="图片" :label-width="CarouselWidth" prop="url">
                    <el-upload
                            action="https://jsonplaceholder.typicode.com/posts/"
                            list-type="picture-card"
                            :on-preview="handlePictureCardPreview"
                            :on-remove="handleRemove">
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible">
                        <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                </el-form-item>
                <el-form-item label="跳转类型" :label-width="CarouselWidth" prop="type">
                    <el-select v-model="CarouselForm.type" placeholder="请选择跳转类型">
                        <el-option label="外部连接" :value="1"></el-option>
                        <el-option label="内部连接" :value="2"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="外部链接地址" :label-width="CarouselWidth" prop="linl">
                    <el-input v-model="CarouselForm.link" autocomplete="off" placeholder="请输入外部链接地址"></el-input>
                </el-form-item>
                <el-form-item label="内部链接地址" :label-width="CarouselWidth" prop="linl">
                    <el-input v-model="CarouselForm.router" autocomplete="off" placeholder="请输入内部链接地址"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="CarouselSubmit('CarouselForm')" type="primary">添 加</el-button>
            </div>
        </el-dialog>

        <!-- 检索结果 -->
        <el-row :gutter="20" class="goodsindex-list">
            <el-col :span="24">
                <el-table
                        :data="this.json.list"
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
                            width="180">
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
                            prop="number"
                            label="库存"
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
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="下架"
                            width="150">
                        <template slot-scope="scope">　　
                            <el-button type="danger" v-if="scope.row.state == 0 ? false : true"
                                       @click="deleteById(scope.row.id)" class="goodsindex-queryInfo-li"
                                       size="small">下架
                            </el-button>
                            <el-button type="success" v-if="scope.row.state == 1 ? false : true"
                                       @click="deleteById(scope.row.id)" class="goodsindex-queryInfo-li"
                                       size="small">上架
                            </el-button>
                        </template>

                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="修改信息"
                            width="150">
                        <template slot-scope="scope">　　
                            <el-button type="primary" @click="modifyById(scope.row.id)" class="goodsindex-queryInfo-li"
                                       size="small">修改信息
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
                        :hide-on-single-page="true"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="queryInfo.page"
                        :page-sizes="[10, 20, 50, 100]"
                        :page-size="queryInfo.pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="400">
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
                    pageSize: 10
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
                // 上传
                dialogImageUrl: '',
                dialogVisible: false,
                // 弹出层
                CarouselShow: false,
                CarouselForm: {
                    name: '',
                    type: '',
                    linl: '',
                    url: ''
                },
                CarouselRules: {
                    name: [
                        {required: true, message: '请输入轮播图名称', trigger: 'blur'},
                        {min: 1, max: 10, message: '长度为 1~10 个字符', trigger: 'blur'}
                    ],
                    type: [
                        {required: true, message: '请选择跳转类型', trigger: 'change'}
                    ],
                    url: [
                        {required: true, message: '请选择图片', trigger: 'change'}
                    ],
                    linl: [
                        {required: true, validator: validatelinl, trigger: 'blur'}
                    ]
                },
                CarouselWidth: '120px'
            }
        },
        methods: {
            handleSizeChange() {

            },
            handleCurrentChange() {

            },

            CarouselSubmit(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        alert('submit!');
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
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
            },
            clearAdvanced() {
                this.queryInfo.name = this.queryInfo.type = this.queryInfo.type2 = '';
                this.selectAdvance();
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