package com.xj.project.sparkstram;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;


/**
 * 单词统计测试
 *
 * @author xiangjing
 * @date 2018/3/29
 * @company 天极云智
 */
public class WordCountTest {
    public static void main(String[] args) {
        SparkConf sConf = new SparkConf();
        sConf.setMaster("spark://master:7077");
        sConf.setAppName("xj_test");
        sConf.set("spark.executor.memory","2G");
        sConf.setJars(Arrays.asList("D:\\ScalaWorkspace\\spark\\target\\spark-1.0-SNAPSHOT.jar").toArray(new String[1]));

        JavaStreamingContext jsc = new JavaStreamingContext(sConf, Durations.seconds(30));
        JavaDStream<String> words=jsc.textFileStream("hdfs://secondary:9000/data/");
        JavaPairDStream<String,Integer> count=words.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String line) throws Exception {
                return Arrays.asList(line.split(" "));
            }
        }).mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });
        JavaPairDStream<String,Integer> countWords=count.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        });
        countWords.print();
        jsc.start();
        jsc.awaitTermination();
        jsc.stop();
        jsc.close();

    }
}
