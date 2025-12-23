<template>
  <div
    class="relative flex min-h-screen w-full flex-col bg-background-light dark:bg-background-dark"
  >
    <!-- TopNavBar -->
    <header
      class="sticky top-0 z-50 flex items-center justify-center border-b border-border-light dark:border-border-dark bg-background-light/80 dark:bg-background-dark/80 backdrop-blur-sm"
    >
      <div
        class="flex h-16 w-full max-w-7xl items-center justify-between px-4 sm:px-6 lg:px-8"
      >
        <div class="flex items-center gap-4">
          <svg
            class="h-6 w-6 text-primary"
            fill="none"
            viewBox="0 0 48 48"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              d="M44 4H30.6666V17.3334H17.3334V30.6666H4V44H44V4Z"
              fill="currentColor"
            ></path>
          </svg>
          <h2
            class="text-xl font-bold leading-tight tracking-[-0.015em] text-text-light dark:text-text-dark"
          >
            FoodGo
          </h2>
        </div>

        <nav class="hidden items-center gap-9 md:flex">
          <router-link
            to="/"
            class="text-sm font-medium text-text-light dark:text-text-dark hover:text-primary dark:hover:text-primary"
          >
            Trang chủ
          </router-link>
          <router-link
            to="/search"
            class="text-sm font-medium text-text-light dark:text-text-dark hover:text-primary dark:hover:text-primary"
          >
            Khám phá
          </router-link>

          <!-- Show Login if not authenticated -->
          <router-link
            v-if="!authStore.isAuthenticated"
            to="/auth/login"
            class="text-sm font-medium text-text-light dark:text-text-dark hover:text-primary dark:hover:text-primary"
          >
            Đăng nhập
          </router-link>

          <!-- Show Profile dropdown if authenticated -->
          <div v-else class="relative group">
            <button
              class="flex items-center gap-2 text-sm font-medium text-text-light dark:text-text-dark hover:text-primary dark:hover:text-primary"
            >
              <span class="material-symbols-outlined text-xl"
                >account_circle</span
              >
              <span>{{
                authStore.user?.fullName || authStore.user?.email
              }}</span>
            </button>

            <!-- Dropdown menu -->
            <div
              class="absolute right-0 mt-2 w-48 bg-white dark:bg-surface-dark rounded-lg shadow-lg border border-border-light dark:border-border-dark opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all"
            >
              <router-link
                to="/profile"
                class="block px-4 py-2 text-sm text-text-light dark:text-text-dark hover:bg-gray-100 dark:hover:bg-gray-800 rounded-t-lg"
              >
                Hồ sơ cá nhân
              </router-link>
              <router-link
                to="/booking-history"
                class="block px-4 py-2 text-sm text-text-light dark:text-text-dark hover:bg-gray-100 dark:hover:bg-gray-800"
              >
                Lịch sử đặt bàn
              </router-link>
              <router-link
                to="/membership"
                class="block px-4 py-2 text-sm text-text-light dark:text-text-dark hover:bg-gray-100 dark:hover:bg-gray-800"
              >
                Đăng ký membership
              </router-link>
              <button
                @click="handleLogout"
                class="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-gray-100 dark:hover:bg-gray-800 rounded-b-lg"
              >
                Đăng xuất
              </button>
            </div>
          </div>
        </nav>

        <button
          @click="handleOwnerClick"
          class="flex min-w-[84px] cursor-pointer items-center justify-center overflow-hidden rounded-full h-10 px-4 bg-primary text-white text-sm font-bold leading-normal tracking-[0.015em] hover:bg-opacity-90 transition-colors"
        >
          <span class="truncate">Dành cho chủ quán</span>
        </button>
      </div>
    </header>

    <!-- Owner registration modal (moved out of header for correct overlay) -->
    <transition name="fade">
      <div
        v-if="showOwnerModal"
        @click.self="showOwnerModal = false"
        class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4"
      >
        <div
          class="relative bg-white dark:bg-surface-dark rounded-xl max-w-lg w-full max-h-[90vh] overflow-auto p-6 shadow-lg border border-border-light dark:border-border-dark"
        >
          <button
            @click="showOwnerModal = false"
            class="absolute top-3 right-3 text-subtext-light hover:text-red-600 rounded-full p-1"
            aria-label="Đóng"
          >
            <span class="material-symbols-outlined">close</span>
          </button>

          <h3 class="text-xl font-bold mb-2">Đăng ký làm chủ quán</h3>
          <p class="text-sm text-subtext-light dark:text-subtext-dark mb-4">
            Bạn chưa phải là chủ quán. Vui lòng đăng ký quán để tiếp tục.
          </p>

          <form
            @submit.prevent="submitOwnerRegistration"
            class="grid grid-cols-1 sm:grid-cols-2 gap-4 mb-4"
          >
            <div class="sm:col-span-2">
              <label class="block text-sm font-medium mb-2"
                >Tên quán <span class="text-red-500">*</span></label
              >
              <input
                v-model="ownerForm.name"
                class="w-full px-4 py-2 rounded-lg border"
                placeholder="Tên quán"
                required
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-2"
                >Số điện thoại</label
              >
              <input
                v-model="ownerForm.phoneNumber"
                class="w-full px-4 py-2 rounded-lg border"
                placeholder="Số điện thoại"
              />
            </div>

            <div>
              <label class="block text-sm font-medium mb-2"
                >Loại quán (bắt buộc) <span class="text-red-500">*</span></label
              >
              <select
                v-model="ownerForm.typeId"
                class="w-full px-4 py-2 rounded-lg border"
              >
                <option value="">Chọn loại quán</option>
                <option v-for="t in types" :key="t.id" :value="t.id">
                  {{ t.name }}
                </option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium mb-2">Tỉnh/Thành</label>
              <select
                v-model="ownerForm.provinceId"
                @change="onProvinceChange"
                class="w-full px-4 py-2 rounded-lg border"
              >
                <option value="">Chọn tỉnh</option>
                <option v-for="p in provinces" :key="p.id" :value="p.id">
                  {{ p.name }}
                </option>
              </select>
            </div>

            <div>
              <label class="block text-sm font-medium mb-2"
                >Quận/Huyện (bắt buộc)
                <span class="text-red-500">*</span></label
              >
              <select
                v-model="ownerForm.districtId"
                class="w-full px-4 py-2 rounded-lg border"
              >
                <option value="">Chọn quận/huyện</option>
                <option v-for="d in districts" :key="d.id" :value="d.id">
                  {{ d.name }}
                </option>
              </select>
            </div>

            <div class="sm:col-span-2">
              <label class="block text-sm font-medium mb-2"
                >Địa chỉ <span class="text-red-500">*</span></label
              >
              <input
                v-model="ownerForm.address"
                class="w-full px-4 py-2 rounded-lg border"
                placeholder="Địa chỉ"
                required
              />
            </div>

            <div class="sm:col-span-2">
              <label class="block text-sm font-medium mb-2">Mô tả</label>
              <textarea
                v-model="ownerForm.description"
                rows="3"
                class="w-full px-4 py-2 rounded-lg border"
                placeholder="Mô tả ngắn"
              ></textarea>
            </div>

            <div class="sm:col-span-2 flex items-center justify-end gap-3">
              <button
                type="button"
                @click="showOwnerModal = false"
                class="px-4 py-2 border rounded-lg"
              >
                Hủy
              </button>
              <button
                type="submit"
                :disabled="isSubmittingOwner"
                class="px-4 py-2 bg-primary text-white rounded-lg"
              >
                {{ isSubmittingOwner ? "Đang gửi..." : "Đăng ký quán" }}
              </button>
            </div>
          </form>

          <div v-if="ownerError" class="mt-3 text-red-600">
            {{ ownerError }}
          </div>
          <div v-if="ownerSuccess" class="mt-3 text-green-600">
            {{ ownerSuccess }}
          </div>
        </div>
      </div>
    </transition>

    <!-- Main Content -->
    <main class="flex flex-1 flex-col items-center">
      <router-view />
    </main>

    <!-- Footer -->
    <footer
      class="w-full bg-surface-light dark:bg-surface-dark border-t border-border-light dark:border-border-dark"
    >
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div class="text-center text-text-light dark:text-text-dark">
          <p class="text-sm">&copy; 2025 FoodGo. All rights reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useAuthStore} from "@/stores/auth";
