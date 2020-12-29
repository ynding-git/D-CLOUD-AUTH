package com.ynding.cloud.physical.order.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * <p> </p>
 *
 * @author dyn
 * @version 2020/9/27
 * @since JDK 1.8
 */
@Data
public class PriceInfo {
    private Long id;

    private BigDecimal price;
}
