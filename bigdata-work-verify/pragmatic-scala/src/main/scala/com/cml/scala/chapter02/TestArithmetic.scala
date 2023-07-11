package com.cml.scala.chapter02

/**
 * @title: TestArithmetic
 * @projectName pragmatic-scala
 * @description: TODO
 * @author wuque
 * @date 2022/11/2414:37
 */
object TestArithmetic {

  def main(args: Array[String]): Unit = {
    //    1）对于除号“/”，它的整数除和小数除是有区别的：整数之间做除法时，只保留整数部分而舍弃小数部分。
    var r1: Int = 10 / 3
    println(r1) //3

    var r2: Double = 10 / 3
    println(r2) //3.0

    var r3: Double = 10.0 / 3
    println(r3) //3.3333333333333335
    println(r3.formatted("%.2f")) //3.33  含义：保留小数点2位，使用四舍五入

    //（2）对一个数取模a%b，和Java的取模规则一样。
    var r4 = 10 % 3
    println(r4) //1
  }

}
