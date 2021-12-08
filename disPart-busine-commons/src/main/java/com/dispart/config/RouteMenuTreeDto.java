package com.dispart.config;

import lombok.Data;

import java.util.List;

/**
 * @author:xts
 * @date:Created in 2021/7/6 16:14
 * @description
 * @modified by:
 * @version: 1.0
 */
@Data
public class RouteMenuTreeDto {
    private String path;
    private String name;
    private String redirect;
    private String title;
    private String id;
    private List<RouteMenuDto> children;
}