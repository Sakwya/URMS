<template>
	<el-scrollbar>
		<el-menu width="300px" :style="{borderRadius:'10px'}">
			<el-menu-item index="1"><router-link to="/">
					<span><el-icon>
							<HomeFilled />
						</el-icon>控制面板</span></router-link>
			</el-menu-item>
			<el-sub-menu index="2">
				<template #title>
					<el-icon>
						<Operation />
					</el-icon><span>权限管理</span>
				</template>
				<el-menu-item index="2-1" v-if="menus.m1||menus.m2||menus.m3"><router-link to="/user"><el-icon>
							<UserFilled />
						</el-icon><span>用户维护</span></router-link></el-menu-item>
				<el-menu-item index="2-2" v-if="menus.m4||menus.m5"><router-link to="/role"><el-icon>
							<Avatar />
						</el-icon><span>角色维护</span></router-link></el-menu-item>
				<el-menu-item index="2-3" v-if="menus.m6"><router-link to="/menu"><el-icon>
							<Briefcase />
						</el-icon><span>菜单维护</span></router-link></el-menu-item>
			</el-sub-menu>
			<el-sub-menu index="3" v-if="menus.m7||menus.m8||menus.m9">
				<template #title>
					<el-icon>
						<List />
					</el-icon><span>业务审核</span>
				</template>
				<el-menu-item index="3-1" v-if="menus.m7"><el-icon>
						<Checked />
					</el-icon><span>实名认证审核</span></el-menu-item>
				<el-menu-item index="3-2" v-if="menus.m8"><el-icon>
						<Checked />
					</el-icon><span>广告审核</span></el-menu-item>
				<el-menu-item index="3-3" v-if="menus.m9"><el-icon>
						<Checked />
					</el-icon> <span>项目审核</span></el-menu-item>
			</el-sub-menu>
			<el-sub-menu index="4" v-if="menus.m10||menus.m11||menus.m12">
				<template #title>
					<el-icon>
						<Menu />
					</el-icon><span>业务管理</span>
				</template>
				<el-menu-item index="4-1" v-if="menus.m10"><el-icon>
						<PictureFilled />
					</el-icon><span>资质维护</span></el-menu-item>
				<el-menu-item index="4-2" v-if="menus.m11"><el-icon>
						<Histogram />
					</el-icon><span>分类管理</span></el-menu-item>
				<el-menu-item index="4-3" v-if="menus.m12"><el-icon>
						<DataLine />
					</el-icon><span>流程管理</span></el-menu-item>
				<el-menu-item index="4-4"><el-icon>
						<MoreFilled />
					</el-icon></el-menu-item>
			</el-sub-menu>
			<el-menu-item index="5" v-if="menus.m13">
				<span><el-icon>
						<Management />
					</el-icon>参数管理</span>
			</el-menu-item>
		</el-menu>
	</el-scrollbar>
</template>

<script setup>
	import {
		reactive
	} from "vue"
	import axios from "../axios.js";
	import menus from "./menus.js"
	console.log(menus)
</script>

<style>
	a {
		color: val(var(--el-menu-active-color))
	}
</style>