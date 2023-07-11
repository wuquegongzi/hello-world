package com.cml.spark

import org.apache.spark.sql.SparkSession

/**
 * @title: SimpleApp
 * @projectName pragmatic-spark
 * @description: TODO
 * @author wuque
 * @date 2022/11/2511:45
 */
object SimpleApp {

  def main(args: Array[String]): Unit = {

    val logFile = "/Users/wuque/Downloads/111.md" // Should be some file on your system
    val spark = SparkSession.builder.appName("Simple Application").getOrCreate()
    val logData = spark.read.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("structure")).count()
    val numBs = logData.filter(line => line.contains("Lake")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    spark.stop()

  }

}
