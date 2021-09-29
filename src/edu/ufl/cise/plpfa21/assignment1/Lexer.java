package edu.ufl.cise.plpfa21.assignment1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer implements IPLPLexer {
	public String input;
	public String parentInput;
	int pos = 0;
	int line =1;

	public Lexer(String input) {
		this.input = input;
		this.parentInput = input;
	}

	public int checkSpace(String input, int i) {
		while (input.charAt(i) == ' ') {
			i++;
		}
		//input=input.substring(i);
		//i=0;
		return i;
	}

	public int removeComment(String input, int i) throws LexicalException {
		
		return i;

	}

	@Override
	public IPLPToken nextToken() throws LexicalException {
		int startPos = pos;
		int startLine =line;
		String s = "";
		int flag = 0, i = 0, count = 0;
		if (input.charAt(0) == '\n') {
			s += input.charAt(0);
			pos = 0;
			line++;
			return new Tokens(s, parentInput, startPos, startLine);
		} else if ((input.charAt(0) == '\r' && input.charAt(1) == '\n')) {
			s += input.charAt(0);
			s += input.charAt(1);
			pos = 0;
			line++;
			return new Tokens(s, parentInput, startPos, startLine);
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
				line++;
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
					return new Tokens(s, parentInput, startPos, startLine);
				} else {
					int lineNumber = new Tokens(s, parentInput, startPos,startLine).getLine();
					int posNumber = new Tokens(s, parentInput, startPos,startLine).getCharPositionInLine();
					throw new LexicalException("invalid token", lineNumber, posNumber);
				}
			}
			case '|': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '|') {
					s += input.charAt(i + 1);
					pos++;
					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos, startLine);
				} else
				{
					int lineNumber = new Tokens(s, parentInput, startPos, startLine).getLine();
					int posNumber = new Tokens(s, parentInput, startPos, startLine).getCharPositionInLine();
					throw new LexicalException("invalid token", lineNumber, posNumber);
				}
			}
			case '=': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '=') {
					s += input.charAt(i + 1);
					pos++;

					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos, startLine);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos, startLine);
			}
			case '!': {
				s += input.charAt(i);
				pos++;

				if (input.charAt(i + 1) == '=') {
					s += input.charAt(i + 1);
					pos++;

					input = input.substring(i + 2);
					return new Tokens(s, parentInput, startPos, startLine);
				}
				input = input.substring(i + 1);
				return new Tokens(s, parentInput, startPos, startLine);
			}
			case '*', '+', '-', ';', ',', ':', '(', ')', '[', ']', '<', '>': {
				s += input.charAt(i);
				pos++;

				input = input.substring(i + 1);
				if(input.charAt(0)=='\n' && input.length()!=1)
				{i=i-1;
				//line++;
				//pos=0;
					break;}
				else
				return new Tokens(s, parentInput, startPos, startLine);
			}

			case '/': {
				if (input.charAt(i) == '/' && input.charAt(i + 1) == '*') {
					//i = removeComment(input, i) - 1;
					//int temp=i;
					////////////////////////////////
					i += 2;
					pos +=2;
					try {
						while (input.charAt(i) != '*' && input.charAt(i + 1) != '/') {
							i++;
							pos++;
							//input=input.substring(pos);
						}
						input=input.substring(i+2);
						i=0;
					} catch (Exception e) {
						int lineNumber = new Tokens(s, parentInput, startPos,startLine).getLine();
						int posNumber = new Tokens(s, parentInput, startPos,startLine).getCharPositionInLine();
						throw new LexicalException("invalid comment", lineNumber, posNumber);
					}
					//i += 2;
					pos +=2;
					if (input.charAt(i) == ' ') {
						i = checkSpace(input, i)-1;
					}
					///////////////////////////////
					
					//pos = i + 1;
					startPos = pos + 1;
					// input=input.substring(pos,input.length()-1);
					continue;
				} else {
					s += input.charAt(i);
					pos++;

					input = input.substring(i + 1);
					return new Tokens(s, parentInput, startPos, startLine);
				}

			}
			case '\"': {
				int temp=0;
				if (input.length() >= 2) {
					Pattern stringPattern = Pattern.compile("(\"[^\"]*\")");
					Matcher match = stringPattern.matcher(input);
					if (match.find()) {
						s += match.group(1);
						
						input = input.substring(i+s.length(), input.length());
						pos += s.length();
						//i=pos-1;
						if(input.charAt(0)=='\n' && input.length()!=1) {
							input = input.substring(1);
							pos = 0;
							line++;
							flag = 1;
						}
						for(int a=0; a<s.length()-1; a++) {
							if(s.charAt(a)=='\n') {
								pos=0;
								line++;
								temp=a;
							}
						}
						while(s.charAt(temp)!='\"') {
							pos++;
							temp++;
						}
						
						return new Tokens(s, parentInput, startPos, startLine);

					} else {
						//throw new LexicalException("incomplete string literal", 1, 1);
						return new Tokens("\n", parentInput, startPos, startLine);
					}
				} else {
					int lineNumber = new Tokens(s, parentInput, startPos,line).getLine();
					int posNumber = new Tokens(s, parentInput, startPos,line).getCharPositionInLine();
					throw new LexicalException("Invalid token", lineNumber, posNumber);
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
						if(input.charAt(0)=='\n' && input.length()!=1) {
							input = input.substring(1);
							pos = 0;
							line++;
							flag = 1;
						}
						for(int a=0; a<s.length()-1; a++) {
							if(s.charAt(a)=='\n')
								{pos=0;
							line++;}
						}
						return new Tokens(s, parentInput, startPos, startLine);

					} else {
						return new Tokens("\n", parentInput, startPos,startLine);
					}
				} else {
					int lineNumber = new Tokens(s, parentInput, startPos,line).getLine();
					int posNumber = new Tokens(s, parentInput, startPos, line).getCharPositionInLine();
					throw new LexicalException("Invalid token", lineNumber, posNumber);
				}
			}

			case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9': {
				s += input.charAt(i);
				pos++;

				if (((int) input.charAt(i + 1) >= 97 && (int) input.charAt(i + 1) <= 122)
						|| ((int) input.charAt(i + 1) >= 65 && (int) input.charAt(i + 1) <= 90)) {
					input = input.substring(i + 1);
					// pos++;
					if(input.charAt(0)=='\n' && input.length()!=1)
					{i=-1;
					//pos=0;
					//line++;
						break;}
					else
					return new Tokens(s, parentInput, startPos, startLine);
					//return new Tokens(s, parentInput, startPos, line);
				}
				switch (input.charAt(i + 1)) {
				case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9': {
					count++;
					if (count > 10) {
						count = 0;
						int lineNumber = new Tokens(s, parentInput, startPos, line).getLine();
						int posNumber = new Tokens(s, parentInput, startPos, line).getCharPositionInLine();
						throw new LexicalException("integer outofbound", lineNumber, posNumber);
					}
					continue;
				}
				default: {
					input = input.substring(i + 1);
					if(input.charAt(0)=='\n' && input.length()!=1)
					{i=-1;
					//pos=0;
					//line++;
						break;}
					else
					return new Tokens(s, parentInput, startPos, startLine);
					//return new Tokens(s, parentInput, startPos, line);
				}
				}
			}
			break;
			default:
				if (((int) input.charAt(i) >= 97 && (int) input.charAt(i) <= 122)
						|| ((int) input.charAt(i) >= 65 && (int) input.charAt(i) <= 90) || ((int) input.charAt(i) == 95)
						|| ((int) input.charAt(i) == 36)) {
					switch (input.charAt(i + 1)) {

					case '*', '/', '+', '-', ';', ',', ':', '(', ')', '[', ']', '<', '>', '=', '&', '|', '!', '?', '@', '#', '.', '^', '%', '`', '~': {
						s += input.charAt(i);
						pos++;

						input = input.substring(i + 1);
						return new Tokens(s, parentInput, startPos, startLine);
					}
					default:

						if (input.charAt(i + 1) == '\n' && i == input.length() - 2) {
							{
								s += input.charAt(i);
								pos = 0;
								line++;

								input = input.substring(i + 1);
								return new Tokens(s, parentInput, startPos, startLine);
							}
						}

						s += input.charAt(i);
						pos++;

						break;
					}

				} else {
					int lineNumber = new Tokens(s, parentInput, startPos, startLine).getLine();
					int posNumber = new Tokens(s, parentInput, startPos,line).getCharPositionInLine();
					throw new LexicalException("invalid token", lineNumber, posNumber);
				}
			}

			if (flag == 1) {
				break;
			}
		}
		return new Tokens(s, parentInput, startPos, startLine);
	}
}
