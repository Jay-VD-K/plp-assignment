package edu.ufl.cise.plpfa21.assignment2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.ufl.cise.plpfa21.assignment1.CompilerComponentFactory;

class ExampleParserTests {

	static boolean VERBOSE = true;

	void noErrorParse(String input) {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		try {
			parser.parse();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	private void syntaxErrorParse(String input, int line, int column) {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		assertThrows(SyntaxException.class, () -> {
			try {
				parser.parse();
			} catch (SyntaxException e) {
				if (VERBOSE)
					System.out.println(input + '\n' + e.getMessage());
				Assertions.assertEquals(line, e.line);
				Assertions.assertEquals(column, e.column);
				throw e;
			}
		});

	}

	@Test
	public void test0() {
		String input = """

				""";
		noErrorParse(input);
	}

	@Test
	public void test1() {
		String input = """
				VAL a: INT = 0;
				""";
		noErrorParse(input);
	}

	@Test
	public void test2() {
		String input = """
				VAL a: STRING = "hello";
				""";
		noErrorParse(input);
	}

	@Test
	public void test3() {
		String input = """
				VAL b: BOOLEAN = TRUE;
				""";
		noErrorParse(input);
	}

	@Test
	public void test4() {
		String input = """
				VAR b: LIST[];
				""";
		noErrorParse(input);
	}

	// This input has a syntax error at line 2, position 19.
	@Test
	public void test5() {
		String input = """
				FUN func() DO
				WHILE x>0 DO x=x-1 END
				END
				""";
		syntaxErrorParse(input, 2, 19);
	}

	//// self tdd - val
	@Test
	public void test6() {
		String input = """
				VAL a = 0;
				""";
		noErrorParse(input);
	}

	@Test
	public void test7() {
		String input = """
				VAL a: STRING = x-1;
				""";
		noErrorParse(input);
	}

	@Test
	public void test8() {
		String input = """
				VAL bb: INT = FALSE;
				""";
		noErrorParse(input);
	}

	@Test
	public void test9() {
		String input = """
				VAL bb: INT = "String";
				""";
		noErrorParse(input);
	}

	@Test
	public void test10() {
		String input = """
				VAL bb: int = "String";
				""";
		syntaxErrorParse(input, 1, 8);
	}

	@Test
	public void test11() {
		String input = """
				VAL b3 INT = "String";
				""";
		syntaxErrorParse(input, 1, 7);
	}

	@Test
	public void test12() {
		String input = """
				VAL bb: INT = x&&y;
				""";
		noErrorParse(input);
	}

	@Test
	public void test13() {
		String input = """
				VAL 12 INT = "String";
				""";
		syntaxErrorParse(input, 1, 4);
	}

	// var test cases
	@Test
	public void test14() {
		String input = """
				VAR b: LIST[];
				""";
		noErrorParse(input);
	}

	@Test
	public void test15() {
		String input = """
				VAR b: LIST[INT];
				VAR a LIST[INT];
				""";
		syntaxErrorParse(input, 2, 6);
	}

	@Test
	public void test16() {
		String input = """
				VAR b: INT = x+1;
				""";
		noErrorParse(input);
	}

	@Test
	public void test17() {
		String input = """
				VAR b;
				""";
		noErrorParse(input);
	}

	@Test
	public void test18() {
		String input = """
				VAR c123= TRUE;
				""";
		noErrorParse(input);
	}

	@Test
	public void test19() {
		String input = """
				VAR b LIST[];
				""";
		syntaxErrorParse(input, 1, 6);
	}

	@Test
	public void test20() {
		String input = """
				VAR b: LIST[INT[]];
				""";
		syntaxErrorParse(input, 1, 15);
	}

	@Test
	public void test21() {
		String input = """
				VAR b = INT;
				""";
		syntaxErrorParse(input, 1, 8);
	}

	// fun
	@Test
	public void test22() {
		String input = """
				FUN func(a, b:INT):STRING DO
				LET x=TRUE;
				END
				""";
		noErrorParse(input);
	}

	@Test
	public void test23() {
		String input = """
				FUN func(a, b:INT, c): INT DO
				SWITCH x!="true"
				CASE "x": IF x==x
				DO x=x+1;
				RETURN x;
				END
				DEFAULT LET abc=a[1234];
				END
				END
				""";
		noErrorParse(input);
	}

	// not wrking
	@Test
	public void test25() {
		String input = """
				FUN func() DO
				SWITCH x
				CASE x: IF x==x
				DO RETURN x;
				END
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=c[1234];
				END
				END
				""";
		noErrorParse(input);
	}

	@Test
	public void test28() {
		String input = """
				FUN func() DO
				SWITCH x
				CASE x: RETURN x;
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=a[1234];
				END
				END
				""";
		noErrorParse(input);
	}
	// till here

	@Test
	public void test26() {
		String input = """
				FUN func() DO
				SWITCH x
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=a[1234];
				END
				END
				""";
		noErrorParse(input);
	}

	@Test
	public void test24() {
		String input = """
				FUN func(a, b:INT, c): INT DO
				SWITCH x!="true"
				CASE "x": IF x==x+1;
				DO x=x+1;
				RETURN x;
				END
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=[1234];
				END
				END
				""";
		syntaxErrorParse(input, 3, 19);
	}

	@Test
	public void test27() {
		String input = """
				FUN func() DO
				SWITCH x
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=[1234];
				END
				END
				""";
		syntaxErrorParse(input, 4, 16);
	}

	@Test
	public void test29() {
		String input = """
				FUN func() DO
				IF x>0 && y>0 && Z>10
				DO
				x=x+y+z;
				END
				END
				""";
		noErrorParse(input);
	}

	@Test
	public void test30() {
		String input = """
				FUN func() DO
				SWITCH !x
				CASE x: RETURN x;
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=a[1234];
				END
				IF x>0 && y>0 && Z!=10
				DO
				x=x+y+z;
				END
				END
				""";
		noErrorParse(input);
	}

	@Test
	public void test31() {
		String input = """
				FUN func() DO
				SWITCH x
				CASE x: RETURN x;
				CASE y:
				(a(TRUE,
				FALSE));
				CASE NIL: LET a=
				"abc"
				;
				DEFAULT LET abc=a[1234];
				END
				/*should ignore this */
				/*should ignore this too */
				WHILE x>0 ||
				y>0 || Z<10
				DO
				x=x*y-z;
				END
				END
				""";
		noErrorParse(input);
	}

	@Test
	public void test32() {
		String input = """
				FUN func() DO
				WHILE !x == ;
				CASE x: RETURN x;
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=a[1234];
				END
				END
				""";
		syntaxErrorParse(input, 2, 12);
	}

	@Test
	public void test33() {
		String input = """
				FUN func() DO

				END
				""";
		noErrorParse(input);
	}

	@Test
	public void test34() {
		String input = """
				FUN func() DO
						LET a = 2;
				END
				""";
		noErrorParse(input);
	}
	
	@Test
	public void test35() {
		String input = """
				FUN func() DO
				SWITCH !x
				DEFAULT LET abc=a[1234];
				END
				a();
				END
				""";
		noErrorParse(input);
	}

}
