import {
	createRouter,
	createWebHistory
} from 'vue-router'

const routes = [{
		path: '/',
		name: 'index',
		component: () => import('../components/panel.vue'),
	}, {
		path: '/user',
		name: 'user',
		component: () => import('../components/user.vue'),
	}, {
		path: '/role',
		name: 'role',
		component: () => import('../components/role.vue'),
	}, {

		path: '/menu',
		name: 'menu',
		component: () => import('../components/menu.vue'),
	},
	{
		path: '/register',
		name: 'register',
		component: () => import('../components/register.vue'),
	}, {
		path: '/login',
		name: 'login',
		component: () => import('../components/login.vue'),
	}
]

const router = createRouter(({
	history: createWebHistory(),
	routes: routes
}))

router.beforeEach((to, from, next) => {
	const token = localStorage.getItem('token');
	if (to.path !== '/login' && to.path !== '/register' && !token) {
		next('/login'); // 重定向到 login 页面
	} else {
		next(); // 继续路由导航
	}
});
export default router