import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
import { EventBus } from '@/eventbus'

Vue.use(VueRouter)

function lazyLoad(view) {
  return () => import(`@/views/${view}.vue`)
}

const routes = [
  {
    path: '/',
    name: 'home',
    component: lazyLoad('Home'),
    meta: {
      show_footer: true
    }
  },
  {
    path: '/problems',
    name: 'problems',
    component: lazyLoad('Problems')
  },
  {
    path: '/contests',
    name: 'contests',
    component: lazyLoad('Contests')
  },
  {
    path: '/status',
    name: 'status',
    component: lazyLoad('Status')
  },
  {
    path: '/contests/:contestId/status',
    name: 'contestStatus',
    component: lazyLoad('Status'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/contests/:contestId/ranking',
    name: 'contestRanking',
    component: lazyLoad('Ranking'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/ranking',
    name: 'ranking',
    component: lazyLoad('Ranking')
  },
  {
    path: '/queue',
    name: 'queue',
    component: lazyLoad('Queue')
  },
  {
    path: '/problems/:problemId',
    name: 'problem',
    component: lazyLoad('Problem')
  },
  {
    path: '/contests/:contestId/problems/:problemId',
    name: 'contestProblem',
    component: lazyLoad('Problem'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/contests/:contestId',
    name: 'contest',
    component: lazyLoad('Contest'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/contests/:contestId/problems/:problemId',
    name: 'ContestProblem',
    component: lazyLoad('Problem'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/status/:statuId',
    name: 'statu',
    component: lazyLoad('Statu'),
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/users/:username',
    name: 'user',
    component: lazyLoad('User')
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (
    to.matched.some(record => record.meta.requiresAuth) &&
    !store.state.isLogin
  ) {
    EventBus.$emit('callLogin')
  } else {
    next()
  }
})

export default router
