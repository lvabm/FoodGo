<template>
  <div class="px-10 py-8">
    <div class="flex items-center justify-between mb-6">
      <div>
        <h1 class="text-2xl font-bold text-text-light dark:text-text-dark">
          Quản lý Menu
        </h1>
        <p class="text-subtext-light dark:text-subtext-dark">
          Quản lý món ăn của quán
        </p>
      </div>
      <div class="flex items-center gap-3">
        <select
          v-model="selectedOutletId"
          @change="loadMenuItems"
          class="px-3 py-2 border rounded-lg pr-8 truncate appearance-none"
        >
          <option disabled value="">Chọn quán</option>
          <option v-for="o in outlets" :key="o.id" :value="o.id">
            {{ o.name }}
          </option>
        </select>
        <button
          @click="openCreate"
          :disabled="!selectedOutletId"
          :class="[
            'px-4 py-2 rounded-lg',
            selectedOutletId
              ? 'bg-primary text-white'
              : 'bg-gray-100 text-subtext-light cursor-not-allowed',
          ]"
        >
          Tạo món
        </button>
      </div>
    </div>

    <div v-if="loading" class="py-10 flex justify-center">
      <div
        class="animate-spin rounded-full h-10 w-10 border-b-2 border-primary"
      ></div>
    </div>

    <div
      v-if="error || successMessage"
      class="mb-4 p-3 rounded"
      :class="error ? 'bg-red-50 text-red-700' : 'bg-green-50 text-green-700'"
    >
      {{ error || successMessage }}
    </div>

    <div v-else>
      <div class="mb-4 flex items-center gap-2">
        <input
          v-model="query"
          @keyup.enter="loadMenuItems"
          placeholder="Tìm theo tên"
          class="px-3 py-2 border rounded-lg w-64"
        />
        <button
          @click="loadMenuItems"
          class="px-3 py-2 border rounded-lg hover:bg-primary/5"
        >
          Tìm
        </button>
      </div>

      <div
        v-if="menuItems.length === 0"
        class="text-center py-20 text-subtext-light"
      >
        <p>Không có món ăn</p>
      </div>

      <div v-else class="space-y-3">
        <div
          v-for="item in menuItems"
          :key="item.id"
          class="bg-white dark:bg-surface-dark rounded-xl p-4 border border-border-light flex items-center justify-between"
        >
          <div class="flex items-center gap-4">
            <div
              class="w-14 h-14 bg-gray-100 rounded flex items-center justify-center overflow-hidden"
            >
              <img
                v-if="item.imageUrl"
                :src="item.imageUrl"
                class="object-cover w-full h-full"
              />
              <span v-else class="material-symbols-outlined text-primary"
                >restaurant</span
              >
            </div>
            <div>
              <div class="font-medium text-text-light">{{ item.name }}</div>
              <div class="text-sm text-subtext-light">
                <span v-if="item.menuItemName"
                  >Món gốc: {{ item.menuItemName }} •
                </span>
                {{ item.categoryName || "Chung" }} •
                {{ formatCurrency(item.price) }}
              </div>
            </div>
          </div>
          <div class="flex items-center gap-2">
            <button
              @click="openEdit(item)"
              class="px-3 py-1 border rounded text-text-light hover:bg-primary/5"
            >
              Sửa
            </button>
            <button
              @click="confirmDelete(item)"
              class="px-3 py-1 border border-red-500 text-red-500 rounded hover:bg-red-50"
            >
              Xóa
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <div
      v-if="showModal"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="closeModal"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-2xl border border-border-light"
      >
        <h3 class="text-lg font-bold mb-3">
          {{ editMode ? "Sửa món" : "Tạo món" }}
        </h3>
        <div class="grid grid-cols-2 gap-3">
          <div>
            <label class="block text-sm mb-1">Món gốc</label>
            <div class="flex gap-2">
              <input
                v-model="masterQuery"
                @keyup.enter="loadMasterItems(masterQuery)"
                placeholder="Tìm món gốc..."
                class="px-3 py-2 border rounded-l-lg w-full"
              />
              <button
                @click="loadMasterItems(masterQuery)"
                class="px-3 py-2 bg-primary text-white rounded-r-lg"
              >
                Tìm
              </button>
            </div>
            <select
              v-model="form.menuItemId"
              class="w-full px-3 py-2 border rounded-lg mt-2"
            >
              <option value="">Chọn món gốc</option>
              <option v-for="m in masterItems" :key="m.id" :value="m.id">
                {{ m.name }} • {{ m.categoryName || "" }}
              </option>
            </select>
          </div>
          <div>
            <label class="block text-sm mb-1">Tên</label>
            <input
              v-model="form.name"
              class="w-full px-3 py-2 border rounded-lg"
            />
          </div>
          <div>
            <label class="block text-sm mb-1">Giá</label>
            <input
              v-model.number="form.price"
              type="number"
              class="w-full px-3 py-2 border rounded-lg"
            />
          </div>
          <div>
            <label class="block text-sm mb-1">Danh mục</label>
            <select
              v-model="form.categoryId"
              class="w-full px-3 py-2 border rounded-lg"
            >
              <option value="">Chọn danh mục</option>
              <option v-for="c in categories" :key="c.id" :value="c.id">
                {{ c.name }}
              </option>
            </select>
          </div>
          <div>
            <label class="block text-sm mb-1">Loại</label>
            <select
              v-model="form.typeId"
              class="w-full px-3 py-2 border rounded-lg"
            >
              <option value="">Chọn loại</option>
              <option v-for="t in types" :key="t.id" :value="t.id">
                {{ t.name }}
              </option>
            </select>
          </div>
          <div class="col-span-2">
            <label class="block text-sm mb-1">Mô tả</label>
            <textarea
              v-model="form.description"
              class="w-full px-3 py-2 border rounded-lg"
              rows="3"
            ></textarea>
          </div>
        </div>

        <div class="flex justify-end gap-3 mt-4">
          <button @click="closeModal" class="px-4 py-2 border rounded-lg">
            Hủy
          </button>
          <button
            @click="submitForm"
            :disabled="saving"
            class="px-4 py-2 bg-primary text-white rounded-lg"
          >
            <span v-if="saving">Đang lưu...</span>
            <span v-else>Lưu</span>
          </button>
        </div>
      </div>
    </div>

    <!-- Delete confirm -->
    <div
      v-if="showDelete"
      class="fixed inset-0 bg-black/50 flex items-center justify-center z-50"
      @click.self="showDelete = false"
    >
      <div
        class="bg-white dark:bg-surface-dark rounded-xl p-6 w-full max-w-md border border-border-light"
      >
        <h3 class="text-lg font-bold mb-3">Xác nhận xóa</h3>
        <p class="text-subtext-light mb-4">
          Bạn có chắc muốn xóa món <strong>{{ deletingItem?.name }}</strong
          >?
        </p>
        <div class="flex justify-end gap-3">
          <button
            @click="showDelete = false"
            class="px-4 py-2 border rounded-lg"
          >
            Hủy
          </button>
          <button
            @click="deleteItem()"
            :disabled="deleting"
            class="px-4 py-2 bg-red-500 text-white rounded-lg"
          >
            Xóa
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted} from "vue";
import {menuApi, outletApi} from "@/api";
import {useRouter} from "vue-router";

