package com.cml.scala.chapter02

/**
 * @title: TestForceTransfer
 * @projectName pragmatic-scala
 * @description: TODO
 * @author wuque
 * @date 2022/11/2414:33
 */
object TestForceTransfer {

  def main(args: Array[String]): Unit = {

    //（1）将数据由高精度转换为低精度，就需要使用到强制转换
    var n1: Int = 2.7.toInt //这个存在精度损失
    println(n1)

    //（2）强转符号只针对于最近的操作数有效，往往会使用小括号提升优先级
    var r1: Int = 10 * 3.5.toInt + 6 * 1.5.toInt // 10 *3 + 6*1 = 36
    var r2: Int = (10 * 3.5 + 6 * 1.5).toInt // 44.0.toInt = 44

    println("r1=" + r1 + " r2=" + r2)

  }

}
