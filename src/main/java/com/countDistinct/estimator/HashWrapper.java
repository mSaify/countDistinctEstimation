package com.countDistinct.estimator;



import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.codec.digest.MurmurHash2;
import org.apache.commons.lang3.StringUtils;



public class HashWrapper {

    Optional<Integer> randomSeed = Optional.of(0);
    ArrayList<Integer> randomSeeds = new ArrayList<>();

    public HashWrapper(Integer hashFunctions) {
        HashGenerator(hashFunctions);
    }

    private void HashGenerator ( Integer maxHashFunctions){
        IntStream.range(0, maxHashFunctions)
                .forEach(val -> randomSeeds.add(val));
    }

    public Long getHashValue(Integer hashFunctionIndex,String val) {

        randomSeed = Optional.ofNullable(randomSeeds.get(hashFunctionIndex));
        byte[] bytes = StringUtils.getBytes(val, Charset.defaultCharset());
        return Math.abs(MurmurHash2.hash64(bytes, bytes.length, randomSeed.orElseGet(()->0)));


    }
}
