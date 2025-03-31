package com.example.MultipleDB;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan("com.example.MultipleDB.Files")
public class MultipleDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDbApplication.class, args);
        System.out.println("Change");

	}
	//   @Bean
    // CommandLineRunner verifyDb2Creation(@Qualifier("db2DataSource") DataSource db2DataSource) {
    //     return args -> {
    //         try (Connection conn = db2DataSource.getConnection()) {
    //             System.out.println("DB2 CONNECTION SUCCESSFUL!");
    //         }
    //     };
    // }

}
