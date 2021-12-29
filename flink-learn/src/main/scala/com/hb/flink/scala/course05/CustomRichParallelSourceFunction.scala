package com.hb.flink.scala.course05

import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
 * 自定义数据源方式richparallelsourcefunction之scala实现
 */
class CustomRichParallelSourceFunction extends RichParallelSourceFunction[Long]{

  var count = 1L
  var isRunning = true

  override def run(sourceContext: SourceFunction.SourceContext[Long]): Unit = {
    while(isRunning){
      sourceContext.collect(count)
      count += 1
      Thread.sleep(1000)
    }
  }

  override def cancel(): Unit = {
    isRunning = false
  }
}
