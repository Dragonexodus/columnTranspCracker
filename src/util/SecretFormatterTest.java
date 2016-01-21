package util;

import static org.junit.Assert.*;

import org.junit.Test;

public class SecretFormatterTest {


    @Test
    public void testGetSecret() {

        Transposition t = new Transposition("0,1,2,3,4");
        BlockMatrix bm = new BlockMatrix(new String("HalloWelt").toCharArray(), t, false);
        bm.transpose();
        SecretFormatter sf = new SecretFormatter(bm, 5, 10);

        assertEquals("HaWlellot", sf.getSecret());

    }

}
