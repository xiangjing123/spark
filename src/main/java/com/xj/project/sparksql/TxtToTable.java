package com.xj.project.sparksql;

import com.xj.project.transform.utils.DateUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.hive.HiveContext;

import java.util.Arrays;

/**
 * 将txt 转换为table
 *
 * @author xiangjing
 * @date 2018/4/2
 * @company 天极云智
 */
public class TxtToTable {

    public static void main(String[] args) {
        SparkConf sConf = new SparkConf();
        sConf.setMaster("spark://master:7077");
        sConf.set("spark.executor.memory","2G");
        sConf.setAppName("txt_table");
       // sConf.setJars(Arrays.asList("D:\\ScalaWorkspace\\spark\\target\\spark.jar").toArray(new String[1]));

        JavaSparkContext jsc = new JavaSparkContext(sConf);
        HiveContext sqlContext = new HiveContext(jsc);
        JavaRDD<String> javaRDD =jsc.textFile("hdfs://secondary:9000/data/taskData");
        JavaRDD<TaskConfig> rddRow=javaRDD.map(new Function<String, TaskConfig>() {
            @Override
            public TaskConfig call(String line) throws Exception {
                String[] columns = line.split("\t");
                if( null != columns && columns.length != 0){
                    TaskConfig taskCofig = new TaskConfig();
                    taskCofig.setTaskId(Integer.valueOf(columns[0]));
                    taskCofig.setVersion(columns[1]);
                    taskCofig.setStartTime(columns[2]);
                    taskCofig.setEndTime(columns[3]);
                    taskCofig.setWeek_day(columns[4]);
                    return taskCofig;
                }else{
                    return null;
                }

            }
        });
        DataFrame df = sqlContext.createDataFrame(rddRow, TaskConfig.class);
        df.write().mode(SaveMode.Overwrite).saveAsTable("t_new2_task_config");

    }
}
