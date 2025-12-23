<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý quận huyện</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm quận huyện
      </button>
    </div>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-4 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label class="block text-sm font-medium mb-2">Tỉnh thành</label>
          <select
            v-model="selectedProvinceId"
            @change="onProvinceChange"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option value="">Tất cả tỉnh thành</option>
            <option
              v-for="province in provinces"
              :key="province.id"
              :value="province.id"
            >
              {{ province.name }}
            </option>
          </select>
        </div>
      </div>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Districts Table -->
    <div
      v-if="!isLoading && !error"
      class="bg-white dark:bg-surface-dark rounded-xl border border-border-light dark:border-border-dark overflow-hidden"
    >
      <table class="w-full">
        <thead class="bg-gray-50 dark:bg-surface-light/5">
          <tr>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              ID
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Tên quận huyện
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Tỉnh thành
            </th>
            <th
              class="px-6 py-3 text-right text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Thao tác
            </th>
          </tr>
        </thead>
        <tbody class="divide-y divide-border-light dark:divide-border-dark">
          <tr
            v-for="district in districts"
            :key="district.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ district.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">{{ district.name }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm text-subtext-light dark:text-subtext-dark">
                {{ district.province?.name || "N/A" }}
              </div>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  @click="editDistrict(district)"
                  class="text-primary hover:text-primary/80"
                >
                  Sửa
                </button>
                <button
                  @click="deleteDistrict(district)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="districts.length === 0">
            <td colspan="4" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Create/Edit Modal -->
    <div
      v-if="showCreateModal || editingDistrict"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingDistrict ? "Sửa quận huyện" : "Thêm quận huyện mới" }}
        </h2>
        <form @submit.prevent="saveDistrict" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Tên quận huyện</label>
            <input
              v-model="form.name"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập tên quận huyện"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Tỉnh thành</label>
            <select
              v-model="form.provinceId"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
            >
              <option value="">Chọn tỉnh thành</option>
              <option
                v-for="province in provinces"
                :key="province.id"
                :value="province.id"
              >
                {{ province.name }}
              </option>
            </select>
          </div>
          <div class="flex gap-2 justify-end">
            <button
              type="button"
              @click="closeModal"
              class="px-4 py-2 bg-gray-100 dark:bg-gray-800 rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700"
            >
              Hủy
            </button>
            <button
              type="submit"
              class="px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
            >
              {{ editingDistrict ? "Cập nhật" : "Tạo mới" }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from "vue";
import {locationApi} from "@/api";
import LoadingSpinner from "@/components/common/LoadingSpinner.vue";
import ErrorMessage from "@/components/common/ErrorMessage.vue";

const isLoading = ref(false);
const error = ref(null);
const districts = ref([]);
const provinces = ref([]);
const selectedProvinceId = ref("");
const showCreateModal = ref(false);
const editingDistrict = ref(null);
const form = ref({
  name: "",
  provinceId: "",
});

const fetchProvinces = async () => {
  try {
    const response = await locationApi.getProvinces();
    provinces.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch provinces:", err);
  }
};

const fetchDistricts = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await locationApi.getDistricts(selectedProvinceId.value || null);
    districts.value = response.data || response || [];
  } catch (err) {
    console.error("Error fetching districts:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách quận huyện";
  } finally {
    isLoading.value = false;
  }
};

const onProvinceChange = () => {
  fetchDistricts();
};

const editDistrict = (district) => {
  editingDistrict.value = district;
  form.value = {
    name: district.name || "",
    provinceId: district.provinceId || district.province?.id || "",
  };
};

const deleteDistrict = async (district) => {
  if (!confirm(`Bạn có chắc muốn xóa quận huyện "${district.name}"?`)) {
    return;
  }

  try {
    await locationApi.deleteDistrict(district.id);
    await fetchDistricts();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveDistrict = async () => {
  try {
    if (editingDistrict.value) {
      await locationApi.updateDistrict(editingDistrict.value.id, form.value);
    } else {
      await locationApi.createDistrict(form.value);
    }
    closeModal();
    await fetchDistricts();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingDistrict.value = null;
  form.value = {
    name: "",
    provinceId: "",
  };
};

onMounted(() => {
  fetchProvinces();
  fetchDistricts();
});
</script>
