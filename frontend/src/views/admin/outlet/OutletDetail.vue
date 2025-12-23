<template>
  <div class="px-10 py-8">
    <router-link
      to="/admin/outlets"
      class="flex items-center gap-2 text-primary mb-6 hover:underline"
    >
      <span class="material-symbols-outlined">arrow_back</span>
      Quay lại
    </router-link>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <div v-if="!isLoading && !error && outlet" class="space-y-6">
      <!-- Header -->
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <div class="flex items-start justify-between mb-4">
          <div>
            <h1 class="text-3xl font-bold mb-2">{{ outlet.name }}</h1>
            <div class="flex items-center gap-4 text-sm text-subtext-light">
              <span class="flex items-center gap-1">
                <span class="material-symbols-outlined fill text-yellow-500"
                  >star</span
                >
                <span class="font-medium">{{ outlet.averageRating || 0 }}</span>
                <span>({{ outlet.totalReviews || 0 }} đánh giá)</span>
              </span>
              <span
                :class="
                  outlet.isActive !== false
                    ? 'text-positive'
                    : 'text-red-500'
                "
                class="flex items-center gap-1"
              >
                <span
                  class="w-2 h-2 rounded-full"
                  :class="
                    outlet.isActive !== false
                      ? 'bg-positive'
                      : 'bg-red-500'
                  "
                ></span>
                {{ outlet.isActive !== false ? "Hoạt động" : "Bị khóa" }}
              </span>
            </div>
          </div>
          <div class="flex gap-2">
            <button
              @click="showEditModal = true"
              class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-opacity-90"
            >
              Sửa
            </button>
            <button
              v-if="outlet.isActive === false"
              @click="approveOutlet"
              class="px-4 py-2 bg-positive text-white rounded-lg hover:bg-opacity-90"
            >
              Duyệt
            </button>
            <button
              v-else
              @click="lockOutlet"
              class="px-4 py-2 bg-yellow-600 text-white rounded-lg hover:bg-opacity-90"
            >
              Khóa
            </button>
            <button
              @click="handleDelete"
              class="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-opacity-90"
            >
              Xóa
            </button>
          </div>
        </div>
      </div>

      <!-- Images -->
      <div
        v-if="outletImages.length > 0"
        class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">Hình ảnh</h2>
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div
            v-for="image in outletImages"
            :key="image.id"
            class="relative aspect-square rounded-lg overflow-hidden"
          >
            <ImageDisplay
              :image-url="image.imageUrl"
              :alt="outlet.name"
              placeholder-icon="image"
              container-class="w-full h-full rounded-lg"
              image-class="w-full h-full"
            />
          </div>
        </div>
      </div>

      <!-- Main Info -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column -->
        <div class="lg:col-span-2 space-y-6">
          <!-- Description -->
          <div
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Mô tả</h2>
            <p class="text-subtext-light dark:text-subtext-dark">
              {{ outlet.description || "Chưa có mô tả" }}
            </p>
          </div>

          <!-- Contact Info -->
          <div
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Thông tin liên hệ</h2>
            <div class="grid grid-cols-2 gap-4">
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Địa chỉ
                </p>
                <p class="font-medium">{{ outlet.address }}</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Quận/Huyện
                </p>
                <p class="font-medium">{{ outlet.districtName || "N/A" }}</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Email
                </p>
                <p class="font-medium">{{ outlet.email || "N/A" }}</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Số điện thoại
                </p>
                <p class="font-medium">{{ outlet.phoneNumber || "N/A" }}</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Website
                </p>
                <p class="font-medium">
                  <a
                    v-if="outlet.website"
                    :href="outlet.website"
                    target="_blank"
                    class="text-primary hover:underline"
                  >
                    {{ outlet.website }}
                  </a>
                  <span v-else>N/A</span>
                </p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Loại địa điểm
                </p>
                <p class="font-medium">{{ outlet.outletTypeName || "N/A" }}</p>
              </div>
            </div>
          </div>

          <!-- Operating Hours -->
          <div
            v-if="operatingHours.length > 0"
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Giờ hoạt động</h2>
            <div class="space-y-2">
              <div
                v-for="hours in operatingHours"
                :key="hours.id"
                class="flex items-center justify-between p-3 border border-border-light dark:border-border-dark rounded-lg"
              >
                <span class="font-medium">{{ getDayName(hours.dayOfWeek) }}</span>
                <span v-if="hours.isClosed" class="text-red-500">Đóng cửa</span>
                <span v-else class="text-subtext-light">
                  {{ formatTime(hours.openTime) }} - {{ formatTime(hours.closeTime) }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <!-- Right Column -->
        <div class="space-y-6">
          <!-- Quick Stats -->
          <div
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Thống kê</h2>
            <div class="space-y-4">
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Sức chứa
                </p>
                <p class="text-2xl font-bold">{{ outlet.capacity || 0 }} người</p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Khoảng giá
                </p>
                <p class="text-2xl font-bold">
                  {{ outlet.priceRange || "N/A" }}
                </p>
              </div>
              <div>
                <p class="text-sm text-subtext-light dark:text-subtext-dark mb-1">
                  Tổng đánh giá
                </p>
                <p class="text-2xl font-bold">
                  {{ outlet.totalReviews || 0 }}
                </p>
              </div>
            </div>
          </div>

          <!-- Owner Info -->
          <div
            class="bg-white dark:bg-surface-dark rounded-xl p-6 border border-border-light dark:border-border-dark"
          >
            <h2 class="text-xl font-bold mb-4">Chủ sở hữu</h2>
            <router-link
              :to="`/admin/users/${outlet.ownerId}`"
              class="text-primary hover:underline"
            >
              Xem thông tin chủ sở hữu
            </router-link>
          </div>
        </div>
      </div>
    </div>

    <!-- Edit Modal -->
    <div
      v-if="showEditModal"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="showEditModal = false"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-4xl border border-border-light dark:border-border-dark max-h-[90vh] overflow-y-auto"
      >
        <h2 class="text-xl font-bold mb-4">Sửa địa điểm</h2>
        <form @submit.prevent="saveOutlet" class="space-y-4">
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Tên địa điểm *</label>
              <input
                v-model="editForm.name"
                type="text"
                required
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="Nhập tên địa điểm"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Loại địa điểm *</label>
              <select
                v-model="editForm.typeId"
                required
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              >
                <option value="">Chọn loại địa điểm</option>
                <option
                  v-for="type in outletTypes"
                  :key="type.id"
                  :value="type.id"
                >
                  {{ type.name }}
                </option>
              </select>
            </div>
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Mô tả</label>
            <textarea
              v-model="editForm.description"
              rows="3"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập mô tả"
            ></textarea>
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Địa chỉ *</label>
            <input
              v-model="editForm.address"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập địa chỉ"
            />
          </div>
          <div class="grid grid-cols-3 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Tỉnh/Thành phố</label>
              <select
                v-model="editForm.provinceId"
                @change="onProvinceChange"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              >
                <option value="">Chọn tỉnh/thành phố</option>
                <option
                  v-for="province in provinces"
                  :key="province.id"
                  :value="province.id"
                >
                  {{ province.name }}
                </option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Quận/Huyện *</label>
              <select
                v-model="editForm.districtId"
                required
                :disabled="!editForm.provinceId"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg disabled:opacity-50"
              >
                <option value="">Chọn quận/huyện</option>
                <option
                  v-for="district in districts"
                  :key="district.id"
                  :value="district.id"
                >
                  {{ district.name }}
                </option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Sức chứa</label>
              <input
                v-model.number="editForm.capacity"
                type="number"
                min="0"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="Số người"
              />
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Email</label>
              <input
                v-model="editForm.email"
                type="email"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="email@example.com"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Số điện thoại</label>
              <input
                v-model="editForm.phoneNumber"
                type="tel"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="0123456789"
              />
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Website</label>
              <input
                v-model="editForm.website"
                type="url"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="https://example.com"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Khoảng giá</label>
              <input
                v-model="editForm.priceRange"
                type="text"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="Ví dụ: 100k-500k"
              />
            </div>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium mb-2">Vĩ độ (Latitude)</label>
              <input
                v-model.number="editForm.latitude"
                type="number"
                step="any"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="10.762622"
              />
            </div>
            <div>
              <label class="block text-sm font-medium mb-2">Kinh độ (Longitude)</label>
              <input
                v-model.number="editForm.longitude"
                type="number"
                step="any"
                class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
                placeholder="106.660172"
              />
            </div>
          </div>
          <div class="flex gap-2 justify-end">
            <button
              type="button"
              @click="showEditModal = false"
              class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
            >
              Hủy
            </button>
            <button
              type="submit"
              class="px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
            >
              Cập nhật
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {useRoute, useRouter} from "vue-router";
import {adminApi, outletApi, locationApi} from "@/api";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";
import ImageDisplay from "@/components/common/ImageDisplay.vue";

const route = useRoute();
const router = useRouter();

const isLoading = ref(false);
const error = ref(null);
const outlet = ref(null);
const outletImages = ref([]);
const operatingHours = ref([]);
const showEditModal = ref(false);
const outletTypes = ref([]);
const provinces = ref([]);
const districts = ref([]);
const editForm = ref({
  name: "",
  description: "",
  address: "",
  email: "",
  phoneNumber: "",
  website: "",
  latitude: null,
  longitude: null,
  priceRange: "",
  capacity: null,
  typeId: "",
  provinceId: "",
  districtId: "",
});

const formatTime = (time) => {
  if (!time) return "N/A";
  try {
    if (typeof time === "string") {
      return time.substring(0, 5); // HH:mm format
    }
    return time;
  } catch {
    return time;
  }
};

const getDayName = (dayOfWeek) => {
  const days = [
    "Chủ nhật",
    "Thứ hai",
    "Thứ ba",
    "Thứ tư",
    "Thứ năm",
    "Thứ sáu",
    "Thứ bảy",
  ];
  return days[dayOfWeek] || `Thứ ${dayOfWeek + 1}`;
};

const fetchOutletTypes = async () => {
  try {
    const response = await outletApi.getTypes();
    outletTypes.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch outlet types:", err);
  }
};

const fetchProvinces = async () => {
  try {
    const response = await locationApi.getProvinces();
    provinces.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch provinces:", err);
  }
};

const fetchDistricts = async (provinceId) => {
  if (!provinceId) {
    districts.value = [];
    return;
  }
  try {
    const response = await locationApi.getDistricts(provinceId);
    districts.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch districts:", err);
    districts.value = [];
  }
};

const onProvinceChange = () => {
  editForm.value.districtId = "";
  fetchDistricts(editForm.value.provinceId);
};

const fetchOutletDetail = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const outletId = route.params.id;
    const response = await adminApi.getOutletDetail(outletId);
    outlet.value = response.data || response;

    // Initialize edit form
    editForm.value = {
      name: outlet.value.name || "",
      description: outlet.value.description || "",
      address: outlet.value.address || "",
      email: outlet.value.email || "",
      phoneNumber: outlet.value.phoneNumber || "",
      website: outlet.value.website || "",
      latitude: outlet.value.latitude || null,
      longitude: outlet.value.longitude || null,
      priceRange: outlet.value.priceRange || "",
      capacity: outlet.value.capacity || null,
      typeId: outlet.value.type?.id || outlet.value.typeId || "",
      provinceId: outlet.value.district?.provinceId || "",
      districtId: outlet.value.district?.id || outlet.value.districtId || "",
    };

    // Fetch districts if province is available
    if (editForm.value.provinceId) {
      await fetchDistricts(editForm.value.provinceId);
    }

    // Fetch images
    try {
      const imagesResponse = await outletApi.getOutletImages(outletId);
      outletImages.value = imagesResponse.data || imagesResponse || [];
    } catch (err) {
      console.warn("Could not fetch images:", err);
      outletImages.value = [];
    }

    // Fetch operating hours
    try {
      const hoursResponse = await outletApi.getOperatingHours(outletId);
      operatingHours.value = hoursResponse.data || hoursResponse || [];
    } catch (err) {
      console.warn("Could not fetch operating hours:", err);
      operatingHours.value = [];
    }
  } catch (err) {
    console.error("Error fetching outlet detail:", err);
    error.value = err.response?.data?.message || "Không thể tải thông tin địa điểm";
  } finally {
    isLoading.value = false;
  }
};

