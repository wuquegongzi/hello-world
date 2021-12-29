package com.hb.flink.scala.course05

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

/**
 * DataStream
 */
object DataStreamSourceApp {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment;

//    socketFunction(env) //从socket创建datastream
    
//    nonParallelSourceFunction(env) //自定义数据源方式sourcefunction之scala实现

//    parallelSourceFunction(env) //自定义数据源方式parallelsourcefunction之scala实现

    RichParallelSourceFunction(env) //自定义数据源方式richparallelsourcefunction之scala实现
    env.execute("DataStreamSourceApp")
  }

  /**
   * 从socket创建datastream
   * @param env
   * @return
   */
  def socketFunction(env: StreamExecutionEnvironment) = {

    val data = env.socketTextStream("localhost",9999)

    data.print()
  }

  /**
   * 自定义数据源方式sourcefunction之scala实现
   * @param env
   */
  def nonParallelSourceFunction(env: StreamExecutionEnvironment) = {
    val data = env.addSource(new CustomNonParallelSourceFunction)

    data.print().setParallelism(1)
  }

  /**
   * 自定义数据源方式parallelsourcefunction之scala实现
   * @param env
   * @return
   */
  def parallelSourceFunction(env: StreamExecutionEnvironment) = {
    val data = env.addSource(new CustomParallelSourceFunction)

    data.print() //多路并行，设置并行度为1失效 .setParallelism(1)
  }

  /**
   * 自定义数据源方式richparallelsourcefunction之scala实现
   * @param env
   * @return
   */
  def RichParallelSourceFunction(env: StreamExecutionEnvironment) = {
    val data = env.addSource(new CustomRichParallelSourceFunction)

    data.print().setParallelism(2)
  }



}
