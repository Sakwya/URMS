import {
	reactive
} from "vue"
import axios from "../axios.js";
import {
	useRouter
} from 'vue-router'
const router = useRouter()
const getMenu = () => {
	const userInfo = JSON.parse(localStorage.getItem('userInfo'));
	if (userInfo && userInfo.id) {
		axios.get(`menu/${userInfo.id}`)
			.then(rspn => {
				let menus = rspn.data.data
				let menuIds = []
				for (let i = 0; i < menus.length; i++) {
					menuIds.push(menus[i].id)
				}
				localStorage.setItem('menu', JSON.stringify(menuIds))
				setMenu()
			})
			.catch(error => console.log(error))
	} else {
		localStorage.clear()
		router.push('login')
	}
}


const menus = reactive({})
const setMenu = () => {
	const menuIds = JSON.parse(localStorage.getItem('menu'))
	menus.m1 = menuIds.includes(1)
	menus.m2 = menuIds.includes(2)
	menus.m3 = menuIds.includes(3)
	menus.m4 = menuIds.includes(4)
	menus.m5 = menuIds.includes(5)
	menus.m6 = menuIds.includes(6)
	menus.m7 = menuIds.includes(7)
	menus.m8 = menuIds.includes(8)
	menus.m9 = menuIds.includes(9)
	menus.m10 = menuIds.includes(10)
	menus.m11 = menuIds.includes(11)
	menus.m12 = menuIds.includes(12)
	menus.m13 = menuIds.includes(13)
}
if (!localStorage.getItem('menu')) {
	getMenu()
} else {
	setMenu()
}
export default menus;