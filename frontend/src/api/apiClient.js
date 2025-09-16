import axios from "axios";

axios.defaults.headers.post["Content-Type"] = "application/json";
axios.defaults.headers.post["Accept"] = "*";

const productApiClient = axios.create({
  baseURL: "/api/product",
});

const customerApiClient = axios.create({
  baseURL: "/api/customer",
});

const orderApiClient = axios.create({
  baseURL: "/api/order",
});
export { productApiClient, customerApiClient, orderApiClient };
