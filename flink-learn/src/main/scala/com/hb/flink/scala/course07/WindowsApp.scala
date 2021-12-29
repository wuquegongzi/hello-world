package com.hb.flink.scala.course07

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * Window
 */
object WindowsApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val text = env.socketTextStream("localhost",8888)

    import org.apache.flink.api.scala._
    text.flatMap(_.split(","))
      .map((_,1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
//      .countWindow(5)
      .sum(1)
      .print()
      .setParallelism(1)

    env.execute("WindowsApp")
  }

}
