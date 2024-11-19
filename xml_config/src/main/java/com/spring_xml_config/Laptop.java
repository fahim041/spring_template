package com.spring_xml_config;

public class Laptop implements Computer{
    @Override
    public void compile() {
        System.out.println("compiling in laptop...");
    }
}
