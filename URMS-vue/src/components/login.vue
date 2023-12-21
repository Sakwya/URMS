<template>
	<el-card id="login">
		<el-form :model="loginForm" :rules="rules" class="login-container" label-position="left" label-width="4rem"
			v-loading="loading">
			<el-text tag="b" size="large" type="primary"><el-icon>
					<UserFilled />
				</el-icon>系统登录</el-text>
			<el-form-item prop="username" label="用户名">
				<el-input type="text" v-model="loginForm.username" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item prop="password" label="密码">
				<el-input type="password" v-model="loginForm.password" auto-complete="off"></el-input>
			</el-form-item>
			<el-form-item label="记住密码">
			      <el-switch v-model="checked" />
			    </el-form-item>
			<el-form-item style="width: 100%">
				<el-button type="primary" v-on:click="login">登录</el-button>
				<el-button type="primary" plain><router-link
						to="register">注册</router-link></el-button>
			</el-form-item>
		</el-form>
	</el-card>
</template>
<script>
	export default {
		data() {
			return {
				rules: {
					username: [{
						required: true,
						message: '用户名不能为空',
						trigger: 'blur'
					}],
					password: [{
						required: true,
						message: '密码不能为空',
						trigger: 'blur'
					}]
				},
				checked: true,
				loginForm: {
					username: '',
					password: ''
				},
				loading: false
			}
		},
		methods: {
			login() {
				var _this = this
				this.$axios
					.post('/login', {
						username: this.loginForm.username,
						password: this.loginForm.password
					})
					.then(resp => {
						if (resp.data.code === 200) {
							var data = resp.data.result
							_this.$store.commit('login', data)
							var path = _this.$route.query.redirect
							_this.$router.replace({
								path: path === '/' || path === undefined ? '/admin/dashboard' : path
							})
						} else {
							this.$alert(resp.data.message, '提示', {
								confirmButtonText: '确定'
							})
						}
					})
					.catch(failResponse => {})
			}
		}
	}
</script>
<style>
	#login {
		background: url("../static/title.png") no-repeat;
		background-position: center;
		height: 100%;
		width: 100%;
		background-size: contain;
		position: fixed;
	}

	.login-container {
		border-radius: 15px;
		background-clip: padding-box;
		position: absolute;
		top: 50%;
		left: 50%;
		padding: 2rem;
		transform: translate(-50%,-50%);
		width: 350px;
		background: #eeee;
		box-shadow: 0 0 0.5rem #8888;
	}

</style>