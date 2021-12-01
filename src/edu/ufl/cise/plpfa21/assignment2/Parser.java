package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment1.Lexer;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;
import edu.ufl.cise.plpfa21.assignment3.ast.IASTNode;
import edu.ufl.cise.plpfa21.assignment3.ast.IBlock;
import edu.ufl.cise.plpfa21.assignment3.ast.IDeclaration;
import edu.ufl.cise.plpfa21.assignment3.ast.IExpression;
import edu.ufl.cise.plpfa21.assignment3.ast.INameDef;
import edu.ufl.cise.plpfa21.assignment3.ast.IStatement;
import edu.ufl.cise.plpfa21.assignment3.ast.IType.TypeKind;
import edu.ufl.cise.plpfa21.assignment3.astimpl.AssignmentStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.BinaryExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Block__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.BooleanLiteralExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Declaration__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.FunctionCallExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.FunctionDeclaration___;
import edu.ufl.cise.plpfa21.assignment3.astimpl.IdentExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Identifier__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.IfStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ImmutableGlobal__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.IntLiteralExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.LetStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ListSelectorExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ListType__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.MutableGlobal__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.NameDef__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.NilConstantExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.PrimitiveType__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Program__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.ReturnStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.StringLiteralExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.SwitchStatement__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.Type__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.UnaryExpression__;
import edu.ufl.cise.plpfa21.assignment3.astimpl.WhileStatement__;

import java.lang.UnsupportedOperationException;
import java.util.LinkedList;
import java.util.List;

public class Parser implements IPLPParser {
	public Lexer lexerInput;

	public Parser(Lexer input) {
		this.lexerInput = input;
	}

	IPLPToken token;
	Kind kind;
	int line, pos, value;
	String text, stringText;
	Identifier__ IDGlobal = null;

	// calls nextToken and Kind, assigns line and position => consumes token.
	public void callToken() throws Exception {
		token = lexerInput.nextToken();
		kind = token.getKind();
		line = token.getLine();
		pos = token.getCharPositionInLine();
		text = token.getText();
		// value = token.getIntValue();
	}

	public void callValue() throws Exception {
		value = token.getIntValue();
	}

	public void callStringText() {
		stringText = token.getStringValue();
	}

