import {ref} from "vue";

export function useLoading() {
  const isLoading = ref(false);
  const error = ref(null);

  const withLoading = async (fn) => {
    isLoading.value = true;
    error.value = null;
    try {
      const result = await fn();
      return result;
    } catch (err) {
      error.value = err.message || "An error occurred";
      throw err;
    } finally {
      isLoading.value = false;
    }
  };

  return {
    isLoading,
    error,
    withLoading,
  };
}
