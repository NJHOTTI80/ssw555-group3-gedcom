import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;


public class Individual 
{
	private String id;
	private String name;
	private String sex;
	private GregorianCalendar birthDate;
	private ArrayList<GregorianCalendar> deathDates;
	private HashSet<String> famS;
	private HashSet<String> famC;
	
	public Individual(String id)
	{
		this.id = id;
		this.deathDates = new ArrayList<GregorianCalendar>();
		this.famS = new HashSet<String>();
		this.famC = new HashSet<String>();
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getSex()
	{
		return this.sex;
	}
	
	public void setSet(String sex)
	{
		this.sex = sex;
	}
	public GregorianCalendar getBirthDate()
	{
		return this.birthDate;
	}
	
	public void setBirthDate(GregorianCalendar birthDate)
	{
		this.birthDate = birthDate;
	}
	
	public ArrayList<GregorianCalendar> getDeathDates()
	{
		return this.deathDates;
	}
	
	public void addDeathDate(GregorianCalendar deathDate)
	{
		this.deathDates.add(deathDate);
	}
	
	public HashSet<String> getFamS()
	{
		return this.famS;
	}
	
	public void addFamS(String famS)
	{
		this.famS.add(famS);
	}
	
	public HashSet<String> getFamC()
	{
		return this.famC;
	}
	
	public void addFamC(String famC)
	{
		this.famC.add(famC);
	}
	
	
}
