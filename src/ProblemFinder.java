//Evaluates whether there is an issue with a given family or individual

public class ProblemFinder {
	private ProblemList pl;
	
	public ProblemFinder(){
		pl = new ProblemList();
	}
	
	public boolean isBirthDateAfterDeathDate(Individual i){
		return false;
	}
	
	public boolean multiDeathCheck(Individual i){
		return false;
	}
	
	public boolean isMarriedToMoreThanOnePerson(Individual i){
		return false;
	}
	
	public boolean parentMarriage(Family f, Individual i1, Individual i2)
	{
		return false;
	}
}
