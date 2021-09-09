package lexer_test;



@SuppressWarnings("serial")
public class LexicalException extends Exception {

	public LexicalException(String message, int line, int charPositionInLine) {
		super( "Lexical Error " + line + ":" + charPositionInLine + "  " + message);
	}

}
