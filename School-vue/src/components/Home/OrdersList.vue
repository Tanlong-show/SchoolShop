<template>
    <div class="Information">
        <div class="Information-title">
            <el-button type="danger" @click="deleteSelect" size="mini">删除</el-button>
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
                mulIdSelect:[]

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
                        console.log(response.data)
                        this.ordersShop = response.data

                        // this.json.list[0].pictureUrl='https://img0.baidu.com/it/u=3481486975,4218348512&fm=26&fmt=auto'
                    })
            },
            deleteSelect() {
                console.log(this.multipleSelection.at(0))
                for (let i = 0; i < this.multipleSelection.length; i++) {
                    this.mulIdSelect.push(this.multipleSelection.at(i).ordNumber);
                }
                this.$axios
                    .post("/consumer/ordering/deleteOrdering?list=" + this.mulIdSelect)

                for (let i = 0; i < this.ordersShop.length; i++) {
                    for (let j = 0; j < this.multipleSelection.length; j++) {
                        if(this.ordersShop[i].ordNumber == this.multipleSelection.at(j).ordNumber){
                            this.ordersShop.splice(i,1);
                        }
                    }
                }
                this.multipleSelection.length = 0;
                this.mulIdSelect.length = 0;
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