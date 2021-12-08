package com.dispart.config;

import lombok.Data;

/**
 * @author:xts
 * @date:Created in 2021/7/6 16:19
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class RouteMenuDto {
    private String path;
    private String name;
    private String id;
    private String component;
    private String title;
}