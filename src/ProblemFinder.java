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
	
	public ProblemFinder(){
		pl = new ProblemList();
	}
	
	public ProblemFinder(Hashtable<String, Family> fam, Hashtable<String, Individual> ind)
	{
		pl = new ProblemList();
		familyIndex = fam;
		personIndex = ind;
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
	
	public static boolean isMarriedToMoreThanOnePerson(Hashtable<String, Family> familyIndex, 
			Hashtable<String, Individual> indIndex, Individual i) {
		if ( i.getFamS().size() <= 1 ) return false;
		else return true;
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
	
	public boolean parentMarriage(Individual i)
	{
		HashSet<String> marrFams = i.getFamS();
		ArrayList<String> spouses = i.getAllSpousesIDs(familyIndex);
		ArrayList<String> allchildren = new ArrayList<String>();
		Iterator<String> f = marrFams.iterator();
		//Gather all children of an individual
		while(f.hasNext())
		{
			allchildren.addAll(familyIndex.get(f.next()).getChildren());	
		}
		//For each spouse of the individual
		for(String spouseID: spouses)
		{
			//If the spouse is the child of an individual, return true.
			if(allchildren.contains(spouseID))
				return true;
		}
		
		return false;
	}
	
	public boolean bornBeforeParents(Individual i)
	{
		String father;
		String mother;
		String fam;
		//Get FamC of i
		HashSet<String> famC = i.getFamC();
		Iterator<String> famID = famC.iterator();
		//Get IDs for father/mother
		if(famID.hasNext())
		{
			fam = famID.next();
			father = familyIndex.get(fam).getHusb();
			mother = familyIndex.get(fam).getWife();
			//Compare the birthdates
			if(i.getBirthDate().before(personIndex.get(father).getBirthDate()) || i.getBirthDate().before(personIndex.get(mother).getBirthDate()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean wrongSpouseClassification(Individual i)
	{		
		String sex = i.getSex();
		HashSet<String> famS = i.getFamS();
		Iterator<String> famIDs = famS.iterator();
		while(famIDs.hasNext())
		{
			Family fam = familyIndex.get(famIDs.next());
			if(sex.equals("M"))
			{
				if(fam.getWife().equals(i.getId()))
					return true;
			}
			else if(sex.equals("F"))
			{
				if(fam.getHusb().equals(i.getId()))
					return true;
			}
		}
		return false;
		
	}

	public boolean isThereNoDivorceRecordForDeadSpuse(Individual person) {
		if ( person.isDead() ) {
			for ( String s : person.getFamS() ) {
				if ( familyIndex.get(s).getDD() == null ) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean marriageToDeadPerson(Individual person)
	{
		for(String s: person.getFamS())
		{
			Family fam = familyIndex.get(s);
			if(person.getSex().equals("M"))
			{
				Individual wife = personIndex.get(fam.getWife());
				if(wife.isDead())
				{
					return wife.getDeathDates().get(0).before(fam.getMD());
				}
			}
			else
			{
				Individual husb = personIndex.get(fam.getHusb());
				if(husb.isDead())
				{
					return husb.getDeathDates().get(0).before(fam.getMD());
				}
			}
		}
		return false;
	}
	
}
