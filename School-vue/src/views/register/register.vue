<template>
    <div class="login">
        <vue-particles
                class="login-bg"
                color='#39AFFD'
                :particleOpacity="0.7"
                :particlesNumber="100"
                shapeType="circle"
                :particleSize="4"
                linesColor="#8DD1FE"
                :linesWidth="1"
                :lineLinked="true"
                :lineOpacity="0.4"
                :linesDistance="150"
                :moveSpeed="3"
                :hoverEffect="true"
                hoverMode="grab"
                :clickEffect="true"
                clickMode="push"
        >
        </vue-particles>
        <!-- 登录面板 -->
        <div class="login-box">
            <div class="login-box-title">
                校园交易平台用户注册
            </div>
            <div class="login-box-from">
                <el-form :model="loginForm" :rules="rules" ref="registerForm" class="demo-ruleForm">
                    <el-form-item prop="username">
                        <el-input v-model="loginForm.username" placeholder="请输入姓名" size="medium">
                            <el-button slot="prepend" icon="el-icon-name"></el-button>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="userid">
                        <el-input v-model="loginForm.userid" placeholder="请输入用户名" size="medium">
                            <el-button slot="prepend" icon="el-icon-user"></el-button>
                        </el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" size="medium">
                            <el-button slot="prepend" icon="el-icon-key"></el-button>
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="success" size="medium" :loading="loading" style="width:100%"
                                   @click="submitForm('registerForm')">注册
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>

    </div>

</template>
<script>
    export default {
        data() {

            let letterRule = (rule, value, callback) => {
                let reg = /^[a-zA-Z0-9_-]{4,18}$/
                if (value === '') {
                    callback(new Error('输入内容不能为空'));
                } else if (!reg.test(value)) {
                    callback(new Error('请输入字母、数字、下划线'));
                } else {
                    callback();
                }
            };
            return {
                loading: false, //登陆状态
                loginForm: {  // 登陆表单
                    userid: '',
                    password: ''
                },
                rules: {  //登陆验证规则
                    userid: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 2, max: 18, message: '长度在 2 到 18 个字符', trigger: 'blur'},
                        {validator: letterRule, trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'},
                        {validator: letterRule, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.loading = true
                        this.register()
                    } else {
                        // console.log('error submit!!');
                        return false;
                    }
                });
            },

            register() {

                let data = {
                    username: this.loginForm.username,
                    userid: this.loginForm.userid,
                    password: this.loginForm.password,
                }
                this.$axios
                    .post("/consumer/user/registerUser?userid=" + data.userid + "&password=" + data.password +"&username="+data.username)
                    .then(response => {
                        console.log(response.data)
                        if (response.data == "注册成功") {
                            this.$message.success('注册成功');
                            //如果用户存在并正确则赋予admin令牌，后期考虑jerry身份为管理员或者普通用户，在store/moudles/user.js
                            this.$axios
                                .post("/consumer/user/getUser?userid=" + data.userid)
                                .then(response => {

                                    localStorage.setItem("token", response.data.userId)

                                })
                            //管理员给令牌1
                            if(data.root == 1){
                                this.$store
                                    .dispatch('user/login', {username: "jerry"})//admin为令牌
                                    .then(() => {
                                        this.loading = true
                                        // 登陆成功后重定向
                                        this.$router.push({
                                            path: this.$route.query.redirect || '/index',
                                            query: {userid: data.userid}
                                        })

                                    })
                                    .catch(err => {
                                        this.loading = true
                                        // console.log(err)
                                    })
                            }else{
                                this.$store
                                    .dispatch('user/login', {username: "admin"})//admin为令牌
                                    .then(() => {
                                        this.loading = true
                                        // 登陆成功后重定向
                                        this.$router.push({
                                            path: this.$route.query.redirect || '/index',
                                            query: {userid: data.userid}
                                        })

                                    })
                                    .catch(err => {
                                        this.loading = true
                                        // console.log(err)
                                    })
                            }

                        }else if (response.data == "此账户已存在！") {

                            this.$message.error('此账户已存在！');
                            this.loading = false
                        } else {
                            this.$message.error('服务器繁忙！请稍后');
                        }

                    })


            }
        }

    }
</script>

<style scoped>
    .login {
        width: 100%;
        height: 100%;
        /* display: flex;
        justify-content: center;
        align-items: center;
        background-image: url('../../assets/image/login.jpg');
        background-repeat: no-repeat;
        background-size: 100% 100%;
        color: #cccccc; */
        position: relative;
    }

    .login-bg {
        width: 100%;
        height: 100%;
        background: #3E3E3E;
    }

    .login-box {
        width: 350px;
        /* height: 287px; */
        background: hsla(0, 0%, 100%, .3);
        border-radius: 5px;
        box-shadow: 0 0 2px #f7f7f7;
        border: 1px solid #f7f7f7;
        position: absolute;
        left: 50%;
        top: 50%;
        margin-left: -175px;
        margin-top: -150px;

    }

    .login-box-title {
        line-height: 50px;
        font-size: 20px;
        color: #ffffff;
        text-align: center;
        border-bottom: 1px solid #ffffff;
    }

    .login-box-from {
        width: 100%;
        height: auto;
        padding: 30px;
        box-sizing: border-box;
    }
</style>