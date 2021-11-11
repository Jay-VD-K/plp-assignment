package edu.ufl.cise.plpfa21.assignment3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ufl.cise.plpfa21.assignment1.CompilerComponentFactory;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds;
import edu.ufl.cise.plpfa21.assignment2.IPLPParser;
import edu.ufl.cise.plpfa21.assignment3.ast.IASTNode;
import edu.ufl.cise.plpfa21.assignment3.ast.IBinaryExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IBlock;
import edu.ufl.cise.plpfa21.assignment3.ast.IBooleanLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IFunctionCallExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IFunctionDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IIdentExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IIdentifier;
import edu.ufl.cise.plpfa21.assignment3.ast.IImmutableGlobal;
import edu.ufl.cise.plpfa21.assignment3.ast.IIntLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IListType;
import edu.ufl.cise.plpfa21.assignment3.ast.IMutableGlobal;
import edu.ufl.cise.plpfa21.assignment3.ast.INameDef;
import edu.ufl.cise.plpfa21.assignment3.ast.INilConstantExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IPrimitiveType;
import edu.ufl.cise.plpfa21.assignment3.ast.IProgram;
import edu.ufl.cise.plpfa21.assignment3.ast.IReturnStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.ISwitchStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IStringLiteralExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.IType;
import edu.ufl.cise.plpfa21.assignment3.ast.IType.TypeKind;

class ExampleASTParserTests implements PLPTokenKinds {

	private IASTNode getAST(String input) throws Exception {
		IPLPParser parser = CompilerComponentFactory.getParser(input);
		return parser.parse();
	}

