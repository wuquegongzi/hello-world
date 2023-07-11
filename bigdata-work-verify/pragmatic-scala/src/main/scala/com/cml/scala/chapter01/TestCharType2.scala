package com.cml.scala.chapter01

/**
 * @title: TestCharType2
 * @projectName pragmatic-scala
 * @description: 测试整型
 * @author wuque
 * @date 2022/11/2316:57
 */
object TestCharType2 {

  def main(args: Array[String]): Unit = {
    var c1: Char = 'a';
    println("c1: "+ c1);

    //注意：这里涉及自动类型提升，其实编译器可以自定判断是否超出范围
    //不过idea提示报错
    var c2: Char = 'a' + 1
    println(c2)

    //    \t ：一个制表位，实现对齐的功能
    println("姓名\t年龄")

    //   \n ：换行符
    println("西门庆\n潘金莲")

    //（4）\\ ：表示\
    println("c:\\天黑了\\饿狼来了")

    //（5）\" ：表示"
    println("你过来：\"看我一拳打死你\"")

  }

}
