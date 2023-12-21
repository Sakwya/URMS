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
				<query-list :api="api" :attrs="attrs" :attrs_="attrs_"></query-list>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	export default {
		data() {
			return {
				api: "/role",
				attrs: [{
					prop: "role_id",
					label: "#",
					width: "auto",
				}, {
					type: "selection",
					width: "auto",
				}, {
					prop: "role_name",
					label: "角色名称",
				}],
				attrs_: [{
					prop: "role_name",
					label: "角色名称",
					data: "",
				}, ]
			}
		},
		methods: {}
	}
</script>

<style>
</style>