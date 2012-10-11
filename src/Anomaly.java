
/**
 * An anomaly is a problem that can occur
 * in a family tree but is still a problem
 * that needs to be made evident.
 *
 */
public class Anomaly extends Problem {

	public Anomaly(int lineNum, String msg) {
		super(lineNum, msg);
	}

	public String toString() {
		String s = "Anomaly\t";
		s += Integer.toString( getLineNumber() );
		s += "\t";
		s += getMessage();
		s += "\n";
				
		return s;
	}
}
