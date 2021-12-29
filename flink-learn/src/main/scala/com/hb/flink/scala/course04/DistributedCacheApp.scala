package com.hb.flink.scala.course04

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

/**
 * 分布式缓存
 */
object DistributedCacheApp {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val filePath = "file:///Users/leon/Documents/items/test/hello.txt"

    //注册一个本地文件/HDFS文件
    env.registerCachedFile(filePath,"pk-scala-dc")

    import org.apache.flink.api.scala._
    val data = env.fromElements("hadoop","spark","flink")

    data.map(new RichMapFunction[String,String] {

      override def open(parameters: Configuration): Unit = {
        //在open方法中获取到分布式缓存的内容
        val dcFile = getRuntimeContext.getDistributedCache.getFile("pk-scala-dc")

        val lines = FileUtils.readLines(dcFile) //java 语法

        import scala.collection.JavaConverters._ //确保Java集合与scala集合兼容
        for (ele <- lines.asScala){
          println(ele)
        }

      }
      override def map(in: String): String = {
         in
      }
    }).print()

  }

}
