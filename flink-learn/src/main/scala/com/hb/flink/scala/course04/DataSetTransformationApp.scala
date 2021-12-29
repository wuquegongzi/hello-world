package com.hb.flink.scala.course04

import org.apache.flink.api.common.operators.Order
import org.apache.flink.api.java.operators.DataSource
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

import scala.collection.mutable.ListBuffer

/**
 * 转换函数
 */
object DataSetTransformationApp {


  def main(args: Array[String]): Unit = {

    var env = ExecutionEnvironment.getExecutionEnvironment

//    mapFunction(env)  //map
//    filterFunction(env) //filter
//    mapPartitionFunction(env) //mapPartition
//    firstFunction(env) //first
//    flatMapFunction(env) flatMap
//    distinctFunction(env)
//    joinFunction(env)
//    outerJoinFunction(env)
    crossFunction(env)
  }


  /**
   * map
   * @param env
   */
  def mapFunction(env: ExecutionEnvironment): Unit ={
    var data = env.fromCollection(List(1,2,3,4,5,6,7,8,9,10))

//    data.print()

    //对data中的每个元素都做一个+1的操作
//    data.map((x:Int) => x+1).print()
//    data.map((x) => x+1).print()
//    data.map(x => x+1).print()
    data.map(_+1).print() //最精简版 打死都要记住滴

  }

  /**
   * filter
   * @param env
   */
  def filterFunction(env: ExecutionEnvironment): Unit ={

//    var data = env.fromCollection(List(1,2,3,4,5,6,7,8,9,10))
//    data.map(_ + 1).filter(_ > 5).print()

    env.fromCollection(List(1,2,3,4,5,6,7,8,9,10))
      .map(_ + 1)
      .filter(_ > 5)
      .print()
  }

  /**
   * mapPartition
   * @param env
   *       DataSource 100个元素，把结果存储到数据库中
   *       mapPartition 根据并行度设置进行并行
   */
  def mapPartitionFunction(env:ExecutionEnvironment): Unit ={

    var students = new ListBuffer[String]

    for( i<-1 to 100){
      students.append("student:" + i)
    }

    var data = env.fromCollection(students)
                  .setParallelism(1) //设置并行度，默认为1


    //不使用mapPartition 性能低下，连接直接爆掉
    var i = 0
//    data.map(x => {
//
//      //每一个元素要存储到数据库中去，肯定需要先获取到一个connection
//      var connection = DBUtils.getConnection
//
//      i = i+1
//
//      println(i + "打开连接："+connection)
//
//      //存储到DB中去
//      //释放连接
//      DBUtils.returnConnection(connection)
//
//    }).print()

    data.mapPartition(x => {

      var connection = DBUtils.getConnection

      i = i+1
      println(i + "打开连接："+connection)

            //存储到DB中去
            //释放连接
            DBUtils.returnConnection(connection)
      x
    }).print()
  }

  /**
   * first
   * @param environment
   */
  def firstFunction(environment: ExecutionEnvironment): Unit ={
    var data =ListBuffer[(Int,String)]()

    data.append((1,"spark"))
    data.append((1,"hadoop"))
    data.append((2,"flink"))
    data.append((2,"spring boot"))
    data.append((2,"java"))
    data.append((3,"linux"))
    data.append((4,"vue"))

    var ds = environment.fromCollection(data)
//    ds.first(3).print(); //取前三条
//    ds.groupBy(0).first(2).print() //分组后再取每一组里的前两条
    ds.groupBy(0).sortGroup(1,Order.ASCENDING).first(2).print() //分组后正向排序
//    Order.DESCENDING  //降序
  }


  /**
   * flatmap
   * @param env
   */
  def flatMapFunction(env: ExecutionEnvironment): Unit ={
    val info = ListBuffer[String]()

    info.append("hadoop,spark")
    info.append("hadoop,flink")
    info.append("flink,flink")

    var data = env.fromCollection(info)
//    data.print()
//    data.map(_.split(",")).print()

    //.map((_,1)) 给每一个元素赋值1
    data.flatMap(_.split (",")).map((_,1)).groupBy(0).sum(1).print()

  }

  /**
   * distinct
   * @param env
   */
  def distinctFunction(env: ExecutionEnvironment): Unit = {

    val info = ListBuffer[String]()

    info.append("hadoop,spark")
    info.append("hadoop,flink")
    info.append("flink,flink")

    var data = env.fromCollection(info)

    data.flatMap(_.split (",")).distinct().print()

  }

  /**
   * join
   * @param env
   */
  def joinFunction(env: ExecutionEnvironment): Unit = {

    val info1 = ListBuffer[(Int,String)]()

    info1.append((1,"leon"))
    info1.append((2,"chen"))
    info1.append((3,"leige"))

    val info2 = ListBuffer[(Int,String)]()

    info2.append((1,"北京"))
    info2.append((2,"上海"))
    info2.append((4,"杭州"))

    var data1 = env.fromCollection(info1)
    var data2 = env.fromCollection(info2)

    data1.join(data2)
      .where(0)
      .equalTo(0)
      .apply((first,second)=>{
        (first._1,first._2,second._2)
      }).print()

  }

  def outerJoinFunction(env: ExecutionEnvironment): Unit = {

    val info1 = ListBuffer[(Int,String)]()

    info1.append((1,"leon"))
    info1.append((2,"chen"))
    info1.append((3,"leige"))

    val info2 = ListBuffer[(Int,String)]()

    info2.append((1,"北京"))
    info2.append((2,"上海"))
    info2.append((4,"杭州"))

    var data1 = env.fromCollection(info1)
    var data2 = env.fromCollection(info2)

    println("~~~~左连接~~~~~")

    data1.leftOuterJoin(data2)
      .where(0)
      .equalTo(0)
      .apply((first,second)=>{
        if(null == second){
          (first._1,first._2,"-")
        }else{
          (first._1,first._2,second._2)
        }
      }).print()

    println("~~~~右连接~~~~~")

    data1.rightOuterJoin(data2)
      .where(0)
      .equalTo(0)
      .apply((first,second)=>{
        if(null == first){
          (second._1,"-",second._2)
        }else{
          (first._1,first._2,second._2)
        }
      }).print()

    println("~~~~全连接~~~~~")

    data1.fullOuterJoin(data2)
      .where(0)
      .equalTo(0)
      .apply((first,second)=>{
        if(null == first){
          (second._1,"-",second._2)
        }else if(null == second) {
          (first._1, first._2, "-")
        }else {
          (first._1,first._2,second._2)
        }
      }).print()
  }


  /**
   * cross 笛卡尔积
   * @param env
   */
  def crossFunction(env: ExecutionEnvironment): Unit = {

    val info1 = List("曼联","曼城")
    val info2 = List(3,1,0)

    var data1 = env.fromCollection(info1)
    var data2 = env.fromCollection(info2)

    data1.cross(data2).print()
  }
}
