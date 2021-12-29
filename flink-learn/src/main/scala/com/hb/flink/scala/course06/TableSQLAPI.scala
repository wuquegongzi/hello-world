package com.hb.flink.scala.course06

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.table.api._
import org.apache.flink.table.api.scala._
import org.apache.flink.types.Row

/**
 * TABLE
 */
object TableSQLAPI {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val tableEnv = BatchTableEnvironment.create(env)

    var filePath = "file:///Users/leon/Documents/items/test/people.csv"

    val csv = env.readCsvFile[People](filePath,ignoreFirstLine = false)

//    csv.print()

    val peopleTable = tableEnv.fromDataSet(csv)
    tableEnv.registerTable("people",peopleTable)

    //sql
    val resultTable = tableEnv.sqlQuery("select * from people")

    tableEnv.toDataSet[Row](resultTable).print()

  }

  case class People(name:String,
                    age:Int,
                    year:Int
                   ){}

}
