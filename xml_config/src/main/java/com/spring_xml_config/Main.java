package com.spring_xml_config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");

        Developer obj = context.getBean("dev", Developer.class);
        obj.code();
    }
}