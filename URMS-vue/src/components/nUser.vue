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
				<el-card :style="{borderRadius:'10px'}">
					<template #header>
						<div :style="{display:'flex'}">
							<el-text>
								<el-icon>
									<Grid />
								</el-icon>
								数据列表
							</el-text>
						</div>
					</template>
					<div :style="{display:'flex',justifyContent:'space-between'}">
						<div>
							<el-input v-model="filter">
								<template #prepend><el-select v-model="filterType">
								<el-option v-for="(item,index) in attrs" v-if="'prop' in item" :key="item.prop" :label="item.label" :value="item.prop"></el-option>
								</el-select></template>
							</el-input>
						</div>
						<el-button type="warning" @click="get()" style="margin-left: 12px;"><el-icon>
								<Search />
							</el-icon>
							查询</el-button>
						<div style="flex-grow: 1;" />
						<el-button type="primary" @click="dialogVisable=true"><el-icon>
								<Plus />
							</el-icon>
							新增</el-button>
						<el-button type="danger" @click="deleteRows()"><el-icon>
								<Close />
							</el-icon>
							删除</el-button>
					</div>
					<el-divider />
					<el-table :data="filterTableData" border stripe ref="table">
						<el-table-column v-for="(c,index) in attrs" :key="index" :type="c.type" :prop="c.prop"
							:label="c.label" :width="c.width"></el-table-column>
						<!-- <el-table-column label="操作" /> -->
					</el-table>
				</el-card>
				<el-dialog title="新增数据" v-model="dialogVisable" width="20rem"
					:style="{borderRadius:'10px',border:'0.125rem solid var(--el-color-primary)'}">
					<el-form :model="formData.data" label-width="auto" label-suffix=":">
						<el-form-item v-for="(value, key) in formData.label" :key="key" :prop="key" :label="value">
							<el-input v-model="formData.data[key]"></el-input>
						</el-form-item>
					</el-form>
					<template #footer>
						<span>
							<el-button @click="dialogVisable = false">取消</el-button>
							<el-button type="primary" @click="insert();dialogVisable = false">
								提交
							</el-button>
						</span>
					</template>
				</el-dialog>
			</el-main>
		</el-container>
	</el-container>
</template>

<script setup>
	import {
		onMounted
	} from 'vue';
	import {
		computed,
		reactive,
		ref
	} from 'vue';
	import axios from '../axios.js';
	const api = "/user";
	const attrs = [{
			prop: "id",
			label: "ID",
			width: "60",
		}, {
			type: "selection",
			width: "40",
		},
		{
			prop: "account",
			label: "账号",
		}, {
			prop: "username",
			label: "名称",
		}, {
			prop: "email",
			label: "邮箱地址",
		}
	];
	const dialogVisable = ref(false);
	const tableData = ref([])
	const filter = ref("")
	const filterType = ref("")
	const filterTableData = computed(() =>
	  tableData.value.filter(
	    (data) =>
	      !filter.value ||
	      data.username.toLowerCase().includes(filter.value.toLowerCase())
	  )
	)
	const formData = reactive({
		label: {
			account: "账号",
			username: "名称",
			password: "密码",
			email: "邮箱地址"
		},
		data: {
			account: "",
			username: "",
			password: "",
			email: ""
		}
	});
	const getAll = () => {
		axios.get('user').then(rspn => {
			tableData.value = rspn.data.data;
		}).catch(error=>{
			console.log(error)
		})
	}
	onMounted(() => {
		getAll();
	});
</script>

<style>
</style>