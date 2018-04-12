package com.xj.project.transorm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xj.project.sparksql.TaskConfig;
import com.xj.project.transform.dao.TaskConfigDao;
import com.xj.project.transform.utils.DateUtil;
import com.xj.project.transform.utils.PageUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * task config 转值
 *
 * @author xiangjing
 * @date 2018/3/30
 * @company 天极云智
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TransormTaskConfig implements Serializable {

    private static final long serialVersionUID = -8455719978026465668L;

    @Resource
    private TaskConfigDao taskConfigDao;

    @Test
    public void transorm() throws Exception{
        System.out.println("程序开始运行时间="+DateUtil.format(new Date()));
        PageUtil page =new PageUtil();
        page.setPageNo(2);
        page.setPageSize(20);

       List<Map<String,Object>> tasks=taskConfigDao.findList(null);
        List<List<String>> result= new ArrayList<>();
        List<String> list=null;

        for(Map<String,Object> task:tasks){
            JSONArray jsonArray=JSON.parseArray(task.get("task_time_range").toString());
            if(jsonArray.size() >0 ){
                for(int i=0;i<jsonArray.size();i++){
                    if(null != task.get("week_day")){
                        JSONArray week_day= JSON.parseArray(task.get("week_day").toString());
                        if(week_day.size()!=0){
                            JSONArray array=jsonArray.getJSONArray(i);
                            list = new ArrayList<>();
                            list.add(task.get("task_id").toString());
                            list.add(DateUtil.format(DateUtil.parse(task.get("version").toString())));
                            if(array.size() >0 ){
                                for(int j=0;j<array.size();j++){
                                    list.add(array.get(j).toString());
                                }
                                result.add(list);
                            }
                            StringBuffer week_dy =new StringBuffer();
                            for(int j=0;j<week_day.size();j++){
                                week_dy.append(week_day.get(j).toString()+",");
                            }
                            list.add(week_dy.substring(0,week_dy.length()-1));
                        }
                    }



                }

            }
        }

        toFiel(result);

    }

   /* public void arrayToTable(List<List<String>> result){
        SparkConf sc = new SparkConf();
        sc.setAppName("count sign");
        sc.setMaster("spark://master:7077");
        sc.set("spark.executor.memory","2G");
        sc.setJars(Arrays.asList("D:\\ScalaWorkspace\\spark\\target\\spark.jar").toArray(new String[1]));

        JavaSparkContext jsc = new JavaSparkContext(sc);
        SQLContext sqlContext = new SQLContext(jsc);

        JavaRDD<List<String>> rdd=jsc.parallelize(result);
        System.out.println(rdd.collect());

        JavaRDD<TaskConfig> rddTaskConfig= rdd.map(new Function<List<String>, TaskConfig>() {
            private static final long serialVersionUID = -8455719978026465668L;

            @Override
            public TaskConfig call(List<String> array) throws Exception {
                TaskConfig config = new TaskConfig();
                config.setTaskId(Integer.valueOf(array.get(0)));
                config.setVersion(array.get(1));
                config.setStartTime(array.get(2));
                config.setEndTime(array.get(3));
                config.setWeek_day(array.get(4));
                return config;
            }


        });
        DataFrame df = sqlContext.createDataFrame(rddTaskConfig,TaskConfig.class);
        df.show();
        System.out.println("程序结束运行时间="+DateUtil.format(new Date()));

    }*/
   public void toFiel(List<List<String>> result) throws IOException {
       StringBuffer sb = new StringBuffer();
       result.forEach(array -> {
           array.forEach(column -> sb.append(column+"\t"));
           sb.append("\r\n");
       });
       File file =new File("d://taskData");
       if(!file.exists()){
           file.createNewFile();
       }

       FileOutputStream fos = new FileOutputStream(file);
       fos.write(sb.toString().getBytes());
       fos.flush();
       fos.close();
       System.out.println("程序结束运行时间="+DateUtil.format(new Date()));


   }
}
