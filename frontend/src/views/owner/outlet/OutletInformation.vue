<template>
  <div class="px-10 py-8">
    <h1 class="text-2xl font-bold mb-6">Thông tin quán</h1>

    <div v-if="isLoading" class="py-12">
      <div
        class="animate-spin rounded-full h-10 w-10 border-b-2 border-primary"
      ></div>
    </div>

    <div v-else>
      <div
        v-if="outlets.length === 0"
        class="p-6 bg-white dark:bg-surface-dark rounded-lg"
      >
        <p class="text-subtext-light dark:text-subtext-dark mb-4">
          Bạn chưa có quán nào. Hãy tạo quán mới để bắt đầu quản lý.
        </p>
        <button
          @click="openCreateOutlet"
          class="px-4 py-2 bg-primary text-white rounded-lg"
        >
          Tạo quán mới
        </button>
      </div>

      <div v-else class="grid grid-cols-1 lg:grid-cols-4 gap-6">
        <!-- Left: Outlet selector + summary -->
        <div class="lg:col-span-1 bg-white dark:bg-surface-dark p-4 rounded-lg">
          <label class="text-sm font-medium">Chọn quán</label>
          <select
            v-model="selectedOutletId"
            @change="onSelectOutlet"
            class="w-full mt-2 p-2 border rounded"
          >
            <option v-for="o in outlets" :key="o.id" :value="o.id">
              {{ o.name }}
            </option>
          </select>

          <div class="mt-4">
            <h3 class="text-lg font-semibold">Tóm tắt</h3>
            <div class="mt-2 text-sm text-subtext-light dark:text-subtext-dark">
              <p><strong>Booking hiện tại:</strong> {{ stats.bookings }}</p>
              <p>
                <strong>Doanh thu (tạm):</strong> {{ stats.revenueDisplay }}
              </p>
              <p>
                <strong>Đánh giá:</strong>
                <span v-if="selectedOutlet">{{
                  selectedOutlet.averageRating ?? "Chưa có"
                }}</span
                ><span v-else>—</span>
              </p>
              <p>
                <strong>Lượt đánh giá:</strong>
                {{ selectedOutlet?.totalReviews ?? 0 }}
              </p>
            </div>

            <div class="mt-4 flex gap-2">
              <button @click="viewOnSite" class="px-3 py-2 border rounded">
                Xem trang
              </button>
              <button @click="goToMenu" class="px-3 py-2 border rounded">
                Thực đơn
              </button>
            </div>
          </div>
        </div>

        <!-- Right: Details -->
        <div class="lg:col-span-3 bg-white dark:bg-surface-dark p-6 rounded-lg">
          <div class="flex justify-between items-start">
            <div>
              <h2 class="text-xl font-bold mb-1">
                {{ selectedOutlet?.name || "Không có thông tin" }}
              </h2>
              <p class="text-sm text-subtext-light dark:text-subtext-dark">
                {{ selectedOutlet?.address }}
              </p>
            </div>

            <div class="flex gap-2">
              <button
                @click="openEdit"
                class="px-4 py-2 bg-primary text-white rounded-lg"
              >
                Chỉnh sửa
              </button>
              <button
                @click="toggleActive"
                class="px-4 py-2 border rounded"
                :disabled="isSaving"
              >
                {{ selectedOutlet?.isActive ? "Tắt quán" : "Kích hoạt" }}
              </button>
            </div>
          </div>

          <div class="mt-6 grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <h3 class="font-semibold mb-2">Liên hệ</h3>
              <p>SĐT: {{ selectedOutlet?.phoneNumber || "-" }}</p>
              <p>Email: {{ selectedOutlet?.email || "-" }}</p>
              <p>Giá: {{ selectedOutlet?.priceRange || "Chưa có" }}</p>
            </div>

            <div>
              <h3 class="font-semibold mb-2">Thông số</h3>
              <p>Sức chứa: {{ selectedOutlet?.capacity || "-" }}</p>
              <p>Loại: {{ selectedOutlet?.outletTypeName || "-" }}</p>
              <p>Quận: {{ selectedOutlet?.districtName || "-" }}</p>
            </div>
          </div>

          <div class="mt-6">
            <h3 class="font-semibold mb-2">Mô tả</h3>
            <p class="text-subtext-light dark:text-subtext-dark">
              {{ selectedOutlet?.description || "Chưa có mô tả" }}
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Edit Modal -->
    <div
      v-if="showEdit"
      class="fixed inset-0 bg-black/40 flex items-center justify-center z-50"
    >
      <div
        class="bg-white dark:bg-surface-dark p-6 rounded-lg w-full max-w-2xl"
      >
        <h3 class="text-lg font-semibold mb-4">Chỉnh sửa thông tin quán</h3>
        <div class="grid grid-cols-1 gap-3">
          <input
            v-model="editForm.name"
            placeholder="Tên quán"
            class="p-2 border rounded"
          />
          <input
            v-model="editForm.address"
            placeholder="Địa chỉ"
            class="p-2 border rounded"
          />
          <div class="grid grid-cols-2 gap-2">
            <input
              v-model="editForm.phoneNumber"
              placeholder="SĐT"
              class="p-2 border rounded"
            />
            <input
              v-model="editForm.email"
              placeholder="Email"
              class="p-2 border rounded"
            />
          </div>
          <div class="grid grid-cols-2 gap-2">
            <input
              v-model="editForm.priceRange"
              placeholder="Price range"
              class="p-2 border rounded"
            />
            <input
              v-model.number="editForm.capacity"
              placeholder="Capacity"
              class="p-2 border rounded"
            />
          </div>
        </div>

        <div class="mt-4 flex justify-end gap-2">
          <button
            @click="saveEdit"
            class="px-4 py-2 bg-primary text-white rounded"
            :disabled="isSaving"
          >
            Lưu
          </button>
          <button
            @click="closeEdit"
            class="px-4 py-2 border rounded"
            :disabled="isSaving"
          >
            Hủy
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from "vue";
import {useRouter} from "vue-router";
import {outletApi} from "@/api/outlet";
import {bookingApi} from "@/api/booking";
import {useAuthStore} from "@/stores/auth";

