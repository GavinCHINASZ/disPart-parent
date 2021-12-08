package com.dispart.vo.horn;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 13:38
 * @ Description:
 */
public class DecoderException extends Exception {
    private static final long serialVersionUID = 1L;

    public DecoderException() {
    }

    public DecoderException(String message) {
        super(message);
    }

    public DecoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecoderException(Throwable cause) {
        super(cause);
    }
}