package edu.ufl.cise.plpfa21.assignment1;

public class Lexer implements IPLPLexer{

	public String input;
	public String parentInput;
	
	public Lexer(String input) {
		this.input= input;
		this.parentInput= input;
	}
	
	public String next() {
		int i=0; String s="";
		
		if(input.charAt(0)==' ') {
			while(input.charAt(i)==' ') {
				s=s+input.charAt(i);
				i++;
			}
		}
		
		for(;i<input.length();i++) {
			if((int)input.charAt(i)==10 || input.charAt(i)==' ')
				{
					input=input.substring(i+1);
					return s;
					
				}
			else
				s=s+input.charAt(i);
		}
		return s;
	}

	@Override
	public IPLPToken nextToken() throws LexicalException {
		String s= next();
		return new Tokens(s, parentInput);
	}

}
