/*This class is responsible for translating the input GEDCOM file into data structures.
 * The data structures will then be utilized to parse the file for errors.
 * All errors will be logged as either Error or Anomaly depending on the nature of what is found.
 * This will eventually be output on the command line.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.GregorianCalendar;
import java.lang.Integer;

public class GedReader {
	private boolean birthD = false;
	private boolean deathD = false;
	private boolean marrD = false;
	private boolean divD = false;
	private Hashtable<String, Family> familyIndex;
	
	public void readGED(String filename){
		String line;
		File input;
		Scanner rf;
		String id;
		Family curF = null;
		boolean parseInd = false;
		boolean parseFam = false;
		familyIndex = new Hashtable<String, Family>(50);
		
		try{
			input = new File(filename);
			if(input != null)
			{
				rf = new Scanner(input);
				while(rf.hasNextLine()){
					line = rf.nextLine();
					if(line.charAt(0) == '0')
					{
						if(parseInd == true){
							//Enter curIndividual in the hashtable, set flag to false
							parseInd = false;
						}
						else if(parseFam == true){
							familyIndex.put(curF.getID(), curF);
							parseFam = false;
						}
						if(line.indexOf("FAM") != -1){
							parseFam = true;
							parseInd = false;
							id = parseID(line);
							curF = new Family(id);
						}
						else if(line.indexOf("INDI") != -1){
							parseInd = true;
							parseFam = false;
							id = parseID(line);
							//Create new individual
						}
					}
					else{
						if(parseInd == true)
							addIndData(line);
						else if(parseFam == true)
							addFamData(line, curF);
					}
				}
				placeChildren();
			}
		} catch(FileNotFoundException e){
			System.out.println("The file provided was invalid.");
		}
	}
	
	public String parseID(String entry){
		String delim = "[ ]+";
		String[] tokens = entry.split(delim);
		String found = "";
		int i;
		
		for(i = 0; i < tokens.length; i++){
			if(tokens[i].indexOf("@") != -1){
				found = tokens[i];
				i = tokens.length;
			}
		}
			
		return found;
	}
	
	public void addIndData(String entry){
		String delim = "[ ]+";
		String[] tokens = entry.split(delim);
		String tag = tokens[1];
		
		if(tag.equalsIgnoreCase("NAME")){
			//Add name to an existing Individual object
		}else if(tag.equalsIgnoreCase("SEX")){
			//Add gender to an existing Individual object
		}else if(tag.equalsIgnoreCase("BIRT")){
			birthD = true;
		}else if(tag.equalsIgnoreCase("DEAT")){
			deathD = true;
		}else if(tag.equalsIgnoreCase("FAMC")){
			//Add FamC ID to an existing individual object
		}else if(tag.equalsIgnoreCase("FAMS")){
			//Add FamS ID to an existing individual object
		}else if(tag.equalsIgnoreCase("DATE")){
			//Add either Birthday or Deathday to an existing individual object
			//depending on active flag.
		}
	}
	
	public void addFamData(String entry, Family cur){
		String delim = "[ ]+";
		String[] tokens = entry.split(delim);
		String tag = tokens[1];
		int month = 1;
		
		if(tag.equalsIgnoreCase("HUSB")){
			cur.setHusb(tokens[2]);
		}else if(tag.equalsIgnoreCase("WIFE")){
			cur.setWife(tokens[2]);
		}else if(tag.equalsIgnoreCase("CHIL")){
			cur.addChild(tokens[2]);
		}else if(tag.equalsIgnoreCase("MARR")){
			marrD = true;
		}else if(tag.equalsIgnoreCase("DIV")){
			divD = true;
		}else if(tag.equalsIgnoreCase("DATE")){
			if(tokens.length > 3){
				if(tokens[3].equalsIgnoreCase("JAN"))
					month = 1;
				else if(tokens[3].equalsIgnoreCase("FEB"))
					month = 2;
				else if(tokens[3].equalsIgnoreCase("MAR"))
					month = 3;
				else if(tokens[3].equalsIgnoreCase("APR"))
					month = 4;
				else if(tokens[3].equalsIgnoreCase("MAY"))
					month = 5;
				else if(tokens[3].equalsIgnoreCase("JUN"))
					month = 6;
				else if(tokens[3].equalsIgnoreCase("JUL"))
					month = 7;
				else if(tokens[3].equalsIgnoreCase("AUG"))
					month = 8;
				else if(tokens[3].equalsIgnoreCase("SEP"))
					month = 9;
				else if(tokens[3].equalsIgnoreCase("OCT"))
					month = 10;
				else if(tokens[3].equalsIgnoreCase("NOV"))
					month = 11;
				else if(tokens[3].equalsIgnoreCase("DEC"))
					month = 12;
				GregorianCalendar m = new GregorianCalendar(Integer.parseInt(tokens[2]), month, Integer.parseInt(tokens[4]));
				if(marrD == true){
					marrD = false;
					cur.setMarriage(m);
				}
				else if(divD == true){
					divD = false;
					cur.setDivorce(m);
				}	
			}
		}
	}
	
	public void placeChildren(){
		
	}
}

