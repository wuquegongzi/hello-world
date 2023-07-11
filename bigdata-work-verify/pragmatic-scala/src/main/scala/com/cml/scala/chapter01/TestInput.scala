package com.cml.scala.chapter01

import scala.io.StdIn

/**
 * @title: TestInput
 * @projectName pragmatic-scala
 * @description: 键盘输入
 * @author wuque
 * @date 2022/11/2316:07
 */
object TestInput {

  def main(args: Array[String]): Unit = {

    //输入姓名
    println("input name: ")
    var name = StdIn.readLine()

    //输入年龄
    println("input age: ")
    var age = StdIn.readInt()

    //输入薪水
    println("input sal: ")
    var sal = StdIn.readDouble()

    //打印
    println("name: " + name)
    println("age: " + age)
    println("sal: " + sal)

  }

}
