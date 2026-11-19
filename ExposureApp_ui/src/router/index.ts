import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Exposures from '../components/Exposures.vue';
import ExposureDetail from '../components/ExposureDetail.vue';
import InstrumentExposures from '../components/InstrumentExposures.vue';
import InstrumentExposureDetail from '../components/InstrumentExposureDetail.vue';
import SectorExposures from '../components/SectorExposures.vue';
import SectorExposureDetail from '../components/SectorExposureDetail.vue';
import CurrencyExposures from '../components/CurrencyExposures.vue';
import CurrencyExposureDetail from '../components/CurrencyExposureDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/exposures',
    name: 'Exposures',
    component: Exposures
  },
  {
    path: '/exposure/:id',
    name: 'ExposureDetail',
    component: ExposureDetail,
    props: true
  },
  {
    path: '/instrumentExposures',
    name: 'InstrumentExposures',
    component: InstrumentExposures
  },
  {
    path: '/instrumentExposure/:id',
    name: 'InstrumentExposureDetail',
    component: InstrumentExposureDetail,
    props: true
  },
  {
    path: '/sectorExposures',
    name: 'SectorExposures',
    component: SectorExposures
  },
  {
    path: '/sectorExposure/:id',
    name: 'SectorExposureDetail',
    component: SectorExposureDetail,
    props: true
  },
  {
    path: '/currencyExposures',
    name: 'CurrencyExposures',
    component: CurrencyExposures
  },
  {
    path: '/currencyExposure/:id',
    name: 'CurrencyExposureDetail',
    component: CurrencyExposureDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
