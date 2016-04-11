// standard library imports
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class TestAcceptance {
	private static PrintStream out = System.out;
	private static final boolean debug = false;

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
		catch( RuntimeException exception){
			out.printf(
				"error reading dfa file %s: %s\n",
				dfa_fname, exception);
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
		if( program == null)
			program = "";
		if( debug)
			out.printf( "program: {%s}\n", program);

		// run program, get result
		boolean result = auto.run( program);
		int state = auto.state();

		// output results
		if( debug)
			out.printf( "program state: %s\n", state);
		if( debug)
			out.printf( "program result: %s\n", result ? "true": "false");
		if( ! debug)
			out.printf( "%s\n", result ? "YES": "NO");}

	// sub-classes
	private static class Automata {
		// private members
		private int start;
		private int state;
		private TreeSet<Integer> ends;
		private TreeMap<Integer,TreeMap<Character,Integer>> map;

		// constructor
		public Automata( String filename)
				throws FileNotFoundException {
			// init vars
			this.start = 0;
			this.state = start;
			this.ends = new TreeSet<Integer>();
			this.map = new TreeMap<Integer,TreeMap<Character,Integer>>();

			// start reading dfa file
			BufferedReader in = new BufferedReader(
				new FileReader( filename));
			try {
				String start_line = in.readLine();
				try { this.start = Integer.parseInt( start_line);}
				catch( NumberFormatException exception){
					throw new RuntimeException (
						"failed to parse start state line", exception);}
				this.state = this.start;

				// parse ends
				Scanner end_scanner = new Scanner( in.readLine());
				while( end_scanner.hasNextInt())
					this.ends.add( end_scanner.nextInt());

				// parse map lines
				String line;
				while( ( line = in.readLine()) != null){
					Scanner scanner = new Scanner( line);
					try {
						// read edge vars
						int a = scanner.nextInt();
						int b = scanner.nextInt();
						char c = scanner.next().charAt( 0);

						// create edge
						TreeMap<Character,Integer> a_map = map.get( a);
						if( a_map == null)
							a_map = new TreeMap<Character,Integer>();
						a_map.put( c, b);
						map.put( a, a_map);}
					// catch problems
					catch( RuntimeException exception){
						throw new RuntimeException(
							String.format( "failed to parse map line \"%s\"", line),
							exception);}}}
			// catch problems
			catch( IOException exception){
				throw new RuntimeException(
					"io error while reading dfa file", exception);}
			// close file and return
			try { in.close();}
			catch( IOException exception){}}

		// functions
		private boolean run( String program){
			int program_length = program.length();
			for( int i = 0; i < program_length; i++){
				// get instruction
				char c = program.charAt( i);

				// get map for state
				TreeMap<Character,Integer> a_map = this.map.get( state);
				if( a_map == null) return false;

				// get next state
				Integer next = a_map.get( c);
				if( next == null) return false;
				this.state = next.intValue();}
			// return true if in end state
			return ends.contains( this.state);}

		private int state(){
			return this.state;}
	}
}