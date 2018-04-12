package com.xj.project.sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.hive.HiveContext;

import java.util.Arrays;
import java.util.HashMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * spark sql 测试
 *
 * @author xiangjing
 * @date 2018/3/22
 * @company 天极云智
 */
public class SparkSql implements Serializable{
    public static void main(String[] args) {


        SparkConf sConf = new SparkConf();
        sConf.setMaster("yarn");
        sConf.set("spark.executor.memory","2G");
        SparkContext sc= new SparkContext("spark://master:7077","xj_test",sConf);

        HiveContext sqlContext = new HiveContext(sc);
        List<String> list= Arrays.asList("t_internship_journal","t_internship_summary","t_sign","t_student","t_sys_user ","t_task","t_task_config","t_task_config_detail","t_task_group","t_task_student");
        list.forEach(table -> mysqlToTest(table.trim(),sqlContext));

    }

    public static void mysqlToTest(String dbName,HiveContext sqlContext){
        DataFrameReader reader=sqlContext.read().format("jdbc");
        Map<String,String> map = new HashMap<>();
        map.put("url","jdbc:mysql://39.108.147.95:3306/shixi2.5?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true");
        map.put("driver","com.mysql.jdbc.Driver");
        map.put("user","shixitest");
        map.put("password","shixiTestpwd!*@$");
        map.put("dbtable",dbName);
        reader.options(map);
        DataFrame df =reader.load();
        df.write().mode(SaveMode.Overwrite).saveAsTable(dbName);
       /* df.registerTempTable("student");*/
    }
}
