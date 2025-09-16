// Utilities
import { defineStore } from "pinia";
import { showSnackbar } from "@/helpers/snackbarHelper";

import { customerApiClient, orderApiClient } from "@/api/apiClient";

export const useOrdersStore = defineStore("ordersStore", {
  state: () => ({
    orders: [],
  }),
  getters: {
    getOrders: (state) => state.orders,
  },
  actions: {
    async fetchOrders() {
      try {
        const response = await orderApiClient.get("/");

        this.orders = response.data;
      } catch (error) {
        showSnackbar({ message: "Etwas ist schief gelaufen", color: "error" });
      }
    },

    async saveOrder(order, customer) {
      try {
        const customerId = await customerApiClient.post(
          "/findOrCreateUser",
          customer
        );
        order.customerId = customerId.data;

        const response = await orderApiClient.post("/", order);
      } catch (error) {
        console.log(error);
        showSnackbar({ message: "Etwas ist schief gelaufen", color: "error" });
      }
    },
  },
});
