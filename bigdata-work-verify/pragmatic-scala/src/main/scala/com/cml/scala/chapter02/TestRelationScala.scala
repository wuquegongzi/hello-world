package com.cml.scala.chapter02

/**
 * @title: TestRelationScala
 * @projectName pragmatic-scala
 * @description: TODO
 * @author wuque
 * @date 2022/11/2414:42
 */
object TestRelationScala {

  //==更加类似于Java中的equals
  def main(args: Array[String]): Unit = {
    val s1 = "aaa"
    val s2 = new String("aaa")

    println(s1 == s2) //  true
    println(s1.eq(s2))   // false
  }

}
