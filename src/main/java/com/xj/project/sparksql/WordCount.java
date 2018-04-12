package com.xj.project.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;

/**
 * 单词统计
 *
 * @author xiangjing
 * @date 2018/3/29
 * @company 天极云智
 */
public class WordCount {
    public static void main(String[] args) {
        SparkConf sConf = new SparkConf();
        sConf.setMaster("spark://master:7077");
        sConf.setAppName("xj_test");
        sConf.set("spark.executor.memory","2G");
        sConf.setJars(Arrays.asList("D:\\ScalaWorkspace\\spark\\target\\spark-1.0-SNAPSHOT.jar").toArray(new String[1]));
        JavaSparkContext jsc = new JavaSparkContext(sConf);
        JavaRDD<String> javaRDD= jsc.textFile("hdfs://secondary:9000/data/word.txt");
        JavaPairRDD<String,Integer> words=javaRDD.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String word) throws Exception {
                return Arrays.asList(word.split(" "));
            }
        }).mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        JavaPairRDD<String,Integer> wordCounts= words.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        System.out.println(wordCounts.collect());

    }
}
