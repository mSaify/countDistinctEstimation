package com.countDistinct.estimator.InputReaders;

public class Query {

    public ColumnType columnType;

    public FilterType filterType;

    public GroupType groupBy;

    public String[] filterValues;

    public String originalQuery;

    public String querydetails;

    public Query(String query) {

        originalQuery = query;
        var arr = query.split("\t");

        columnType =  getColumnType(arr[0]);
        groupBy = getGroupType(arr[0]);
        filterType =  getFilterType(arr[0]);

        filterValues = new String[arr.length-1];

        for(int i=1; i<arr.length; i++) {

            filterValues[i-1]=arr[i];
        }

        this.querydetails =  ConstantQueryExplanation.get(Integer.parseInt(arr[0]), filterValues,filterType);

    }


    private ColumnType getColumnType(String val) {

        if(val.equals("1") || val.equals("8") || val.equals("10"))
            return ColumnType.IPAddress;
        else
            return ColumnType.JobSignature;
    }

    private FilterType getFilterType(String val) {

        if(val.equals("9") || val.equals("10"))
            return FilterType.MultipleValue;
        else if(val.equals("7") || val.equals("8") || val.equals("5") || val.equals("3"))
            return FilterType.SingleValue;
        else if(val.equals("4") || val.equals("6"))
            return FilterType.Range;

        return FilterType.None;
    }

    private GroupType getGroupType(String val) {

        if(val.equals("7") || val.equals("8") || val.equals("9") || val.equals("10"))
            return GroupType.Geogrpahy;
        else if (val.equals("5") || val.equals("6"))
            return GroupType.Month;
        else if (val.equals("3") || val.equals("4"))
            return GroupType.ClusterId;

        return GroupType.None;
    }
}
