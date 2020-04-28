package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaListener {
    public static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(DemoApplication.class, args);
    }

    @Autowired
    ProductRepository productRepository;

    @StreamListener(Processor.INPUT)
    public void onEventByString(@Payload OrderPlaced orderPlaced){
        if( orderPlaced.getEventType().equals("OrderPlaced")){
            System.out.println("======================");
            System.out.println("재고량 수정 - 기존데이터가 없으니 현재는 그냥 저장로직만 수행");
            Product p = new Product();
            p.setName(orderPlaced.getProductName());
            p.setStock(orderPlaced.getQty());
            productRepository.save(p);
            System.out.println("======================");
        }

    }
}