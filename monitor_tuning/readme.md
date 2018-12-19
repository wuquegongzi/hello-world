# 一、JVM参数类型

  ## 1.标准参数 
  
   > * **-help**
   > * **-server** **-client**  
   > * **-version**

  
  ## 2.X参数
   > * **-Xint** : 解释执行  
   > * **-Xcomp**:第一次使用就编译成本地代码  
   > * **-Xmixed**:混合模式,JVM自己决定
   

  ## 3.XX参数
  ###3.1 Boolean类型
   > 格式： -XX:[+-]< name > 表示启用或者禁用name属性  
     eg:  
     -XX:+UseConcMarkSweepGC   启用CMS垃圾收集器  
     -XX:+UseG1GC  启用G1垃圾收集器
   
  ###3.2 非Boolean类型
  > 格式：-XX:< name > = < value > 表示name属性的值是value  
    eg:  
    -XX:MaxGCPauseMillis=500  
    XX:GCTimeRatio=19
  
   &#9728; _特殊说明：_  **-Xmx -Xms**  
   &emsp;&emsp;不是X参数，而是XX参数  
   * -Xmx 等价于 -XX:InitialHeapSize
   * -Xms 等价于 -XX:MaxHeapSize

  