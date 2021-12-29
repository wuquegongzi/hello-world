package com.hb.flink.scala.course02

import org.apache.flink.api.scala.ExecutionEnvironment

/**
 * scala 实现批处理程序
 */
object BatchWCScalaApp {

  def main(args: Array[String]): Unit = {

    var input ="file:///Users/leon/Desktop/input"

    var env = ExecutionEnvironment.getExecutionEnvironment

    var text = env.readTextFile(input)

    //引入隐式转换
    import org.apache.flink.api.scala._

    text.flatMap(_.toLowerCase.split("\t"))
      .filter(_.nonEmpty)
      .map((_,1))  //1 ->value
      .groupBy(0)  //0 ->key
      .sum(1).print()
  }
}
