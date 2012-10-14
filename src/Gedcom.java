import java.io.File;


public class Gedcom {

	public static void main(String args[]) {
		if(args.length != 1)
		{
			System.out.println("Please enter exactly 1 GEDCOM File");
		}
		else
		{
			String fileName = args[0];
			if(!fileName.endsWith(".ged"))
			{
				System.out.println("Please enter a file ending in \".ged\"");
			}
			else
			{
				File file = new File(fileName);
				if(!file.exists())
				{
					System.out.println("File '"+fileName+"' can't be found or doesn't exist");
				}
				else
				{
					GedReader parser = new GedReader();
					parser.readGED(file);
					System.out.println("File parsed!"); //General output, will be replaced in sprint 2.
					//Find errors and anomalies. (Starting in sprint 2)
				}
			}			
		}
	}
}
