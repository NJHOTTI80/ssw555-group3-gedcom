import java.util.GregorianCalendar;

//Evaluates whether there is an issue with a given family or individual

public class ProblemFinder {
	private ProblemList pl;
	
	public ProblemFinder(){
		pl = new ProblemList();
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
		return false;
	}
	
	public static boolean isMarriedToMoreThanOnePerson(Individual i){
		return false;
	}
	
	public boolean parentMarriage(Family f, Individual i1, Individual i2)
	{
		return false;
	}
}
