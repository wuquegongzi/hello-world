package com.hb.flink.scala.course04

import scala.util.Random

object DBUtils {

  /**
   * 模拟打开连接
   * @return
   */
  def getConnection() = {

    new Random().nextInt(10) + ""
  }

  def returnConnection(conn:String) = {
     println("关闭连接:"+conn)
  }
}
