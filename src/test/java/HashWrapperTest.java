


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.Random;

import com.countDistinct.estimator.HashWrapper;


@RunWith(MockitoJUnitRunner.class)
public class HashWrapperTest {

    String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    @Test
    public void HashTest() {

        HashWrapper hashFunctions = new HashWrapper(10);
        for(int i=0;i<10;i++) {
            Assert.assertNotNull(hashFunctions.getHashValue(i,generateRandomChars(candidateChars, 17)));
        }
    }


    public static String generateRandomChars(String candidateChars, int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }

        return sb.toString();
    }
}
