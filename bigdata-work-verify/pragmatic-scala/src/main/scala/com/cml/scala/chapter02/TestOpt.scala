package com.cml.scala.chapter02

/**
 * @title: TestOpt
 * @projectName pragmatic-scala
 * @description: 运算符本质
 * @author wuque
 * @date 2022/11/2414:56
 */
object TestOpt {

  def main(args: Array[String]): Unit = {
    // 标准的加法运算
    val i:Int = 1.+(1)

    // （1）当调用对象的方法时，.可以省略
    val j:Int = 1 + (1)

    // （2）如果函数参数只有一个，或者没有参数，()可以省略
    val k:Int = 1 + 1

    println(i.toString())
    println(j toString())
    println(k toString)
  }

}
