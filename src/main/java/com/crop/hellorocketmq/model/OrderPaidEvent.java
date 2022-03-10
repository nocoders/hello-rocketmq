package com.crop.hellorocketmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author linmeng
 * @version 1.0
 * @date 2022年3月10日 15:18
 */
@Data
@AllArgsConstructor
public class OrderPaidEvent implements Serializable {
    private String orderId;

    private BigDecimal paidMoney;
}
