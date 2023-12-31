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
								<template #prepend>
									<el-select v-model="filterType" placeholder="属性名">
										<el-option v-for="(item, index) in attrs" :key="item.prop" :label="item.label"
											:value="item.prop"></el-option>
									</el-select>
								</template>
							</el-input>
						</div>
						<!-- 					<el-button type="warning" @click="printSelect()" style="margin-left: 12px;"><el-icon>
								<Search />
							</el-icon>
							查询</el-button> -->
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
					<el-table :data="tableDataByPage" border stripe ref="table">
						<el-table-column type="selection"></el-table-column>
						<el-table-column v-for="(c,index) in attrs" :key="index" :prop="c.prop" :label="c.label"
							:width="c.width"></el-table-column>
						<el-table-column label="操作" width='200' fixed="right">
							<template #default="scope">
								<el-button type="success" @click="edit(scope.$index)"><el-icon>
										<Edit />
									</el-icon></el-button>
								<el-button type="primary" @click="assign(scope.row.id)"><el-icon>
										<EditPen />
									</el-icon></el-button>
								<el-button type="danger" @click="deleteSingle(scope.row.id);"><el-icon>
										<Close />
									</el-icon></el-button>
							</template>
						</el-table-column>
					</el-table>
					<div :style="{display:'flex',justifyContent:'space-between',marginTop:'0.5rem'}">
						<el-button text @click="frontPage()"><el-icon>
								<ArrowLeft />
							</el-icon></el-button>
						<el-input :style="{width:'2rem'}" v-model="pageNumInput" @change="correctPageNum()"></el-input>
						<el-button text @click="nextPage()"><el-icon>
								<ArrowRight />
							</el-icon></el-button>

						<div style="flex-grow: 1;" />
						<el-text type="primary">共有{{Math.ceil(tableData.length/itemNum)}}页，{{tableData.length}}条数据。
							每页显示<el-input :style="{width:'2rem'}" @change="correctItemNum()" size="small"
								v-model="itemNumInput"></el-input>条数据。
						</el-text>
					</div>
				</el-card>
				<el-dialog title="修改数据" v-model="editDialogVisable" width="20rem"
					:style="{borderRadius:'10px',border:'0.125rem solid var(--el-color-primary)'}">
					<el-form :model="editData.data" label-width="auto" label-suffix=":">
						<el-form-item v-for="(value, key) in editData.label" :key="key" :prop="key" :label="value">
							<el-input v-model="editData.data[key]"></el-input>
						</el-form-item>
					</el-form>
					<template #footer>
						<span>
							<el-button @click="editDialogVisable = false">取消</el-button>
							<el-button type="primary" @click="updata();editDialogVisable = false">
								提交
							</el-button>
						</span>
					</template>
				</el-dialog>
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
							<el-button type="primary" @click="save();dialogVisable = false">
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
	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus'
	const api = "/menu";
	const attrs = [{
			prop: "id",
			label: "ID",
			width: "60",
		},
		{
			prop: "name",
			label: "功能名",
		}
	];
	const formData = reactive({
		label: {
			name: "功能名"
		},
		data: {
			name: ""
		}
	});
	const editData = reactive({
		label: {
			name: "功能名"
		},
		data: {
			id: "",
			name: ""
		}
	})
	const editDialogVisable = ref(false);
	const dialogVisable = ref(false);
	const tableData = ref([])
	const pageNum = ref(1)
	const pageNumInput = ref(1)
	const itemNum = ref(10)
	const itemNumInput = ref(10)
	const frontPage = () => {
		if (pageNum.value > 1) {
			pageNum.value--
		} else {
			pageNum.value = 1
		}
		pageNumInput.value = pageNum.value
	}
	const nextPage = () => {
		if (pageNum.value < Math.ceil(tableData.value.length / itemNum.value)) {
			pageNum.value++
		} else {
			pageNum.value = Math.ceil(tableData.value.length / itemNum.value)
		}
		pageNumInput.value = pageNum.value
	}
	const correctPageNum = () => {
		let value = parseInt(pageNumInput.value)
		if (!isNaN(value)) {
			pageNum.value = value
		}
		if (pageNum.value < 1) {
			pageNum.value = 1
		} else if (pageNum.value > Math.ceil(tableData.value.length / itemNum.value)) {
			pageNum.value = Math.ceil(tableData.value.length / itemNum.value)
		}
		pageNumInput.value = pageNum.value
	}
	const correctItemNum = () => {
		let value = parseInt(itemNumInput.value)
		if (!isNaN(value)) {
			itemNum.value = value
		}
		if (itemNum.value < 5) {
			itemNum.value = 5
		} else if (itemNum.value > 50) {
			itemNum.value = 50
		}
		itemNumInput.value = itemNum.value
	}
	const tableDataByPage = computed(
		() => filterTableData.value.slice((pageNum.value - 1) * itemNum.value, pageNum.value * itemNum.value)
	)
	const filter = ref("")
	const filterType = ref("")
	const filterTableData = computed(
		() => tableData.value.filter(
			(data) =>
			!filter.value || !filterType.value ||
			String(data[filterType.value]).toLowerCase().includes(filter.value.toLowerCase())
		))
	const table = ref()
	const selectedRow = computed(() => table.value.getSelectionRows())
	const getAll = () => {
		axios.get('menu').then(rspn => {
			tableData.value = rspn.data.data;
		}).catch(error => {
			console.log(error)
		})
	};
	const edit = (index) => {
		editDialogVisable.value = true
		let data = tableDataByPage.value[index]
		for (let key of Object.keys(data)) {
			editData.data[key] = data[key]
		}

	}
	const save = () => {
		axios.post('menu', JSON.parse(JSON.stringify(formData.data)))
			.then(rspn => {
				ElMessage({
					type: 'success',
					message: "添加成功！",
					duration: 1000,
					onClose: () => {
						getAll()
					}
				})
			}).catch(error => {
				ElMessage({
					type: 'error',
					message: error
				})
				console.log(error)
			})
	}
	const updata = () => {
		axios.post('menu', JSON.parse(JSON.stringify(editData.data)))
			.then(rspn => {
				ElMessage({
					type: 'success',
					message: "修改成功！",
					duration: 1000,
					onClose: () => {
						getAll()
					}
				})
			}).catch(error => {
				ElMessage({
					type: 'error',
					message: error
				})
				console.log(error)
			})
	}
	const deleteRows = () => {
		ElMessageBox.confirm(
				'确定要删除这些数据吗?',
				'Warning', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {
				selectedRow.value.forEach(
					row => {
						console.log(row.id)
						deleteById(row.id)
					}
				)
				ElMessage({
					type: 'success',
					message: '删除成功。',
					duration: 1000,
					onClose: () => {
						getAll()
					}
				})
			})
			.catch(() => {})
		getAll()
	}
	const deleteSingle = (id) => {
		ElMessageBox.confirm(
				'确定要删除这条数据吗?',
				'Warning', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}
			)
			.then(() => {

				deleteById(id)
				ElMessage({
					type: 'success',
					message: '删除成功。',
					duration: 1000,
					onClose: () => {
						getAll()
					}
				})
			})
			.catch(() => {})
		getAll()
	}
	const deleteById = (id) => {
		axios.delete(`menu/${id}`)
			.then(rspn => {
				if (!rspn.data.data) {
					ElMessage({
						type: 'error',
						message: '删除失败！'
					})
				}
			})
			.catch(error => {
				ElMessage({
					type: 'error',
					message: error.data
				})
			})
	}
	window.data = tableData
	onMounted(() => {
		getAll();
	});
</script>

<style>
</style>