<template>
    <div class="Information">
        <div class="Information-title">
            <el-button type="danger" :disabled="this.multipleSelection.length==0 ? true : false" @click="deleteSelect"
                       size="mini">删除
            </el-button>
            <el-button type="success" :disabled="this.multipleSelection.length==1 ? false : true" @click="AddCouponShow = true"
                       size="mini">支付
            </el-button>
        </div>
        <div class="Information-table">
            <el-table
                    ref="multipleTable"
                    :data="ordersShop"
                    tooltip-effect="dark"
                    style="width: 100%"
                    @selection-change="handleSelectionChange">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="ordNumber"
                        label="订单编号">
                </el-table-column>
                <el-table-column label="商品图片">
                    　　
                    <template slot-scope="scope">
                        　　　　<img :src="scope.row.pictureUrl" width="60" height="60"/>
                        　　
                    </template>
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="名称">
                </el-table-column>
                <el-table-column
                        prop="price"
                        label="价格"
                        width="240">
                </el-table-column>
                <el-table-column
                        prop="updateTime"
                        label="更新时间"
                        width="240">
                </el-table-column>
            </el-table>
        </div>
        <!-- 添加或编辑关键词 -->
        <el-dialog title="添加类型" :visible.sync="AddCouponShow">
            <el-form :model="AddCouponForm" :rules="AddCouponRules" ref="AddCouponForm">
                <el-form-item label="价格￥" :label-width="AddCouponWidth2" prop="money">
                    <el-input style="width: 150px;" v-model="AddCouponForm.money" autocomplete="off" placeholder="意向价格"></el-input> 元
                </el-form-item>
                <el-form-item label="备注信息" :label-width="AddCouponWidth" prop="notice">
                    <el-input v-model="AddCouponForm.notice" autocomplete="off" placeholder="交易备注信息"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="AddCouponSubmit('AddCouponForm')" type="success">￥微信支付</el-button>
                <el-button @click="AddCouponSubmit('AddCouponForm')" type="primary">#支付宝支付</el-button>
                <el-button @click="AddCouponSubmit('AddCouponForm')" type="danger">&面交</el-button>

            </div>

        </el-dialog>
    </div>
</template>

<script>
    export default {

        props: ['parent'],

        created() {
            this.getOrders();
        },


        //监听parent变化，触发行为
        watch: {
            parent(newVal, oldVal) {
                this.parent = newVal
                this.getOrders();
            },
            immediate: true,
        },


        data() {
            return {

                ordersShop: [],

                //所有选中的行(对象)
                multipleSelection: [],
                //存订单编号
                mulIdSelect: [],

                // 弹出层
                AddCouponShow: false,
                AddCouponForm:{
                    name: '',
                    start: '',
                    end: ''
                },
                AddCouponRules:{
                    money: [
                        { required: true, message: '请输入详细价格', trigger: 'blur' },
                        { min: 1, max: 4, message: '长度在 1 到 4 个字符', trigger: 'blur' }
                    ],
                    notice: [
                        { required: true, message: '请输入详细信息', trigger: 'blur' },
                        { min: 5, max: 25, message: '长度在 5 到 25 个字符', trigger: 'blur' }
                    ]
                },
                AddCouponWidth: '120px',
                AddCouponWidth2: '130px',

            }
        },
        methods: {
            toggleSelection(rows) {
                console.log("rows: " + rows)
                if (rows) {
                    rows.forEach(row => {
                        this.$refs.multipleTable.toggleRowSelection(row);
                    });
                } else {
                    this.$refs.multipleTable.clearSelection();
                }
            },
            handleSelectionChange(val) {
                //存储选中行
                this.multipleSelection = val;
            },
            getOrders() {
                this.$axios
                    .post("/consumer/ordering/orderingStatus?status=" + this.parent)
                    .then(response => {
                        this.ordersShop = response.data

                        // this.json.list[0].pictureUrl='https://img0.baidu.com/it/u=3481486975,4218348512&fm=26&fmt=auto'
                    })
            },


            deleteMy() {
                for (let i = 0; i < this.multipleSelection.length; i++) {
                    this.mulIdSelect.push(this.multipleSelection.at(i).ordNumber);
                }
                this.$axios
                    .post("/consumer/ordering/deleteOrdering?list=" + this.mulIdSelect)

                for (let i = 0; i < this.ordersShop.length; i++) {
                    for (let j = 0; j < this.multipleSelection.length; j++) {
                        if (this.ordersShop[i].ordNumber == this.multipleSelection.at(j).ordNumber) {
                            this.ordersShop.splice(i, 1);
                            i--;
                            break;
                        }
                    }
                }
                this.multipleSelection.length = 0;
                this.mulIdSelect.length = 0;
                this.$message.success("已删除")
            },
            deleteSelect() {

                var tl = this
                this.$confirm("是否确认删除选中行", "提示", {
                    iconClass: "el-icon-question",//自定义图标样式
                    confirmButtonText: "确认",//确认按钮文字更换
                    cancelButtonText: "取消",//取消按钮文字更换
                    showClose: true,//是否显示右上角关闭按钮
                    type: "warning",//提示类型  success/info/warning/error
                }).then(function () {
                    //确认操作
                    tl.deleteMy()
                }).then((data) => {
                    //取消操作
                })



                // if (window.confirm("删除流程定义的同时，相关任务也会删除，确认么？")) {
                //     for (let i = 0; i < this.multipleSelection.length; i++) {
                //         this.mulIdSelect.push(this.multipleSelection.at(i).ordNumber);
                //     }
                //     this.$axios
                //         .post("/consumer/ordering/deleteOrdering?list=" + this.mulIdSelect)
                //
                //     for (let i = 0; i < this.ordersShop.length; i++) {
                //         for (let j = 0; j < this.multipleSelection.length; j++) {
                //             if(this.ordersShop[i].ordNumber == this.multipleSelection.at(j).ordNumber){
                //                 this.ordersShop.splice(i,1);
                //                 i--;
                //                 break;
                //             }
                //         }
                //     }
                //     this.multipleSelection.length = 0;
                //     this.mulIdSelect.length = 0;
                // } else {
                //     return false;
                // }


            }

        }
    }
</script>

<style scoped>
    .Information-title {
        width: 100%;
        height: auto;
        margin-bottom: 10px;
    }
</style>