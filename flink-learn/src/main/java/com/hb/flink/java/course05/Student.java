package com.hb.flink.java.course05;

/**
 * @ClassName Students
 * @Description TODO
 * @Author minglei.chen
 * @Date 2020/2/3 6:51 下午
 * @Version 1.0
 *
 * 建表语句：
 * CREATE TABLE `test`.`Untitled`  (
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 *   `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
 *   `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
 *   `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
 *   PRIMARY KEY (`id`) USING BTREE
 * ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
 */
public class Student {

    private int id;
    private String name;
    private int age;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
