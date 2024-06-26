import axios from 'axios';

axios.defaults.baseURL = '/api'

function isTokenExpired() {
	const expirationTime = localStorage.getItem('expirationTime');
	if (!expirationTime) {
		return true; // 如果没有设置过期时间，认为 token 已过期
	}
	const currentTime = new Date().getTime();
	return currentTime > parseInt(expirationTime, 10);
}

axios.interceptors.request.use((config) => {
		if (isTokenExpired()) {
			localStorage.removeItem('token');
			localStorage.removeItem('expirationTime');
		} else {
			const token = localStorage.getItem('token');
			if (token) {
				config.headers['Authorization'] = `Bearer ${token}`;
			}
		}
		return config;
	},
	(error) => {
		return Promise.reject(error);
	}
);
export default axios;