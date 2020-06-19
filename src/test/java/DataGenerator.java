import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(MockitoJUnitRunner.class)
public class DataGenerator {


    private TestFileWriter fileWriter;

    public DataGenerator() {

        fileWriter = new TestFileWriter();
    }

    @Test
    public void GenerateData() {

        var res = GeneratorDistinctIPv4Address(200000);
        res.stream().forEach(x-> System.out.println(x));
    }

    public List<String> GenerateIPAddress(long ln, long distinct) {

        var res = GeneratorDistinctIPv4Address((int)distinct);
        return ReturnRandomRepeatedValues(ln,res);
    }

    public List<String> GenerateJobId(long ln, long distinct) {

        var res = GeneratorDistinctJobId((int)distinct);
        return ReturnRandomRepeatedValues(ln,res);

    }

    public List<String> ReturnRandomRepeatedValues(long ln, List<String> distinctValues) {

        var random = new Random();

        ArrayList<String> allValues = new ArrayList<>();

        for(var i=0; i<ln;i++) {

            var index = random.nextInt((int)distinctValues.size()-1);
            allValues.add(distinctValues.get(index));

        }
        return allValues;

    }

    private List<String> GeneratorDistinctJobId(int distinct) {

        Random r = new Random();
        ArrayList<String> jobIds = new ArrayList<>();

        for(var i=0; i<distinct;i++){

            var jobId = Math.abs(r.nextLong());

            jobIds.add(String.valueOf(jobId));
        }

        return jobIds;
    }

    private List<String> GeneratorDistinctIPv4Address(long n) {

        Random r = new Random();
        ArrayList<String> ipAddresses = new ArrayList<>();

        for(var i=0; i<n;i++){

            var ipaddress = r.nextInt(255) + "." + r.nextInt(255) + "." +
                            r.nextInt(255) + "." + r.nextInt(255);

            ipAddresses.add(ipaddress);
        }



        return ipAddresses;
    }

    private List<String> DateRange() {

        ArrayList<String> dates = new ArrayList<>();

        for (var month=1;month<=12;month++) {

            for(var day = 1 ; day <=31; day++ ) {

                var d =  day<10 ? "0" + String.valueOf(day) : String.valueOf(day);
                var m =  month<10 ? "0" + String.valueOf(month) : String.valueOf(month);
                dates.add(d + ":" + m);
            }
        }

        return dates;
    }

    private List<String> ClusterIds() {
        return IntStream.range(0,100)
                .mapToObj(x-> String.valueOf(x))
                .collect(Collectors.toList());
    }
    private List<String> Geography() {
        return IntStream.range(0,10)
                .mapToObj(x-> String.valueOf(x))
                .collect(Collectors.toList());
    }

    @Test
    public void Test100000IpAddress() throws IOException {

        var ipadrTotal = 100000;
        var ipadrDistinct = 60000;

        var jobidsTotal = 10000;
        var jobidsDistinct = 1500;

        var res = CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);

        fileWriter.WriteCsv("100000IpAddr.csv",res);


    }

    public List<String> CompleteDataSet(long ipadrTotal, long ipadrDistinct, long jobidsTotal , long jobidsDistinct) {

        List<String> result = new ArrayList<>();

        var ipaddresses = GenerateIPAddress(ipadrTotal, (int)ipadrDistinct);
        var jobIds = GenerateJobId(jobidsTotal, (int)jobidsDistinct);
        var dates = DateRange();
        var clusterIds = ClusterIds();
        var geos = Geography();

        var random = new Random();


        for(var ipaddr:ipaddresses) {

            var jobId = jobIds.get(random.nextInt((int)jobidsTotal));
            var date = dates.get(random.nextInt(365));
            var clusterId = clusterIds.get(random.nextInt(100));
            var geo = geos.get(random.nextInt(10));

            var value = String.format("%s\t%s\t%s\t%s\t%s",ipaddr,clusterId, jobId, date,geo );

            result.add(value);

        }

        return result;

    }

}
