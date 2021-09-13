package edu.ufl.cise.plpfa21.assignment1;

public class Lexer implements IPLPLexer {
	public String input;
	public String parentInput;
	
	public Lexer(String input) {
		this.input = input;
		this.parentInput = input;
	}

	@Override
	public IPLPToken nextToken() throws LexicalException {
		String s = "", sample = "int i = i+7;";
		int flag = 0, i = 0;
		if (input.charAt(0) == '\n' || (input.charAt(0) == '\r' && input.charAt(1) == '\n')) {
			s += input.charAt(0);
			return new Tokens(s, parentInput);
		} else if (input.charAt(0) == ' ') {
			while (input.charAt(i) == ' ') {
				// s=s+input.charAt(i);
				i++;
			}
		}

		for (; i < input.length()-1; i++) {
			// if regex input break; s=token; break;else sthen go to switch
			switch (input.charAt(i)) {
			case ' ':
			case '\n':
				// case \r\n:
			case '\t': {
				input = input.substring(i + 1);
				flag = 1;
			}
				break;
			case '&': {
				s += input.charAt(i);
				if (input.charAt(i + 1) == '&') {
					s += input.charAt(i + 1);
					input = input.substring(i + 2);
					return new Tokens(s, parentInput);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput);
			}
			case '|': {
				s += input.charAt(i);
				if (input.charAt(i + 1) == '|') {
					s += input.charAt(i + 1);
					input = input.substring(i + 2);
					return new Tokens(s, parentInput);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput);
			}
			case '=': {
				s += input.charAt(i);
				if (input.charAt(i + 1) == '=') {
					s += input.charAt(i + 1);
					input = input.substring(i + 2);
					return new Tokens(s, parentInput);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput);
			}
			case '!': {
				s += input.charAt(i);
				if (input.charAt(i + 1) == '=') {
					s += input.charAt(i + 1);
					input = input.substring(i + 2);
					return new Tokens(s, parentInput);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput);
			}
			case '*', '/', '+', '-', ';', ',', ':', '(', ')', '[', ']', '<', '>': {
				s += input.charAt(i);
				input = input.substring(i + 1);
				return new Tokens(s, parentInput);
			}
			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9': {
				s += input.charAt(i);
				if (((int) input.charAt(i + 1) >= 97 && (int) input.charAt(i + 1) <= 122)
						|| ((int) input.charAt(i + 1) >= 65 && (int) input.charAt(i + 1) <= 90)) {
					input = input.substring(i + 1);
					return new Tokens(s, parentInput);
				}
				break;
			}
			default:
				s += input.charAt(i);
				break;
			}
			if (flag == 1) {
				break;
			}
		}
		System.out.println(s);
		return new Tokens(s, parentInput);
	}
}
