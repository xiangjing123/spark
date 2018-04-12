package com.xj.project.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import java.util.Arrays;

/**
 * 测试统计查询
 *
 * @author xiangjing
 * @date 2018/4/2
 * @company 天极云智
 */
public class CountTaskConfig {

    public static void main(String[] args) {
        long current=System.currentTimeMillis();

        SparkConf sConf = new SparkConf();
        sConf.setMaster("yarn-cluster");
        sConf.setAppName("CountTaskConfig");
     /*   sConf.set("spark.executor.memory","2G");
        sConf.set("spark.total.executor.cores","4");*/
       // sConf.setJars(Arrays.asList("D:\\ScalaWorkspace\\spark\\target\\spark.jar").toArray(new String[1]));
        SparkContext sc = new SparkContext(sConf);
        SQLContext sqlContext = new SQLContext(sc);
        DataFrame taskConfig=sqlContext.read().load("hdfs://secondary:9000/user/hive/warehouse/t_new2_task_config/part-r-00000-f5ac497a-31e9-424b-8fa1-543902d38f1f.gz.parquet", "hdfs://secondary:9000/user/hive/warehouse/t_new2_task_config/part-r-00001-f5ac497a-31e9-424b-8fa1-543902d38f1f.gz.parquet");
        taskConfig.registerTempTable("t_new_task_config");
        DataFrame taskStudent=sqlContext.read().load("hdfs://secondary:9000/user/hive/warehouse/t_task_student/part-r-00000-03372b72-98d9-4160-b4a6-ae4af5dabb65.gz.parquet");
        taskStudent.registerTempTable("t_task_student");
        DataFrame sign=sqlContext.read().load("hdfs://secondary:9000/user/hive/warehouse/t_sign/part-r-00000-c23a359c-95f3-47b6-80d0-6149e7d5c755.gz.parquet");
        sign.registerTempTable("t_sign");
        sqlContext.sql("SELECT count(*) FROM (SELECT taskId,version FROM t_new_task_config WHERE '2018-03-23 00:00:00'  <= endTime AND '2018-03-23 00:00:00' >= startTime AND instr(week_day, '5') > 0) tk LEFT JOIN t_task_student ts ON tk.taskId = ts.task_id AND tk.version = ts.version where isnotnull(ts.task_id)").show();
        sqlContext.sql("SELECT count(*) FROM (SELECT taskId,version FROM t_new_task_config WHERE '2018-03-23 00:00:00'  <= endTime AND '2018-03-23 00:00:00' >= startTime) tk LEFT JOIN t_task_student ts ON tk.taskId = ts.task_id AND tk.version = ts.version where isnotnull(ts.task_id)").show();
        sqlContext.sql("select count(*) from t_sign where date_format(sign_time,'yyyy-MM-dd')='2018-03-23' AND status != -2").show();
        System.out.println("所用时间="+(System.currentTimeMillis()-current));
    }
}
