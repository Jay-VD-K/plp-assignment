package lexer_test;


public interface IPLPLexer {
	
	IPLPToken nextToken() throws LexicalException;
}
