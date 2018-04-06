package com.accelya.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@SpringBootApplication
@RibbonClient(name = "say-hello", configuration = SayHelloConfiguration.class)
public class UserApplication {

//
//    @Autowired
//    private DiscoveryClient client;

//    @Autowired
//    private LoadBalancerClient loadBalancerClient;

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/hi")
    public String hi(@RequestParam(value = "name", defaultValue = "Artaban") String name) {

//        ServiceInstance serviceInstance = loadBalancerClient.choose("say-hello");

//        List<ServiceInstance> instances = client.getInstances("say-hello");

//        instances.forEach(System.out::println);

//        ServiceInstance serviceInstance = instances.get(0);

//        log.debug(" Host :" + serviceInstance.getHost());
//        log.debug(" Port :" + serviceInstance.getPort());
        String greeting = this.restTemplate.getForObject("http://say-hello/greeting", String.class);

        return String.format("%s, %s!", greeting, name);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
