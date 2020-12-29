package com.ynding.cloud.physical.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> </p>
 *
 * @author dyn
 * @version 2020/9/27
 * @since JDK 1.8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfo {

    private Long id;

    private Long productId;
}
