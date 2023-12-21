import {
	createApp
} from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import * as axios from 'axios'
import side_bar from './components/side_bar.vue'
import head_bar from './components/head_bar.vue'
import query_list from './components/query_list.vue'

axios.defaults.baseURL = 'http://localhost:8081/api'


import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
// VuePress主题以及样式（这里也可以选择github主题）
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';

// Prism
import Prism from 'prismjs';
// 代码高亮
import 'prismjs/components/prism-json';
// 选择使用主题
VMdPreview.use(vuepressTheme, {
	Prism,
});

const app = createApp(App).use(router).use(ElementPlus).use(VMdPreview);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
	app.component(key, component)
}
app.config.globalProperties.$axios = axios
app.mount('#app')
app.component('side-bar', side_bar)
app.component('head-bar', head_bar)
app.component('query-list', query_list)