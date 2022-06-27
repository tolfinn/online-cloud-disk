<template>
  <div>
    <FileinfoVue :list="list" :title="title"></FileinfoVue>
  </div>
</template>

<script>
import FileinfoVue from "../../components/fileinfo.vue";
export default {
  name: "others",
  components: { FileinfoVue },
  data() {
    return {
      list: [],
      title: '其它文件',
    }
  },
  mounted() {
    let params = new URLSearchParams();
    params.append("attribute", 4);
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