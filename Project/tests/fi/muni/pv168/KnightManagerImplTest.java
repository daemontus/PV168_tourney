package fi.muni.pv168;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Describe the class here.
 *
 * @author David Kizivat
 * @version 0.1
 */
public class KnightManagerImplTest {

    private KnightManagerImpl manager;

    @Before
    public void setUp() {
        manager = new KnightManagerImpl();
    }

    @Test
    public void createKnight() {

        Knight knight = new Knight(null, "TestKnightName", "TestCastle", new Date(0), "TestHeraldry"); //not-null name
        manager.createKnight(knight);
        Long id = knight.getId();

        assertNotNull(id);

        Knight result = manager.getKnightById(knight.getId());
        assertEquals(knight, result);
        assertNotSame(knight, result);
        assertDeepEquals(knight, result);
    }

    @Test
    public void getAllKnights() {

        Knight knight1 = new Knight(null, "TestName", "TestCastle", new Date(0), "TestHeraldry");
        manager.createKnight(knight1);
        Knight knight2 = new Knight(null, "TestName2", "TestCastle2", new Date(1), "TestHeraldry2");
        manager.createKnight(knight2);

        List<Knight> expected = Arrays.asList(knight1, knight2);
        List<Knight> actual = manager.findAllKnights();

        assertDeepEquals(expected, actual);
    }

    @Test
    public void updateKnightName() {

        Knight knight = new Knight(1l, "TestKnight", "TestCastle", new Date(0), "TestHeraldry");
        Knight knight2 = new Knight(2l, "TestKnight2", "TestCastle2", new Date(1), "TestHeraldry2");
        manager.createKnight(knight);
        manager.createKnight(knight2);
        Long id = knight.getId();

        knight.setName(knight2.getName());   //edit name
        manager.updateKnight(knight);
        assertDeepEquals(knight, manager.getKnightById(id));


        //are other records affected?
        assertDeepEquals(knight2, manager.getKnightById(knight2.getId()));
    }

    @Test
    public void updateKnightCastle() {

        Knight knight = new Knight(1l, "TestKnight", "TestCastle", new Date(0), "TestHeraldry");
        Knight knight2 = new Knight(2l, "TestKnight2", "TestCastle2", new Date(1), "TestHeraldry2");
        manager.createKnight(knight);
        manager.createKnight(knight2);
        Long id = knight.getId();

        knight.setCastle(knight2.getCastle());   //edit castle
        manager.updateKnight(knight);
        assertDeepEquals(knight, manager.getKnightById(id));

        //are other records affected?
        assertDeepEquals(knight2, manager.getKnightById(knight2.getId()));
    }

    @Test
    public void updateKnightBorn() {

        Knight knight = new Knight(1l, "TestKnight", "TestCastle", new Date(0), "TestHeraldry");
        Knight knight2 = new Knight(2l, "TestKnight2", "TestCastle2", new Date(1), "TestHeraldry2");
        manager.createKnight(knight);
        manager.createKnight(knight2);
        Long id = knight.getId();

        knight.setBorn(knight2.getBorn());   //edit born
        manager.updateKnight(knight);
        assertDeepEquals(knight, manager.getKnightById(id));

        //are other records affected?
        assertDeepEquals(knight2, manager.getKnightById(knight2.getId()));
    }

    @Test
    public void updateKnightHeraldry() {

        Knight knight = new Knight(1l, "TestKnight", "TestCastle", new Date(0), "TestHeraldry");
        Knight knight2 = new Knight(2l, "TestKnight2", "TestCastle2", new Date(1), "TestHeraldry2");
        manager.createKnight(knight);
        manager.createKnight(knight2);
        Long id = knight.getId();

        knight.setHeraldry(knight2.getHeraldry()); //set not null points
        manager.updateKnight(knight);
        assertDeepEquals(knight, manager.getKnightById(id));

        //are other records affected?
        assertDeepEquals(knight2, manager.getKnightById(knight2.getId()));
    }



    @Test
    public void deleteKnight() {


        Knight knight = new Knight(1l, "TestKnight", "TestCastle", new Date(0), "TestHeraldry");
        manager.createKnight(knight);
        Knight knight2 = new Knight(2l, "TestKnight2", "TestCastle2", new Date(1), "TestHeraldry2");
        manager.createKnight(knight2);

        assertNotNull(manager.getKnightById(knight.getId()));
        assertNotNull(manager.getKnightById(knight2.getId()));

        manager.deleteKnight(knight);

        assertNull(manager.getKnightById(knight.getId()));
        assertNotNull(manager.getKnightById(knight2.getId()));

    }

    private static void assertDeepEquals(List<Knight> expected, List<Knight> actual) {
        assertEquals(expected.size(), actual.size());
        Collections.sort(expected, idComparator);
        Collections.sort(actual, idComparator);
        for (int i=0; i<actual.size(); i++) assertDeepEquals(expected.get(i), actual.get(i));
    }

    private static void assertDeepEquals(Knight expected, Knight actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCastle(), actual.getCastle());
        assertEquals(expected.getBorn(), actual.getBorn());
        assertEquals(expected.getHeraldry(), actual.getHeraldry());
    }

    private static Comparator<Knight> idComparator = new Comparator<Knight>() {
        @Override
        public int compare(Knight knight, Knight knight2) {
            if (knight.getId() == null || knight2.getId() == null) throw new IllegalArgumentException("Cant compare null ids");
            return knight.getId().compareTo(knight2.getId());
        }
    };
}
