<template>
  <v-container>
    <v-row justify="end">
      <v-col cols="2" style="position: fixed">
        <v-btn class="text-none" stacked variant="text" @click="dialog = true">
          <v-badge :content="cartItems.length" color="error">
            <v-icon size="x-large" icon="mdi-cart-variant"></v-icon>
          </v-badge>
        </v-btn>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col cols="4">
        <h1>craftbeer</h1>
      </v-col>
    </v-row>
    <v-row justify="center" class="text-center">
      <v-col cols="5">
        <p>aus hopfen, malz und bern</p>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col cols="6">
        <v-divider color="#f0c40f" thickness="4px"></v-divider>
      </v-col>
    </v-row>

    <v-row justify="center" class="mt-10">
      <v-col cols="3" class="beer-type-selector">
        <v-chip
          :class="{ active: isActive('Alle') }"
          class="beer-chip"
          v-on:click="(event) => addToSelectedFilters(event.target.innerText)"
        >
          Alle
        </v-chip>
        <v-chip
          :class="{ active: isActive('Am Lager') }"
          class="beer-chip"
          v-on:click="(event) => addToSelectedFilters(event.target.innerText)"
        >
          Am Lager
        </v-chip>
        <v-chip
          :class="{ active: isActive('Dunkel') }"
          class="beer-chip"
          v-on:click="(event) => addToSelectedFilters(event.target.innerText)"
        >
          Dunkel
        </v-chip>
        <v-chip
          :class="{ active: isActive('Hell') }"
          class="beer-chip"
          v-on:click="(event) => addToSelectedFilters(event.target.innerText)"
        >
          Hell
        </v-chip>
      </v-col>
    </v-row>

    <v-row justify="center" class="px-16">
      <v-col cols="3" v-for="beer in getBeers" :key="beer.id">
        <v-card class="mx-auto my-12" max-width="374">
          <v-img cover height="250" :src="getImgUrl(beer.imageUrl)"></v-img>

          <v-card-item>
            <v-card-title>{{ beer.name }}</v-card-title>
          </v-card-item>

          <v-card-text>
            <div class="text-subtitle-1">{{ beer.price.toFixed(2) }} CHF</div>

            <div>süffig - spritzig - frisch wie zitronen</div>
            <div>noch {{ beer.stock }} übrig</div>
          </v-card-text>
          <v-card-actions>
            <v-btn color="#f0c40f" variant="flat" @click="addToCart(beer)">
              HINZUFÜGEN
            </v-btn>
          </v-card-actions>
          <v-card-actions>
            <v-expansion-panels>
              <v-expansion-panel elevation="0" title="Zutaten">
                <v-expansion-panel-text>
                  <div
                    v-for="ingredient in beer.ingredients"
                    :key="ingredient.id"
                  >
                    <li>
                      {{ ingredient.ingredient.name }}: {{ ingredient.amount }}
                    </li>
                  </div>
                </v-expansion-panel-text>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <v-dialog v-model="dialog" width="auto">
      <v-card width="600px">
        <v-card-text>
          <h2>Warenkorb</h2>
          <v-divider></v-divider>
          <v-row class="mt-4">
            <v-col
              cols="12"
              v-for="item in [...new Set(cartItems)]"
              :order="item.name"
              :key="item.id"
              class="mt-n3"
            >
              <div class="d-flex justify-between align-center">
                <div>{{ item.name }}</div>
                <div class="ml-auto d-flex">
                  <div class="justify-between align-center">
                    <v-btn
                      icon
                      @click="removeFromCart(item)"
                      variant="text"
                      size="small"
                      class="mr-n3"
                    >
                      <v-icon>mdi-minus</v-icon>
                    </v-btn>
                    <v-btn
                      icon
                      @click="addToCart(item)"
                      variant="text"
                      size="small"
                    >
                      <v-icon>mdi-plus</v-icon>
                    </v-btn>
                  </div>

                  <b class="mx-2 d-flex align-center justify-center">{{
                    itemCount(item)
                  }}</b>
                  <div
                    class="d-flex align-center justify-end"
                    style="width: 100px; text-align: right"
                  >
                    {{ (itemCount(item) * item.price).toFixed(2) }} CHF
                  </div>
                </div>
              </div>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="goToCheckout()"
            >Weiter zum checkout</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
import { useBeersStore } from "@/store/beer.js";
import { mapActions, mapState } from "pinia";

export default {
  data: () => ({
    beers: [],

    dialogm1: "",
    dialog: false,

    cartItems: [],
    selectedFilters: [],
  }),

  computed: {
    ...mapState(useBeersStore, ["getBeers"]),
  },

  async mounted() {
    await this.fetchBeers();
  },

  methods: {
    getImgUrl(url) {
      return new URL(`../assets/${url}`, import.meta.url).href;
    },
    addToSelectedFilters(beerSort) {
      if (beerSort === "Alle") {
        this.selectedFilters = [];
        return;
      }

      if (this.selectedFilters.includes(beerSort)) {
        this.selectedFilters = this.selectedFilters.filter(
          (filter) => filter !== beerSort
        );
      } else {
        this.selectedFilters.push(beerSort);
      }
    },

    addToCart(beer) {
      this.cartItems.push(beer);
      this.cartItems.sort((a, b) => (a.name > b.name ? 1 : -1));
    },

    removeFromCart(beer) {
      if (this.itemCount(beer) > 1) {
        this.cartItems.splice(
          this.cartItems.findIndex((item) => item.beerId === beer.beerId),
          1
        );
        return;
      } else {
        this.cartItems = this.cartItems.filter(
          (item) => item.beerId !== beer.beerId
        );
      }
      this.cartItems.sort((a, b) => (a.name > b.name ? 1 : -1));
    },

    isActive(beerSort) {
      return this.selectedFilters.includes(beerSort);
    },

    itemCount(beer) {
      return this.cartItems.filter((item) => item.beerId === beer.beerId)
        .length;
    },

    goToCheckout() {
      localStorage.clear();
      localStorage.setItem("cart", JSON.stringify(this.cartItems));

      this.$router.push("/checkout");
    },

    ...mapActions(useBeersStore, ["fetchBeers"]),
  },
};
</script>

<style scoped>
h1 {
  font-size: 5rem;
  font-weight: 500;
  text-align: center;
}

p {
  font-size: 1.5rem;
  font-weight: 300;
  margin-top: -40px;
}

.beer-type-selector {
  display: flex;
  justify-content: space-between;
}

.beer-chip:hover {
  background-color: #f0c40f;
  color: #1c1e20;
  cursor: pointer;
}

.active {
  background-color: #f0c40f;
  color: #1c1e20;
}
</style>
