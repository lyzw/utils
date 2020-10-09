package me.sapling.utils.common.base.bean.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/7/21
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class Tuple3<K,V,T>{

    K param1;

    V param2;

    T param3;

}
