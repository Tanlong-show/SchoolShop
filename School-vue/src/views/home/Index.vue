<template>
    <div style="background-color:#FCFCFC;font-family:'宋体';height:100%;">
        <div>
            <my-bread level1='个人中心' level2='修改密码' :level3="levelName"></my-bread>
        </div>
        <div style="margin-top:25px;margin-left:80px;">
            <el-row :gutter="10">
                <el-col :span="2">
                    <div style="background-color:#FFEBCD;width:60px;height:60px;display:inline-block;border-radius:50%;overflow:hidden;">
                        <el-image :src="src" style="width:60px;height:60px;"></el-image>
                    </div>
                    <div style="margin-top:5px;margin-left:6px;"><span v-text="user.name"></span></div>
                    <div style="margin-top:50px;margin-left:1px;">
                        <el-button type="text" style="font-size:15px;color:#4D4D4D;" @click="infomationClick()">
                            个人信息<span style="color:#B0E0E6;" v-show="infomationShow" class="el-icon-s-promotion"></span>
                        </el-button>
                    </div>
                    <div style="margin-top:5px;margin-left:1px;">
                        <el-button type="text" style="font-size:15px;color:#4D4D4D;" @click="passwordClick()">修改信息<span
                                style="color:#B0E0E6;" v-show="passwordShow" class="el-icon-s-promotion"></span>
                        </el-button>
                    </div>
                </el-col>

                <el-col :span="21">
                    <el-row :gutter="10" style="margin-top:20px;">
                        <el-col :span="2">
                        </el-col>
                        <el-col :span="5" v-text="'账号：'+user.userId"></el-col>
                    </el-row>
                    <!-- 个人信息 -->
                    <el-row v-show="infomationShow">
                        <el-card style="margin-top:30px;">
                            <el-row>
                                <el-col :span="6">
                                    <el-row :gutter="12" style="margin-top:20px;">
                                        <el-col :span="8">
                                            <div style="text-align:right;"><span>姓名：</span></div>
                                        </el-col>
                                        <el-col :span="16" v-text="user.name"></el-col>
                                    </el-row>
                                    <el-row :gutter="12" style="margin-top:60px;">
                                        <el-col :span="8">
                                            <div style="text-align:right;"><span>信誉分：</span></div>
                                        </el-col>
                                        <el-col :span="16" v-text="user.grade"></el-col>
                                    </el-row>
                                    <el-row :gutter="12" style="margin-top:60px;">
                                        <el-col :span="8">
                                            <div style="text-align:right;"><span>签名：</span></div>
                                        </el-col>
                                        <el-col :span="16" v-text="user.signature"></el-col>
                                    </el-row>
                                    <el-row :gutter="12" style="margin-top:60px;">
                                        <el-col :span="8">
                                            <div style="text-align:right;"><span>权限：</span></div>
                                        </el-col>
                                        <el-col :span="16" v-if="user.root == 0" style="color: blue">普通用户</el-col>
                                        <el-col :span="16" v-if="user.root == 1" style="color: red">管理员</el-col>

                                    </el-row>
                                    <el-row :gutter="12" style="margin-top:60px;">
                                        <el-col :span="8">
                                            <div style="text-align:right;"><span>认证：</span></div>
                                        </el-col>
                                        <el-col :span="16" v-if="user.root == 0" style="color: blue">未认证</el-col>
                                        <el-col :span="16" v-if="user.root == 1" style="color: red">已认证</el-col>
                                    </el-row>
                                    <el-row :gutter="12" style="margin-top:30px;">
                                        <el-col :span="8">
                                            <div style="text-align:right;"><span>性别：</span></div>
                                        </el-col>
                                        <el-col :span="16" v-if="user.root == 0" style="color: blue">女</el-col>
                                        <el-col :span="16" v-if="user.root == 1" style="color: red">男</el-col>
                                    </el-row>
                                </el-col>
                                <el-col :span="18">
                                    <el-row>
                                        <el-col>
                                            <el-timeline>
                                                <el-timeline-item :timestamp="index+1"
                                                                  v-for="(item,index) in this.goods.list"
                                                                  placement="top">
                                                    <el-card>
                                                        <div><span><span
                                                                style="font-family:'宋体';font-size:15px;"><strong
                                                                v-text="item.name"></strong></span><span
                                                                style="font-family:'宋体';font-size:12px;margin-left:20px;"><strong
                                                                v-text="'价格：'+item.price"></strong></span></span>
                                                        </div>
                                                        <div>
                                                            <img style="width: 50px; height: 50px;"
                                                                 :src="item.pictureUrl"></img>
                                                        </div>
                                                    </el-card>
                                                </el-timeline-item>
                                            </el-timeline>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                        </el-card>
                    </el-row>
                    <!-- 修改密码 -->
                    <el-row v-show="passwordShow">
                        <el-card style="margin-top:30px;">
                            <el-form :model="personalForm">
                                <el-row :gutter="12" style="margin-top:20px;">
                                    <el-col :span="6">
                                        <div style="text-align:right;"><span>姓名：</span></div>
                                    </el-col>
                                    <el-col :span="5">
                                        <el-form-item prop="name">
                                            <el-input type="text" v-model="user.name"
                                                      placeholder="请输入您的姓名"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row :gutter="12" style="margin-top:20px;">
                                    <el-col :span="6">
                                        <div style="text-align:right;"><span>个性签名：</span></div>
                                    </el-col>
                                    <el-col :span="25">
                                        <el-form-item prop="signature">
                                            <el-input type="text" v-model="user.signature"
                                                      placeholder="请输入您的签名"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row :gutter="12" style="margin-top:20px;">
                                    <el-col :span="6">
                                        <div style="text-align:right;"><span>性别：</span></div>
                                    </el-col>
                                    <el-col :span="5">
                                        <el-form-item prop="signature">
                                            <el-col :xs="8" :sm="6" :md="6" :lg="4" :xl="4">
                                                <el-select @change="changeSex" class="goodsindex-queryInfo-li"
                                                           v-model="user.sex"
                                                           size="small" clearable placeholder="请选择性别">
                                                    <el-option
                                                            v-for="item in options"
                                                            :disabled="true"
                                                            :key="item"
                                                            :label="item"
                                                            :value="item">
                                                    </el-option>
                                                </el-select>
                                            </el-col>
                                        </el-form-item>
                                    </el-col>
                                </el-row>
                                <el-row :gutter="12" style="margin-top:1px;">
                                    <el-col :span="6">
                                        <div style="text-align:right;"><span>密码：</span></div>
                                    </el-col>
                                    <el-col :span="5">
                                        <el-form-item prop="password">
                                            <el-input type="password" show-password v-model="user.password"
                                                      placeholder="请输入新的密码"></el-input>
                                        </el-form-item>
                                    </el-col>
                                </el-row>

                                <el-row :gutter="12" style="margin-top:10px;">
                                    <el-col :span="17" style="text-align:center;">
                                        <el-button type="primary" plain round size="medium" @click="submit()">修改
                                        </el-button>
                                    </el-col>
                                </el-row>
                            </el-form>
                        </el-card>
                    </el-row>
                </el-col>
            </el-row>
        </div>

        <div>

        </div>
    </div>
