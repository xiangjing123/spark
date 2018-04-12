package com.xj.project.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * json格式数据源
 *
 * @author xiangjing
 * @date 2018/4/12
 * @company 天极云智
 */
public class JSONDataFrame {

    public static void main(String[] args) {
        SparkConf sConf = new SparkConf();
        sConf.setMaster("spark://master:7077");
        sConf.setAppName("xj_test");
        sConf.set("spark.executor.memory", "2G");
        JavaSparkContext jsc = new JavaSparkContext(sConf);
        SQLContext sqlContext = new SQLContext(jsc);
        DataFrame df = sqlContext.read().json("hdfs://secondary:9000/data/student.json");
        df.show();

    }
}
