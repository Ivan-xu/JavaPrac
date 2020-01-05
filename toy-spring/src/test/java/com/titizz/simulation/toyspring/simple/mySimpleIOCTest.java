package com.titizz.simulation.toyspring.simple;

import com.titizz.simulation.toyspring.Car;
import com.titizz.simulation.toyspring.Wheel;
import org.junit.Test;

/**
 * Created by code4wt on 17/8/19.
 */
public class mySimpleIOCTest {

    @Test
    public void getBean() throws Exception {
        String location = mySimpleIOC.class.getClassLoader().getResource("toy-spring-ioc.xml").getFile();
        mySimpleIOC bf = new mySimpleIOC(location);
        Wheel wheel = (Wheel) bf.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) bf.getBean("car");
        System.out.println(car);
    }
}