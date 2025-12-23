import {createRouter, createWebHistory} from "vue-router";
import {useAuthStore} from "@/stores/auth";

// Layouts
import AdminLayout from "@/layouts/AdminLayout.vue";
import UserLayout from "@/layouts/UserLayout.vue";
import OwnerLayout from "@/layouts/OwnerLayout.vue";

// User Pages
import UserHome from "@/views/user/Home.vue";
import UserLogin from "@/views/user/auth/Login.vue";
import UserRegister from "@/views/user/auth/Register.vue";
import UserForgotPassword from "@/views/user/auth/ForgotPassword.vue";
import UserResetPassword from "@/views/user/auth/ResetPassword.vue";
import UserSearch from "@/views/user/search/Search.vue";
import OutletDetail from "@/views/user/outlet/OutletDetail.vue";
import BookingForm from "@/views/user/booking/BookingForm.vue";
import BookingHistory from "@/views/user/booking/BookingHistory.vue";
import BookingConfirmation from "@/views/user/booking/BookingConfirmation.vue";
import UserProfile from "@/views/user/profile/Profile.vue";
import MembershipSubscription from "@/views/user/membership/MembershipSubscription.vue";

// Admin Pages
import AdminDashboard from "@/views/admin/Dashboard.vue";
import UserManagement from "@/views/admin/user/UserManagement.vue";
import UserDetail from "@/views/admin/user/UserDetail.vue";
import OutletManagement from "@/views/admin/outlet/OutletManagement.vue";
import OutletDetailAdmin from "@/views/admin/outlet/OutletDetail.vue";
import CategoryManagement from "@/views/admin/category/CategoryManagement.vue";
import OutletTypeManagement from "@/views/admin/category/OutletTypeManagement.vue";
import FeatureManagement from "@/views/admin/category/FeatureManagement.vue";
import MenuManagement from "@/views/admin/menu/MenuManagement.vue";
import OrderManagement from "@/views/admin/order/OrderManagement.vue";
import ReviewManagement from "@/views/admin/review/ReviewManagement.vue";
import CountryManagement from "@/views/admin/geographic/CountryManagement.vue";
import ProvinceManagement from "@/views/admin/geographic/ProvinceManagement.vue";
import DistrictManagement from "@/views/admin/geographic/DistrictManagement.vue";
import MembershipManagement from "@/views/admin/membership/MembershipManagement.vue";
import ReportList from "@/views/admin/reporting/ReportList.vue";
import TransactionHistory from "@/views/admin/reporting/TransactionHistory.vue";

// Owner Pages
import OwnerDashboard from "@/views/owner/Dashboard.vue";
import BookingManagement from "@/views/owner/booking/BookingManagement.vue";
import BookingDetail from "@/views/owner/booking/BookingDetail.vue";
import OwnerMenuManagement from "@/views/owner/menu/MenuManagement.vue";
import OutletInformation from "@/views/owner/outlet/OutletInformation.vue";
import FeedbackReview from "@/views/owner/feedback/FeedbackReview.vue";
import OwnerStatistics from "@/views/owner/statistics/Statistics.vue";

const routes = [
  // User Routes
  {
    path: "/",
    component: UserLayout,
    children: [
      {path: "", name: "Home", component: UserHome},
      {path: "search", name: "Search", component: UserSearch},
      {path: "outlet/:id", name: "OutletDetail", component: OutletDetail},
      {
        path: "booking/:outletId",
        name: "BookingForm",
        component: BookingForm,
        meta: {requiresAuth: true},
      },
      {
        path: "booking-history",
        name: "BookingHistory",
        component: BookingHistory,
      },
      {
        path: "booking-confirmation/:id",
        name: "BookingConfirmation",
        component: BookingConfirmation,
      },
      {path: "profile", name: "UserProfile", component: UserProfile},
      {
        path: "membership",
        name: "MembershipSubscription",
        component: MembershipSubscription,
      },
    ],
  },
  // User Auth Routes (no layout)
  {
    path: "/auth",
    children: [
      {path: "login", name: "Login", component: UserLogin},
      {path: "register", name: "Register", component: UserRegister},
      {
        path: "forgot-password",
        name: "ForgotPassword",
        component: UserForgotPassword,
      },
      {
        path: "reset-password",
        name: "ResetPassword",
        component: UserResetPassword,
      },
    ],
  },
  // Admin Routes
  {
    path: "/admin",
    component: AdminLayout,
    children: [
      {path: "", name: "AdminDashboard", component: AdminDashboard},
      {path: "users", name: "UserManagement", component: UserManagement},
      {path: "users/:id", name: "UserDetail", component: UserDetail},
      {path: "outlets", name: "OutletManagement", component: OutletManagement},
      {
        path: "outlets/:id",
        name: "OutletDetailAdmin",
        component: OutletDetailAdmin,
      },
      {
        path: "menus",
        name: "MenuManagement",
        component: MenuManagement,
      },
      {
        path: "categories",
        name: "CategoryManagement",
        component: CategoryManagement,
      },
      {
        path: "outlet-types",
        name: "OutletTypeManagement",
        component: OutletTypeManagement,
      },
      {
        path: "features",
        name: "FeatureManagement",
        component: FeatureManagement,
      },
      {
        path: "orders",
        name: "OrderManagement",
        component: OrderManagement,
      },
      {
        path: "reviews",
        name: "ReviewManagement",
        component: ReviewManagement,
      },
      {
        path: "countries",
        name: "CountryManagement",
        component: CountryManagement,
      },
      {
        path: "provinces",
        name: "ProvinceManagement",
        component: ProvinceManagement,
      },
      {
        path: "districts",
        name: "DistrictManagement",
        component: DistrictManagement,
      },
      {
        path: "memberships",
        name: "MembershipManagement",
        component: MembershipManagement,
      },
      {path: "reports", name: "ReportList", component: ReportList},
      {
        path: "transactions",
        name: "TransactionHistory",
        component: TransactionHistory,
      },
    ],
  },
  // Owner Routes
  {
    path: "/owner",
    component: OwnerLayout,
    children: [
      {path: "", name: "OwnerDashboard", component: OwnerDashboard},
      {
        path: "bookings",
        name: "BookingManagement",
        component: BookingManagement,
      },
      {path: "bookings/:id", name: "BookingDetail", component: BookingDetail},
      {path: "menu", name: "OwnerMenuManagement", component: OwnerMenuManagement},
      {path: "outlet", name: "OutletInformation", component: OutletInformation},
      {path: "feedback", name: "FeedbackReview", component: FeedbackReview},
      {path: "statistics", name: "OwnerStatistics", component: OwnerStatistics},
    ],
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Route guards
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  // Check admin routes - require authentication and ADMIN role
  if (to.path.startsWith("/admin")) {
    if (!authStore.isAuthenticated) {
      next("/auth/login");
      return;
    }
    if (!authStore.isAdmin) {
      // Show 403 or redirect to home
      next("/");
      return;
    }
  }

  // Check owner routes - require authentication and OWNER role
  if (to.path.startsWith("/owner")) {
    if (!authStore.isAuthenticated) {
      next("/auth/login");
      return;
    }
    if (!authStore.isOwner) {
      // Show 403 or redirect to home
      next("/");
      return;
    }
  }

  // Check if route requires auth
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next("/auth/login");
    return;
  }

  next();
});

export default router;