import {useRouter} from "vue-router";
import {outletApi} from "@/api/outlet";
import {locationApi} from "@/api/location";

const authStore = useAuthStore();
const router = useRouter();

// Logout
const handleLogout = () => {
  authStore.logout();
  router.push("/auth/login");
};

// Owner registration modal state
const showOwnerModal = ref(false);
const isSubmittingOwner = ref(false);
const ownerError = ref("");
const ownerSuccess = ref("");
const categories = ref([]);

const ownerForm = ref({
  name: "",
  address: "",
  phoneNumber: "",
  categoryId: "",
  description: "",
  typeId: "",
  provinceId: "",
  districtId: "",
});

const types = ref([]);
const provinces = ref([]);
const districts = ref([]);

const handleOwnerClick = () => {
  if (!authStore.isAuthenticated) {
    router.push("/auth/login");
    return;
  }
  if (authStore.isOwner) {
    router.push("/owner");
    return;
  }
  // show registration modal
  ownerError.value = "";
  ownerSuccess.value = "";
  showOwnerModal.value = true;
};

const submitOwnerRegistration = async () => {
  if (!ownerForm.value.name || !ownerForm.value.address) {
    ownerError.value = "Vui lòng nhập tên quán và địa chỉ";
    return;
  }
  if (!ownerForm.value.typeId) {
    ownerError.value = "Vui lòng chọn loại quán";
    return;
  }
  if (!ownerForm.value.districtId) {
    ownerError.value = "Vui lòng chọn quận/huyện";
    return;
  }
  isSubmittingOwner.value = true;
  ownerError.value = "";
  ownerSuccess.value = "";
  try {
    const payload = {
      name: ownerForm.value.name,
      address: ownerForm.value.address,
      phoneNumber: ownerForm.value.phoneNumber,
      outletCategoryId: ownerForm.value.categoryId || undefined,
      typeId: Number(ownerForm.value.typeId),
      districtId: Number(ownerForm.value.districtId),
      description: ownerForm.value.description,
    };
    await outletApi.createOutlet(payload);
    ownerSuccess.value = "Đăng ký quán thành công! Chuyển đến trang quản lý...";

    // refresh profile to get owner role (if backend assigns)
    try {
      await authStore.fetchUserProfile();
    } catch (e) {
      console.warn("Could not refresh profile after outlet creation", e);
    }

    // close modal and navigate to owner dashboard
    showOwnerModal.value = false;
    router.push("/owner");
  } catch (err) {
    console.error("Error creating outlet:", err);
    ownerError.value = err.message || "Tạo quán thất bại";
  } finally {
    isSubmittingOwner.value = false;
  }
};

onMounted(async () => {
  try {
    const res = await outletApi.getCategories();
    categories.value = res || [];
  } catch (e) {
    console.warn("Could not load outlet categories", e);
  }

  // load outlet types
  try {
    const t = await outletApi.getTypes();
    types.value = t || [];
  } catch (e) {
    console.warn("Could not load outlet types", e);
  }

  // load provinces (no country filter by default)
  try {
    const p = await locationApi.getProvinces();
    provinces.value = p || [];
  } catch (e) {
    console.warn("Could not load provinces", e);
  }
});

const onProvinceChange = async () => {
  const provId = ownerForm.value.provinceId;
  if (!provId) {
    districts.value = [];
    ownerForm.value.districtId = "";
    return;
  }
  try {
    const d = await locationApi.getDistricts(provId);
    districts.value = d || [];
  } catch (e) {
    console.warn("Could not load districts for province", provId, e);
    districts.value = [];
  }
};
</script>
