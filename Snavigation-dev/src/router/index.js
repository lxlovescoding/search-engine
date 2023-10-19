import { createRouter, createWebHistory } from 'vue-router';

const routes = [
    {
        name: 'home',
        path: '/',
        component: () => import('@/views/HomeView.vue'), // 替换成你的组件
    },
    {
        path: '/search/:id',
        component: () => import('@/views/SearchView.vue'), // 替换成你的组件
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
