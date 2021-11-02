<template>
    <div class="Informationall">
        <!-- <div class="Informationall-box"> -->
        <el-tabs v-model="activeName" class="Informationall-box" @tab-click="to0">

            <el-tab-pane label="待支付订单" name="first">
                <div class="Informationall-list">
                    <InformationList />
                </div>
            </el-tab-pane>
            <el-tab-pane label="已支付订单" name="second">
                <div class="Informationall-list">
                    <InformationList />
                </div>
            </el-tab-pane>
            <el-tab-pane label="退回单" name="third">
                <div class="Informationall-list">
                    <InformationList />
                </div>
            </el-tab-pane>
        </el-tabs>
        <!-- </div> -->
        <OrdersList :parent="parent"></OrdersList>

    </div>

</template>
<script>
    import OrdersList from '../../components/Home/OrdersList'

    export default {

        data(){
            return {
                activeName: 'first',
                parent: 0
            }
        },

        components:{
            OrdersList
        },

        methods:{
            //控制parent值，传给子组件状态控制，订单状态：0-未处理 1-已处理  2-被卖家取消
            to0(tab, event){
                if(tab.name == 'first'){
                    this.parent = 0;
                }else if(tab.name == "second"){
                    this.parent = 1;
                }else{
                    this.parent = 2;
                }

            },


        },

    }
</script>

<style>
    .Informationall{
        width: 100%;
        min-height: 100%;
        padding: 25px;
        box-sizing: border-box;
    }
    .Informationall-box{
        width: 100%;
        min-height: 100%;
        border-radius: 5px;
        padding: 15px;
        box-sizing: border-box;
        background: #ffffff;
    }
    .Informationall-box .el-tabs__nav-scroll{
        justify-content: flex-start !important;
        padding-left: 15px;
        box-sizing: border-box;
    }
    .Informationall-list{
        width: 100%;
        height: auto;
        padding: 15px;
        box-sizing: border-box;
    }
</style>