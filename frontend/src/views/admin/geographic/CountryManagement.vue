<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <h1 class="text-2xl font-bold">Quản lý quốc gia</h1>
      <button
        @click="showCreateModal = true"
        class="flex items-center gap-2 px-4 py-2 bg-primary text-white rounded-lg hover:bg-opacity-90"
      >
        <span class="material-symbols-outlined">add</span>
        Thêm quốc gia
      </button>
    </div>

    <!-- Loading State -->
    <div v-if="isLoading" class="flex justify-center items-center h-64">
      <LoadingSpinner size="lg" />
    </div>

    <!-- Error State -->
    <ErrorMessage v-if="error" :message="error" />

    <!-- Countries Table -->
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
              Tên quốc gia
            </th>
            <th
              class="px-6 py-3 text-left text-xs font-medium text-subtext-light dark:text-subtext-dark uppercase tracking-wider"
            >
              Mã quốc gia
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
            v-for="country in countries"
            :key="country.id"
            class="hover:bg-gray-50 dark:hover:bg-surface-light/5"
          >
            <td class="px-6 py-4 whitespace-nowrap text-sm">{{ country.id }}</td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm font-medium">{{ country.name }}</div>
            </td>
            <td class="px-6 py-4 whitespace-nowrap">
              <div class="text-sm text-subtext-light dark:text-subtext-dark">
                {{ country.code || "N/A" }}
              </div>
            </td>
            <td
              class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
            >
              <div class="flex items-center justify-end gap-2">
                <button
                  @click="editCountry(country)"
                  class="text-primary hover:text-primary/80"
                >
                  Sửa
                </button>
                <button
                  @click="deleteCountry(country)"
                  class="text-red-600 hover:text-red-500"
                >
                  Xóa
                </button>
              </div>
            </td>
          </tr>
          <tr v-if="countries.length === 0">
            <td colspan="4" class="px-6 py-8 text-center text-subtext-light">
              Không có dữ liệu
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Create/Edit Modal -->
    <div
      v-if="showCreateModal || editingCountry"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light dark:border-border-dark"
      >
        <h2 class="text-xl font-bold mb-4">
          {{ editingCountry ? "Sửa quốc gia" : "Thêm quốc gia mới" }}
        </h2>
        <form @submit.prevent="saveCountry" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Tên quốc gia</label>
            <input
              v-model="form.name"
              type="text"
              required
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="Nhập tên quốc gia"
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Mã quốc gia</label>
            <input
              v-model="form.code"
              type="text"
              class="w-full px-4 py-2 border border-border-light dark:border-border-dark rounded-lg"
              placeholder="VN, US, etc."
            />
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
              {{ editingCountry ? "Cập nhật" : "Tạo mới" }}
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
const countries = ref([]);
const showCreateModal = ref(false);
const editingCountry = ref(null);
const form = ref({
  name: "",
  code: "",
});

const fetchCountries = async () => {
  isLoading.value = true;
  error.value = null;
  try {
    const response = await locationApi.getCountries();
    countries.value = response.data || response || [];
  } catch (err) {
    console.error("Error fetching countries:", err);
    error.value = err.response?.data?.message || "Không thể tải danh sách quốc gia";
  } finally {
    isLoading.value = false;
  }
};

const editCountry = (country) => {
  editingCountry.value = country;
  form.value = {
    name: country.name || "",
    code: country.code || "",
  };
};

const deleteCountry = async (country) => {
  if (!confirm(`Bạn có chắc muốn xóa quốc gia "${country.name}"?`)) {
    return;
  }

  try {
    await locationApi.deleteCountry(country.id);
    await fetchCountries();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const saveCountry = async () => {
  try {
    if (editingCountry.value) {
      await locationApi.updateCountry(editingCountry.value.id, form.value);
    } else {
      await locationApi.createCountry(form.value);
    }
    closeModal();
    await fetchCountries();
  } catch (err) {
    alert(err.response?.data?.message || "Có lỗi xảy ra");
  }
};

const closeModal = () => {
  showCreateModal.value = false;
  editingCountry.value = null;
  form.value = {
    name: "",
    code: "",
  };
};

onMounted(() => {
  fetchCountries();
});
</script>