	@Test
	public void test0() throws Exception {
		String input = """

				""";
		System.out.println("\n\ntest 0: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test1() throws Exception {
		String input = """
				VAL a: INT = 0;
				""";
		System.out.println("\n\ntest 1: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "a");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IPrimitiveType);
		IPrimitiveType n9 = (IPrimitiveType) n8;
		assertEquals(n9.getType(), TypeKind.INT);
		IExpression n10 = n3.getExpression();
		assertTrue(n10 instanceof IIntLiteralExpression);
		IIntLiteralExpression n11 = (IIntLiteralExpression) n10;
		assertEquals(n11.getValue(), 0);
	}

	@Test
	public void test2() throws Exception {
		String input = """
				VAL a: STRING = "hello";
				""";
		System.out.println("\n\ntest 2: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "a");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IPrimitiveType);
		IPrimitiveType n9 = (IPrimitiveType) n8;
		assertEquals(n9.getType(), TypeKind.STRING);
		IExpression n10 = n3.getExpression();
		assertTrue(n10 instanceof IStringLiteralExpression);
		IStringLiteralExpression n11 = (IStringLiteralExpression) n10;
		assertEquals("hello", n11.getValue());
	}

	@Test
	public void test3() throws Exception {
		String input = """
				VAL b: BOOLEAN = TRUE;
				""";
		System.out.println("\n\ntest 3: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "b");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IPrimitiveType);
		IPrimitiveType n9 = (IPrimitiveType) n8;
		assertEquals(n9.getType(), TypeKind.BOOLEAN);
		IExpression n10 = n3.getExpression();
		assertTrue(n10 instanceof IBooleanLiteralExpression);
		IBooleanLiteralExpression n11 = (IBooleanLiteralExpression) n10;
		assertEquals(n11.getValue(), true);
	}

	@Test
	public void test4() throws Exception {
		String input = """
				VAR b: LIST[];
				""";
		System.out.println("\n\ntest 4: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IMutableGlobal);
		IMutableGlobal n3 = (IMutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "b");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IListType);
		IListType n9 = (IListType) n8;
	}

	@Test
	public void test5() throws Exception {
		String input = """
				VAR c = f();
				""";
		System.out.println("\n\ntest 5: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IMutableGlobal);
		IMutableGlobal n3 = (IMutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "c");
		IExpression n8 = n3.getExpression();
		assertTrue(n8 instanceof IFunctionCallExpression);
		IFunctionCallExpression n9 = (IFunctionCallExpression) n8;
		IIdentifier n10 = n9.getName();
		assertTrue(n10 instanceof IIdentifier);
		IIdentifier n11 = (IIdentifier) n10;
		assertEquals(n11.getName(), "f");
	}

	@Test
	public void test6() throws Exception {
		String input = """
				VAL c = a+b;
				""";
		System.out.println("\n\ntest 6: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "c");
		IExpression n8 = n3.getExpression();
		assertTrue(n8 instanceof IBinaryExpression);
		IBinaryExpression n9 = (IBinaryExpression) n8;
		IExpression n10 = n9.getLeft();
		assertTrue(n10 instanceof IIdentExpression);
		IIdentExpression n12 = (IIdentExpression) n10;
		IIdentifier n13 = n12.getName();
		assertTrue(n13 instanceof IIdentifier);
		IIdentifier n14 = (IIdentifier) n13;
		assertEquals(n14.getName(), "a");
		IExpression n11 = n9.getRight();
		assertTrue(n11 instanceof IIdentExpression);
		IIdentExpression n15 = (IIdentExpression) n11;
		IIdentifier n16 = n15.getName();
		assertTrue(n16 instanceof IIdentifier);
		IIdentifier n17 = (IIdentifier) n16;
		assertEquals(n17.getName(), "b");
		assertEquals(n9.getOp(), Kind.PLUS);
	}

	@Test
	public void test7() throws Exception {
		String input = """
				VAL d = ((2));
				""";
		System.out.println("\n\ntest 7: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "d");
		IExpression n8 = n3.getExpression();
		assertTrue(n8 instanceof IIntLiteralExpression);
		IIntLiteralExpression n9 = (IIntLiteralExpression) n8;
		assertEquals(n9.getValue(), 2);
	}

	@Test
	public void test8() throws Exception {
		String input = """
				VAL d = ((a+b)/(c+f()));
				""";
		System.out.println("\n\ntest 8: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IImmutableGlobal);
		IImmutableGlobal n3 = (IImmutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "d");
		IExpression n8 = n3.getExpression();
		assertTrue(n8 instanceof IBinaryExpression);
		IBinaryExpression n9 = (IBinaryExpression) n8;
		IExpression n10 = n9.getLeft();
		assertTrue(n10 instanceof IBinaryExpression);
		IBinaryExpression n12 = (IBinaryExpression) n10;
		IExpression n13 = n12.getLeft();
		assertTrue(n13 instanceof IIdentExpression);
		IIdentExpression n15 = (IIdentExpression) n13;
		IIdentifier n16 = n15.getName();
		assertTrue(n16 instanceof IIdentifier);
		IIdentifier n17 = (IIdentifier) n16;
		assertEquals(n17.getName(), "a");
		IExpression n14 = n12.getRight();
		assertTrue(n14 instanceof IIdentExpression);
		IIdentExpression n18 = (IIdentExpression) n14;
		IIdentifier n19 = n18.getName();
		assertTrue(n19 instanceof IIdentifier);
		IIdentifier n20 = (IIdentifier) n19;
		assertEquals(n20.getName(), "b");
		assertEquals(n12.getOp(), Kind.PLUS);
		IExpression n11 = n9.getRight();
		assertTrue(n11 instanceof IBinaryExpression);
		IBinaryExpression n21 = (IBinaryExpression) n11;
		IExpression n22 = n21.getLeft();
		assertTrue(n22 instanceof IIdentExpression);
		IIdentExpression n24 = (IIdentExpression) n22;
		IIdentifier n25 = n24.getName();
		assertTrue(n25 instanceof IIdentifier);
		IIdentifier n26 = (IIdentifier) n25;
		assertEquals(n26.getName(), "c");
		IExpression n23 = n21.getRight();
		assertTrue(n23 instanceof IFunctionCallExpression);
		IFunctionCallExpression n27 = (IFunctionCallExpression) n23;
		IIdentifier n28 = n27.getName();
		assertTrue(n28 instanceof IIdentifier);
		IIdentifier n29 = (IIdentifier) n28;
		assertEquals(n29.getName(), "f");
		assertEquals(n21.getOp(), Kind.PLUS);
		assertEquals(n9.getOp(), Kind.DIV);
	}

	@Test
	public void test9() throws Exception {
		String input = """
				VAR A:LIST[LIST[INT]];
				""";
		System.out.println("\n\ntest 9: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IMutableGlobal);
		IMutableGlobal n3 = (IMutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "A");
		IType n8 = n5.getType();
		assertTrue(n8 instanceof IListType);
		IListType n9 = (IListType) n8;
		IType n10 = n9.getElementType();
		assertTrue(n10 instanceof IListType);
		IListType n11 = (IListType) n10;
		IType n12 = n11.getElementType();
		assertTrue(n12 instanceof IPrimitiveType);
		IPrimitiveType n13 = (IPrimitiveType) n12;
		assertEquals(n13.getType(), TypeKind.INT);
	}

	@Test
	public void test10() throws Exception {
		String input = """
				VAR A;
				VAL B=0;

				""";
		System.out.println("\n\ntest 10: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		IDeclaration n2 = n1.get(0);
		assertTrue(n2 instanceof IMutableGlobal);
		IMutableGlobal n3 = (IMutableGlobal) n2;
		INameDef n4 = n3.getVarDef();
		assertTrue(n4 instanceof INameDef);
		INameDef n5 = (INameDef) n4;
		IIdentifier n6 = n5.getIdent();
		assertTrue(n6 instanceof IIdentifier);
		IIdentifier n7 = (IIdentifier) n6;
		assertEquals(n7.getName(), "A");
		IDeclaration n8 = n1.get(1);
		assertTrue(n8 instanceof IImmutableGlobal);
		IImmutableGlobal n9 = (IImmutableGlobal) n8;
		INameDef n10 = n9.getVarDef();
		assertTrue(n10 instanceof INameDef);
		INameDef n11 = (INameDef) n10;
		IIdentifier n12 = n11.getIdent();
		assertTrue(n12 instanceof IIdentifier);
		IIdentifier n13 = (IIdentifier) n12;
		assertEquals(n13.getName(), "B");
		IExpression n14 = n9.getExpression();
		assertTrue(n14 instanceof IIntLiteralExpression);
		IIntLiteralExpression n15 = (IIntLiteralExpression) n14;
		assertEquals(n15.getValue(), 0);
	}

	@Test
	public void test11() throws Exception {
		String input = """
				FUN f()
						DO
				RETURN NIL;
				END
				""";
		System.out.println("\n\ntest 11: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		int n2 = n1.size();
		assertEquals(n2, 1);
		IDeclaration n3 = n1.get(0);
		assertTrue(n3 instanceof IFunctionDeclaration);
		IFunctionDeclaration n4 = (IFunctionDeclaration) n3;
		IIdentifier n5 = n4.getName();
		assertEquals(n5.getName(), "f");
		List<INameDef> n6 = n4.getArgs();
		int n7 = n6.size();
		assertEquals(n7, 0);
		IType n8 = n4.getResultType();
		assertEquals(n8, null);
		IBlock n9 = n4.getBlock();
		List<IStatement> n10 = n9.getStatements();
		int n11 = n10.size();
		assertEquals(n11, 1);
		IStatement n12 = n10.get(0);
		assertTrue(n12 instanceof IReturnStatement);
		IReturnStatement n13 = (IReturnStatement) n12;
		IExpression n14 = n13.getExpression();
		assertTrue(n14 instanceof INilConstantExpression);
	}

	@Test
	public void test12() throws Exception {
		String input = """
				FUN func(a, b:INT, c): INT
						DO
				SWITCH x
				CASE y: x&&y; a=TRUE;
				DEFAULT LET abc=a[1234] DO END
				END
				END
				""";
		System.out.println("\n\ntest 12: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		int n2 = n1.size();
		assertEquals(n2, 1);
		IDeclaration n3 = n1.get(0);

		assertTrue(n3 instanceof IFunctionDeclaration);
		IFunctionDeclaration n4 = (IFunctionDeclaration) n3;

		IIdentifier n5 = n4.getName();
		assertEquals(n5.getName(), "func");

		List<INameDef> n6 = n4.getArgs();
		int n7 = n6.size();
		assertEquals(n7, 3);

		IType n8 = n4.getResultType();
		assertTrue(n8 instanceof IPrimitiveType);

		IBlock n9 = n4.getBlock();
		List<IStatement> n10 = n9.getStatements();
		int n11 = n10.size();
		assertEquals(n11, 1);

		IStatement n12 = n10.get(0);
		assertTrue(n12 instanceof ISwitchStatement);
		ISwitchStatement n13 = (ISwitchStatement) n12;

		IExpression n14 = n13.getSwitchExpression();
		assertTrue(n14 instanceof IIdentExpression);

		List<IExpression> n15 = n13.getBranchExpressions();
		List<IBlock> n16 = n13.getBlocks();
		IBlock n17 = n13.getDefaultBlock();

		int n18 = n15.size();
		assertEquals(n18, 1);

		n18 = n16.size();
		assertEquals(n18, 1);
		assertTrue(n17 instanceof IBlock);

		// CASE y: (a(TRUE,FALSE));

		IExpression n19 = n15.get(0);
		assertTrue(n19 instanceof IIdentExpression);

		IIdentExpression n22 = (IIdentExpression) n19;
		IIdentifier n23 = n22.getName();
		assertTrue(n23 instanceof IIdentifier);
		IIdentifier n24 = (IIdentifier) n23;
		assertEquals(n24.getName(), "y");

		IBlock n20 = n16.get(0);
	}

	@Test
	public void test13() throws Exception {
		String input = """
				FUN func(a, b:INT, c): INT DO
				SWITCH x!="true"
				CASE "x": IF x==x
				DO x=x+1;
				RETURN x;
				END
				DEFAULT LET abc=a[1234] DO END
				END
				END
				""";
		System.out.println("\n\ntest 13: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test22() throws Exception {
		String input = """
				FUN func(a, b:INT):STRING DO
				LET x=TRUE DO END
				END
				""";
		System.out.println("\n\ntest 22: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();

	}

	@Test
	public void test23() throws Exception {
		String input = """
				FUN func(a, b:INT, c): INT DO
				SWITCH x!="true"
				CASE "x": IF x==x
				DO x=x+1;
				RETURN x;
				END
				DEFAULT LET abc=a[1234] DO END
				END
				END
				""";
		System.out.println("\n\ntest 23: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	// not wrking
	@Test
	public void test25() throws Exception {
		String input = """
				FUN func() DO
				SWITCH x
				CASE x: IF x==x
				DO RETURN x;
				END
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=c[1234] DO END
				END
				END
				""";
		System.out.println("\n\ntest 25: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test28() throws Exception {
		String input = """
				FUN func() DO
				SWITCH x
				CASE x: RETURN x;
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=a[1234] DO END
				END
				END
				""";
		System.out.println("\n\ntest 28: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
		int n2 = n1.size();
		assertEquals(n2, 1);
		IDeclaration n3 = n1.get(0);

		assertTrue(n3 instanceof IFunctionDeclaration);
		IFunctionDeclaration n4 = (IFunctionDeclaration) n3;

		IIdentifier n5 = n4.getName();
		assertEquals(n5.getName(), "func");

		List<INameDef> n6 = n4.getArgs();
		int n7 = n6.size();
		assertEquals(n7, 0);
	}
	// till here

	@Test
	public void test26() throws Exception {
		String input = """
				FUN func() DO
				SWITCH x
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=a[1234]DO END
				END
				END
				""";
		System.out.println("\n\ntest 26: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test29() throws Exception {
		String input = """
				FUN func() DO
				IF x>0 && y>0 && Z>10
				DO
				x=x+y+z;
				END
				END
				""";
		System.out.println("\n\ntest 29: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test30() throws Exception {
		String input = """
				FUN func() DO
				SWITCH !x
				CASE x: RETURN x;
				CASE y: (a(TRUE,FALSE));
				DEFAULT LET abc=a[1234] DO END
				END
				IF x>0 && y>0 && Z!=10
				DO
				x=x+y+z;
				END
				END
				""";
		System.out.println("\n\ntest 30: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test31() throws Exception {
		String input = """
				FUN func() DO
				SWITCH x
				CASE x: RETURN x;
				CASE y:
				(a(TRUE,
				FALSE));
				CASE NIL: LET a=
				"abc"
				DO
				END

				DEFAULT LET abc=a[1234] DO END
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
		System.out.println("\n\ntest 31: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test33() throws Exception {
		String input = """
				FUN func() DO
				IF x DO END
				END
				""";
		System.out.println("\n\ntest 33: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test34() throws Exception {
		String input = """
				FUN func() DO
						LET a = 2		DO END
				END
				""";
		System.out.println("\n\ntest 34: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test35() throws Exception {
		String input = """
				FUN func() DO
				SWITCH !x
				DEFAULT LET abc=a[1234] DO END
				END
				a();
				END
				""";
		System.out.println("\n\ntest 35: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test36() throws Exception {
		String input = """
				VAR x = 9;



				""";
		System.out.println("\n\ntest 36: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test37() throws Exception {//
		String input = """
				FUN f(x:INT, y:BOOLEAN, z:LIST[INT]) DO
				END
				""";
		System.out.println("\n\ntest 37: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test38() throws Exception {
		String input = """
				FUN b(a,b,c) DO END
				""";
		System.out.println("\n\ntest 38: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test39() throws Exception {
		String input = """
				FUN a(b:INT, C:STRING) DO END
				""";
		System.out.println("\n\ntest 39: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test40() throws Exception {
		String input = """
				FUN a(b:INT) DO END
				""";
		System.out.println("\n\ntest 40: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

	@Test
	public void test41() throws Exception {
		String input = """
				FUN b() DO END
				""";
		System.out.println("\n\ntest 41: \n");
		IASTNode ast = getAST(input);
		assertTrue(ast instanceof IProgram);
		IProgram n0 = (IProgram) ast;
		List<IDeclaration> n1 = n0.getDeclarations();
	}

}