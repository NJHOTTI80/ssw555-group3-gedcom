import static org.junit.Assert.*;

import org.junit.Test;


public class GedcomTest {

	@Test
	public void testAllErrorsAndAnomaliesAreOutput() {
		Anomaly a = new Anomaly(1, "married to sibling");
		Error e = new Error(3, "invalid tag");
		
		ProblemList pl = new ProblemList();
		
		pl.add(a);
		pl.add(e);
			
		assertEquals(2, pl.size());
		assertEquals(1, pl.get(0).getLineNumber());
		assertEquals(3, pl.get(1).getLineNumber());
		
		assertEquals("married to sibling", pl.get(0).getMessage());
		assertEquals("invalid tag", pl.get(1).getMessage());
		
		String s = pl.buildOutputString();
		
		assertTrue(s.contains("3"));
		assertTrue(s.contains("1"));
		assertTrue(s.contains("married to sibling"));
		assertTrue(s.contains("invalid tag"));
		
		
	}

}