const router = useRouter();
const authStore = useAuthStore();

const isLoading = ref(true);
const isSaving = ref(false);
const outlets = ref([]);
const selectedOutletId = ref(null);
const selectedOutlet = ref(null);
const showEdit = ref(false);
const editForm = ref({});

const stats = ref({bookings: 0, revenueDisplay: "0 VND"});

const fetchOwnerOutlets = async () => {
  isLoading.value = true;
  try {
    const data = await outletApi.getMyOutlets();
    // normalize response
    const items = data?.data || data || [];
    outlets.value = items;
    if (outlets.value.length > 0) {
      selectedOutletId.value = outlets.value[0].id;
      setSelectedOutlet(selectedOutletId.value);
    }
  } catch (err) {
    console.error("Error loading owner outlets", err);
    outlets.value = [];
  } finally {
    isLoading.value = false;
  }
};

const setSelectedOutlet = (id) => {
  selectedOutlet.value = outlets.value.find((o) => o.id === id) || null;
  loadStats();
};

const onSelectOutlet = () => setSelectedOutlet(selectedOutletId.value);

const loadStats = async () => {
  if (!selectedOutlet.value) {
    stats.value = {bookings: 0, revenueDisplay: "0 VND"};
    return;
  }

  try {
    // Fetch bookings count for this outlet in owner management view
    const params = {
      page: 0,
      size: 1,
      outletId: selectedOutlet.value.id,
      viewType: "MANAGE_BOOKINGS",
    };
    const res = await bookingApi.getMyBookings(params);
    const total =
      res?.totalElements ??
      res?.data?.totalElements ??
      (Array.isArray(res?.data) ? res.data.length : 0);
    stats.value.bookings = total || 0;

    // TODO: Compute revenue if backend provides it — placeholder for now
    stats.value.revenueDisplay = "0 VND";
  } catch (err) {
    console.error("Error loading stats", err);
    stats.value = {bookings: 0, revenueDisplay: "0 VND"};
  }
};

const openEdit = () => {
  if (!selectedOutlet.value) return;
  editForm.value = {
    name: selectedOutlet.value.name,
    address: selectedOutlet.value.address,
    phoneNumber: selectedOutlet.value.phoneNumber,
    email: selectedOutlet.value.email,
    priceRange: selectedOutlet.value.priceRange,
    capacity: selectedOutlet.value.capacity,
  };
  showEdit.value = true;
};

const closeEdit = () => {
  showEdit.value = false;
};

const saveEdit = async () => {
  if (!selectedOutlet.value) return;
  isSaving.value = true;
  try {
    const payload = {
      name: editForm.value.name,
      address: editForm.value.address,
      phoneNumber: editForm.value.phoneNumber,
      email: editForm.value.email,
      priceRange: editForm.value.priceRange,
      capacity: editForm.value.capacity,
    };
    await outletApi.updateOutlet(selectedOutlet.value.id, payload);
    // Refresh
    await fetchOwnerOutlets();
    showEdit.value = false;
  } catch (err) {
    console.error("Error saving outlet", err);
    alert(err?.message || "Lỗi khi lưu thông tin");
  } finally {
    isSaving.value = false;
  }
};

const toggleActive = async () => {
  if (!selectedOutlet.value) return;
  isSaving.value = true;
  try {
    const patch = {isActive: !selectedOutlet.value.isActive};
    await outletApi.updateOutlet(selectedOutlet.value.id, patch);
    await fetchOwnerOutlets();
  } catch (err) {
    console.error("Error toggling active", err);
  } finally {
    isSaving.value = false;
  }
};

const viewOnSite = () => {
  if (!selectedOutlet.value) return;
  router.push(`/outlet/${selectedOutlet.value.id}`);
};

const goToMenu = () => {
  router.push({
    path: "/owner/menu",
    query: {outletId: selectedOutlet.value?.id},
  });
};

const openCreateOutlet = () => {
  router.push("/owner/menu");
};

onMounted(() => {
  fetchOwnerOutlets();
});
</script>
