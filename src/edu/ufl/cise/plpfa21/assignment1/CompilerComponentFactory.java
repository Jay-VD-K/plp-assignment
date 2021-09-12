package edu.ufl.cise.plpfa21.assignment1;

public class CompilerComponentFactory {

	static IPLPLexer getLexer(String input) {
		return new Lexer(input);
	}
}
