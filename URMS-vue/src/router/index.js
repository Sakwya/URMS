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
	path: '/register',
	name: 'register',
	component: () => import('../components/register.vue'),
}, {
	path: '/login',
	name: 'login',
	component: () => import('../components/login.vue'),
}]

const router = createRouter(({
	history: createWebHistory(),
	routes: routes
}))
export default router