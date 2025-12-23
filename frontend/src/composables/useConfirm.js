import {ref} from "vue";

// Shared state for confirm dialog
const confirmState = ref({
  show: false,
  message: "",
  title: "",
  onConfirm: null,
  onCancel: null,
});

export function useConfirm() {
  const confirm = (message, title = "Xác nhận") => {
    return new Promise((resolve) => {
      confirmState.value = {
        show: true,
        message,
        title,
        onConfirm: () => {
          confirmState.value.show = false;
          resolve(true);
        },
        onCancel: () => {
          confirmState.value.show = false;
          resolve(false);
        },
      };
    });
  };

  return {
    confirmState,
    confirm,
  };
}

// Export confirmState for use in ConfirmDialog component
export {confirmState};

