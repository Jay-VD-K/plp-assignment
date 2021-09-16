package edu.ufl.cise.plpfa21.assignment1;

public class Tokens implements IPLPToken {

	String input, parentInput;
	int pos;
	static int length = 0;

	public Tokens(String input, String parentInput, int pos) {
		this.input = input;
		this.parentInput = parentInput;
		this.pos = pos;
	}

	String s = parentInput;

	@Override
	public Kind getKind() {
		String s = input;
		Character c = s.charAt(0);
		String start = c.toString();
		String remain = s.substring(1, s.length());

		if (start.matches("[a-zA-Z_$]") && remain.matches("[a-zA-Z_$0-9]+")) {

			switch (s) {
			case "fun":
				return Kind.KW_FUN;

			case "do":
				return Kind.KW_DO;

			case "end":
				return Kind.KW_END;

			case "let":
				return Kind.KW_LET;

			case "switch":
				return Kind.KW_SWITCH;

			case "case":
				return Kind.KW_CASE;

			case "default":
				return Kind.KW_DEFAULT;

			case "if":
				return Kind.KW_IF;

			case "else":
				return Kind.KW_ELSE;

			case "while":
				return Kind.KW_WHILE;

			case "return":
				return Kind.KW_RETURN;

			case "list":
				return Kind.KW_LIST;

			case "var":
				return Kind.KW_VAR;

			case "val":
				return Kind.KW_VAL;

			case "nil":
				return Kind.KW_NIL;

			case "true":
				return Kind.KW_TRUE;

			case "false":
				return Kind.KW_FALSE;

			case "int":
				return Kind.KW_INT;

			case "String":
				return Kind.KW_STRING;

			case "boolean":
				return Kind.KW_BOOLEAN;

			case "float":
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
				count++;
		}
		return count;

	}

	@Override
	public int getCharPositionInLine() {
		return pos;
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
