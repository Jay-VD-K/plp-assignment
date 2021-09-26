package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment1.Lexer;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class Parser implements IPLPParser {
	public Lexer lexerInput;
	// public String parentInput;
	// int pos = 0;

	public Parser(Lexer input) {
		this.lexerInput = input;
		// this.parentInput = input;
	}

	IPLPToken token;
	Kind kind;
	int line, pos;

	// calls nextToken and Kind
	public void callToken() throws Exception {
		token = lexerInput.nextToken();
		kind = token.getKind();
		line = token.getLine();
		pos = token.getCharPositionInLine();
	}

	public void function() throws Exception {
		callToken();
		if (kind == Kind.IDENTIFIER) {
			callToken();
			if (kind == Kind.LPAREN) {
				callToken();
				if (kind == Kind.IDENTIFIER) {
					callToken();
					nameDef();
					// should check for comma and more nameDef
					token = lexerInput.nextToken();
					kind = token.getKind();
					while (kind == Kind.COMMA) // checks for multiple namedefs
					{
						callToken();
						if (kind == Kind.IDENTIFIER) {
							callToken();
							nameDef();
						} else
							throw new SyntaxException("fail 14 token", 1, 1);
					}
					// need to check for multiple nameDefs
					// should check for rparen after nameDef ->
					token = lexerInput.nextToken();
					kind = token.getKind();
				}
				if (kind == Kind.RPAREN) {
					callToken();
					if (kind == Kind.COLON) {
						type();
						token = lexerInput.nextToken();
						kind = token.getKind();
					}
					if (kind == Kind.KW_DO) {
						callToken();
						block();
						// token = lexerInput.nextToken();
						// kind = token.getKind();
						if (kind != Kind.KW_END)
							throw new SyntaxException("fail 1 token", 1, 1);
					} else
						throw new SyntaxException("invalid 2 token", 1, 1);
				} else
					throw new SyntaxException("invalid 3 token", 1, 1);
			} else
				throw new SyntaxException("invalid 4 token", 1, 1);
		} else
			throw new SyntaxException("invalid 5 token", 1, 1);

	}

	public void block() throws Exception {
		// callToken();
		while (kind != Kind.KW_END || kind != Kind.KW_DEFAULT) {
			statement();
		}
		// need to check for multiple types of statement *
	}

	public void statement() throws Exception {
		// callToken();
		switch (kind) {
		case KW_LET: {
		}
			break;
		case KW_SWITCH: {
		}
			break;
		case KW_IF: {
		}
			break;
		case KW_WHILE: {
			callToken();
			expression();
			callToken();
			if (kind == Kind.KW_DO) {
				callToken();
				block();
				token = lexerInput.nextToken();
				kind = token.getKind();
				if (kind != Kind.KW_END)
					throw new SyntaxException("invalid 6 token", 1, 1);
			} else
				throw new SyntaxException("invalid 7 token", 1, 1);
		}
			break;
		case KW_RETURN: {
		}
			break;
		// case IDENTIFIER:
		default: {
			expression();
			// callToken();
			if (kind == Kind.ASSIGN) {
				callToken();
				expression();
			}

			callToken();
		}
			if (kind != Kind.SEMI)
				throw new SyntaxException("semi not found", line, pos);
		}
	}

	public void nameDef() throws Exception {
		// callToken();
		if (kind == Kind.COLON) {
			callToken();
			type();
		}
	}

	public void expression() throws Exception {
		logicalExpression();
	}

	public void logicalExpression() throws Exception {
		// callToken();
		comparisionExpression();
		// should not call next token always.
		// callToken();
		while (kind == Kind.AND || kind == Kind.OR) {
			comparisionExpression();
		}
	}

	public void comparisionExpression() throws Exception {
		// callToken();
		additiveExpression();
		// callToken();
		while (kind == Kind.GT || kind == Kind.LT || kind == Kind.EQUALS || kind == Kind.NOT_EQUALS) {
			callToken();
			additiveExpression();
		}
	}

	public void additiveExpression() throws Exception {
		// callToken();
		multiplicativeExpression();
		// callToken();
		while (kind == Kind.PLUS || kind == Kind.MINUS) {
			callToken();
			multiplicativeExpression();
		}
	}

	public void multiplicativeExpression() throws Exception {
		// callToken();
		unaryExpression();
		// callToken();
		while (kind == Kind.TIMES || kind == Kind.DIV) {
			unaryExpression();
		}
	}

	public void unaryExpression() throws Exception {
		// callToken();
		if (kind == Kind.BANG || kind == Kind.MINUS) {
			callToken();
			primaryExpression();
		} else
			primaryExpression();
	}

	public void primaryExpression() throws Exception {
		// token = lexerInput.nextToken();
		// kind = token.getKind();
		// callToken();
		switch (kind) {
		case KW_NIL: {
		}
			break;
		case KW_TRUE: {
		}
			break;
		case KW_FALSE: {
		}
			break;
		case INT_LITERAL: { // should consume herel;
		}
			break;
		case STRING_LITERAL: {
		}
			break;
		case LPAREN: {// call expression() and check RPAREN
			expression();
			callToken();
			if (kind != Kind.RPAREN)
				throw new SyntaxException("invalid 8 token", 1, 1);
		}
			break;
		case IDENTIFIER: {
			// if has next [ ] then call expression() and check for ] or ( expression ,
			// expression*) OR just identifier ->
			callToken();
			switch (kind) {
			case LSQUARE: {
				expression();
				callToken();
				if (kind != Kind.RSQUARE)
					throw new SyntaxException("invalid 8 token", 1, 1);
			}
				break;
			case LPAREN: {
				expression();
				callToken();
				while (kind == Kind.COMMA) {
					expression();
				}
			}
				break;
			default:
				break;

			}

		}
			break;
		default:
			throw new SyntaxException("invalid 12 token", 1, 1);
		}
		// callToken();

	}

	public void type() throws Exception {
		// callToken();
		switch (kind) {
		case KW_INT: {
		}
			break;
		case KW_STRING: {
		}
			break;
		case KW_BOOLEAN: {
		}
			break;
		case KW_LIST: {
			callToken();
			if (kind == Kind.LSQUARE) {
				callToken();
				// while(kind !=Kind.RSQUARE)
				if (kind == Kind.RSQUARE)
					callToken();
				else
					type();
			}
		}
			break;
		default:
			throw new SyntaxException("invalid 10 token", 1, 1);
		}
	}

	@Override
	public void parse() throws Exception {
		// TODO Auto-generated method stub

		// should call nextToken from instance of lexer
		// IPLPLexer lexer = lexerInput.getLexer(input);
		{
			token = lexerInput.nextToken();
			kind = token.getKind();
			System.out.println("new token " + token + "token kind" + kind);
			/// for second token

			switch (kind) {
			case KW_FUN:
				function();
				break;
			case KW_VAR: {
				callToken();
				if (kind == Kind.IDENTIFIER) {
					callToken();
					nameDef();
					// callToken();
					if (kind == Kind.ASSIGN) {
						expression();
						callToken();
					}
					if (kind != Kind.SEMI)
						throw new SyntaxException("invalid 13 token", 1, 1);
				} else
					throw new SyntaxException("invalid 14 token", 1, 1);
				// nextToken() should be either a LPRAN or Assign
				// expression();
				// should end with semi colon;
			}
				break;
			case KW_VAL: {
				callToken();
				if (kind == Kind.IDENTIFIER) {
					callToken();
					nameDef();
					callToken();
					if (kind == Kind.ASSIGN) {
						callToken();
						expression();
						callToken();
					} else
						throw new SyntaxException("invalid 16 token", 1, 1);
					if (kind != Kind.SEMI)
						throw new SyntaxException("invalid 17 token", 1, 1);
				} else
					throw new SyntaxException("invalid 14 token", 1, 1);
				// nextToken() should be Assign
				// expression();
				// should end with semi colon
			}
				break;
			default:
				throw new SyntaxException("invalid 9 token", 1, 1);

			}
		}

	}

}
