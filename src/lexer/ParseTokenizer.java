package lexer;

import java.util.Stack;

/**
 * This class uses a custom StringTokenizer to facilitate lexigraphical analysis
 * of a token string. It does two major things beyond what the StringTokenizer
 * is doing: first, it implements a peek stack for token look-ahead; and second,
 * it handles the special two-symbol operators (combining two operators into a
 * single operator in the case of '==','>=','<=','!=','++' and '--'.
 *
 * @author Alan
 */
public class ParseTokenizer {

    public ParseTokenizer() {
        this("");
    }

    public ParseTokenizer(String str) {
        this(str, null);
    }

    public ParseTokenizer(String str, String[] delims) {
        this(str, delims, false);
    }

    public ParseTokenizer(String str, String[] delims, boolean rdels) {
        this(str, delims, rdels, false);
    }

    public ParseTokenizer(String str, String[] delims, boolean rdels, boolean igwhite) {
        // strip off end of line comments
        if (str.contains("//")) {
            str = str.substring(0, str.indexOf("//"));
        }
        st = new StringTokenizer(str, delims, rdels, igwhite);
        //peekStack = new Stack<String>();
        peekStr = null;
    }

    public ParseTokenizer(ParseTokenizer pt) {
        st = new StringTokenizer(pt.st);
        //peekStack = (Stack<String>) pt.peekStack.clone();
        peekStr = pt.peekStr;
    }

    public boolean hasMoreTokens() {
        return (peekStr != null) || st.hasMoreTokens();
    }

    public String nextToken() {
        String token;
        if (peekStr != null) {
            token = peekStr;
            peekStr = null;
        } else if (st.hasMoreTokens()) {
            token = st.nextToken();
        } else {
            return null;
        }
        return token;
    }
    
    public String peekToken() {
        String token;
        if (peekStr != null) {
            token = peekStr;
        } else if (st.hasMoreTokens()) {
            token = st.nextToken();
        } else {
            return null;
        }
        peekStr = token;
        return token;
    }

    public int getStartPos() {
        return st.getLastStartPos();
    }
    //
    private StringTokenizer st;
    private String peekStr;
    
}
