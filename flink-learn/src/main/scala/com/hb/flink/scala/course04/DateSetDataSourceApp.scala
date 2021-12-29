package com.hb.flink.scala.course04

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

/**
 * 基于Scala的DataSet API 数据源 集合创建管理
 */
object DateSetDataSourceApp {

  def main(args: Array[String]): Unit = {
   var env = ExecutionEnvironment.getExecutionEnvironment

//    formCollection(env)  //基于集合
//    readFile(env)  //基于文件
//    readCsvFile(env) //基于CSV
//    readRecursiveFiles(env)  //递归文件
    readCompressedFiles(env)

  }

  /**
   * 读取压缩文件
   * @param env
   */
  def readCompressedFiles(env: ExecutionEnvironment): Unit ={

    env.readTextFile("/Users/leon/Documents/items/test/1/testtar.tar").print()
  }

  /**
   * 从递归问价获取dataset
   */
  def readRecursiveFiles(env: ExecutionEnvironment): Unit ={

    var filePath = "/Users/leon/Documents/items/test";

    println("～～～～华丽的分割线～～～～～")
    // create a configuration object
    val parameters = new Configuration

    // set the recursive enumeration parameter
    parameters.setBoolean("recursive.file.enumeration", true)

    // pass the configuration to the data source
    env.readTextFile(filePath)
      .withParameters(parameters)
      .print()

  }


  /**
   * 基于csv
   * @param env
   */
  def readCsvFile(env: ExecutionEnvironment): Unit ={
    import org.apache.flink.api.scala._

    var csvPath = "/Users/leon/Documents/items/test/people.csv";

    env.readCsvFile[(String, Int, Int)](csvPath,ignoreFirstLine=false).print()

  }

  /**
   * 基于文件
   * @param env
   */
  def readFile(env : ExecutionEnvironment): Unit ={

    //基于文件
    var filePath = "file:///Users/leon/Documents/items/test/hello.txt"
    //基于文件夹 也ok
//    var filePath = "file:///Users/leon/Documents/items/test"
    env.readTextFile(filePath).print()
  }

  /**
   * 基于集合
   * @param env
   */
  def formCollection(env : ExecutionEnvironment): Unit ={

    import org.apache.flink.api.scala._

    val data = 1 to 10

    env.fromCollection(data).print()
  }

}
