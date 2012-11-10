import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
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
	public void testIndividualDataStructure()
	{
		Individual i1 = new Individual("@I0@");
		Individual i2 = new Individual("@I1@");
		
		GregorianCalendar birthDate1 = new GregorianCalendar();
		GregorianCalendar birthDate2 = new GregorianCalendar();
		GregorianCalendar deathDate1 = new GregorianCalendar();	
		GregorianCalendar deathDate1a = new GregorianCalendar();	
		birthDate1.set(1940, 10, 24);
		birthDate2.set(1960, 5, 30);
		deathDate1.set(2004, 8, 12);
		deathDate1a.set(2004, 8, 13);
		
		i1.setName("Marie Louisee");
		i1.setSex("F");
		i1.setBirthDate(birthDate1);
		i1.addDeathDate(deathDate1);
		i1.addDeathDate(deathDate1a);
		i1.addFamS("@F0@");
		i1.addFamS("@F1@");
		i1.addFamC("@F3@");
		
		i2.setName("John Smith");
		i2.setSex("M");
		i2.setBirthDate(birthDate2);
		i2.addFamC("@F0@");
		
		assertEquals(2, i1.getDeathDates().size());
		assertEquals("John Smith", i2.getName());
		assertEquals("F", i1.getSex());
		GregorianCalendar test = new GregorianCalendar();
		test.set(1960, 5, 30);
		assertEquals(test, i2.getBirthDate());
		assertTrue(i1.getFamS().contains("@F0@"));
		assertTrue(i2.getFamC().contains("@F0@"));
		assertTrue(i1 != i2);
		
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
		assertEquals("I0", t1.getHusb()); //I0 is the husband of family 0
		assertEquals("I5", t2.getWife()); //I5 is the wife of family 1
		assertEquals(null, t1.getDD()); //Family 0 never divorced
		assertTrue(t1.getMD().after(t2.getMD())); //family 0 married after family 1 did
		assertTrue(t2.getMD().before(t2.getDD()));  //family 1 divorced after they were married
		assertTrue(t1c.contains("I2")); //I2 is one of the children of family 0
		assertTrue(t1.getID() != t2.getID()); //family 0 and family 1 are two different families.
	}
	
	@Test
	public void testAnomalyWhenIndMarriedToMoreThanOnePerson() {
		Individual i1 = new Individual("1");
		Individual i2 = new Individual("2");
		Individual i3 = new Individual("3");
		Family f1 = new Family("1");
		Family f2 = new Family("2");
		
		
		Hashtable<String, Individual> indTable = new Hashtable<String, Individual>();
		indTable.put(i1.getId(), i1);
		indTable.put(i2.getId(), i2);
		indTable.put(i3.getId(), i3);
		
		f1.setHusb("1");
		f1.setWife("2");
		i1.addFamS(f1.getID());
		i2.addFamS(f1.getID());
		f1.setMarriage(new GregorianCalendar(2002, 6, 5));
		
		f2.setHusb("1");
		f2.setWife("3");
		i1.addFamS(f2.getID());
		i3.addFamS(f2.getID());
		f2.setMarriage(new GregorianCalendar(2012, 6, 5));
		
		Hashtable<String, Family> famTable = new Hashtable<String, Family>();
		famTable.put(f1.getID(), f1);
		famTable.put(f2.getID(), f2);
		
		assertTrue( ProblemFinder.isMarriedToMoreThanOnePerson(famTable, indTable, i1) );
		//assertTrue( !ProblemFinder.isMarriedToMoreThanOnePerson(i3) );
	}
	
	@Test
	public void testWhenBirthdateLaterThanDeathDate() {
		Individual i1 = new Individual("1");
		Individual i2 = new Individual("2");
		
		i1.setBirthDate(new GregorianCalendar(1950, 10, 31));
		i2.setBirthDate(new GregorianCalendar(1950, 10, 31));
		
		i1.addDeathDate(new GregorianCalendar(1920, 10, 31));
		i2.addDeathDate(new GregorianCalendar(1990, 10, 31));
		
		assertTrue( ProblemFinder.isBirthDateAfterDeathDate(i1) );
		assertTrue( !ProblemFinder.isBirthDateAfterDeathDate(i2) );
	}

	@Test
	public void testMultiDeath(){
		ProblemFinder pf = new ProblemFinder();
		Individual test1 = new Individual("3");
		Individual test2 = new Individual("42");
		
		test1.addDeathDate(new GregorianCalendar(1937, 6, 12));
		test1.addDeathDate(new GregorianCalendar(1937, 12, 6));
		test2.addDeathDate(new GregorianCalendar(1918, 6, 6));
		
		assertTrue(pf.multiDeathCheck(test1));
		assertTrue(!pf.multiDeathCheck(test2));
	}
	
	@Test
	public void testMarriageToParent(){
		Hashtable<String, Family> familyIndex = new Hashtable<String, Family>(50);
		Hashtable<String, Individual> personIndex = new Hashtable<String, Individual>(200);
		Vector<String> listOfPeople = new Vector<String>(200);
		Vector<String> listOfFams = new Vector<String>(50);
		
		Individual test1 = new Individual("5");
		Individual test2 = new Individual("6");
		Individual test3 = new Individual("7");
		Individual test4 = new Individual("8");
		Individual test5 = new Individual("9");
		Family testf1 = new Family("1");
		Family testf2 = new Family("2");
		Family testf3 = new Family("3");
		
		test1.addFamS("1");
		test1.addFamS("2");
		test2.addFamS("1");
		test3.addFamC("1");
		test3.addFamS("1");
		test4.addFamS("3");
		test5.addFamS("3");
		test1.setSex("M");
		test2.setSex("F");
		test3.setSex("F");
		test4.setSex("M");
		test5.setSex("F");
		testf1.setHusb("5");
		testf1.setWife("6");
		testf2.setHusb("5");
		testf2.setWife("7");
		testf3.setHusb("8");
		testf3.setWife("9");
		testf1.addChild(test3.getId());
		
		personIndex.put(test1.getId(), test1);
		personIndex.put(test2.getId(), test2);
		personIndex.put(test3.getId(), test3);
		personIndex.put(test4.getId(), test4);
		personIndex.put(test5.getId(), test5);
		familyIndex.put(testf1.getID(), testf1);
		familyIndex.put(testf2.getID(), testf2);
		familyIndex.put(testf3.getID(), testf3);
		listOfPeople.add(test1.getId());
		listOfPeople.add(test2.getId());
		listOfPeople.add(test3.getId());
		listOfPeople.add(test4.getId());
		listOfPeople.add(test5.getId());
		listOfFams.add(testf1.getID());
		listOfFams.add(testf2.getID());
		listOfFams.add(testf3.getID());
		
		ProblemFinder pf = new ProblemFinder(familyIndex, personIndex, listOfPeople, listOfFams);
		
		assertTrue(!pf.parentMarriage(test3)); //Daughter married to father.  Should not be true because the function checks if the parent was married to their child, not the other way around.
		assertTrue(pf.parentMarriage(test1)); //Father married to daughter.
		assertTrue(!pf.parentMarriage(test4)); //Normal
		assertTrue(!pf.parentMarriage(test2)); //Spouse of someone who married daughter in another marriage.
	}
	
	@Test
	public void testMarriageToSibling()
	{
		Family fam1 = new Family("1");
		Family fam2 = new Family("2");
		
		Individual ind1 = new Individual("1");
		Individual ind2 = new Individual("2");
		
		ind1.setSex("M");
		
		ind1.addFamC("2");
		ind2.addFamC("2");
		
		fam1.setHusb(ind1.getId());
		fam1.setWife(ind2.getId());
		
		fam2.addChild(ind1.getId());
		fam2.addChild(ind2.getId());
		
		Hashtable<String, Individual> indTable = new Hashtable<String, Individual>();
		indTable.put(ind1.getId(), ind1);
		indTable.put(ind2.getId(), ind2);
		
		Hashtable<String, Family> famTable = new Hashtable<String, Family>();
		famTable.put(fam1.getID(), fam1);
		famTable.put(fam2.getID(), fam2);
		
		assertTrue(ProblemFinder.isMarriedToSibling(famTable, indTable, ind1));	
	}
	
	@Test
	public void testWrongSpouseClassification()
	{
		Hashtable<String, Family> familyIndex = new Hashtable<String, Family>();
		Hashtable<String, Individual> personIndex = new Hashtable<String, Individual>(200);
		Vector<String> listOfPeople = new Vector<String>(200);
		Vector<String> listOfFams = new Vector<String>(50);
		
		Family fam = new Family("F1");
		
		Individual ind1 = new Individual("1");
		Individual ind2 = new Individual("2"); 
		
		ind1.setSex("M");
		ind2.setSex("F");
		
		ind1.addFamS(fam.getID());
		ind2.addFamS(fam.getID());
		
		fam.setWife(ind1.getId());
		fam.setHusb(ind2.getId());
		
		familyIndex.put(fam.getID(), fam);
		
		ProblemFinder pf = new ProblemFinder(familyIndex, personIndex, listOfPeople, listOfFams);
		
		assertTrue(pf.wrongSpouseClassification(ind1));
		assertTrue(pf.wrongSpouseClassification(ind2));
		
	}
}
