package com.example.test.business.impl;

import com.example.test.business.HermesWhatsAppBusiness;
import com.example.test.dto.HermesDto;

import org.springframework.stereotype.Service;

@Service
public class HermesWhatsAppBusinessImpl implements HermesWhatsAppBusiness {

  @Override
  public void execute(HermesDto hermesDto) {

    if (hermesDto.getId().equals(1)) {
      try {
        Thread.sleep(3000);
        throw new RuntimeException("Error for test retry");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
