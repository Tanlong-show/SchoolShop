<template>
    <div class="Spike">
        <!-- 添加 -->
        <el-row :gutter="20" class="Spike-Info">
            <el-col :xs="6" :sm="4" :md="3" :lg="2" :xl="2" :offset="1">
                <el-button type="primary" class="Spike-Info-li" size="small" @click="SpikeShow = true">添加</el-button>
            </el-col>
        </el-row>
        <!-- 查询列表 -->
        <el-row class="Spike-list">
            <el-col :span="24">
                <el-table
                        :data="list"
                        border
                        style="width: 100%">
                    <el-table-column
                            type="index"
                            label="序"
                            width="50">
                    </el-table-column>
                    <el-table-column
                            prop="startTime"
                            label="秒杀开始时间"
                            width="220">
                    </el-table-column>
                    <el-table-column
                            prop="goods.address"
                            label="售卖点"
                            width="220">
                    </el-table-column>
                    <el-table-column
                            prop="content"
                            label="备注内容"
                            width="220">
                    </el-table-column>
                    <el-table-column
                            prop="goods.name"
                            label="商品名称"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="goods"
                            label="图片">
                        <template slot-scope="scope">
                            　　　　<img :src="scope.row.goods.pictureUrl" width="60" height="60"/>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" prop="id">
                        <template slot-scope="scope">
                            <el-button type="danger"
                                       @click="deleteFlashSale(scope.row.content)"
                                       class="goodsindex-queryInfo-li"
                                       size="small">删除
                            </el-button>
                            <el-button type="warning"
                                       @click=""
                                       class="goodsindex-queryInfo-li"
                                       size="small">修改
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <!-- 添加或编辑关键词 -->
        <el-dialog title="秒杀商品添加" :visible.sync="SpikeShow">
            <el-form :model="flashGood" ref="flashGood">
                <el-form-item label="秒杀关键词" :label-width="SpikeWidth" prop="content">
                    <el-input v-model="flashGood.content" autocomplete="off" placeholder="请输入备注"></el-input>
                </el-form-item>

                <el-form-item label="秒杀商品选择" :label-width="SpikeWidth" prop="name">
                    <!-- 选择商品-->
                    <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                        <el-select @change="changeGoods" class="goodsindex-queryInfo-li" v-model="goodName"
                                   size="small" clearable placeholder="请选择秒杀商品">
                            <el-option
                                    v-for="item in options"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                </el-form-item>

                <el-form-item label="商品图片" :label-width="SpikeWidth" prop="picture">
                    <img style="width: 80px;height: 96px;" :src="pictureUrl">
                </el-form-item>


                <el-form-item label="开始时间" :label-width="SpikeWidth" prop="start">
                    <el-date-picker size="small" type="datetime" v-model="flashGood.startTime" placeholder="选择时间"
                                    value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss">
                    </el-date-picker>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button :disabled="flashGood.content=='' || flashGood.startTime=='' || goodName==''"
                           @click="SpikeSubmit('SpikeForm')" type="primary">添 加
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {

        mounted() {
            this.$axios
                .post("/consumer/goods/getGoodsListByUserId?name="
                    + '' + "&sortOneName=" + '' + "&sortTwoName=" + '')
                .then(response => {
                    this.options = response.data
                })

            this.$axios
                .post("/consumer/flashsale/getAllFlashSale")
                .then(response => {
                    this.list = response.data
                    console.log(this.list)
                })
        },

        data() {
            return {
                //秒杀选择商品信息列表
                options: [],
                //秒杀商品列表
                list: [],
                //选择的商品名称
                goodName: '',
                flashGood: {
                    goodsId: '',
                    startTime: '',
                    content: '',
                },

                pictureUrl: 'http://image.woshipm.com/wp-files/2017/05/8aenOxc4vS758zK8UTc1.gif',

                tableData: [],
                // 弹出层
                SpikeShow: false,

                SpikeWidth: '120px'
            }
        },
        methods: {
            changeGoods(data) {
                this.flashGood.goodsId = data

                for (let i = 0; i < this.options.length; i++) {
                    if (this.options[i].id == data) {
                        this.pictureUrl = this.options[i].pictureUrl
                    }
                }
            },

            SpikeSubmit(formName) {
                console.log(this.flashGood)
                this.$message.success("添加成功!")
                this.SpikeShow = false
                this.$axios
                    .post("/consumer/flashsale/insertFlashSale", this.flashGood)
                    .then(response => {

                    })
            },

            deleteFlashSale(content){
                for (let i = 0; i < this.list.length; i++) {
                    if(this.list[i].content == content){
                        this.list.splice(i,1)
                        return;
                    }
                }
            }
        }
    }
</script>

<style scoped>
    .Spike {
        width: 100%;
        min-height: 100%;
        padding: 15px;
        box-sizing: border-box;
    }

    .Spike-Info {
        width: 100%;
        margin-bottom: 15px;
    }

    .Spike-Info-li {
        width: 100%;
    }

    .Spike-list {
        width: 100%;
        margin-bottom: 20px;
    }

    .Spike-page-box {
        display: flex;
        justify-content: flex-end;
    }
</style>