const router = useRouter();

// State
const outlets = ref([]);
const selectedOutletId = ref("");
const menuItems = ref([]);
const categories = ref([]);
const types = ref([]);
const loading = ref(false);
const error = ref("");
const successMessage = ref("");
const query = ref("");
const showModal = ref(false);
const editMode = ref(false);
const form = ref({
  name: "",
  price: 0,
  categoryId: "",
  typeId: "",
  description: "",
  menuItemId: null, // master menu item id (UUID)
});
const masterQuery = ref("");
const masterItems = ref([]);
const saving = ref(false);
const showDelete = ref(false);
const deleting = ref(false);
const deletingItem = ref(null);

// Helpers
const formatCurrency = (amount) => {
  if (amount === null || amount === undefined) return "₫0";
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount);
};

// Load outlets and categories/types
const loadOutlets = async () => {
  try {
    // Only load outlets owned by current owner
    const resp = await outletApi.getMyOutlets();
    // Response should be a list
    outlets.value = resp.data || resp || [];
    if (outlets.value.length > 0) selectedOutletId.value = outlets.value[0].id;
  } catch (err) {
    console.error("Failed to load owner's outlets:", err);
    outlets.value = [];
  }
};

const loadMeta = async () => {
  try {
    const [cResp, tResp] = await Promise.all([
      menuApi.getMenuCategories(),
      menuApi.getMenuTypes(),
    ]);
    categories.value = cResp.data || cResp || [];
    types.value = tResp.data || tResp || [];
  } catch (err) {
    console.error(err);
  }
};

