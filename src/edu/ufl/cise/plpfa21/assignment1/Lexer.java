package edu.ufl.cise.plpfa21.assignment1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public int removeComment(String input, int i) throws LexicalException {
		i += 2;
		try {
			while (input.charAt(i) != '*' && input.charAt(i + 1) != '/') {
				i++;
				// input=input.substring(i);
			}
		} catch (Exception e) {
			throw new LexicalException("invalid comment", 1, 1);
		}
		i += 2;
		if (input.charAt(i) == ' ') {
			i = checkSpace(input, i);
		}
		return i;

	}

	@Override
	public IPLPToken nextToken() throws LexicalException {
		int startPos = pos;
		String s = "";
		int flag = 0, i = 0, count = 0;
		if (input.charAt(0) == '\n') {
			s += input.charAt(0);
			pos = 0;
			return new Tokens(s, parentInput, startPos);
		} else if ((input.charAt(0) == '\r' && input.charAt(1) == '\n')) {
			s += input.charAt(0);
			s += input.charAt(1);
			pos = 0;
			return new Tokens(s, parentInput, startPos);
		} else if (input.charAt(0) == ' ') {
			i = checkSpace(input, i);
			pos += i;
			startPos += i;
		}

		for (; i < input.length() - 1; i++) {

			switch (input.charAt(i)) {
			case '\n': {
				input = input.substring(i + 1);
				pos = 0;
				flag = 1;
			}
				break;
			case ' ':
			case '\r':
			case '\t': {
				input = input.substring(i + 1);
				pos++;
				flag = 1;
			}
				break;
			case '&': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '&') {
					s += input.charAt(i + 1);
					pos++;
					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos);
				} else
					throw new LexicalException("invalid token", 1, 1);
			}
			case '|': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '|') {
					s += input.charAt(i + 1);
					pos++;
					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos);
				} else
					throw new LexicalException("invalid token", 1, 1);
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
			case '*', '+', '-', ';', ',', ':', '(', ')', '[', ']', '<', '>': {
				s += input.charAt(i);
				pos++;

				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos);
			}

			case '/': {
				if (input.charAt(i) == '/' && input.charAt(i + 1) == '*') {
					i = removeComment(input, i) - 1;
					pos = i + 1;
					startPos = i + 1;
					// input=input.substring(pos,input.length()-1);
					continue;
				} else {
					s += input.charAt(i);
					pos++;

					input = input.substring(i + 1);
					return new Tokens(s, parentInput, startPos);
				}

			}
			case '\"': {
				if (input.length() >= 2) {
					Pattern stringPattern = Pattern.compile("(\"[^\"]*\")");
					Matcher match = stringPattern.matcher(input);
					if (match.find()) {
						s += match.group(1);
						input = input.substring(s.length(), input.length());
						pos += s.length();
						return new Tokens(s, parentInput, startPos);

					} else {
						throw new LexicalException("incomplete string literal", 1, 1);
					}
				} else {
					throw new LexicalException("Invalid token", 1, 1);
				}
			}

			case '\'': {
				if (input.length() >= 2) {
					Pattern stringPattern = Pattern.compile("(\'[^\']*\')");
					Matcher match = stringPattern.matcher(input);
					if (match.find()) {
						s += match.group(1);
						input = input.substring(s.length(), input.length());
						pos += s.length();
						return new Tokens(s, parentInput, startPos);

					} else {
						throw new LexicalException("incomplete string literal", 1, 1);
					}
				} else {
					throw new LexicalException("Invalid token", 1, 1);
				}
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
						|| ((int) input.charAt(i) >= 65 && (int) input.charAt(i) <= 90) || ((int) input.charAt(i) == 95)
						|| ((int) input.charAt(i) == 36)) {
					switch (input.charAt(i + 1)) {

					case '*', '/', '+', '-', ';', ',', ':', '(', ')', '[', ']', '<', '>', '=', '&', '|', '!', '?', '@', '#', '.', '^', '%', '`', '~': {
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
					throw new LexicalException("invalid token", 1, 1);
				}
			}

			if (flag == 1) {
				break;
			}
		}
		return new Tokens(s, parentInput, startPos);
	}
}
