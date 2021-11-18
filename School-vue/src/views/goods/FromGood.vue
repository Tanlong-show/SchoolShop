<template>
    <div class="FromGood">
        <el-form :model="goodFrom" :rules="ruleGood" ref="goodFrom" label-width="120px" class="demo-ruleForm">
            <el-form-item label="产品名称" prop="name">
                <el-input v-model="goodFrom.name" placeholder="请输入产品名称"></el-input>
            </el-form-item>
            <el-form-item label="产品描述" prop="description">
                <el-input v-model="goodFrom.description" placeholder="请输入产品描述"></el-input>
            </el-form-item>

            <el-form-item label="产品现价" prop="price">
                <el-input-number v-model="goodFrom.price" :precision="2" :step="1" :max="10000"
                                 :min="1"></el-input-number>
            </el-form-item>
            <el-form-item label="总量" prop="number">
                <el-input-number v-model="goodFrom.number" :step="1" :precision='0' :max="1000"
                                 :min="0"></el-input-number>
            </el-form-item>
            <el-form-item label="售卖地点" prop="address">
                <el-input v-model="goodFrom.address" placeholder="请输入售卖地点"></el-input>
            </el-form-item>

            <el-form-item label="商品分类" prop="sortone">
                <el-input v-model="goodFrom.sortone" placeholder="商品一级分类"></el-input>
                <hr>
                <el-input v-model="goodFrom.sorttwo" placeholder="商品二级分类"></el-input>
            </el-form-item>
            <el-form-item label="封面图" prop="dialogImageUrl">
                <img :src="goodFrom.pictureUrl" style="width: 150px;height: 150px" alt="">
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
            <el-form-item label="宣传图" prop="region">

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
            <el-form-item>
                <el-button type="primary" @click="submitForm('goodFrom')">立即添加</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>

    export default {

        mounted() {
            var id = this.$route.query.id
            this.id = id;
            if (id != 0) {
                this.$axios
                    .post("/consumer/goods/getGoodsById?id=" + id)
                    .then(response => {
                        this.goodFrom = response.data
                    })
            }

        },


        data() {
            return {
                id:'',
                goodFrom: {},
                ruleGood: {},
                dialogImageUrl: '',
                dialogVisible: false

            }
        },
        methods: {
            //添加/更新商品
            submitForm() {
                var tl = this
                this.$confirm("是否确认修改/添加?", "提示", {
                    iconClass: "el-icon-question",//自定义图标样式
                    confirmButtonText: "确认",//确认按钮文字更换
                    cancelButtonText: "取消",//取消按钮文字更换
                    showClose: true,//是否显示右上角关闭按钮
                    type: "warning",//提示类型  success/info/warning/error
                }).then(function () {
                    //确认操作
                    tl.goodFrom.userId = localStorage.getItem("token")
                    //默认是创建待审核
                    tl.goodFrom.state = 2 //进入创建审核
                    //判断是否是修改待审核
                    if (tl.id != 0) {
                        tl.goodFrom.state = 3 //进入修改审核
                    }

                    tl.$axios
                        .post("/consumer/goods/updateGoods", tl.goodFrom)
                        .then(response => {

                        })
                    tl.$message.success("添加/修改成功!")
                }).then((data) => {
                    //取消操作
                })


            },
            handleRemove(file, fileList) {
                console.log(file, fileList);
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url;
                this.dialogVisible = true;
            }
        }
    }
</script>

<style scoped>
    .FromGood {
        width: 100%;
        min-height: 100%;
        padding: 15px;
        box-sizing: border-box;
    }
</style>