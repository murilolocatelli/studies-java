import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 10,
  duration: '60s'
}

export default function() {
  let params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  let payload = JSON.stringify({
    'nome': 'Murilo',
    'cpf': '123456789-01',
    'username': 'murilo',
    'password': '456'
  });

  let res = http.post('http://localhost:8080/usuarios', payload, params);

  check(res, {
    'status was 204': r => r.status == 204,
    'transaction time OK': r => r.timings.duration < 1000,
  });
  sleep(1);
}
