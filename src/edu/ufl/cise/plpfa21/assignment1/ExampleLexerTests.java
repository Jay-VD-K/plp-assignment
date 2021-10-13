package edu.ufl.cise.plpfa21.assignment1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

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
				INT i = i+7;
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
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test11() throws LexicalException {
		String input = """
				IF abc ELSE def i+=10;
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
			assertEquals(text, "IF");
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
			assertEquals(text, "ELSE");
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
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 21);
			String text = token.getText();
			assertEquals(text, ";");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
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
			assertEquals(kind, Kind.OR);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 4);
			String text = token.getText();
			assertEquals(text, "||");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 7);
			String text = token.getText();
			assertEquals(text, "abcd");
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
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
	public void test15() throws LexicalException {
		String input = """
				" string with double" 23
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
			String text = token.getText();
			assertEquals(text, "\" string with double\"");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 22);
			int text = token.getIntValue();
			assertEquals(text, 23);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 24);
		}
	}

	@Test
	public void test16() throws LexicalException {
		String input = """
				' string with single'23
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
			String text = token.getText();
			assertEquals(text, "\' string with single\'");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 21);
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
	public void test17() throws LexicalException {
		String input = """
				/* comment *//*comment2*/ "abc\n b"
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 26);
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
				"

				"
				xxx
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 4);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
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
				'abc"def'
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			String text = token.getText();
			assertEquals(text, "\'abc\"def\'");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test23() throws LexicalException {
		String input = """
				"This is a string""And another string"
				"and another" "and another again"
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
			assertEquals(stringValue, "This is a string");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 18);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "And another string");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "and another");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 14);
			String stringValue = token.getStringValue();
			assertEquals(stringValue, "and another again");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test24() throws LexicalException {
		String input = """
				"xx
				yy
				zz"abc
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
			assertEquals(stringValue, "xx\nyy\nzz");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 3);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 3);
			String stringValue = token.getText();
			assertEquals(stringValue, "abc");

		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test25() throws LexicalException {
		String input = """
				SWITCH x!="true"
				CASE "x": IF x=x+1;
				""";
		IPLPLexer lexer = getLexer(input);

		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_SWITCH);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
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
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.NOT_EQUALS);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 8);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 10);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_CASE);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.STRING_LITERAL);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 5);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.COLON);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 8);
		}
	}

	@Test
	public void test26() throws LexicalException {
		String input = """
				VAL a = 0;
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_VAL);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 4);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 6);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 8);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.SEMI);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 9);
		}
		/*
		 * { IPLPToken token = lexer.nextToken(); Kind kind = token.getKind();
		 * assertEquals(kind, Kind.KW_INT); int charPositionInLine =
		 * token.getCharPositionInLine(); assertEquals(charPositionInLine, 12); } {
		 * IPLPToken token = lexer.nextToken(); Kind kind = token.getKind();
		 * assertEquals(kind, Kind.RSQUARE); int charPositionInLine =
		 * token.getCharPositionInLine(); assertEquals(charPositionInLine, 15); } {
		 * IPLPToken token = lexer.nextToken(); Kind kind = token.getKind();
		 * assertEquals(kind, Kind.SEMI); int charPositionInLine =
		 * token.getCharPositionInLine(); assertEquals(charPositionInLine, 16); }
		 */
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test29() throws LexicalException {
		String input = """
				IF x>0 && y>0 && Z>10
				DO
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_IF);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.GT);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.AND);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.GT);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.AND);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.GT);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_DO);
		}

		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test30() throws LexicalException {
		String input = """
				END- END* ENDabc
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.MINUS);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.TIMES);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test31() throws LexicalException {
		String input = """
				DO
				END
				IF THEN else
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_DO);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_IF);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}

	}

	@Test
	public void test32() throws LexicalException {
		String input = """
				abc/*...*/def
				ghi//....
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
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 11);
			String text = token.getText();
			assertEquals(text, "def");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
			String text = token.getText();
			assertEquals(text, "ghi");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.DIV);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 3);
			String text = token.getText();
			assertEquals(text, "/");
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.DIV);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 4);
			String text = token.getText();
			assertEquals(text, "/");
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});

	}

	@Test
	public void test33() throws LexicalException {
		String input = """
				$abc:TRUE
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
			assertEquals(kind, Kind.COLON);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_TRUE);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test34() throws LexicalException {
		String input = """
				a:INT = 88888888888888888888888888888888888888888;
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
			assertEquals(kind, Kind.COLON);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_INT);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
		}
		assertThrows(LexicalException.class, () -> {
			@SuppressWarnings("unused")
			IPLPToken token = lexer.nextToken();
		});
	}

	@Test
	public void test35() throws LexicalException {
		String input = """
				WHILE !x && ;
				CASE x: RETURN x
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_WHILE);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.BANG);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.AND);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.SEMI);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_CASE);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.COLON);
		}
	}

	@Test
	public void test36() throws LexicalException {
		String input = """
				DO\rLET a = 2;
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_DO);
			int line = token.getLine();
			assertEquals(line, 1);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_LET);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 0);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 4);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.ASSIGN);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 6);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 8);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.SEMI);
			int line = token.getLine();
			assertEquals(line, 2);
			int charPositionInLine = token.getCharPositionInLine();
			assertEquals(charPositionInLine, 9);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

	@Test
	public void test37() throws LexicalException {
		String input = """
				FUN func() DO

					END
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_FUN);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.LPAREN);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.RPAREN);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_DO);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}
	
	@Test
	public void test38() throws LexicalException {
		String input = """
				FUN func() DO
				SWITCH x
				CASE 0 : y=0;
				DEFAULT y=3;
				END
				/*SWITCH*/
				END
				/*FUN*/
				""";
		IPLPLexer lexer = getLexer(input);
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_FUN);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);

		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.LPAREN);
			
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.RPAREN);
			
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_DO);
			
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_SWITCH);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.IDENTIFIER);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_CASE);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.INT_LITERAL);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.COLON);
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
			assertEquals(kind, Kind.KW_DEFAULT);
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
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.KW_END);
		}
		{
			IPLPToken token = lexer.nextToken();
			Kind kind = token.getKind();
			assertEquals(kind, Kind.EOF);
		}
	}

}