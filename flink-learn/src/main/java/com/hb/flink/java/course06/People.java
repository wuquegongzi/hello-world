package com.hb.flink.java.course06;

/**
 * @ClassName People
 * @Description TODO
 * @Author minglei.chen
 * @Date 2020/2/4 10:44 上午
 * @Version 1.0
 */
public class People {

    private String name;
    private int age;
    private int year;

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", year=" + year +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
