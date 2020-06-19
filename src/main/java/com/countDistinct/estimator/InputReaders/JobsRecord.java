package com.countDistinct.estimator.InputReaders;

public class JobsRecord  {

    public String IPAddress;

    public byte ClusterId;

    public long JobSignature;

    public byte date;

    public byte month;

    public byte geography;


    public JobsRecord(String val) {

        var arr = val.split("\t");

        IPAddress = arr[0];

        ClusterId = Byte.parseByte(arr[1]);

        JobSignature = Long.parseLong(arr[2]);

        date = Byte.parseByte(arr[3].split(":")[0]);

        month = Byte.parseByte(arr[3].split(":")[1]);

        geography = Byte.parseByte(arr[4]);

    }
}
