package edu.ufl.cise.plpfa21.assignment2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ufl.cise.plpfa21.assignment2.PLPTokenKinds.Kind;

public class Parser implements IPLPParser {
	public Lexer lexerInput;
	//public String parentInput;
	//int pos = 0;
	
	public Parser(Lexer input) {
		this.lexerInput = input;
	//	this.parentInput = input;
	}
	
	public void function() {
		
	}
	
	public void block() {
		
	}
	
	public void statement() {
		
	}
	
	public void nameDef() {
		
	}
	
	public void expression() {
		
	}
	
	public void logicalExpression() {
		
	}
	
	public void comparisionExpression() {
		
	}
	
	public void additiveExpression() { 
		
	}
	
	public void multiplicativeExpression() {
		
	}
	
	public void unaryExpression() {
		
	}
	
	public void primaryExpression() {
		
	}
	
	public void type() {
		
	}
	
	@Override
	public void parse() throws Exception {
		// TODO Auto-generated method stub
		
		//should call nextToken from instance of lexer
		//IPLPLexer lexer = lexerInput.getLexer(input);
		{
			IPLPToken token = lexerInput.nextToken();
			Kind kind = token.getKind();
			System.out.println("new token "+token+"token kind"+kind);
			///for second token
			
			switch (kind) {
			case KW_FUN : function(); break;
			case KW_VAR : {
				nameDef();
				//nextToken() should be either a LPRAN or Assign 
				//expression();
				//should end with semi colon;
				}
			break;
			case KW_VAL : {
							nameDef();
							//nextToken() should be Assign 
							//expression();
							//should end with semi colon
					}
					break;
			default: 
				throw new SyntaxException("invalid token", 1, 1);
			
			}
		}
		
		
		
	}

}