const approveOutlet = async () => {
  if (!confirm(`Bạn có chắc muốn duyệt địa điểm "${outlet.value.name}"?`)) {
    return;
  }

  try {
    await adminApi.approveOutlet(outlet.value.id);
    await fetchOutletDetail();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const lockOutlet = async () => {
  if (!confirm(`Bạn có chắc muốn khóa địa điểm "${outlet.value.name}"?`)) {
    return;
  }

  try {
    await adminApi.lockOutlet(outlet.value.id);
    await fetchOutletDetail();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const handleDelete = async () => {
  if (!confirm(`Bạn có chắc muốn xóa địa điểm "${outlet.value.name}"?`)) {
    return;
  }

  try {
    await outletApi.deleteOutlet(outlet.value.id);
    alert("Đã xóa địa điểm thành công");
    router.push("/admin/outlets");
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveOutlet = async () => {
  try {
    const data = {
      name: editForm.value.name,
      description: editForm.value.description || null,
      address: editForm.value.address,
      email: editForm.value.email || null,
      phoneNumber: editForm.value.phoneNumber || null,
      website: editForm.value.website || null,
      latitude: editForm.value.latitude || null,
      longitude: editForm.value.longitude || null,
      priceRange: editForm.value.priceRange || null,
      capacity: editForm.value.capacity || null,
      typeId: parseInt(editForm.value.typeId),
      districtId: parseInt(editForm.value.districtId),
    };

    await outletApi.updateOutlet(outlet.value.id, data);
    alert("Đã cập nhật địa điểm thành công");
    showEditModal.value = false;
    await fetchOutletDetail();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

onMounted(() => {
  fetchOutletTypes();
  fetchProvinces();
  fetchOutletDetail();
});
</script>
