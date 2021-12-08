package com.dispart.vo.horn;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 13:38
 * @ Description:
 */
public interface BinaryEncoder extends Encoder {
    byte[] encode(byte[] var1) throws EncoderException;
}

