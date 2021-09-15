package edu.ufl.cise.plpfa21.assignment1;

public class Lexer implements IPLPLexer {
	public String input;
	public String parentInput;
	int pos = 0;

	public Lexer(String input) {
		this.input = input;
		this.parentInput = input;
	}

	public int checkSpace(String input, int i) {
		while (input.charAt(i) == ' ') {
			i++;
		}
		return i;
	}

	@Override
	public IPLPToken nextToken() throws LexicalException {
		int startPos = pos;
		String s = "";
		int flag = 0, i = 0, count = 0;
		if (input.charAt(0) == '\n' || (input.charAt(0) == '\r' && input.charAt(1) == '\n')) {
			s += input.charAt(0);
			pos = 0;
			return new Tokens(s, parentInput, startPos);
		} else if (input.charAt(0) == ' ') {
			i = checkSpace(input, i);
			pos += i;
			startPos += i;
		}
		if (input.charAt(i) == '/' && input.charAt(i + 1) == '*') {
			i += 2;
			while (input.charAt(i) != '*' && input.charAt(i + 1) != '/') {
				i++;
			}
			i += 2;
		}
		if (input.charAt(i) == ' ') {
			i = checkSpace(input, i);
			pos += i;
			startPos += i + 1;
		}

		for (; i < input.length() - 1; i++) {

			switch (input.charAt(i)) {
			case '\n':
				pos = 0;
			case ' ':
			case '\r':
			case '\t': {
				input = input.substring(i + 1);
				flag = 1;
			}
				break;
			case '&': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '&') {
					s += input.charAt(i + 1);

					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos);
			}
			case '|': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '|') {
					s += input.charAt(i + 1);

					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos);
			}
			case '=': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '=') {
					s += input.charAt(i + 1);
					pos++;

					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos);
			}
			case '!': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '=') {
					s += input.charAt(i + 1);
					pos++;

					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos);
			}
			case '*', '/', '+', '-', ';', ',', ':', '(', ')', '[', ']', '<', '>': {
				s += input.charAt(i);
				pos++;

				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos);
			}

			case '"': {
				int j = i + 1;
				s += input.charAt(i);
				pos++;
				for (; j < input.length() - 2; j++) {
					if (input.charAt(j) != '"') {
						s += input.charAt(j);
					} else if (input.charAt(j) == '\\')
						switch (input.charAt(j + 1)) {
						case '\b', '\t', '\n', '\r', '\f', '\'', '\\':
							continue;
						case '\"': {
							if (input.charAt(j + 2) != '"')
								throw new LexicalException("incorrect escape sequence", 1, 1);
						}
						default:
							throw new LexicalException("incorrect escape sequence", 1, 1);
						}
				}
				s += input.charAt(j);

				i = j;
				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos);
			}
			case '\'': {
				int j = i + 1;
				s += input.charAt(i);
				pos++;
				for (; j < input.length() - 2; j++) {
					if (input.charAt(j) != '\'') {
						s += input.charAt(j);
					} else if (input.charAt(j) == '\\')
						switch (input.charAt(j + 1)) {
						case '\b', '\t', '\n', '\r', '\f', '\"', '\'', '\\':
							continue;
						default:
							throw new LexicalException("incorrect escape sequence", 1, 1);
						}
				}
				s += input.charAt(j);

				i = j;
				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos);
			}

			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9': {
				s += input.charAt(i);
				pos++;

				if (((int) input.charAt(i + 1) >= 97 && (int) input.charAt(i + 1) <= 122)
						|| ((int) input.charAt(i + 1) >= 65 && (int) input.charAt(i + 1) <= 90)) {
					input = input.substring(i + 1);
					// pos++;
					return new Tokens(s, parentInput, startPos);
				}
				switch (input.charAt(i + 1)) {
				case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9': {
					count++;
					if (count > 10) {
						count = 0;
						int lineNumber = new Tokens(s, parentInput, startPos).getLine();
						int posNumber = new Tokens(s, parentInput, startPos).getCharPositionInLine();
						System.out.println("error" + lineNumber + posNumber);
						throw new LexicalException("integer outofbound", lineNumber, posNumber);
					}
					continue;
				}
				default: {
					input = input.substring(i + 1);
					return new Tokens(s, parentInput, startPos);
				}
				}
			}
			default:
				if (((int) input.charAt(i) >= 97 && (int) input.charAt(i) <= 122)
						|| ((int) input.charAt(i) >= 65 && (int) input.charAt(i) <= 90)) {
					switch (input.charAt(i + 1)) {

					case '*', '/', '+', '-', ';', ',', ':', '(', ')', '[', ']', '<', '>', '=', '&', '|', '!': {
						s += input.charAt(i);
						pos++;

						input = input.substring(i + 1);
						return new Tokens(s, parentInput, startPos);
					}
					default:

						if (input.charAt(i + 1) == '\n' && i == input.length() - 2) {
							{
								s += input.charAt(i);
								pos = 0;

								input = input.substring(i + 1);
								return new Tokens(s, parentInput, startPos);
							}
						}

						s += input.charAt(i);
						pos++;

						break;
					}
				} else {
					throw new LexicalException("hello", 1, 1);
				}
			}

			if (flag == 1) {
				break;
			}
		}
		return new Tokens(s, parentInput, startPos);
	}
}
