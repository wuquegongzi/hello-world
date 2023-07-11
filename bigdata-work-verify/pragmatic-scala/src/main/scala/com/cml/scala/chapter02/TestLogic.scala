package com.cml.scala.chapter02

/**
 * @title: TestLogic
 * @projectName pragmatic-scala
 * @description: 逻辑运算符
 * @author wuque
 * @date 2022/11/2414:43
 */
object TestLogic {

  def main(args: Array[String]): Unit = {

    //  测试：&&、||、！
    var a = true
    var b = false

    println("a&&b=" + (a && b)) //  a&&b=false
    println("a || b =" + (a || b)) //  a || b =true
    println("!(a&&b)" + (!(a && b))) // !(a&&b)true

  }

}
