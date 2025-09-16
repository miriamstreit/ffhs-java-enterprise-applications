// Utilities
import { defineStore } from "pinia";
import { showSnackbar } from "@/helpers/snackbarHelper";

import { productApiClient } from "@/api/apiClient";

export const useBeersStore = defineStore("beersStore", {
  state: () => ({
    beers: [],
  }),
  getters: {
    getBeers: (state) => state.beers,
  },
  actions: {
    async fetchBeers() {
      try {
        const response = await productApiClient.get("/");

        this.beers = response.data;
      } catch (error) {
        showSnackbar({ message: "Etwas ist schief gelaufen", color: "error" });
      }
    },
  },
});
