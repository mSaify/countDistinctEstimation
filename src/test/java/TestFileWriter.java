import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TestFileWriter {



    public void WriteCsv(String fileName, List<String> values) throws IOException {

        FileUtils.writeLines(new File(fileName), values);

    }

    public void WriteTsv(String fileName, List<String> values) throws IOException {

        FileUtils.writeLines(new File(fileName), values);

    }
}
