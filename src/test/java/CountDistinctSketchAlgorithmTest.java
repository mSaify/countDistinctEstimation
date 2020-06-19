

import com.countDistinct.estimator.CountDistinctDataStructure;
import com.countDistinct.estimator.CountDistinctSketchAlgorithm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CountDistinctSketchAlgorithmTest {


    @Test
    public void TestTrailingZeros() {

        var res = new CountDistinctDataStructure();

        Assert.assertEquals(2,res.TrailingZeros(4));
        Assert.assertEquals(0, res.TrailingZeros( (long)Math.pow(2,63)));
        Assert.assertEquals(63, res.TrailingZeros( (long)Math.pow(2,63)+1));
    }


    @Test
    public void TestSketchUpdate() {

        var res = new CountDistinctDataStructure();


        //val z = 1024 and R=2^5
        //expected w=11 i=0
        var exp1 = res.SketchUpdateValues(1024, (int)Math.pow(2,5));
        Assert.assertEquals(exp1[0].longValue(),11);
        Assert.assertEquals(exp1[1].longValue(),0);

        //val z = 63 and R=2^20
        //expected w=11 i=0
        var hashVal = (long)Math.pow(2,63);

        var exp2 = res.SketchUpdateValues(hashVal, (int)Math.pow(2,20));
        Assert.assertEquals(1,exp2[0].longValue());

    }

    @Test
    public void ValidateSketchWithJob() {

        var R = (int)Math.pow(2,10);

        var hashFunctions = 2;

        var jobIds = new DataGenerator().GenerateJobId(1000, 250);

        var algo = new CountDistinctSketchAlgorithm(R,hashFunctions);

        algo.GenerateCountDistinctSketch(jobIds);

        var res = algo.EstimateCount();

        System.out.println("count distinct " + res );

       Assert.assertTrue(32<res && res <256);

    }

}


