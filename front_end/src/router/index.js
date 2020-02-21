import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Problems from '../views/Problems.vue'
import Problem from '../views/Problem.vue'
import Contests from '../views/Contests.vue'
import Contest from '../views/Contest.vue'
import Status from '../views/Status.vue'
import Ranking from '../views/Ranking.vue'
import Queue from '../views/Queue.vue'

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
    path: '/statu',
    name: 'statu',
    component: Status
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
    name: 'problemDetail',
    component: Problem
  },
  {
    path: '/contests/:contestId',
    name: 'contestDetail',
    component: Contest
  },
  {
    path: '/contests/:contestId/problems/:problemId',
    name: 'ContestProblem',
    component: Problem,
    meta: {
      isContest: true
    }
  }
]

const router = new VueRouter({
  routes
})

export default router
