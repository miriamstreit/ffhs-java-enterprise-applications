<template>
  <v-contaienr>
    <v-form>
      <v-container>
        <v-row>
          <v-col cols="12" md="6">
            <v-text-field v-model="customer.name" label="Vorname" required></v-text-field>
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field v-model="customer.surname" label="Nachname" required></v-text-field>
          </v-col>

          <v-col cols="12" md="6">
            <v-text-field v-model="customer.street" label="Strasse" required></v-text-field>
          </v-col>

          <v-col cols="12" md="3">
            <v-select label="Ort" v-model="customer.state" :items="['Bern', 'Thun']"></v-select>
          </v-col>

          <v-col cols="12" md="3">
            <v-select label="Land" v-model="customer.country" :items="['Schweiz', 'Deutschland']"></v-select>
          </v-col>
        </v-row>
      </v-container>
    </v-form>

    <v-row class="ml-1">
      <v-col cols="6">
        <v-card class="pa-10">
          <h2 class="mb-7">Ihr Warenkorb</h2>
          <v-row>
            <v-col cols="12" v-for="item in getUniqueItemsById(cartItems)" :order="item.name" :key="item.id"
              class="mt-n3">
              <div class="d-flex justify-between align-center">
                <div>{{ item.name }}</div>
                <div class="ml-auto d-flex">
                  <b class="mx-2 d-flex align-center justify-center">{{ itemCount(item) }}</b>
                  <div class="d-flex align-center justify-end" style="width: 100px; text-align: right;">
                    {{ (itemCount(item) * item.price).toFixed(2) }} CHF
                  </div>
                </div>
              </div>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
      <v-col cols="6" class="d-flex align-end justify-end">
        <v-btn color="primary" @click="sendOrder()">Bestellen</v-btn>
      </v-col>
    </v-row>

  </v-contaienr>
</template>

<script>
import { useOrdersStore } from '@/store/order.js';
import { mapActions, mapState } from 'pinia';

export default {
  data: () => ({
    customer: {
      name: '',
      surname: '',
      street: '',
      state: '',
      country: '',
    },

    cartItems: [],
  }),

  methods: {
    itemCount(beer) {
      return this.cartItems.filter((item) => item.beerId === beer.beerId).length;
    },

    getUniqueItemsById(arr) {
      const ids = new Set();

      return arr.filter(item => {
        if (!ids.has(item.beerId)) {
          ids.add(item.beerId);
          return true;
        }
        return false;
      });
    },

    prepareBeerOrder() {
      let orderDate = new Date().toISOString().slice(0, 10);

      const updatedObject = {
        beerOrder: [],
        orderDate: orderDate
      };

      for (const beer of this.getUniqueItemsById(this.cartItems)) {
        const updatedBeer = {
          beerId: beer.beerId,
          amount: this.itemCount(beer),
        };

        updatedObject.beerOrder.push(updatedBeer);
      }

      return updatedObject;
    },

    sendOrder() {
      let order = this.prepareBeerOrder();
      this.saveOrder(order, this.customer);
    },

    ...mapActions(useOrdersStore, ['saveOrder'])
  },

  mounted() {
    this.cartItems = JSON.parse(localStorage.getItem('cart')) || []
  }
}
</script>