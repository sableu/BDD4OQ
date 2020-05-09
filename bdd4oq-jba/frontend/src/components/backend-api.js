import axios from 'axios'

const AXIOS = axios.create({
  baseURL: `/api`,
  timeout: 1000
});

export default {
    hello_service(name) {
        return AXIOS.get(`/hello/` + name);
    }
}
