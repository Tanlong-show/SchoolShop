<template>
    <div class="dynamic">
        <vue-seamless-scroll :data='dynamicList' class="dynamic-warp">
            <div class="dynamic-list" v-for="item in dynamicList">
                <div class="dynamic-list-left">
                    <img :src="item.headpic"/>
                </div>
                <div class="dynamic-list-right">
                    <p class="dynamic-list-right-top">
                        <span>{{item.buyerName}}</span>
                        <span>{{item.type}}</span>
                        <span>{{item.goodName}}</span>
                    </p>
                    <p class="dynamic-list-right-bottom">
                        {{item.time}}
                    </p>
                </div>
            </div>
        </vue-seamless-scroll>
    </div>
</template>

<script>
    // 滚动样式详见: https://chenxuan1993.gitee.io/component-document/index_prod#/component/seamless-default
    export default {
        props: ['dynamicList'],

        created() {
        },

        data() {
            return {
            }
        }
    }
</script>

<style scoped>
    .dynamic {
        width: 100%;
        height: auto;
    }

    .dynamic-warp {
        height: 651px;
        overflow: hidden;
    }

    .dynamic-list {
        width: 100%;
        height: 93px;
        padding: 25px 0;
        border-bottom: 1px solid #dcdfe6;
        box-sizing: border-box;
        display: flex;
        justify-content: space-between;
        cursor: pointer;
    }

    .dynamic-list:hover {
        background: #DBFEFB;
    }

    .dynamic-list-left, .dynamic-list-left img {
        width: 42px;
        height: 42px;
        border-radius: 21px;
        overflow: hidden;
        margin-right: 25px;
    }

    .dynamic-list-right {
        flex: 1;
        height: 42px;
        display: flex;
        justify-content: space-between;
        flex-direction: column;

    }

    .dynamic-list-right .dynamic-list-right-top {
        font-size: 14px;
    }

    .dynamic-list-right-top span:nth-child(1) {
        color: #3B3C3C;
        margin-right: 15px;
    }

    .dynamic-list-right-top span:nth-child(2) {
        color: #7C7E81;
        margin-right: 5px;
    }

    .dynamic-list-right-top span:nth-child(3) {
        color: #2E93FD;
    }

    .dynamic-list-right-bottom {
        color: #999999;
        font-size: 12px;
    }
</style>