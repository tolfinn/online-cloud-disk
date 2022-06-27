<template>
  <div>
  <Fileinfo :list="list"></Fileinfo>
  </div>
</template>

<script>
import Fileinfo from '../components/fileinfo.vue';
export default {
    name: "collection",
    components: { Fileinfo },
        data() {
        return {
            list: [],
        }
    },
        mounted() {
        this.$axios.get(
            "/file/collect",
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
}
</script>

<style>

</style>