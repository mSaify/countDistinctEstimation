package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.FilterType;
import com.countDistinct.estimator.InputReaders.GroupType;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class SketchDataSetBase implements ICountable {

    protected List<CountDistinctSketchAlgorithm> sketches;

    protected CountDistinctDataStructure UnionofDatasets(List<CountDistinctDataStructure> countStructures) {
        CountDistinctDataStructure unionStructure = deepCopy(countStructures.get(0));

        for(var i=1;i<countStructures.size(); i++)
        {
            unionStructure.Union(countStructures.get(i));
        }

        return unionStructure;

    }

    private CountDistinctDataStructure deepCopy(CountDistinctDataStructure ele) {
        return SerializationUtils.clone(ele);

    }



    protected int[] expandValues(String[] filterValues) {

        var start = Integer.parseInt(filterValues[0]);
        var end = Integer.parseInt(filterValues[1]);

        var filterVals = new int[end-start+1];

        var counter=0;
        for(int i=start; i<=end; i++) {

            filterVals[counter++]=i;
        }

        return filterVals;

    }

    @Override
    public long countWithFilter(FilterType filterType, String[] filterValues) {

        if(filterType == FilterType.SingleValue) {
            var sketch = this.sketches.get(Integer.parseInt(filterValues[0]));
            return sketch.EstimateCount();

        }
        else if(filterType == FilterType.MultipleValue || filterType == FilterType.Range) {

            var unionStructures = new ArrayList<CountDistinctDataStructure>();

            for(var filter : filterValues) {

                unionStructures.add(this.sketches
                        .get(Integer.parseInt(filter))
                        .CountDistinctDataStructure);

            }

            return this.UnionofDatasets(unionStructures).EstimateCount();

        }

        return 0;
    }


    @Override
    public void ConstructDataStructure() {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void GroupBy(GroupType val) {

    }
}
