package com.dispart.vo.horn;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 13:36
 * @ Description:
 */
public class EncoderException extends Exception {
    private static final long serialVersionUID = 1L;

    public EncoderException() {
    }

    public EncoderException(String message) {
        super(message);
    }

    public EncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncoderException(Throwable cause) {
        super(cause);
    }
}