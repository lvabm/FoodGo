<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý tỉnh thành</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm tỉnh thành
      </button>
    </div>

    <!-- Filters -->
    <div
      class="bg-white dark:bg-surface-dark rounded-xl p-4 border border-border-light dark:border-border-dark mb-6"
    >
      <div class="flex gap-4">
        <div class="flex-1">
          <label class="block text-sm font-medium mb-2">Quốc gia</label>
          <select
            v-model="selectedCountryId"
            @change="fetchProvinces"
            class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
          >
            <option value="">Tất cả quốc gia</option>
            <option
              v-for="country in countries"
              :key="country.id"
              :value="country.id"
            >
              {{ country.name }}
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

    <!-- Provinces Table -->
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
              Tên tỉnh thành
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Quốc gia
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
            v-for="province in provinces"
            :key="province.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ province.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">{{ province.name }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm text-subtext-light dark:text-subtext-dark">
                {{ province.country?.name || "N/A" }}
              </div>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  @click="editProvince(province)"
                  class="text-primary hover:text-primary/80"
                >
                  Sửa
                </button>
                <button
                  @click="deleteProvince(province)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="provinces.length === 0">
            <td colspan="4" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Create/Edit Modal -->
    <div
      v-if="showCreateModal || editingProvince"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingProvince ? "Sửa tỉnh thành" : "Thêm tỉnh thành mới" }}
        </h2>
        <form @submit.prevent="saveProvince" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Tên tỉnh thành</label>
            <input
              v-model="form.name"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập tên tỉnh thành"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Quốc gia</label>
            <select
              v-model="form.countryId"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
            >
              <option value="">Chọn quốc gia</option>
              <option
                v-for="country in countries"
                :key="country.id"
                :value="country.id"
              >
                {{ country.name }}
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
              {{ editingProvince ? "Cập nhật" : "Tạo mới" }}
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
const provinces = ref([]);
const countries = ref([]);
const selectedCountryId = ref("");
const showCreateModal = ref(false);
const editingProvince = ref(null);
const form = ref({
  name: "",
  countryId: "",
});

const fetchCountries = async () => {
  try {
    const response = await locationApi.getCountries();
    countries.value = response.data || response || [];
  } catch (err) {
    console.warn("Could not fetch countries:", err);
  }
};

const fetchProvinces = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const params = selectedCountryId.value ? {countryId: selectedCountryId.value} : {};
    const response = await locationApi.getProvinces(selectedCountryId.value || null);
    provinces.value = response.data || response || [];
  } catch (err) {
    console.error("Error fetching provinces:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách tỉnh thành";
  } finally {
    isLoading.value = false;
  }
};

const editProvince = (province) => {
  editingProvince.value = province;
  form.value = {
    name: province.name || "",
    countryId: province.countryId || province.country?.id || "",
  };
};

const deleteProvince = async (province) => {
  if (!confirm(`Bạn có chắc muốn xóa tỉnh thành "${province.name}"?`)) {
    return;
  }

  try {
    await locationApi.deleteProvince(province.id);
    await fetchProvinces();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveProvince = async () => {
  try {
    if (editingProvince.value) {
      await locationApi.updateProvince(editingProvince.value.id, form.value);
    } else {
      await locationApi.createProvince(form.value);
    }
    closeModal();
    await fetchProvinces();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingProvince.value = null;
  form.value = {
    name: "",
    countryId: "",
  };
};

onMounted(() => {
  fetchCountries();
  fetchProvinces();
});
</script>
