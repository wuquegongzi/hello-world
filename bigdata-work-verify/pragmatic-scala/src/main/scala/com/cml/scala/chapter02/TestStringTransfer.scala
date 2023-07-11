package com.cml.scala.chapter02

/**
 * @title: TestStringTransfer
 * @projectName pragmatic-scala
 * @description: TODO
 * @author wuque
 * @date 2022/11/2414:35
 */
object TestStringTransfer {

  def main(args: Array[String]): Unit = {

    //（1）基本类型转String类型（语法：将基本类型的值+"" 即可）
    var str1: String = true + "你kin"
    println(str1)
    var str2: String = 4.444 + "拜拜"
    println(str2)
    var str3: String = 4444 + ""
    println(str3)

    //（2）String类型转基本数值类型（语法：调用相关API）
    var s1: String = "11"
    var n1: Byte = s1.toByte
    var n2: Short = s1.toShort
    var n3: Int = s1.toInt
    var n4: Long = s1.toLong
    println(s1)
    println(n1)
    println(n2)
    println(n3)
    println(n4)
  }

}
