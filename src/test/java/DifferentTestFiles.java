import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DifferentTestFiles {

    private DataGenerator dataGenerator;
    private TestFileWriter fileWriter;

    public DifferentTestFiles() {

        dataGenerator = new DataGenerator();
        fileWriter = new TestFileWriter();
    }

    @Test
    public void Test100000IpAddress() throws IOException {

        var ipadrTotal = 100000;
        var ipadrDistinct = 70000;

        var jobidsTotal = 50000;
        var jobidsDistinct = 30000;

        var res = dataGenerator.CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);
        fileWriter.WriteTsv("100000IpAddr.tsv",res);

    }

    @Test
    public void Test1000000IpAddress() throws IOException {

        var ipadrTotal = 1000000;
        var ipadrDistinct = 500000;

        var jobidsTotal = 20000;
        var jobidsDistinct = 15000;

        var res = dataGenerator.CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);
        fileWriter.WriteTsv("1000000IpAddr.tsv",res);

    }


    @Test
    public void Test2000000IpAddress() throws IOException {

        var ipadrTotal = 2000000;
        var ipadrDistinct = 250000;

        var jobidsTotal = 100000;
        var jobidsDistinct = 15000;

        var res = dataGenerator.CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);
        fileWriter.WriteTsv("2000000IpAddr.tsv",res);

    }

    @Test
    public void Test5000000IpAddress() throws IOException {

        var ipadrTotal = 5000000;
        var ipadrDistinct = 1500000;

        var jobidsTotal = 1000000;
        var jobidsDistinct = 150000;

        var res = dataGenerator.CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);
        fileWriter.WriteTsv("5000000IpAddr.tsv",res);

    }

    @Test
    public void Test10000000IpAddress() throws IOException {

        var ipadrTotal = 10000000;
        var ipadrDistinct = 2500000;

        var jobidsTotal = 1000000;
        var jobidsDistinct = 150000;

        var res = dataGenerator.CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);
        fileWriter.WriteTsv("10000000IpAddr.tsv",res);

    }

    @Test
    public void Test50000000IpAddress() throws IOException {

        var ipadrTotal = 50000000;
        var ipadrDistinct = 25000000;

        var jobidsTotal = 100000;
        var jobidsDistinct = 15000;

        var res = dataGenerator.CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);
        fileWriter.WriteTsv("50000000IpAddr.tsv",res);

    }

    @Test
    public void Test1000IpAddress() throws IOException {

        var ipadrTotal = 1000;
        var ipadrDistinct = 600;

        var jobidsTotal = 500;
        var jobidsDistinct = 400;

        var res = dataGenerator.CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);

        fileWriter.WriteTsv("1000IpAddr.tsv",res);

    }

    @Test
    public void Test10000IpAddress() throws IOException {

        var ipadrTotal = 10000;
        var ipadrDistinct = 6000;

        var jobidsTotal = 7000;
        var jobidsDistinct = 6000;

        var res = dataGenerator.CompleteDataSet(ipadrTotal,  ipadrDistinct,  jobidsTotal ,  jobidsDistinct);
        fileWriter.WriteTsv("10000IpAddr.tsv",res);

    }

}
