package com.hb.flink.scala.course04

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.core.fs.FileSystem.WriteMode

object SinkApp {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val data = 1.to(10)

    val text = env.fromCollection(data)

    val filePath = "/Users/leon/Documents/items/test/sink-out"

    text.writeAsText(filePath,WriteMode.OVERWRITE)
//      .setParallelism(1) //设置并行度为1，默认，sink-out为一个文件
//      .setParallelism(2) //设置并行度为1，默认，sink-out为一个文件夹

    env.execute("DataSet SinkApp")
  }

}
