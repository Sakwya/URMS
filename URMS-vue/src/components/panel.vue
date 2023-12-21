<template>
	<el-container>
		<el-header :style="{padding:0}">
			<head-bar></head-bar>
		</el-header>
		<el-container>
			<el-aside :style="{padding:'20px'}">
				<side-bar></side-bar>
			</el-aside>
			<el-main>
				<el-button type="info" :dark="false" plain><el-icon><InfoFilled />&nbsp;</el-icon>README.md</el-button>
				<v-md-preview :text="markdownContent"></v-md-preview>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	export default {
		components: {},
		data() {
			return {
				markdownContent: ''
			}
		},
		mounted() {
			// 用axios加载本地Markdown文件
			this.$axios.get('http://localhost:8080/static/README.md')
				.then(response => {
					// 将文件内容保存到数据属性中
					this.markdownContent = response.data;
				})
				.catch(error => {
					console.error('Error loading Markdown file:', error);
				});
		},
	}
</script>

<style>
</style>