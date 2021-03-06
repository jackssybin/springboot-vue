import Vue from 'vue'
import Router from 'vue-router'
import store from './store'

if (process.env.NODE_ENV !== 'production') {
    Vue.use(Router);
    //解决点击当前路由报错问题
    const originalPush = Router.prototype.push;
    Router.prototype.push = function push(location) {
        return originalPush.call(this, location).catch(err => err)
    };
}

const baseRouters = [
    {path: '/login', name: 'login', component: () => import('@/views/login/Index')}
];

const router = new Router({
    // mode: 'history',
    base: process.env.BASE_URL,
    routes: baseRouters
});

/**
 *取消上一个页面的请求
 */
function clearAxiosCancelToken() {
    let cancelArray = window['_axiosCancelToken'] || [];
    cancelArray.forEach(value => {
        value();
    });
    window['_axiosCancelToken'] = [];
}

/**
 * 重新生成路由
 * @returns {{children: Array, haveHomePage: boolean}}
 */
function handleRouterChildren() {
    let routers = store.state.routers.routers;
    let children = [];
    let haveHomePage = false;
    routers.forEach((value) => {
        if ('home' === value['name']) {
            haveHomePage = true;
        }
        let router = {
            path: value.path,
            name: value.name,
            meta: {
                title: value.meta.title,
                icon: value.meta.icon
            }
        };
        try {
            router['components'] = require('@/views/index/' + value.components_bak + '/Index');
        } catch (e) {
            console.debug(value.path, '没有找到对应组件');
            router['components'] = require(`@/views/errorPage/404`);
        }
        router['components_bak'] = value.components_bak;
        children.push(router);
    });
    return {
        children, haveHomePage
    };
}

router.beforeEach((to, from, next) => {
    clearAxiosCancelToken();
    const token = store.state.token;
    if ((null == token || '' === token) && 'login' !== to.name) {
        next('/login');
        return;
    }
    //刷新页面，重新初始化路由
    if ((null == from.name || '' === from.name) && (null == to.name || '' === to.name)) {
        let {children, haveHomePage} = handleRouterChildren();
        let parentRouters = [{
            path: '/',
            components: require('@/views/layout/Index'),
            name: 'index',
            children
        }];
        router.addRoutes(parentRouters);
        next(haveHomePage ? '/home' : children[0]['path'] || '/');
        return;
    }
    //正常放行
    next();
});

export default router;