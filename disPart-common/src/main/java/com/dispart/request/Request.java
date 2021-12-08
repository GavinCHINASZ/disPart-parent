package com.dispart.request;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

public class Request<T> {

    @Valid
    private RequestHead head;

    @Valid
    private T body;

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
