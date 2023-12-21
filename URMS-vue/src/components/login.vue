<template>
	<el-card id="form-body">
		<el-card id="form-container">
			<template #header>
				<el-text size="large" type="primary"><el-icon>
						<UserFilled />
					</el-icon>用户登录</el-text>
			</template>
			<el-form ref="lForm" :model="loginForm" :rules="rules" label-position="left" label-width="4rem"
				v-loading="loading">
				<el-form-item prop="account" label="账户:">
					<el-input name="account" type="text" v-model="loginForm.account"></el-input>
				</el-form-item>
				<el-form-item prop="password" label="密码:">
					<el-input name="password" type="password" v-model="loginForm.password"></el-input>
				</el-form-item>
				<el-form-item style="width: 100%">
					<el-button type="primary" :disabled="isDisabled" v-on:click="login">登录</el-button>
					<el-button type="primary" plain><router-link to="register">注册</router-link></el-button>
				</el-form-item>
			</el-form></el-card>
	</el-card>
</template>
<script>
	import {
		ElMessage
	} from 'element-plus';
	import {
		useRouter
	} from 'vue-router';
	import {
		reactive,
		ref,
		onMounted,
		watch
	} from 'vue';
	import axios from 'axios'
	import throttle from 'lodash/throttle'; // 导入节流函数
	export default {
		setup() {
			const lForm = ref(null);
			const isDisabled = ref(true); // 添加 isDisabled 变量
			const router = useRouter();
			const rules = reactive({
				account: [{
					required: true,
					message: '请输入账户',
					trigger: 'blur'
				}],
				password: [{
					required: true,
					message: '请输入密码',
					trigger: 'blur'
				}]
			});
			const loginForm = reactive({
				account: '',
				password: ''
			});
			const countDown = ref(5);
			const message = ref('登陆成功，5秒后自动跳转……');
			watch(countDown, (newValue, oldValue) => {
				message.value = `登陆成功，${newValue}秒后自动跳转……`;
			});
			const login = () => {
				axios.post('/login', {
						account: loginForm.account,
						password: loginForm.password
					})
					.then(successResponse => {
						if (successResponse.status === 200 && successResponse.data.code == "200") {
							countDown.value = 5;
							const timeoutId = setTimeout(() => {
								router.push("/");
							}, countDown.value * 1000); // 延迟 5 秒后执行跳转
							localStorage.setItem('token', successResponse.data.data.token);
							const messageInstance = ElMessage({
								showClose: true,
								message: message,
								type: 'success',
								center: true,
								duration: 0,
								onClose: () => {
									router.push("/");
									clearTimeout(timeoutId); // 点击后取消延时跳转
								}
							});
							const timer = setInterval(() => {
								if (--countDown.value > 0) {} else {
									messageInstance.close()
									clearInterval(timer);
								}
							}, 1000)
						} else {
							ElMessage({
								showClose: true,
								message: successResponse.data.data.message,
								type: 'error'
							})
						}
					})
					.catch(failResponse => {
						ElMessage({
							showClose: true,
							message: failResponse,
							type: 'error',
						})
					})
			}
			const handleThrottledMouseMove = throttle((event) => {
				// 处理鼠标移动事件
				const body = document.documentElement || document.body;
				const mouseX = event.pageX;
				const mouseY = event.pageY;
				const percentX = (mouseX / body.clientWidth) * 20 + 40; // 计算 X 轴百分比
				const percentY = (mouseY / body.clientHeight) * 20 + 40; // 计算 Y 轴百分比

				let container = document.getElementById("form-container");
				container.style.top = `${percentY}%`;
				container.style.left = `${percentX}%`;
				document.getElementById("form-body").style.backgroundPosition =
					`${percentX*0.2+40}% ${percentY*0.2+40}%`;
			}, 50); // 设置节流的时间间隔为 200ms
			onMounted(() => {
				document.onmousemove = handleThrottledMouseMove;
			});
			watch(loginForm, () => {
				lForm.value.validate((valid) => {
					isDisabled.value = !valid;
				});
			}, {
				deep: true
			});
			return {
				rules,
				loginForm,
				login,
				isDisabled,
				lForm
			}
		}
	}
</script>
<style>
	#form-body {
		background: url("../static/title.png") no-repeat;
		background-position: 50% 50%;
		height: 100%;
		width: 100%;
		position: fixed;
		transition: background-position 0.5s ease-out;
	}

	#form-container {
		border-radius: 15px;
		background-clip: padding-box;
		position: absolute;
		top: 50%;
		left: 50%;
		transform: translate(-50%, -50%);
		width: 350px;
		background: #eeee;
		box-shadow: 0 0 0.5rem #8888;
		transition: top 0.5s ease-out, left 0.5s ease-out;
	}
</style>