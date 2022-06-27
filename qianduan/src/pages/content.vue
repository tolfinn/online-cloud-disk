<template>
	<div>
		<div id="pieReport" style="width: 400px;height: 300px;"></div>
	</div>
</template>

<script>
export default {
	name: "content",
	data() {
		return {
			charts: "",
			opinion: ["图片", "文档", "视频", "音频", "其它"],
			opinionData: [
				{ value: 12, name: "图片", itemStyle: "#E53D30" },
				{ value: 18, name: "文档", itemStyle: "#F9BE15" },
				{ value: 3, name: "视频", itemStyle: "#DFC381" },
				{ value: 5, name: "音频", itemStyle: "#79d2c0" },
				{ value: 32, name: "其它", itemStyle: "#2A7AED" }
			]
		};
	},
	methods: {
		drawPie(id) {
			this.charts = this.$echarts.init(document.getElementById(id));
			this.charts.setOption({
				tooltip: {
					trigger: "item",
					formatter: "({a})<br/>({b}):({c}M) ({d}%)"
				},
				legend: {
					bottom: 10,
					left: "center",
					data: this.opinion
				},
				series: [
					{
						name: "状态",
						type: "pie",
						radius: "65%",
						center: ["50%", "50%"],
						avoidLabelOverlap: false,
						itemStyle: {
							emphasis: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: "rgba(0, 0, 0, 0.5)"
							},
							color: function (params) {
								//自定义颜色
								var colorList = ["#E53D30", "#F9BE15", "#DFC381", "#79d2c0", "#2A7AED"];
								return colorList[params.dataIndex];
							}
						},
						data: this.opinionData
					}
				]
			});
		},
	},
	mounted() {
		this.$nextTick(function () {
			this.drawPie("pieReport");
		});
	}

}

</script>

<style scoped>
</style>