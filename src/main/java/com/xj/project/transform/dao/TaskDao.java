package com.xj.project.transform.dao;

import com.xj.project.transform.model.Task;

public interface TaskDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated Fri Mar 30 16:09:09 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated Fri Mar 30 16:09:09 CST 2018
     */
    int insert(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated Fri Mar 30 16:09:09 CST 2018
     */
    int insertSelective(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated Fri Mar 30 16:09:09 CST 2018
     */
    Task selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated Fri Mar 30 16:09:09 CST 2018
     */
    int updateByPrimaryKeySelective(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated Fri Mar 30 16:09:09 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(Task record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated Fri Mar 30 16:09:09 CST 2018
     */
    int updateByPrimaryKey(Task record);
}