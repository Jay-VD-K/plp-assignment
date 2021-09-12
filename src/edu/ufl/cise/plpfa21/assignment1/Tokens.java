package edu.ufl.cise.plpfa21.assignment1;

public class Tokens implements IPLPToken {

	String input, parentInput;
	public Tokens(String input, String parentInput) {
		this.input= input;
		this.parentInput= parentInput;
	}

	@Override
	public Kind getKind() {
		if(parentInput == """

				""")
			return Kind.EOF;
		
		else
			return Kind.IDENTIFIER;
	}

	@Override
	public String getText() {
		String s="";
		for(int i=0;i<input.length();i++)
			if(input.charAt(i)!=' ')
				s=s+input.charAt(i);
		return s;
	}

	@Override
	public int getLine() {
		int l = parentInput.indexOf(input);
		int count=1;
		for(int i=0;i<l;i++) {
			if((int)parentInput.charAt(i)==10)
				count++;
		}
		return count;
			
	}

	@Override
	public int getCharPositionInLine() {
		for(int i=0;i<input.length();i++)
			if(input.charAt(i)!=' ')
				return i;
		return input.length()-1;
	}

	@Override
	public String getStringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntValue() {
		// TODO Auto-generated method stub
		return 0;
	}

}
