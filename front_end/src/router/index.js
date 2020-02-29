import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Problems from '../views/Problems.vue'
import Problem from '../views/Problem.vue'
import Contests from '../views/Contests.vue'
import Contest from '../views/Contest.vue'
import Status from '../views/Status.vue'
import Statu from '../views/Statu.vue'
import Ranking from '../views/Ranking.vue'
import Queue from '../views/Queue.vue'
import User from '../views/User.vue'
import store from '@/store'
import { EventBus } from '@/eventbus'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    meta: {
      show_footer: true
    }
  },
  {
    path: '/problems',
    name: 'problems',
    component: Problems
  },
  {
    path: '/contests',
    name: 'contests',
    component: Contests
  },
  {
    path: '/status',
    name: 'status',
    component: Status
  },
  {
    path: '/contests/:contestId/status',
    name: 'contestStatus',
    component: Status,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/contests/:contestId/ranking',
    name: 'contestRanking',
    component: Ranking,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/ranking',
    name: 'ranking',
    component: Ranking
  },
  {
    path: '/queue',
    name: 'queue',
    component: Queue
  },
  {
    path: '/problems/:problemId',
    name: 'problem',
    component: Problem
  },
  {
    path: '/contests/:contestId/problems/:problemId',
    name: 'contestProblem',
    component: Problem,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/contests/:contestId',
    name: 'contest',
    component: Contest,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/contests/:contestId/problems/:problemId',
    name: 'ContestProblem',
    component: Problem,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/status/:statuId',
    name: 'statu',
    component: Statu,
    meta: {
      requireAuth: true
    }
  },
  {
    path: '/users/:username',
    name: 'user',
    component: User
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
