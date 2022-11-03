import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 10,
  duration: '30s'
}

export default function() {
  let res = http.get('http://localhost:8080/students-mvc/1');
  check(res, {
    'status was 200': r => r.status == 200,
    'transaction time OK': r => r.timings.duration < 1000,
  });
  sleep(1);
}
