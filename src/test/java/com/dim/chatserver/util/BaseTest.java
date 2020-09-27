package com.dim.chatserver.util;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * An abstract class with utility methods.
 */
public abstract class BaseTest {

    protected String[] initValidLengthTextData() {
        return new String[] { "c", "fhsdkfjsdfg()5435345xcfcvxc{}", "null", "NULL",
                genRandomString(160) };
    }

    protected String[] initInvalidTextLengthData() {
        return new String[] { "", " ", "  ", null, genRandomString(161) };
    }

    protected String[] initInvalidEmotionLenghtData() {
        return new String[] { "", " ", "  ", null, "a", genRandomString(11) };
    }

    protected String[] initInvalidEmotionWithDigitsData() {
        return new String[] { "ds0f()dggf", "ads1()jggk", "hdsf()mg2b", "9dsf(3gf", "{sf()d4gf",
                "d5f()dggf", "fdsf()6]gf", "d7f()dggf", "hdsf(8dggf", "dsf9)d[gf", "123456789" };
    }

    protected String[] initValidEmotionData() {
        return new String[] { ":)", ":-D", ":}D", ":::---}}}D", "abcdefgh", "null", "NULL" };
    }

    protected String genRandomString(int length) {
        final Random r = new Random();
        final StringBuilder sb = new StringBuilder();
        IntStream.range(0, length).forEach(i -> sb.append((char) ('a' + r.nextInt(26))));
        return sb.toString();
    }
}
