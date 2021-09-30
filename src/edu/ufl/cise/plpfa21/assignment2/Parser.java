package edu.ufl.cise.plpfa21.assignment2;

import edu.ufl.cise.plpfa21.assignment1.IPLPToken;
import edu.ufl.cise.plpfa21.assignment1.Lexer;
import edu.ufl.cise.plpfa21.assignment1.PLPTokenKinds.Kind;

public class Parser implements IPLPParser {
	public Lexer lexerInput;

	public Parser(Lexer input) {
		this.lexerInput = input;
	}

	IPLPToken token;
	Kind kind;
	int line, pos;

	// calls nextToken and Kind, assigns line and position => consumes token.
	public void callToken() throws Exception {
		token = lexerInput.nextToken();
		kind = token.getKind();
		line = token.getLine();
		pos = token.getCharPositionInLine();
	}

	public void program() throws Exception {
		while (kind != Kind.EOF) {
			declaration();
		}

	}

	public void declaration() throws Exception {
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
					callToken();
					expression();
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
		}
			break;
		case KW_VAL: {
			callToken();
			if (kind == Kind.IDENTIFIER) {
				callToken();
				nameDef();
				// callToken();
				if (kind == Kind.ASSIGN) {
					callToken();
					expression();
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
		}
			break;
		default:
			throw new SyntaxException("invalid syntax 4", line, pos);

		}
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
					// callToken();
					while (kind == Kind.COMMA) // checks for multiple namedefs
					{
						callToken();
						if (kind == Kind.IDENTIFIER) {
							callToken();
							nameDef();
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
						type();
						// callToken();
					}
					if (kind == Kind.KW_DO) {
						callToken();

						// token = lexerInput.nextToken();
						// kind = token.getKind();
						if (kind == Kind.KW_END)
							callToken();
						else {
							block();
							if (kind == Kind.KW_END)
								callToken();
							else
								throw new SyntaxException("end not found", line, pos);
						}
					} else
						throw new SyntaxException("invalid syntax 6", line, pos);
				} else
					throw new SyntaxException("invalid 7 synatx", line, pos);
			} else
				throw new SyntaxException("invalid 8 syntax", line, pos);
		} else
			throw new SyntaxException("invalid 9 syntax", line, pos);

	}

	public void block() throws Exception {
		// callToken();

		while (kind != Kind.KW_DEFAULT && kind != Kind.KW_END && kind != Kind.EOF && kind != Kind.KW_CASE) {
			statement();
		}
		// need to check for multiple types of statement *
	}

	public void statement() throws Exception {
		// callToken();
		switch (kind) {
		case KW_LET: {
			callToken();
			if (kind == Kind.IDENTIFIER) {
				callToken();
				nameDef();
				if (kind == Kind.ASSIGN) {
					callToken();
					expression();
				}
				if (kind == Kind.SEMI)
					callToken();
				else
					throw new SyntaxException("semi not found 4", line, pos);
			} else
				throw new SyntaxException("invalid 10 token", line, pos);
		}
			break;
		case KW_SWITCH: {
			callToken();
			expression();
			// if(kind == Kind.KW_CASE) {

			while (kind != kind.KW_DEFAULT) {
				callToken();
				expression();
				if (kind == Kind.COLON) {
					callToken();
					if (kind != Kind.KW_CASE)
						block();
					else
						continue;
				} else
					throw new SyntaxException("error syntax", line, pos);
			}
			if (kind == Kind.KW_DEFAULT) {
				callToken();
				if (kind == Kind.KW_END)
					callToken();
				else {
					block();
					if (kind == Kind.KW_END)
						callToken();

					else
						throw new SyntaxException("end  not found 3", line, pos);
				}
			} else
				throw new SyntaxException("error syntax 2", line, pos);

			// }
			// else
			// throw new SyntaxException("error syntax 6", line, pos);
		}
			break;
		case KW_IF: {
			callToken();
			expression();
			if (kind == Kind.KW_DO) {
				callToken();

				if (kind == Kind.KW_END)
					callToken();
				else {
					block();
					if (kind == Kind.KW_END)
						callToken();
					else
						throw new SyntaxException("error syntax3", line, pos);
				}
			} else
				throw new SyntaxException("error syntax4", line, pos);
		}
			break;
		case KW_WHILE: {
			callToken();
			expression();
			// callToken();
			if (kind == Kind.KW_DO) {
				callToken();

				if (kind == Kind.KW_END)
					callToken();
				else {
					block();
					if (kind == Kind.KW_END)
						callToken();
					else
						throw new SyntaxException("end not found 4", line, pos);
				}
			} else
				throw new SyntaxException("invalid 11 syntax", line, pos);
		}
			break;
		case KW_RETURN: {
			callToken();
			expression();
			if (kind == Kind.SEMI)
				callToken();
			else
				throw new SyntaxException("semi not found 3", line, pos);
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
			// callToken(); //why is this being called? check.
			if (kind == Kind.SEMI)
				callToken();
			else
				throw new SyntaxException("semi not found", line, pos);
		}
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
			callToken();
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
			callToken();
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
		case KW_NIL:
			callToken();
			break;
		case KW_TRUE:
			callToken();
			break;
		case KW_FALSE:
			callToken();
			break;
		case INT_LITERAL:
			callToken();
			break;
		case STRING_LITERAL:
			callToken();
			break;
		case LPAREN: {// call expression() and check RPAREN
			callToken();
			expression();
			// callToken();
			if (kind == Kind.RPAREN)
				callToken();
			else
				throw new SyntaxException("rpran not found", line, pos);
		}
			break;
		case IDENTIFIER: {
			// if has next [ ] then call expression() and check for ] or ( expression ,
			// expression*) OR just identifier ->
			callToken();
			switch (kind) {
			case LSQUARE: {
				callToken();
				expression();
				// callToken();
				if (kind == Kind.RSQUARE)
					callToken();
				else
					throw new SyntaxException("rsquare not found", line, pos);
			}
				break;
			case LPAREN: {
				callToken();
				if (kind == Kind.RPAREN)
					callToken();
				else {
				expression();
				// callToken();
				while (kind == Kind.COMMA) {
					callToken();
					expression();
				}
				if (kind == Kind.RPAREN)
					callToken();
				
				else
					throw new SyntaxException("rpran not found", line, pos);
				}
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

	}

	public void type() throws Exception {
		// callToken();
		switch (kind) {
		case KW_INT:
			callToken();
			break;
		case KW_STRING:
			callToken();
			break;
		case KW_BOOLEAN:
			callToken();
			break;
		case KW_LIST: {
			callToken();
			if (kind == Kind.LSQUARE) {
				callToken();
				// while(kind !=Kind.RSQUARE)
				if (kind == Kind.RSQUARE)
					callToken();
				else {
					type();
					if (kind == Kind.RSQUARE)
						callToken();
					else
						throw new SyntaxException("invalid 14 syntax", line, pos);
				}

			}
		}
			break;
		default:
			throw new SyntaxException("invalid 13 syntax", line, pos);
		}
	}

	@Override
	public void parse() throws Exception {
		// TODO Auto-generated method stub

		// should call nextToken from instance of lexer
		// IPLPLexer lexer = lexerInput.getLexer(input);
		{
			callToken();
			// System.out.println("new token " + token + "token kind" + kind);
			program();
			if (kind == kind.EOF)
				return;
			else
				throw new SyntaxException("invalid 15 syntax", line, pos);
			/// for second token
		}

	}

}
