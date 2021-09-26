package edu.ufl.cise.plpfa21.assignment1;

import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import edu.ufl.cise.plpfa21.assignment2.Parser;

public class CompilerComponentFactory {

	static IPLPLexer getLexer(String input) {
		return new Lexer(input);
	}
	
    public static IPLPParser getParser(String input) {
      	 //Implement this in Assignment 2
      	 //Your parser will create a lexer.
    	return new Parser(new Lexer(input));
       }

}
