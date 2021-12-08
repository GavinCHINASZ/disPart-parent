package com.dispart.vo;

/**
 * 对公网银响应报文
 */
public class ResponseMsg {
    private ResponseHeader header;
    private ResponseBody body;

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public ResponseBody getBody() {
        return body;
    }

    public void setBody(ResponseBody body) {
        this.body = body;
    }
}
