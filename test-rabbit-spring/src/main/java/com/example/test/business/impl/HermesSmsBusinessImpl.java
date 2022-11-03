package com.example.test.business.impl;

import com.example.test.business.HermesSmsBusiness;
import com.example.test.dto.HermesDto;

import org.springframework.stereotype.Service;

@Service
public class HermesSmsBusinessImpl implements HermesSmsBusiness {

  @Override
  public void execute(HermesDto hermesDtoDto) {

    if (hermesDtoDto.getId().equals(1)) {
      try {
        Thread.sleep(3000);
        throw new RuntimeException("Error for test retry");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
