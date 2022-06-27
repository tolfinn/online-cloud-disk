<template>
    <div>
        <FileinfoVue :list="list" :title="title"></FileinfoVue>
    </div>
</template>

<script>
import FileinfoVue from "../../components/fileinfo.vue";
export default {
    name: 'myfile',
    components: { FileinfoVue },
    props: ['path', 'search'],
    data() {
        return {
            list: [],
            title: '我的文件',
        }
    },
    mounted() {
        // 直接在这查找数据不用全局事件总线
        //我的文件展示
        this.$axios.get(
            "/file/index1"
        ).then(res => {
            console.log(res)
            if (res.code == 6666) {
                // alert('qqcg')
                this.list = res.data;
            }
        }).catch(err => {
            console.log(err)
        });
    },
    watch: {
        path(val) {
            let params = new URLSearchParams();
            //点击文件夹 展示文件夹里面的文件
            // alert('查看文件内容');
            params.append("fileid", val);
            this.$axios.post(
                "/file/nextLayer", params
            ).then(res => {
                console.log('展示文件夹里面的文件', res)
                if (res.code == 6666) {
                    this.list = res.data;
                }
            }).catch(err => {
                console.log(err)
            });
        },
        search(val) {
            // let params = new URLSearchParams();
            //点击文件夹 展示文件夹里面的文件
            // alert('查看文件内容');
            // params.append("name", val);
            this.$axios.get(
                "/file/fuzzyQuery?name="+val
            ).then(res => {
                console.log('模糊查询文件：', res)
                if (res.code == 6666) {
                    this.list = res.data;
                }
            }).catch(err => {
                console.log(err)
            });
        }
    }
}
</script>

<style>
</style>