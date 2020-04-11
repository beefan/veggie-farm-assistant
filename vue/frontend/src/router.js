import Vue from 'vue'
import Router from 'vue-router'
import auth from './auth'
import Dashboard from './views/Dashboard.vue'
import Login from './views/Login.vue'
import Register from './views/Register.vue'
import UploadHarvestTimes from './views/UploadHarvestTimes.vue'
import Expiration from './views/Expiration.vue'
import CropPlanner from './views/CropPlanner.vue'
import Homepage from './views/Homepage.vue'
import FarmLog from './views/FarmLog.vue'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [{
            path: '/',
            name: 'home',
            component: Homepage,
            meta: {
                requiresAuth: false
            }
        },
        {
            path: "/login",
            name: 'login',
            component: Login,
            meta: {
                requiresAuth: false
            }
        },
        {
            path: "/register",
            name: 'register',
            component: Register,
            meta: {
                requiresAuth: false
            }
        },
        {
            path: "/harvesttimes",
            name: 'harvesttimes',
            component: UploadHarvestTimes,
        },
        {
            path: "/wasteinfo",
            name: 'expiration',
            component: Expiration,
        },
        {
            path: "/cropplanner",
            name: 'cropplanner',
            component: CropPlanner
        },
        {
            path: "/dashboard",
            name: 'dashboard',
            component: Dashboard
        },
        {
            path: "/farmlog",
            name: "farmlog",
            component: FarmLog
        }
    ]
})

router.beforeEach((to, from, next) => {
    // Determine if the route requires Authentication
    const requiresAuth = to.matched.some(x => x.meta.requiresAuth);
    const user = auth.getUser();

    // If it does and they are not logged in, send the user to "/login"
    if (requiresAuth && !user) {
        next("/login");
    } else {
        // Else let them go to their next destination
        next();
    }
});

export default router;