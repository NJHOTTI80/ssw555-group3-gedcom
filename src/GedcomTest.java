import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.GregorianCalendar;
import java.util.Vector;

public class GedcomTest {

	@Test
	public void testAllErrorsAndAnomaliesAreOutput() {
		Anomaly a = new Anomaly(1, "married to sibling");
		Error e = new Error(3, "invalid tag");
		
		ProblemList pl = new ProblemList();
		
		pl.add(a);
		pl.add(e);
			
		assertEquals(2, pl.size());
		assertEquals(1, pl.get(1).getLineNumber());
		assertEquals(3, pl.get(3).getLineNumber());
		
		assertEquals("married to sibling", pl.get(1).getMessage());
		assertEquals("invalid tag", pl.get(3).getMessage());
		
		String s = pl.buildOutputString();
		
		assertTrue(s.contains("3"));
		assertTrue(s.contains("1"));
		assertTrue(s.contains("married to sibling"));
		assertTrue(s.contains("invalid tag"));
	}
	
	@Test
	public void testFamilyDataStructure()
	{
		Family t1 = new Family("f0");
		Family t2 = new Family("f1");
		GregorianCalendar marr1 = new GregorianCalendar();
		marr1.set(1975, 5, 11);
		GregorianCalendar marr2 = new GregorianCalendar();
		marr2.set(1965, 12, 13);
		GregorianCalendar div = new GregorianCalendar();
		div.set(1980, 6, 17);

		t1.setHusb("I0");
		t1.setWife("I1");
		t1.addChild("I2");
		t1.addChild("I3");
		t1.addChild("I4");
		t1.setMarriage(marr1);
		
		t2.setHusb("I2");
		t2.setWife("I5");
		t2.addChild("I6");
		t2.setMarriage(marr2);
		t2.setDivorce(div);
		
		Vector<String> t1c = t1.getChildren();
		
		assertEquals(3, t1.getChildSize()); //family 0 has 3 kids
		assertEquals(1, t2.getChildSize()); //family 1 has 1 kid
		assertEquals("I0", t1.getHusb()); //I0 is the husband of family 1
		assertEquals("I5", t2.getWife()); //I5 is the wife of family 1
		assertEquals(null, t1.getDD()); //Family 0 never divorced
		assertTrue(t1.getMD().after(t2.getMD())); //family 0 married after family 1 did
		assertTrue(t2.getMD().before(t2.getDD()));  //family 1 divorced after they were married
		assertTrue(t1c.contains("I2")); //I2 is one of the children of family 0
		assertTrue(t1.getID() != t2.getID()); //family 0 and family 1 are two different families.
	}

}
