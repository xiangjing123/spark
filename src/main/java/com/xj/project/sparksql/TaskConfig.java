package com.xj.project.sparksql;

import java.io.Serializable;
import java.util.Date;

/**
 * taskConfig
 *
 * @author xiangjing
 * @date 2018/4/2
 * @company 天极云智
 */
public class TaskConfig implements Serializable{

    private static final long serialVersionUID = -8455719978026465668L;

    private  int taskId;

    private String version;

    private String startTime;

    private String endTime;

    private String week_day;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getWeek_day() {
        return week_day;
    }

    public void setWeek_day(String week_day) {
        this.week_day = week_day;
    }
}
