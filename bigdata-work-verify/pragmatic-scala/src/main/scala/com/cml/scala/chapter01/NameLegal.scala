package com.cml.scala.chapter01

/**
 * @title: NameLegal
 * @projectName pragmatic-scala
 * @description: 检查名称是否合法
 * @author wuque
 * @date 2022/11/2315:24
 */
object NameLegal {

  def main(args: Array[String]): Unit = {

    //1.以字母或者下划线开头，后接字母、数字、下划线
     var hello: String ="" ;
     var hello2:String = " ";

    //    var 1 hello:String = ""  数字不能开头
    //    var h - b: String = ""  不能用-
    //    var x h: String = ""  不能用空格

    var h_4: String = ""
    val _ab: String = ""
    var Int: String = "" //ok 因为在Scala中Int是预定义的字符,不是关键字，但不推荐
    var _: String = "" // ok 单独一个下划线不可以作为标识符，因为_被认为是一个方法

    //2.以操作符开头，且只包含操作符（+ - * / # ! 等）
    var +*-/#! : String = ""
    //    var +*-/#!1 : String = "" 以操作符开头必须都是操作

    //3.用反引号`...`包括的任意字符串，即使是Scala关键字（39）个也可以
    //    var if:String = ""  不用用关键字
    var `if`: String = ""
    
  }

}
