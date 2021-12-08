package com.dispart.model.order;

import java.lang.annotation.*;

/**
 * @author 杨海珲
 * @date 2021/6/16 2:06 下午
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface YHHIndex {
    int index();
}
