import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class TXTtoJSON_ICD10 
{
	public static void main(String[] args)
	{
		// Converts ICD-10 codes (Text file) into JSON tables by category
		Scanner read = null;
		PrintWriter pw = null;
		
		// Reads in ICD-10 codes as a text file
		try 
		{
			read = new Scanner(new File("ICD-10 Codes nh.txt"));
			// Eliminates tabs in reading code
			read.useDelimiter("\t");
		} 
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}

		// Sets variables for ICD-10 categories
		String[] alphaPrefixes = {"AB", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "VWXY", "Z"}; 
		String currPrefix = alphaPrefixes[0];
		int pfCounter = 0;
		
		// Creates the first JSON file
		try 
		{
			 pw = new PrintWriter("ICD10_"+currPrefix+".json", "UTF-8");
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
			
			if (currPrefix.indexOf(prefixLtr) < 0) 
			{
				pw.println("}");
				pw.close();
				pfCounter++;
				currPrefix = alphaPrefixes[pfCounter];
				
				// Continue creating JSON files for each category until the code has processed
				try 
				{
					pw = new PrintWriter("ICD10_" + currPrefix + ".json", "UTF-8");
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
			String sDesc = lScan.next();
			String lDesc = lScan.next();
			pw.println("\t{");
			pw.println("\t\tcode: \"" + code + "\",");
			
			// Prints the short and long descriptions of each ICD-10 code
			if (sDesc.charAt(0)== '"') 
			{
				pw.println("\t\tshortDescription: " + sDesc + ",");
				pw.println("\t\tlongDescription: " + lDesc + ",");
			}
			
			else 
			{
				pw.println("\t\tshortDescription: \"" + sDesc + "\",");
				pw.println("\t\tlongDescription: \"" + lDesc + "\",");
			}
				
			pw.println("\t},");
		}
		
		pw.println("}");
		pw.close();
	}
}