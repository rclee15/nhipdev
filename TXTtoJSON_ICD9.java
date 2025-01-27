import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class TXTtoJSON_ICD9
{
	public static void main(String[] args)
	{
		// Converts ICD-9 codes (Text file) into JSON tables by category
		Scanner read = null;
		PrintWriter pw = null;
		
		// Reads in ICD-9 codes as a text file
		try 
		{
			read = new Scanner(new File("ICD-9 Codes nh.txt"));
			// Eliminates tabs in reading code
			read.useDelimiter("\t");
		} 
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		// Sets variables for the ICD-9 categories
		String [] prefixes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", 
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "E", "V"};
		String currPrefix = prefixes [0];
		int pfCounter = 0;
		
		// Create the first JSON file
		try 
		{
			 pw = new PrintWriter("ICD9_" + currPrefix + ".json", "UTF-8");
		} 
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		
		pw.println("{");
		
		// Creates the rest of the JSON files
		while (read.hasNextLine()) 
		{
			String line = read.nextLine();
			System.out.println(line);
			char prefixLtr = line.charAt(0);
			if (currPrefix.contains(""))
			{
				read.useDelimiter("");
			}
			
			if (currPrefix.indexOf(prefixLtr) < 0) 
			{
				pw.println("}");
				pw.close();
				pfCounter++;
				currPrefix = prefixes [pfCounter];
				
				// Continue creating JSON files for each category until the code has processed
				try 
				{
					pw = new PrintWriter("ICD9_" + currPrefix + ".json", "UTF-8");
				} 
				
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} 
				
				catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
				
				pw.println("{");
			}
			
			Scanner lScan = new Scanner(line);
			lScan.useDelimiter("\t");
			String code = lScan.next();
			pw.println("\t{");
			pw.println("\t\tcode: \"" + code + "\",");
			pw.println("\t},");
		}
		
		pw.println("}");
		pw.close();
	}
}