//package com.example.TDDMiniProject;
//
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.util.logging.Logger;
//
//@Configuration
//public class LoadDatabases {
//
//    private static final Logger log = (Logger) LoggerFactory.getLogger(LoadDatabases.class);
//
//    @Bean
//    CommandLineRunner initDatabase(OrderRepository orderRepository){
//
//        return args ->{
//            log.info("Preloading" + orderRepository.save(new OrderEntity("Customer 1", LocalDate.now(), "Address 1", 10.0)));
//            log.info("Preloading" + orderRepository.save(new OrderEntity("Customer 2", LocalDate.now(), "Address 2", 20.0)));
//
//        };
//    }
//}
