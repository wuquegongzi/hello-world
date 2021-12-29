package com.hb.flink.scala.course04

import org.apache.flink.api.common.accumulators.LongCounter
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.FileSystem.WriteMode

/**
 * 计数器
 *
 * 基于Flink scala编程的计数器
 */
object CounterApp {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    import org.apache.flink.api.scala._
    val data = env.fromElements("hadoop","spark","flink")

    //并行度为1没有问题，并行度>1的时候，会有并发问题
//    data.map(new RichMapFunction[String,Long]() {
//      var counter = 0l
//      override def map(in: String): Long = {
//        counter = counter + 1
//        println("counter : "+counter)
//        counter
//      }
//    }).print()

    val info = data.map(new RichMapFunction[String,String] {

      //定义计数器
      val counter = new LongCounter()

      override def open(parameters: Configuration): Unit = {

        //注册计数器,ele-counts-scala为计数器的名
        getRuntimeContext.addAccumulator("ele-counts-scala",counter)
      }

      override def map(in: String): String = {
        counter.add(1)
        in
      }
    })

    val filePath = "file:///Users/leon/Documents/items/test/counter-app"
    info.writeAsText(filePath,WriteMode.OVERWRITE)
    val jobResult = env.execute("CounterApp")

    val num = jobResult.getAccumulatorResult[Long]("ele-counts-scala")
    println(num)
  }

}
