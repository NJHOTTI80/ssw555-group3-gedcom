
public class Error extends Problem {

	public Error(int lineNum, String msg) {
		super(lineNum, msg);
		
	}
	
	public String toString() {
		String s = "Error\t";
		s += Integer.toString( getLineNumber() );
		s += "\t";
		s += getMessage();
		s += "\n";
				
		return s;
	}

}
