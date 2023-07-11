package com.cml.scala.chapter01

/**
 * @title: TestSpecialType
 * @projectName pragmatic-scala
 * @description: Unit类型、Null类型和Nothing类型
 * @author wuque
 * @date 2022/11/2317:14
 */
object TestSpecialType {

  def main(args: Array[String]): Unit = {

    def sayOk: Unit = { // unit 表示没有返回值，即void
        println("yoyoyo")
      }

    //Nothing，可以作为没有正常返回值的方法的返回类型，非常直观的告诉你这个方法不会正常返回，
    // 而且由于Nothing是其他任意类型的子类，他还能跟要求返回值的方法兼容。
    def sayNothing: Nothing = {
      throw new Exception
    }

    println(sayOk)

    println(sayNothing)
  }

}
