package com.spring_xml_config;

public class Desktop implements Computer{
    @Override
    public void compile() {
        System.out.println("compiling in desktop...");
    }
}