const extractItemsFromResponse = (resp) => {
  // Normalize different response shapes (page wrapper or raw array)
  if (!resp) return [];
  if (Array.isArray(resp)) return resp;
  if (Array.isArray(resp.data)) return resp.data;
  if (Array.isArray(resp.data?.data)) return resp.data.data;
  return [];
};

const loadMasterItems = async (q = "") => {
  try {
    const params = {q, page: 0, size: 100};
    const resp = await menuApi.searchMasterMenuItems(params);
    masterItems.value = extractItemsFromResponse(resp);
  } catch (err) {
    console.error("Failed to load master menu items:", err);
    masterItems.value = [];
  }
};

const loadMenuItems = async () => {
  if (!selectedOutletId.value) return;
  loading.value = true;
  error.value = "";
  try {
    const params = {q: query.value, page: 0, size: 200};
    const resp = await menuApi.getOutletMenuItems(
      selectedOutletId.value,
      params
    );
    menuItems.value = extractItemsFromResponse(resp);
  } catch (err) {
    console.error(err);
    error.value = "Không thể tải menu";
  } finally {
    loading.value = false;
  }
};

const openCreate = async () => {
  editMode.value = false;
  form.value = {
    name: "",
    price: 0,
    categoryId: "",
    typeId: "",
    description: "",
    menuItemId: null,
  };
  await loadMasterItems();
  showModal.value = true;
};

const openEdit = async (item) => {
  editMode.value = true;
  // map backend response to form shape
  form.value = {
    id: item.id,
    name: item.name,
    price: item.price,
    categoryId: item.categoryId || "",
    typeId: item.typeId || "",
    description: item.description || "",
    menuItemId: item.menuItemId || null,
  };
  await loadMasterItems();
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const submitForm = async () => {
  if (!selectedOutletId.value) {
    error.value = "Chọn quán trước khi tạo/mở sửa";
    return;
  }
  // Ensure master menu item is selected
  if (!form.value.menuItemId) {
    error.value = "Vui lòng chọn một món gốc từ danh sách";
    return;
  }
  successMessage.value = "";
  error.value = "";
  saving.value = true;
  try {
    if (editMode.value) {
      // Use outlet-specific update for owner
      await menuApi.updateOutletMenuItem(
        selectedOutletId.value,
        form.value.id,
        form.value
      );
      successMessage.value = "Cập nhật món thành công";
    } else {
      // Create via outlet-specific endpoint
      await menuApi.createOutletMenuItem(selectedOutletId.value, {
        ...form.value,
      });
      successMessage.value = "Tạo món thành công";
    }
    await loadMenuItems();
    showModal.value = false;
  } catch (err) {
    console.error(err);
    error.value = "Lưu thất bại";
  } finally {
    saving.value = false;
  }
};

const confirmDelete = (item) => {
  deletingItem.value = item;
  showDelete.value = true;
};

const deleteItem = async () => {
  if (!deletingItem.value) return;
  deleting.value = true;
  try {
    await menuApi.deleteOutletMenuItem(
      selectedOutletId.value,
      deletingItem.value.id
    );
    showDelete.value = false;
    await loadMenuItems();
    successMessage.value = "Xóa món thành công";
  } catch (err) {
    console.error(err);
    error.value = "Xóa thất bại";
  } finally {
    deleting.value = false;
  }
};

onMounted(async () => {
  await Promise.all([loadOutlets(), loadMeta()]);
  await loadMenuItems();
});
</script>
