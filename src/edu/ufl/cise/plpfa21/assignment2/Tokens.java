package edu.ufl.cise.plpfa21.assignment2;

public class Tokens implements IPLPToken {

	String input, parentInput;
	int pos;
	static int length = 0;

	public Tokens(String input, String parentInput, int pos) {
		this.input = input;
		this.parentInput = parentInput;
		this.parent = parentInput;
		this.pos = pos;
	}

	String parent = parentInput;
	String s = parentInput;
	
	

	@Override
	public Kind getKind() {
		String s = input;
		Character c = s.charAt(0);
		String start = c.toString();
		String remain = s.substring(1, s.length());

		if (start.matches("[a-zA-Z_$]") && remain.matches("[a-zA-Z_$0-9]+")) {

			switch (s) {
			case "FUN":
				return Kind.KW_FUN;

			case "DO":
				return Kind.KW_DO;

			case "END":
				return Kind.KW_END;

			case "LET":
				return Kind.KW_LET;

			case "SWITCH":
				return Kind.KW_SWITCH;

			case "CASE":
				return Kind.KW_CASE;

			case "DEFAULT":
				return Kind.KW_DEFAULT;

			case "IF":
				return Kind.KW_IF;

			case "ELSE":
				return Kind.KW_ELSE;

			case "WHILE":
				return Kind.KW_WHILE;

			case "RETURN":
				return Kind.KW_RETURN;

			case "LIST":
				return Kind.KW_LIST;

			case "VAR":
				return Kind.KW_VAR;

			case "VAL":
				return Kind.KW_VAL;

			case "NIL":
				return Kind.KW_NIL;

			case "TRUE":
				return Kind.KW_TRUE;

			case "FALSE":
				return Kind.KW_FALSE;

			case "INT":
				return Kind.KW_INT;

			case "STRING":
				return Kind.KW_STRING;

			case "BOOLEAN":
				return Kind.KW_BOOLEAN;

			case "FLOAT":
				return Kind.KW_FLOAT;

			default:
				return Kind.IDENTIFIER;

			}
		} else if (s.matches("[0-9]+")) {
			return Kind.INT_LITERAL;

		} else if (start.matches("[a-zA-Z_$]"))
			return Kind.IDENTIFIER;

		else {
			switch (s) {

			case ":":
				return Kind.COLON;

			case ",":
				return Kind.COMMA;

			case "=":
				return Kind.ASSIGN;

			case ";":
				return Kind.SEMI;

			case "(":
				return Kind.LPAREN;

			case ")":
				return Kind.RPAREN;

			case "[":
				return Kind.LSQUARE;

			case "]":
				return Kind.RSQUARE;

			case "&&":
				return Kind.AND;

			case "||":
				return Kind.OR;

			case "<":
				return Kind.LT;

			case ">":
				return Kind.GT;

			case "==":
				return Kind.EQUALS;

			case "!=":
				return Kind.NOT_EQUALS;

			case "+":
				return Kind.PLUS;

			case "-":
				return Kind.MINUS;

			case "*":
				return Kind.TIMES;

			case "/":
				return Kind.DIV;

			case "!":
				return Kind.BANG;

			case "\n":
				return Kind.EOF;

			default: {
				return Kind.STRING_LITERAL;
			}

			}
		}
	}

	@Override
	public String getText() {
		return input;
	}

	@Override
	public int getLine() {
		int l = parentInput.indexOf(input);
		int count = 1;
		for (int i = 0; i < l; i++) {
			if ((int) parentInput.charAt(i) == 10)
			{	count++;
				//pos=0;
			}
			
		}
		return count;

	}

	@Override
	public int getCharPositionInLine() {
		return pos;
		/*int l = parent.indexOf(input);
		parent = parent.substring(input.length(), parent.length());
		pos+=l;
		return pos;*/
	}

	@Override
	public String getStringValue() {
		String s = "";
		for (int i = 1; i < input.length() - 1; i++)
			if (input.charAt(i) != '\\') {
				s = s + input.charAt(i);
			}

		return s;
	}

	@Override
	public int getIntValue() {
		int val = Integer.parseInt(input);
		return val;
	}

}
