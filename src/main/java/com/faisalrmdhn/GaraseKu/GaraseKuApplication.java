package com.faisalrmdhn.GaraseKu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("com.faisalrmdhn.GaraseKu")
public class GaraseKuApplication {

  public static void main(String[] args) {
    SpringApplication.run(GaraseKuApplication.class, args);
  }

}
