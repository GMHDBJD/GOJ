import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Problem from '../views/Problem.vue'
import Contest from '../views/Contest.vue'
import Status from '../views/Status.vue'
import Ranking from '../views/Ranking.vue'
import Queue from '../views/Queue.vue'
import Discuss from '../views/Discuss.vue'

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
    path: '/problem',
    name: 'problem',
    component: Problem
  },
  {
    path: '/contest',
    name: 'contest',
    component: Contest
  },
  {
    path: '/status',
    name: 'status',
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
    path: '/discuss',
    name: 'discuss',
    component: Discuss
  }
]

const router = new VueRouter({
  routes
})

export default router