</template>
<script>


    import user from "../../store/modules/user";

    export default {

        mounted() {
            var userId = localStorage.getItem("token")
            this.$axios
                .post("/consumer/user/getUser?userid=" + userId)
                .then(response => {
                    this.user = response.data
                    this.src = this.user.headpicture
                })
            this.$axios
                .post("/consumer/goods/getGoodsListByUserId?name="
                    + this.queryInfo.name + "&sortOneName=" + this.queryInfo.type + "&sortTwoName=" + this.queryInfo.type2)
                .then(response => {
                    this.goods.list = response.data
                    console.log(this.goods.list)
                })
            this.infomationClick()
        },

        data() {
            return {
                //男，女
                options: ['男', '女'],
                //搜索框,纯做条件
                queryInfo: {
                    name: '',
                    type: '',
                    type2: '',
                    page: 1,
                    pageSize: 10,
                    total: 0,
                },
                //商品
                goods: {
                    list: [],
                },
                src: "",
                levelName: '',
                isCollapse: false,
                infomationShow: false,
                passwordShow: false,
                isDisabled: false,
                time: 60,

                user: {
                    id: "",
                    userId: "",
                    name: "",
                    headpicture: "",
                    password: "",
                    root: "",
                    grade: "",
                    sex: "",
                    signature: "",
                    createTime: "",
                    authentic: "",
                },
            }
        },
        methods: {
            infomationClick(){    //个人信息事件
                this.infomationShow = true
                this.passwordShow = false
            },
            passwordClick(){     //密码事件
                this.infomationShow = false
                this.passwordShow = true
            },

            changeSex(data) {
                this.user.sex = data
            },

            submit() {  //提交
                if (this.user.name === "") {
                    this.$message.warning("姓名不能为空")
                } else if (this.user.signature === "") {
                    this.$message.warning("个性签名不能为空")
                } else if (this.user.password === "") {
                    this.$message.warning("密码不能为空")
                } else {
                    this.$message.success("修改成功！")
                    this.$axios
                        .post("/consumer/user/modifyUser", this.user)
                        .then(response => {
                        })
                }
            },

        }
    }
</script>

<style>
    .name {
        text-align: right;
    }

    .value {
        text-align: left;
    }
</style>
