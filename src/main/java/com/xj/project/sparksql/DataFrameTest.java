package com.xj.project.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.*;
import org.apache.spark.sql.hive.HiveContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * dataFrame 测试
 *
 * @author xiangjing
 * @date 2018/3/28
 * @company 天极云智
 */
public class DataFrameTest {
    public static void main(String[] args) {
        SparkConf sConf = new SparkConf();
        sConf.setMaster("spark://master:7077");
        sConf.setAppName("xj_test");
        sConf.set("spark.executor.memory","2G");
        sConf.setJars(Arrays.asList("D:\\ScalaWorkspace\\spark\\target\\spark.jar").toArray(new String[1]));

        JavaSparkContext jsc =new JavaSparkContext(sConf);
        HiveContext sqlContext = new HiveContext(jsc);
        reflectTransform(jsc,sqlContext);
        //dynamicTransform(jsc,sqlContext);//动态转换
    }
    public static void reflectTransform(JavaSparkContext jsc, HiveContext sqlContext){
        JavaRDD<String> javaRDD=jsc.textFile("hdfs://secondary:9000/data/taskData");
        JavaRDD<TaskConfig> stu=javaRDD.map(new Function<String, TaskConfig>() {
            @Override
            public TaskConfig call(String line) throws Exception {
                String[] s= line.split("\t");
                if(null!=s && s.length!=0){
                    TaskConfig st =new TaskConfig();
                    st.setTaskId(new Integer(s[0]));
                    st.setVersion(s[1]);
                    st.setStartTime(s[2]);
                    st.setEndTime(s[3]);
                   st.setWeek_day(s[4]);
                    return st;
                }else {
                    return null;
                }

            }


        });
        DataFrame df =sqlContext.createDataFrame(stu, TaskConfig.class);
        df.show();
       // df.write().mode(SaveMode.Overwrite).saveAsTable("t_new2_task_config");

    }

    private static void dynamicTransform(JavaSparkContext jsc, SQLContext sqlContext)
    {
        JavaRDD<String> javaRDD=jsc.textFile("hdfs://secondary:9000/data/student");

        JavaRDD<Row> rowRDD = javaRDD.map( line -> {
            String[] parts = line.split(" ");
            int sid = Integer.parseInt(parts[0]);
            String sname = parts[1];
            int sage = Integer.parseInt(parts[2]);
            String sex = parts[3];

            return RowFactory.create(
                    sid,
                    sname,
                    sage,
                    sex
            );
        });

        ArrayList<StructField> fields = new ArrayList<StructField>();
        StructField field = null;
        field = DataTypes.createStructField("sid", DataTypes.IntegerType, true);
        fields.add(field);
        field = DataTypes.createStructField("sname", DataTypes.StringType, true);
        fields.add(field);
        field = DataTypes.createStructField("sage", DataTypes.IntegerType, true);
        fields.add(field);
        field = DataTypes.createStructField("sex", DataTypes.StringType, true);
        fields.add(field);

        StructType schema = DataTypes.createStructType(fields);

        DataFrame df = sqlContext.createDataFrame(rowRDD, schema);
        df.show();



    }

}
