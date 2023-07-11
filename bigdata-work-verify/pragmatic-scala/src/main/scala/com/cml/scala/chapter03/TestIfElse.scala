package com.cml.scala.chapter03

import scala.io.StdIn

/**
 * @title: TestIfElse
 * @projectName pragmatic-scala
 * @description: 流程控制-分支控制if-else
 * @author wuque
 * @date 2022/11/2414:59
 */
object TestIfElse {

  def main(args: Array[String]): Unit = {

    println("input age:")
    var age=StdIn.readShort()
    if (age < 18) {
      println("童年")
    } else {
      println("成年")
    }

    //Scala中if else表达式其实是有返回值的，具体返回值取决于满足条件的代码体的最后一行内容
    val res :String = if (age < 18) {
      "童年"
    } else if (age >= 18 && age < 30) {
      "中年"
    } else {
      "老年"
    }

    println(res)

    //Scala中返回值类型不一致，取它们共同的祖先类型
    val res2 :Any = if (age < 18) {
      "童年"
    } else if (age >= 18 && age < 30) {
      "中年"
    } else {
      100
    }

    println(res2)

    //Java中的三元运算符可以用if else实现
    // Java
    // int result = flag?1:0

    // Scala
    val res3: Any = if (age < 18) "童年" else "成年"
    println(res3)

  }
}
