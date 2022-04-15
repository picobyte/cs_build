package com.cs.app;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.io.InputStream;
import com.moandjiezana.toml.Toml;
import java.util.List;

/**
 * Unit test for toml strings.
 */
public class AppTest 
{
    @Test
    public void ChosenTomlTest()
    {
        InputStream is = AppTest.class.getResourceAsStream("/Chosen.toml");
        assertTrue("Chosen.toml not found?", is != null);
        Toml toml = new Toml().read(is);

        // true positive tests to confirm the tests are working..
        assertTrue("Foobar", toml.getTable("Foobar") == null);

        String base = "DoubleBroadcast.MoreConfident.Undefiled.";
        for (String k: new String[]{"DignityModest[5]", "MoralityStruggle[5]", "Friendly[1]", "OtherMoralityFriendly[5]"}) {
            assertTrue(base + k, toml.getString(base + k) != null);
        }
        // this may change..
        String test = "[mainName] continues struggling to free [himHer]self, paying no heed to the Thralls who are filming [hisHer] continued failure.  ";
        String test2 = toml.getString(base + "DignityModest[3]");
        assertTrue(String.format("'%s'\n!=\n'%s'", test, test2), test.equals(test2));
    }
}
