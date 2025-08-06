package com.example.demo;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


import java.sql.Connection;
import java.sql.Statement;




@SpringBootApplication
public class BaedalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaedalApplication.class, args);
	}

}
