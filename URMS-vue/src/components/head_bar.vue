<template>
	<el-menu default-active="0" mode="horizontal" :ellipsis="false">
		<el-menu-item index="0">
			用户权限管理系统
		</el-menu-item>
		<div style="flex-grow: 1;" />
		<el-sub-menu index="1">
			<template #title>{{username}}</template>
			<el-menu-item index="1-1"><el-icon>
					<Tools />
				</el-icon>个人设置</el-menu-item>
			<el-menu-item index="1-2"><el-icon>
					<Message />
				</el-icon>消息</el-menu-item>
			<el-menu-item index="1-3" @click="logout()"><el-icon>
					<SwitchButton />
				</el-icon>退出系统</el-menu-item>
		</el-sub-menu>
		<el-menu-item index="2">
			<el-icon>
				<QuestionFilled />
			</el-icon><span>反馈</span>
		</el-menu-item>

	</el-menu>
</template>

<script setup>
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus'
	import {
		useRouter
	} from 'vue-router';
	const router = useRouter();
	const logout = () => {
		ElMessageBox.confirm(
				'确定要退出登录吗?',
				'Warning', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				localStorage.clear()
				ElMessage({
					type: 'success',
					message: '退出登录成功。',
					duration: 1000
				})
				router.push('/login');
			})
			.catch(() => {})
	}
	let userInfo = JSON.parse(localStorage.getItem("userInfo"))
	const username = userInfo.username
</script>

<style>
</style>