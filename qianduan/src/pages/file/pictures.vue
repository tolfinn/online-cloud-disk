<template>
  <div>
    <FileinfoVue :list="list" :title="title"></FileinfoVue>
  </div>
</template>

<script>
import FileinfoVue from "../../components/fileinfo.vue";
export default {
  name: "pictures",
  components: { FileinfoVue },
  data() {
    return {
      list: [],
      title: '我的图片',
    }
  },
  mounted() {
    let params = new URLSearchParams();
    params.append("attribute", 0);
    this.$axios.post(
      "/file/module", params
    ).then(res => {
      console.log(res)
      if (res.code == 6666) {
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