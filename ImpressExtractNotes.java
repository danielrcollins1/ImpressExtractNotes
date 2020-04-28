import java.io.*; 
import java.util.Scanner;

/******************************************************************************
*  Extract presenter notes for Impress HTML export.
*
*  @author   Daniel R. Collins (dcollins@superdan.net)
*  @since    2020-04-28
******************************************************************************/

public class ImpressExtractNotes {

	//-----------------------------------------------------------------
	//  Constants
	//-----------------------------------------------------------------

	/** Tag for next slide title */
	final static String TITLE_TAG = "h1";
	
	/** Tag for notes section */
	final static String NOTES_TAG = "h3";

	/** Symbol for underlining titles */
	final static char UNDERLINE = '-';

	//-----------------------------------------------------------------
	//  Fields
	//-----------------------------------------------------------------

	/** Input file name */
	String inFilename;
	
	/** Output filename */
	String outFilename;

	/** Exit after args? */
	boolean exitAfterArgs;
	
	//-----------------------------------------------------------------
	//  Methods
	//-----------------------------------------------------------------

	/**
	*  Main method.
	*/
	public static void main (String[] args) throws IOException {
		ImpressExtractNotes extractor = new ImpressExtractNotes();
		extractor.parseArgs(args);
		if (extractor.exitAfterArgs) {
			extractor.printUsage();
		}
		else {
			extractor.convertFile();
		}
	}

	/**
	*  Parse arguments
	*/
	void parseArgs (String[] args) {
		if (args.length < 2) {
			exitAfterArgs = true;
		}	
		else {
			inFilename = args[0];
			outFilename = args[1];
		}
	}

	/**
	*  Print usage
	*/
	void printUsage () {
		System.out.println("Usage: ImpressExtractArgs infile outfile");
		System.out.println("  infile is an HTML file exported from LO Impress");
		System.out.println("  outfile is a text file of presenter notes");
		System.out.println();
	}

	/**
	*  Convert a file
	*/
	void convertFile () throws IOException {

		// Open files
		Scanner inFile = new Scanner(new File(inFilename), "UTF-8");
      PrintWriter outFile = new PrintWriter(new FileWriter(outFilename));
		
		// Other variables
		String lastTitle = "";
		boolean inNotes = false;

		// Process the file
		while (inFile.hasNext()) {
			String s = inFile.nextLine();
			if (s.startsWith("<" + TITLE_TAG)) {
				lastTitle = cleanLine(s);
				if (inNotes) {
					outFile.println();
					inNotes = false;
				}
			}
			else if (s.startsWith("<" + NOTES_TAG)) {
				outFile.println(lastTitle);
				outFile.println(new String(new char[lastTitle.length()])
					.replace('\0', UNDERLINE));
				inNotes = true;
			}
			else if (inNotes) {
				outFile.println(cleanLine(s));			
			}
		}
	
		// Close files
		inFile.close();
		outFile.close();
	}

	/**
	*  Clean up a line
	*/
	String cleanLine (String s) {
		s = removeTags(s);
		s = replaceSymbols(s);	
		return s;
	}

	/**
	*  Remove HTML tags from line
	*/
	String removeTags (String s) {
		String out = "";
		boolean inTag = false;;
		for (int i = 0; i < s.length(); i++) {
			char nextChar = s.charAt(i);
			if (nextChar == '<') {
				inTag = true;
			}		
			else if (nextChar == '>') {
				inTag = false;			
			}
			else if (!inTag) {
				out += nextChar;
			}			
		}	
		return out;
	}

	/**
	*  Replace mathy HTML symbols
	*/
	String replaceSymbols (String s) {
		s = s.replace("&amp;", "&");
		s = s.replace("&lt;", "<");
		s = s.replace("&gt;", ">");
		return s;
	}
}

