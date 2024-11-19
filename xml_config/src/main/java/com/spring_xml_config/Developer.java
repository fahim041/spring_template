package com.spring_xml_config;

public class Developer {
    private Computer computer;

    public Developer(Computer computer){
        this.computer = computer;
    }

    public void code(){
        System.out.println("coding...");
        computer.compile();
        System.out.println("done!");
    }
}
