package com.jiang;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

/**
 * @author SmilingSea
 * 2024/4/12
 */
public class isBase64
{
    @Test
    public void isbase64(){
        String s  = "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";

        System.out.println(Base64.isBase64(s));

        byte[] decode = java.util.Base64.getDecoder().decode(s);
        System.out.println(decode.toString());


    }
}
