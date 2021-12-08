package com.dispart.vo.horn;

/**
 * @ Author     : zj
 * @ Date       : 2019/1/8 13:37
 * @ Description:
 */
public interface BinaryDecoder extends Decoder {
    byte[] decode(byte[] var1) throws DecoderException;
}
