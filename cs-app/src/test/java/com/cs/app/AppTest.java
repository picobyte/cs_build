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
    public void ProjectTomlTest()
    {
        InputStream is = AppTest.class.getResourceAsStream("/Project.toml");
        assertTrue("Project.toml not found?", is != null);
        Toml toml = new Toml().read(is);

        for (String k: new String[]{"IntroOne.Header", "IntroOne.Tutorial", "IntroOne.Copyright",
                "CampaignMenu.CustomChosen", "CampaignMenu.EasyMode",
                "ImportMenu.NoRosters", "ImportMenu.GotRosters"}) {
            assertTrue(k, toml.getString(k) != null);
        }
        // this may change..
        String test = "\n\n[separator]\n\nFound the following importable rosters in directory.  Which would you like to import?";
        String test2 = toml.getString("ImportMenu.GotRosters");
        assertTrue(String.format("'%s'\n!=\n'%s'", test, test2), test.equals(test2));
    }

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

    @Test
    public void WorldStateTomlTest()
    {
        InputStream is = AppTest.class.getResourceAsStream("/WorldState.toml");
        assertTrue("WorldState.toml not found?", is != null);
        Toml toml = new Toml().read(is);

        String base = "printCapturedLine.";
        for (String k: new String[]{"Impregnation.NoMoralityInnocence[2]", "Hypnotized.LowInnocenceDignity[2]",
                "Hypnotized.NoInnocenceDignity[2]", "Drained.ConfidenceMorality[8]"}) {
            assertTrue(base + k, toml.getString(base + k) != null);
        }
        // this may change..
        String test = "To think I was such a selfish person after all...";
        String test2 = toml.getString(base + "Drained.ConfidenceMorality[8]");
        assertTrue(String.format("'%s'\n!=\n'%s'", test, test2), test.equals(test2));
    }

}