	public Program__ program() throws Exception {
		IPLPToken tokenCheck = this.token;
		List<IDeclaration> listD = new LinkedList<IDeclaration>();

		Declaration__ second;
		// first(line, pos, text, null);
		while (kind != Kind.EOF) {
			second = declaration();
			listD.add(second);
		}
		Program__ first = new Program__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), listD);
		// if (kind == Kind.EOF)
		return first;

		// throw new SyntaxException("invalid 15 syntax", line, pos);
	}

	public Declaration__ declaration() throws Exception {
		Declaration__ first = null;
		switch (kind) {
		case KW_FUN:
			first = function();
			break;
		case KW_VAR: {
			IPLPToken tokenCheck = this.token;
			NameDef__ NameDef;
			IExpression Exp = null;

			callToken();
			if (kind == Kind.IDENTIFIER) {
				IDGlobal = new Identifier__(line, pos, text, text);
				callToken();
				NameDef = nameDef(IDGlobal, this.token);
				// callToken();
				if (kind == Kind.ASSIGN) {
					callToken();
					Exp = expression();
					// callToken();
				}
				if (kind == Kind.SEMI)
					callToken();
				else
					throw new SyntaxException("semi not found 0", line, pos);
			} else
				throw new SyntaxException("syntax error", line, pos);
			// nextToken() should be either a LPRAN or Assign
			// expression();
			// should end with semi colon;
			first = new MutableGlobal__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), NameDef, Exp);
		}
			break;
		case KW_VAL: {
			IPLPToken tokenCheck = this.token;
			NameDef__ NameDef;
			IExpression Exp;

			callToken();
			if (kind == Kind.IDENTIFIER) {
				Identifier__ ID1 = new Identifier__(line, pos, text, text);
				callToken();
				NameDef = nameDef(ID1,this.token);
				// callToken();
				if (kind == Kind.ASSIGN) {
					callToken();
					Exp = expression();
					// callToken();
				} else
					throw new SyntaxException("invalid syntax 1", line, pos);
				if (kind == Kind.SEMI)
					callToken();
				else
					throw new SyntaxException("semi not found 2", line, pos);
			} else
				throw new SyntaxException("invalid syntax 3", line, pos);
			// nextToken() should be Assign
			// expression();
			// should end with semi colon
			first = new ImmutableGlobal__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), NameDef, Exp);
		}
			break;
		default:
			throw new SyntaxException("invalid syntax 4", line, pos);

		}

		return first;
		// throw new UnsupportedOperationException();
	}

	public FunctionDeclaration___ function() throws Exception {
		IPLPToken tokenCheck = this.token;
		FunctionDeclaration___ first;
		// identifier, namedef list, result TYPE, block
		Identifier__ IDFunc;
		List<INameDef> listND = new LinkedList<INameDef>();
		Type__ resultType = null;
		Block__ bloc = null;
		NameDef__ ND;

		callToken();
		if (kind == Kind.IDENTIFIER) {
			IDFunc = new Identifier__(line, pos, text, text);
			callToken();
			if (kind == Kind.LPAREN) {
				callToken();
				if (kind == Kind.IDENTIFIER) {
					IDGlobal = new Identifier__(line, pos, text, text);
					callToken();
					ND = nameDef(IDGlobal,this.token);
					listND.add(ND);
					// should check for comma and more nameDef
					// callToken();
					while (kind == Kind.COMMA) // checks for multiple namedefs
					{
						callToken();
						if (kind == Kind.IDENTIFIER) {
							IDGlobal = new Identifier__(line, pos, text, text);
							callToken();
							ND = nameDef(IDGlobal,this.token);
							listND.add(ND);
						} else
							throw new SyntaxException("invalid synatx 5", line, pos);
					}
					// need to check for multiple nameDefs
					// should check for rparen after nameDef ->
					// callToken(); //q to self --why are we calling this? check.
				}
				if (kind == Kind.RPAREN) {
					callToken();
					if (kind == Kind.COLON) {
						callToken();
						resultType = type();
						// callToken();
					}
					if (kind == Kind.KW_DO) {
						callToken();

						// token = lexerInput.nextToken();
						// kind = token.getKind();
//						if (kind == Kind.KW_END)
//							callToken();
//						else {
						bloc = block();
						if (kind == Kind.KW_END)
							callToken();
						else
							throw new SyntaxException("end not found", line, pos);
//						}
					} else
						throw new SyntaxException("invalid syntax 6", line, pos);
				} else
					throw new SyntaxException("invalid 7 synatx", line, pos);
			} else
				throw new SyntaxException("invalid 8 syntax", line, pos);
		} else
			throw new SyntaxException("invalid 9 syntax", line, pos);

		first = new FunctionDeclaration___(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), IDFunc, listND, resultType, bloc);
		return first;

	}

	public Block__ block() throws Exception {
		// callToken();
		IPLPToken tokenCheck = this.token;
		Block__ first = null;
		List<IStatement> listStat = new LinkedList<IStatement>();
		IStatement second;
		while (kind != Kind.KW_DEFAULT && kind != Kind.KW_END && kind != Kind.EOF && kind != Kind.KW_CASE) {
			second = statement();
			listStat.add(second);
		}
		// need to check for multiple types of statement *
		first = new Block__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), listStat);
		return first;
	}

	public IStatement statement() throws Exception {
		// callToken();
		IPLPToken tokenCheck = this.token;
		IStatement first = null;

		switch (kind) {
		case KW_LET: {

			NameDef__ localDef;
			IExpression Exp = null;
			Block__ bloc = null;

			callToken();
			if (kind == Kind.IDENTIFIER) {
				IDGlobal = new Identifier__(line, pos, text, text);
				callToken();
				localDef = nameDef(IDGlobal,this.token);
				if (kind == Kind.ASSIGN) {
					callToken();
					Exp = expression();
				}
				// add DO block() END
				if (kind == Kind.KW_DO) {
					callToken();

//					if (kind == Kind.KW_END)
//						callToken();
//					else {
					bloc = block();
					if (kind == Kind.KW_END)
						callToken();
					else
						throw new SyntaxException("error syntax3", line, pos);
					// }
				} else
					throw new SyntaxException("error syntax4", line, pos);

				// ask if semi is required or to be removed???? -- to be removed.
			} else
				throw new SyntaxException("invalid 10 token", line, pos);

			first = new LetStatement__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), bloc, Exp, localDef);
		}
			break;

		case KW_SWITCH: {

			IExpression switchExp, BExp = null;
			List<IExpression> listBranchExp = new LinkedList<IExpression>();
			Block__ bloc, defaultBloc = null;
			List<IBlock> listBloc = new LinkedList<IBlock>();

			callToken();
			switchExp = expression();
			// if(kind == Kind.KW_CASE) {

			while (kind != Kind.KW_DEFAULT) {
				callToken();
				BExp = expression();
				listBranchExp.add(BExp);

				if (kind == Kind.COLON) {
					callToken();
					if (kind != Kind.KW_CASE) {
						bloc = block();
						listBloc.add(bloc);
					}

					else
						continue;
					/* 
						bloc = block();
						listBloc.add(bloc);
					if (kind == Kind.KW_CASE) {
						continue; 	*/
				} else
					throw new SyntaxException("error syntax", line, pos);
			}
			if (kind == Kind.KW_DEFAULT) {
				callToken();
//				if (kind == Kind.KW_END)
//					callToken();
//				else {
				defaultBloc = block();
				if (kind == Kind.KW_END)
					callToken();

				else
					throw new SyntaxException("end  not found 3", line, pos);
				// }
			} else
				throw new SyntaxException("error syntax 2", line, pos);

			// }
			// else
			// throw new SyntaxException("error syntax 6", line, pos);

			first = new SwitchStatement__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), switchExp, listBranchExp, listBloc, defaultBloc);
		}
			break;

		case KW_IF: {

			IExpression guardExp;
			IBlock ifBloc = null;

			callToken();
			guardExp = expression();
			if (kind == Kind.KW_DO) {
				callToken();

//				if (kind == Kind.KW_END)
//					callToken();
//				else {
				ifBloc = block();
				if (kind == Kind.KW_END)
					callToken();
				else
					throw new SyntaxException("error syntax3", line, pos);
				// }
			} else
				throw new SyntaxException("error syntax4", line, pos);

			first = new IfStatement__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), guardExp, ifBloc);
		}
			break;

		case KW_WHILE: {

			IExpression guardExp;
			IBlock whileBloc = null;

			callToken();
			guardExp = expression();
			// callToken();
			if (kind == Kind.KW_DO) {
				callToken();

//				if (kind == Kind.KW_END)
//					callToken();
//				else {
				whileBloc = block();
				if (kind == Kind.KW_END)
					callToken();
				else
					throw new SyntaxException("end not found 4", line, pos);
//				}
			} else
				throw new SyntaxException("invalid 11 syntax", line, pos);

			first = new WhileStatement__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), guardExp, whileBloc);
		}
			break;

		case KW_RETURN: {
			
			IExpression returnExp;

			callToken();
			returnExp = expression();
			if (kind == Kind.SEMI)
				callToken();
			else
				throw new SyntaxException("semi not found 3", line, pos);
			System.out.println("410----------"+tokenCheck.getText());
			first = new ReturnStatement__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), returnExp);
		}
			break;

		// case IDENTIFIER:
		default: {

			IExpression leftExp, rightExp = null;

			leftExp = expression();
			// callToken();
			if (kind == Kind.ASSIGN) {
				callToken();
				rightExp = expression();
			}
			// callToken(); //why is this being called? check.
			if (kind == Kind.SEMI)
				callToken();
			else
				throw new SyntaxException("semi not found", line, pos);

			first = new AssignmentStatement__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), leftExp, rightExp);
		}
			break;

		}

		return first;
	}

	public NameDef__ nameDef(Identifier__ ID, IPLPToken namd) throws Exception {
		// callToken();
		IPLPToken tokenCheck = namd;
		Type__ Type = null;
		if (kind == Kind.COLON) {
			callToken();
			Type = type();
		}

		NameDef__ first = new NameDef__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), ID, Type); // ID, TYPE
		// throw new UnsupportedOperationException();
		return first;
	}

	public IExpression expression() throws Exception {
		IExpression first;
		first = logicalExpression();
		return first;
	}

	public IExpression logicalExpression() throws Exception {
		// callToken();
		IPLPToken tokenCheck = this.token;
		IExpression first, second;
		BinaryExpression__ BinaryExp = null;
		Kind kindTemp;

		first = comparisionExpression();
		// should not call next token always.
		// callToken();

		while (kind == Kind.AND || kind == Kind.OR) {
			kindTemp = kind;
			callToken();
			second = comparisionExpression();
			first = new BinaryExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), first, second, kindTemp);
		}
		return first;
	}

	public IExpression comparisionExpression() throws Exception {
		// callToken();
		IPLPToken tokenCheck = this.token;
		IExpression first, second;
		BinaryExpression__ BinaryExp = null;
		Kind kindTemp;

		first = additiveExpression();
		// callToken();

		while (kind == Kind.GT || kind == Kind.LT || kind == Kind.EQUALS || kind == Kind.NOT_EQUALS) {
			kindTemp = kind;
			callToken();
			second = additiveExpression();
			first = new BinaryExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), first, second, kindTemp);
		}
		return first;
	}

	public IExpression additiveExpression() throws Exception {
		// callToken();
		IPLPToken tokenCheck = this.token;
		IExpression first, second;
		BinaryExpression__ BinaryExp = null;
		Kind kindTemp;

		first = multiplicativeExpression();
		// callToken();

		while (kind == Kind.PLUS || kind == Kind.MINUS) {
			kindTemp = kind;
			callToken();
			second = multiplicativeExpression();
			first = new BinaryExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), first, second, kindTemp);
		}
		return first;

	}

	public IExpression multiplicativeExpression() throws Exception {
		// callToken();
		IPLPToken tokenCheck = this.token;
		IExpression first, second;
		BinaryExpression__ BinaryExp = null;
		Kind kindTemp;

		first = unaryExpression();

		// callToken();
		while (kind == Kind.TIMES || kind == Kind.DIV) {
			kindTemp = kind;
			callToken();
			second = unaryExpression();
			first = new BinaryExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), first, second, kindTemp);
		}
		return first;
	}

	public IExpression unaryExpression() throws Exception {
		// callToken();
		IPLPToken tokenCheck = this.token;
		IExpression first, exp = null;
		Kind kindTemp = null;

		if (kind == Kind.BANG || kind == Kind.MINUS) {
			kindTemp = kind;
			callToken();
			exp = primaryExpression();
			first = new UnaryExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), exp, kindTemp);
		} else
			first = primaryExpression();

		return first;
	}

	public IExpression primaryExpression() throws Exception {
		IExpression first;
		IPLPToken tokenCheck = this.token;

		switch (kind) {
		case KW_NIL: {
			first = new NilConstantExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText());
			callToken();
		}
			break;

		case KW_TRUE: {
			first = new BooleanLiteralExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), true);
			callToken();
		}
			break;

		case KW_FALSE: {
			first = new BooleanLiteralExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), false);
			callToken();
		}
			break;

		case INT_LITERAL: {
			callValue();
			first = new IntLiteralExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), value);
			callToken();
		}
			break;

		case STRING_LITERAL: {
			callStringText();
			first = new StringLiteralExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), stringText);
			callToken();
		}
			break;

		case LPAREN: {// call expression() and check RPAREN
			callToken();
			first = expression();
			// callToken();
			if (kind == Kind.RPAREN)
				callToken();
			else
				throw new SyntaxException("rpran not found", line, pos);
		}
			break;

		case IDENTIFIER: {
			Identifier__ IDName;
			// if has next [ ] then call expression() and check for ] or ( expression ,
			// expression*) OR just identifier ->
			IDName = new Identifier__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), tokenCheck.getText());

			first = new IdentExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), IDName);
			callToken();

			switch (kind) {
			case LSQUARE: {

				IExpression index;

				callToken();
				index = expression();
				// callToken();
				if (kind == Kind.RSQUARE)
					callToken();
				else
					throw new SyntaxException("rsquare not found", line, pos);

				first = new ListSelectorExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), IDName, index);
			}
				break;

			case LPAREN: {

				IExpression args;
				List<IExpression> listArgs = new LinkedList<IExpression>();

				// first = (IFunctionCallExpression) first;
				callToken();
				if (kind == Kind.RPAREN)
					callToken();
				else {
					args = expression();
					listArgs.add(args);

					// callToken();
					while (kind == Kind.COMMA) {
						callToken();
						args = expression();
						listArgs.add(args);
					}
					if (kind == Kind.RPAREN)
						callToken();

					else
						throw new SyntaxException("rpran not found", line, pos);
				}

				first = new FunctionCallExpression__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), IDName, listArgs);
			}
				break;

			default:
				break;

			}
		}
			break;
		default:
			throw new SyntaxException("invalid 12 syntax", line, pos);
		}
		// callToken();
		return first;
	}

	public Type__ type() throws Exception {
		// callToken();
		IPLPToken tokenCheck = this.token;
		Type__ first = null;
		switch (kind) {
		case KW_INT: {
			first = new PrimitiveType__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), TypeKind.INT);
			callToken();
		}
			break;
		case KW_STRING: {
			first = new PrimitiveType__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), TypeKind.STRING);
			callToken();
		}
			break;
		case KW_BOOLEAN: {
			first = new PrimitiveType__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), TypeKind.BOOLEAN);
			callToken();
		}
			break;
		case KW_LIST: {
			Type__ Type = null;

			callToken();
			if (kind == Kind.LSQUARE) {
				callToken();
				// while(kind !=Kind.RSQUARE)
				if (kind == Kind.RSQUARE)
					callToken();
				else {
					Type = type();
					if (kind == Kind.RSQUARE)
						callToken();
					else
						throw new SyntaxException("invalid 14 syntax", line, pos);
				}

			}
			first = new ListType__(tokenCheck.getLine(), tokenCheck.getCharPositionInLine(), tokenCheck.getText(), Type);
		}
			break;
		default:
			throw new SyntaxException("invalid 13 syntax", line, pos);
		}
		return first;
		// throw new UnsupportedOperationException();
	}

	@Override
	public IASTNode parse() throws Exception {
		// TODO Auto-generated method stub

		// should call nextToken from instance of lexer
		// IPLPLexer lexer = lexerInput.getLexer(input);
		{
			callToken();
			Program__ first;
			// System.out.println("new token " + token + "token kind" + kind);
			first = program();
			System.out.println("aST=---------- "+ first);
			if (kind == Kind.EOF)
				return first;
			// throw new UnsupportedOperationException();
			else
				throw new SyntaxException("invalid 15 syntax", line, pos);
			/// for second token
		}

	}

}