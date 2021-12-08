package com.dispart.vo;

import java.io.Serializable;

/**
 * 文件推送请求 Vo
 */
public class DISP20210095ReqVo implements Serializable {
    private static final long serialVersionUID = -4604435987364529451L;

    //文件名
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
