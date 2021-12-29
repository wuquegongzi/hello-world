# flink-learn
flink1.9.1

### 1、idea 需要安装scala插件，项目需要配置scala sdk
### 2、scala 版本：scala-2.11.8
### 3、jdk 版本：1.8.0_231

#笔记

对于Flink里面的三种时间
- 事件时间
- 摄取时间
- 处理时间
   
对于流处理来说，应该以事件时间-eventTime为基准 

- 设置窗口时间类型，默认类型是ProcessingTime
 env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


Window Assigners：窗口分配器，定义如何将数据分配给窗口
- tumbling windows 滚动窗口
- sliding windows 滑动窗口
- session windows 会话窗口
- global windows 全局窗口
