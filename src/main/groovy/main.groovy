package com.springandgroovy

import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import com.codergists.somescripts.HelloWorldService
public class Main {

   public static void main(String[] args) throws Exception {

     ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
     HelloWorldService service = (HelloWorldService) context.getBean("helloWorldService");

     System.out.println(service.sayHello());

    }
} 
