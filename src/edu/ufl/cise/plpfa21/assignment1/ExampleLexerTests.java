package edu.ufl.cise.plpfa21.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExampleLexerTests implements PLPTokenKinds {

	IPLPLexer getLexer(String input) {
		return CompilerComponentFactory.getLexer(input);
	}

	@Test
	public void test0() throws LexicalException {
		String input = """

				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test1() throws LexicalException {
		String input = """
				abc
				  def
				     ghi

				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "abc");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 2);
			String text = token.getText();
			assertEquals(text, "def");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 3);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 5);
			String text = token.getText();
			assertEquals(text, "ghi");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test2() throws LexicalException {
		String input = """
				a123 123a
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "a123");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 5);
			String text = token.getText();
			assertEquals(text, "123");
			int val = token.getIntValue();
			assertEquals(val, 123);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 8);
			String text = token.getText();
			assertEquals(text, "a");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test3() throws LexicalException {
		String input = """
				= == ===
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "=");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EQUALS);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 2);
			String text = token.getText();
			assertEquals(text, "==");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EQUALS);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 5);
			String text = token.getText();
			assertEquals(text, "==");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 7);
			String text = token.getText();
			assertEquals(text, "=");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test4() throws LexicalException {
		String input = """
				a %
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "a");
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}

	@Test
	public void test8() throws LexicalException {
		String input = """
				"abc\'"
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "abc'");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test5() throws LexicalException {
		String input = """
				99999999999999999999999999999999999999999999999999999999999999999999999
				""";
		IPLPLexer lexer = getLexer(input);
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}

	@Test
	public void test6() throws LexicalException {
		String input = """
				a+7;
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.PLUS);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.SEMI);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test7() throws LexicalException {
		String input = """
				int i = i+7;
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_INT);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.PLUS);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int text = token.getIntValue();
			assertEquals(text, 7);

		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.SEMI);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test9() throws LexicalException {
		String input = """
				"abc\n b"
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "abc\n b");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}
	@Test
	public void test20() throws LexicalException {
		String input = """
				abc#
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			String stringValue = token.getText();
			assertEquals(stringValue, "abc");
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}
	
	@Test
	public void test21() throws LexicalException {
		String input = """
				$
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			String stringValue = token.getText();
			assertEquals(stringValue, "$");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}
	
	@Test
	public void test22() throws LexicalException {
		String input = """
				" abc"abc" 
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			String text = token.getText();
			assertEquals(text, "\" abc\"");
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}
	

	@Test
	public void test10() throws LexicalException {
		String input = """
				/* comment */ "abc\n b"
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 14);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "abc\n b");
		}
	}
	@Test
	public void test18() throws LexicalException {
		String input = """
				/
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.DIV);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String stringValue = token.getText();
			assertEquals(stringValue, "/");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}
	@Test
	public void test17() throws LexicalException {
		String input = """
				/* comment */ /*comment2*/ "abc\n b"
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 27);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "abc\n b");
		}
	}
	
	@Test
	public void test19() throws LexicalException {
		String input = """
				/* comment */ "abc\n b"
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 14);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "abc\n b");
		}
	}
	
	@Test
	public void test15() throws LexicalException {
		String input = """
				" string with double" 23 
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			String text = token.getText();
			assertEquals(text, "\" string with double\"");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int text = token.getIntValue();
			assertEquals(text, 23);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}
	
	@Test
	public void test16() throws LexicalException {
		String input = """
				' string with single' 23 
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			String text = token.getText();
			assertEquals(text, "\' string with single\'");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int text = token.getIntValue();
			assertEquals(text, 23);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}
	
	@Test
	public void test14() throws LexicalException {
		String input = """
				/* half comment should throw error 
				""";
		IPLPLexer lexer = getLexer(input);
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}

	@Test
	public void test12() throws LexicalException {
		String input = """
				'abc\n b'
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "abc\n b");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}
	
	@Test
	public void test13() throws LexicalException {
		String input = """
				abc || abcd |abc
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.OR);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 4);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 7);
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}

	@Test
	public void test11() throws LexicalException {
		String input = """
				if abc else def i+=10;
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_IF);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "if");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 3);
			String text = token.getText();
			assertEquals(text, "abc");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_ELSE);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 7);
			String text = token.getText();
			assertEquals(text, "else");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 12);
			String text = token.getText();
			assertEquals(text, "def");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 16);
			String text = token.getText();
			assertEquals(text, "i");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.PLUS);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 17);
			String text = token.getText();
			assertEquals(text, "+");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 18);
			String text = token.getText();
			assertEquals(text, "=");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 19);
			int text = token.getIntValue();
			assertEquals(text, 10);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.SEMI);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

}