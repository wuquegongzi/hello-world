package com.cml.scala.chapter03

/**
 * @title: TestFor
 * @projectName pragmatic-scala
 * @description: TODO
 * @author wuque
 * @date 2022/11/2415:17
 */
object TestFor {

  def main(args: Array[String]): Unit = {

    //范围数据循环（To）
    // 1 2 3
    for( i <- 1 to 3 ){
      print(i + " ")
    }
    println("范围数据循环（To）----------------------")
    println()

    //范围数据循环（Until）
    //1 2
    for(i <- 1 until 3) {

      print(i + " ")

    }
    println("范围数据循环（Until）----------------------")
    println()

//    循环守卫，即循环保护式（也称条件判断式，守卫）。保护式为true则进入循环体内部，为false则跳过，类似于continue。
    for(i <- 1 to 3 if i != 2) {

      print(i + " ")
    }
    println("循环守卫----------------------")
    println()

    //循环步长
    println("by----------------------")
    for (i <- 1 to 10 by 2) {

      println("i=" + i)
    }
    println("by----------------------")
    println()


    //嵌套循环
//    没有关键字，所以范围后一定要加；来隔断逻辑
    for(i <- 1 to 3; j <- 1 to 3) {
      println(" i =" + i + " j = " + j)
    }
    println("嵌套----------------------")
    println()

    //引入变量
    for(i <- 1 to 3; j = 4 - i) {
      println("i=" + i + " j=" + j)
    }
    println("引入变量----------------------")
    println()

    //循环返回值
    var res = for(i <- 1 to 10) yield {
      i * 2
    }
    //输出结果：
    //Vector(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
    println(res)
    println("循环返回值----------------------")

    println()

    //倒序打印
    for (i <- 1 to 10 reverse) {
      println(i)
    }
    println("倒序----------------------")


  }

}
