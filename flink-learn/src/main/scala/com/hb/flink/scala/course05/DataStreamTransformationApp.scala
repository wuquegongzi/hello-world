package com.hb.flink.scala.course05

import java.{lang, util}

import org.apache.flink.streaming.api.collector.selector.OutputSelector
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

/**
 * DataStreamTransformation
 */
object DataStreamTransformationApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

//    filterFunction(env) //filter
//    unionFunction(env) //union
    splitAndSelectFunction(env) //splitAndSelect

    env.execute("DataStreamTransformationApp")
  }

  /**
   * split 和 Select
   * @param env
   */
  def splitAndSelectFunction(env: StreamExecutionEnvironment) = {

    val data = env.addSource(new CustomNonParallelSourceFunction)

    val splits = data.split(new OutputSelector[Long] {
      override def select(value: Long): lang.Iterable[String] = {
        val list = new util.ArrayList[String]()
        if(value % 2 == 0){
          list.add("even")
        }else{
          list.add("odd")
        }

        list
      }
    })

    splits.select("even").print()

  }


  /**
   * union
   * @param env
   * @return
   */
  def unionFunction(env: StreamExecutionEnvironment) = {

    import org.apache.flink.api.scala._
    val data1 = env.addSource(new CustomNonParallelSourceFunction)
    val data2 = env.addSource(new CustomNonParallelSourceFunction)

    data1.union(data2).print()
  }

  /**
   * filter
   * @param env
   */
  def filterFunction(env: StreamExecutionEnvironment) = {

    import org.apache.flink.api.scala._
    val data = env.addSource(new CustomNonParallelSourceFunction)

    data.map(x =>{
      println("接收到："+x)
      x
    }).filter(_%2 == 0).print().setParallelism(1)
  }
}
