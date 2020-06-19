package com.countDistinct.estimator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.stream.IntStream;
public class CountDistinctDataStructure extends ArrayList<ArrayList<BitSet>> implements Cloneable, Serializable {

    private int noOfHashFunctions=0;
    private int R;

    public CountDistinctDataStructure(int noOfHashFunctions, int R) {

        this.noOfHashFunctions=noOfHashFunctions;
        this.R = R;

        for(var i=0;i<this.noOfHashFunctions;i++) {
            var bitArr = new ArrayList<BitSet>(64);
              for(var j=0;j<65;j++) {

                bitArr.add(new BitSet(R));
            }
            this.add(bitArr);
        }

    }

    public CountDistinctDataStructure() {

    }

    public CountDistinctDataStructure Union(CountDistinctDataStructure structure2) {

        var hashFuncCounter=0;
        for(var hashFunctions : this ) {

            for(var bitSetsCounter=0; bitSetsCounter<hashFunctions.size(); bitSetsCounter++) {

                hashFunctions.get(bitSetsCounter).or(structure2
                        .get(hashFuncCounter)
                        .get(bitSetsCounter));
            }
        }
        return this;
    }

    public long EstimateCount() {

        var res = IntStream
                .range(0, this.noOfHashFunctions)
                .mapToLong(hashFunctionIndex -> CalculateFo(this.get(hashFunctionIndex),R));


       return (long) median(res.toArray());
    }

    /// update value example
    /// if z = 12 R = 2^4
    /// binary fo z = 1110 here prefix is 11 which is before 10
    /// trailing zeros = 1
    /// w = 2
    /// prefix = 11 (binary) i.e. 3
    /// prefix = (13 / 2^2) mod R = 3
    public Integer[] SketchUpdateValues(long z, int R) {
        var w = 1+ TrailingZeros(z);
        int prefix = (int)((z/ Math.pow(2,w)) % R);
        return new Integer[] {w,prefix};
    }

    public byte TrailingZeros(long val) {

        String bit = Long.toBinaryString(val);
        StringBuilder bit1 = new StringBuilder();
        bit1.append(bit);
        bit1=bit1.reverse();
        byte zero=0;

        for (int i = 0; i < 64; i++) {
            if (bit1.charAt(i) == '0')
                zero++;
            else
                break;
        }
        return zero;
    }


    private long CalculateFo(ArrayList<BitSet> bitSets, int R) {

        ArrayList<Long> allbucketCount= new ArrayList<>();

        for(var  Tw :  bitSets) {
            //count were in bucket bits are 0 out of total R
            long w =   Math.abs(Tw.cardinality());
            allbucketCount.add(w);

        }

        //find bucket which has value more nearer to R/2;
        var filteredBuckets =  allbucketCount
                .stream().filter(z->z!=0)
                .mapToLong(y->y).toArray();

        var hashValues = new HashMap<Long,Integer>();
        final int[] counter = {0};

        Arrays.stream(filteredBuckets)
                .forEach(val-> hashValues.put(val,counter[0]++));

        var res =Arrays.stream(filteredBuckets).sorted().toArray();
        var val = res[res.length/2]; //median of the R buckets the one which has median number of bit 1 set
        var w = hashValues.get(val);

        var p0 = (R-bitSets.get(w).cardinality()) *1.0/R;
        var F0d =  (Math.log(p0)) / (Math.log( 1.0- (1.0/R) ));

        var w2 = Math.pow(2,w);

        return (long) (  w2 * F0d );

    }

    static double median(long[] values) {
        Arrays.sort(values);
        double median;
        int totalElements = values.length;
        if (totalElements % 2 == 0) {
            long sumOfMiddleElements = values[totalElements / 2] +
                    values[totalElements / 2 - 1];
            median = ((double) sumOfMiddleElements) / 2;
        } else {
            median = (double) values[values.length / 2];
        }
        return median;
    }




}
