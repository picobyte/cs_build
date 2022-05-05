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

    @Test
    public void DefaultOverriding()
    {
        // proof of concept toml default value overriding

        InputStream def = AppTest.class.getResourceAsStream("/default.toml");
        InputStream im = AppTest.class.getResourceAsStream("/innocentMale.toml");
        InputStream lim = AppTest.class.getResourceAsStream("/lessInnocentMale.toml");
        InputStream lif = AppTest.class.getResourceAsStream("/lessInnocentFemale.toml");
        InputStream ti = AppTest.class.getResourceAsStream("/tickle.toml");

        assertTrue("default.toml not found?", def != null);
        assertTrue("innocentMale.toml not found?", im != null);
        assertTrue("lessInnocentMale not found?", lim != null);
        assertTrue("lessInnocentFemale not found?", lif != null);
        assertTrue("tickle.toml not found?", ti != null);

        // default has a female specific Pleasured.noStruggle.Orgasm.say
        // for male and innocent levels there are overrides.
        //
        // Secondly, three statements for three injury levels, for both sexes. The more
        // progressive can be overwritten by tickle.toml

        Toml defaults = new Toml().read(def);

        Toml femaleTickleLessInnocent = new Toml(defaults).read(lif);
        femaleTickleLessInnocent = new Toml(femaleTickleLessInnocent).read(ti);

        // lessInnocentFemale start + default end ('.  ')
        String no_inju = "With one last cry of defeat, [mainName] gives in to the urge to orgasm, her intimate folds squeezing down as if lamenting their emptiness.  ";

        String no_inhu_test = femaleTickleLessInnocent.getString("Pleasured.noStruggle.Orgasm.say") +
            femaleTickleLessInnocent.getString("Pleasured.noStruggle.inju.lt3.say");
        assertTrue(String.format("'%s'\n!=\n'%s'", no_inju, no_inhu_test), no_inju.equals(no_inhu_test));

        // lessInnocentFemale start + tickle_inju_eq3 end ('.  Already exhausted[...]')
        String inju_eq3_tickle = "With one last cry of defeat, [mainName] gives in to the urge to orgasm, her intimate folds squeezing down as if lamenting their emptiness.  Already exhausted and gasping for breath, [heShe] can't afford to waste the energy to fight it.  ";

        String inju_eq3_tickle_test = femaleTickleLessInnocent.getString("Pleasured.noStruggle.Orgasm.say") + 
             femaleTickleLessInnocent.getString("Pleasured.noStruggle.inju.eq3.say");

        assertTrue(String.format("'%s'\n!=\n'%s'", inju_eq3_tickle, inju_eq3_tickle_test), inju_eq3_tickle.equals(inju_eq3_tickle_test));
    }
    @Test
    public void SubsetOverriding()
    {
        InputStream mindframes = AppTest.class.getResourceAsStream("/MindFrame.toml");
        assertTrue("MindFrame.toml not found?", mindframes != null);
        Toml mf = new Toml().read(mindframes);

        Toml fem = new Toml(mf.getTable("Default")).read(mf.getTable("Female"));
        Toml male = new Toml(mf.getTable("Default")).read(mf.getTable("Male"));
        Toml futa = new Toml(mf.getTable("Default"));

        String hole = fem.getString("hole");
        assertTrue(String.format("'%s'\n!=\n'%s'", hole, "pussy"), hole.equals("pussy"));

        String organ = fem.getString("organ");
        assertTrue(String.format("'%s'\n!=\n'%s'", organ, "clit"), organ.equals("clit"));


        hole = male.getString("hole");
        assertTrue(String.format("'%s'\n!=\n'%s'", hole, "anus"), hole.equals("anus"));

        organ = male.getString("organ");
        assertTrue(String.format("'%s'\n!=\n'%s'", organ, "penis"), organ.equals("penis"));


        hole = futa.getString("hole");
        assertTrue(String.format("'%s'\n!=\n'%s'", hole, "pussy"), hole.equals("pussy"));

        organ = futa.getString("organ");
        assertTrue(String.format("'%s'\n!=\n'%s'", organ, "penis"), organ.equals("penis"));
    }

}
