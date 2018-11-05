package com.example.coding01;

import com.example.coding01.stock.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Coding01Application implements CommandLineRunner {

    @Autowired
    ImportService importService;

    public static void main(String[] args) {
        SpringApplication.run(Coding01Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        importService.importData();
    }
}