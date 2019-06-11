package g.shuan.combattracker;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

public class EditCreatureTest {
    private DataManager db;

    @Before
    public void setUp() throws Exception {
        db.onUpgrade(db.getWritableDatabase(),1,2);
    }

    @After
    public void tearDown() throws Exception {
    }
}