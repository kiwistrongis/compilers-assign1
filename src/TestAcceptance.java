// standard library imports
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
//import java.util.Vector;

public class TestAcceptance {
	private static PrintStream out = System.out;

	// entry-point method
	public static void main( String[] args){
		//get dfa filename
		String dfa_fname = "data/dfa.txt";
		if( args.length > 0)
			dfa_fname = args[0];

		// get program filename
		String program_fname = "data/test.txt";
		if( args.length > 1)
			program_fname = args[1];

		// compile automata
		Automata auto = null;
		try{ auto = new Automata( dfa_fname);}
		catch( FileNotFoundException exception){
			out.printf( "dfa file %s not found!\n", dfa_fname);
			System.exit( 1);}

		// open program file
		BufferedReader program_reader = null;
		try {
			program_reader = new BufferedReader(
				new FileReader( program_fname));}
		catch( FileNotFoundException exception){
			out.printf( "program file %s not found!\n", program_fname);
			System.exit( 1);}

		// read program
		String program = null;
		try { program = program_reader.readLine();}
		catch( IOException exception){
			out.printf(
				"io error reading program file %s: %s\n",
				program_fname, exception);}
		out.printf( "program: {%s}\n", program);

		// run program, get result
		auto.run( program);
		char state = auto.state();
		boolean result = auto.result();

		// output results
		out.printf( "program state: %s\n", state);
		out.printf( "program state: %s\n", result);}

	// sub-classes
	private static class Automata {
		public Automata( String filename)
				throws FileNotFoundException {
			BufferedReader in = new BufferedReader(
				new FileReader( filename));}

		private void run( String program){}
		private char state(){
			return 'a';}
		private boolean result(){
			return true;}
	}
}