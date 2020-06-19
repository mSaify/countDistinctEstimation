package com.countDistinct.estimator;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.MurmurHash2;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;



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
        byte[] bytes = StringUtils.getBytesUtf8(val);
        return Math.abs(MurmurHash2.hash64(bytes, bytes.length, randomSeed.orElseGet(()->0)));


    }
}
