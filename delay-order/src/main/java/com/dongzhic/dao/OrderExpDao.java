package com.dongzhic.dao;

import com.dongzhic.model.OrderExp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderExpDao {

    int deleteByPrimaryKey(Long id);

    int insert(OrderExp record);

    int insertSelective(OrderExp record);

    OrderExp selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderExp record);

    int updateByPrimaryKey(OrderExp record);

    /**
     * 插入延迟订单
     * @param order
     * @param expire_duration
     * @return
     */
    int insertDelayOrder(@Param("order") OrderExp order,
                         @Param("expire_duration") long expire_duration);

    /**
     * 将指定id且未支付订单的状态改为已过期
     * @param id
     * @return
     */
    int updateExpireOrder(Long id);

    /**
     * 将表中所有时间上已过期但未支付订单的状态改为已过期
     * @return
     */
    int updateExpireOrders();

    /**
     * 找出未支付且未过期的订单
     * @return
     */
    List<OrderExp> selectUnPayOrders();

    /**
     * 找出未支付且已过期的订单
     */
//    List<OrderExp> selectExpiredOrders();

}