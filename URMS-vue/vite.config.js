import {
	defineConfig
} from 'vite'
import vue from '@vitejs/plugin-vue'

import {
	resolve
} from 'path'

const pathResolve = (dir) => {
	return resolve(__dirname, ".", dir)
}

const alias = {
	'@': pathResolve("src")
}

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [vue()],
	resolve: {
		alias
	},
	server: {
		host: 'localhost',
		port: 8080,
		strictPort: true,
		https: false,
		open: true,
		proxy: { // 配置自定义代理规则
			'/api': {
				target: 'http://localhost',
				port: 8081,
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api/, ''),
				ws: true, //WebSocket
			}
		}
	}

})