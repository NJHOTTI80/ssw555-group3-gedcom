import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

//Evaluates whether there is an issue with a given family or individual

public class ProblemFinder {
	private ProblemList pl;
	private Hashtable<String, Family> familyIndex;
	private Hashtable<String, Individual> personIndex;
	private Vector<String> listOfPeople;
	private Vector<String> listOfFams;
	
	public ProblemFinder(){
		pl = new ProblemList();
	}
	
	public ProblemFinder(Hashtable<String, Family> fam, Hashtable<String, Individual> ind, Vector<String> people, Vector<String> families)
	{
		pl = new ProblemList();
		familyIndex = fam;
		personIndex = ind;
		listOfPeople = people;
		listOfFams = families;
	}
	
	public static boolean isBirthDateAfterDeathDate(Individual i){
		for ( GregorianCalendar g : i.getDeathDates() ) {
			if ( i.getBirthDate().after(g) ) {
				return true;
			}
		}
		return false;
	}
	
	public boolean multiDeathCheck(Individual i){
		ArrayList<GregorianCalendar> deaths;
		deaths = i.getDeathDates();
		if(deaths.size() > 1)
			return true;
		return false;
	}
	
	public static boolean isMarriedToMoreThanOnePerson(Individual i){
		return false;
	}

	public static boolean isMarriedToSibling(Hashtable<String, Family> familyIndex, Hashtable<String, Individual> indIndex, Individual ind)
	{
		ArrayList<String> spouses = ind.getAllSpousesIDs(familyIndex);
		ArrayList<String> familyIDsSpouseChildFamily = new ArrayList<String>();
		for(String spouseID: spouses)
		{
			familyIDsSpouseChildFamily.addAll(indIndex.get(spouseID).getFamC());
		}
		Iterator<String> i = ind.getFamC().iterator();
		while(i.hasNext())
		{
			if(familyIDsSpouseChildFamily.contains(i.next()))
				return true;
		}
		return false;
	}
	
	public boolean parentMarriage(Family f, Individual i1, Individual i2)
	{
		return false;
	}
	
	//This function checks all families,  individuals for the errors and anomalies our program supports.
	//Any boolean function above that returns true will add an entry to the problem list in this function.
	//At the end of all error checking, this function will also print the output.
	public void checkAllErrors()
	{
		String output;
		Family f;
		Individual i;
		int k;
		//Check each individual in the file for individual-level errors
		for(k = 0; k < listOfPeople.size(); k++)
		{
			i = personIndex.get(listOfPeople.elementAt(k));
			//Do all error checks on i
			
		}
		
		//Check each family in the file for family-level errors
		for(k = 0; k < listOfFams.size(); k++)
		{
			f = familyIndex.get(listOfFams.elementAt(k));
			//Do all error checks on f
			
		}
		
		output = pl.buildOutputString();
	}
}
