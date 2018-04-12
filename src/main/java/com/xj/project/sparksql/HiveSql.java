package com.xj.project.sparksql;

import com.xj.project.transform.utils.DateUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.hive.HiveContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * hive sql ces
 *
 * @author xiangjing
 * @date 2018/3/26
 * @company 天极云智
 */
public class HiveSql {
    public static void main(String[] args) {
        String driverName="org.apache.hive.jdbc.HiveDriver";
        try {
           long current =System.currentTimeMillis();
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection("jdbc:hive2://master:10000/default", "root", "root");
/*            Statement stat=connection.createStatement();
            connection.prepareStatement("SELECT count(*) FROM (SELECT task_id,version FROM t_new_task_config WHERE CURRENT_TIMESTAMP () <= end_time AND CURRENT_TIMESTAMP () >= start_time AND instr(week_day, '7') > 0) tk LEFT JOIN t_task_student ts ON tk.task_id = ts.task_id AND tk.version = ts.version where isnotnull(ts.task_id);").execute();*/
            /**
             * 总的应签到的学生
             */
            PreparedStatement ps=connection.prepareStatement("SELECT count(*) FROM (SELECT task_id,version FROM t_new_task_config WHERE '2018-03-23 00:00:00'  <= end_time AND '2018-03-23 00:00:00' >= start_time AND instr(week_day, '5') > 0) tk LEFT JOIN t_task_student ts ON tk.task_id = ts.task_id AND tk.version = ts.version where isnotnull(ts.task_id)");
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                for(int i=1;i<=rs.getMetaData().getColumnCount();i++){
                    System.out.print("应签到的实习人数="+rs.getObject(i) + "\t");
                }
                System.out.println();
            }
            /**
             * 当前实习人数
             */
            PreparedStatement ps1=connection.prepareStatement("SELECT count(*) FROM (SELECT task_id,version FROM t_new_task_config WHERE '2018-03-23 00:00:00' <= end_time AND '2018-03-23 00:00:00' >= start_time) tk LEFT JOIN t_task_student ts ON tk.task_id = ts.task_id AND tk.version = ts.version where isnotnull(ts.task_id) ");
            ResultSet rs1=ps1.executeQuery();
            while(rs1.next()){
                for(int i=1;i<=rs1.getMetaData().getColumnCount();i++){
                    System.out.print("总的实习人数="+rs1.getObject(i) + "\t");
                }
                System.out.println();
            }

            PreparedStatement ps2=connection.prepareStatement("select count(*) from t_sign where date_format(sign_time,'yyyy-MM-dd')='2018-03-23' AND status != -2");
            ResultSet rs2=ps2.executeQuery();
            while(rs2.next()){
                for(int i=1;i<=rs2.getMetaData().getColumnCount();i++){
                    System.out.print("已签到人数="+rs2.getObject(i) + "\t");
                }
                System.out.println();
            }

            System.out.println("截止时间="+(System.currentTimeMillis()-current));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
