/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

import java.util.regex.Pattern;

/**
 *
 * @author alan.whitehurst
 */
public enum SType {

    ER, KW, TY, ID, CI, CB, CC, CS, OP, PC;

    @Override
    public String toString() {
        return getSTypeName(this);
    }

    public static String getSTypeName(SType s) {
        return stname[s.ordinal()];
    }

    public static SType getType(String token) {
        if (isKeyword(token)) {
            return KW;
        }
        if (isType(token)) {
            return TY;
        }
        if (isBool(token)) {
            return CB;
        }
        if (isIdentifier(token)) {
            return ID;
        }
        if (isInt(token)) {
            return CI;
        }
        if (isChar(token)) {
            return CC;
        }
        if (isString(token)) {
            return CS;
        }
        if (isOperator(token)) {
            return OP;
        }
        if (isPunct(token)) {
            return PC;
        }
        return ER;
    }

    public static boolean isKeyword(String token) {
        for (String s : keywords) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isType(String token) {
        for (String s : types) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIdentifier(String token) {
        return idPattern.matcher(token).matches();
    }

    public static boolean isInt(String token) {
        return ciPattern.matcher(token).matches();
    }

//    public static boolean isFloat(String token) {
//        return cfPattern.matcher(token).matches();
//    }

    public static boolean isChar(String token) {
        return token.startsWith("'") && token.endsWith("'");
    }

    public static boolean isBool(String token) {
        return token.equals("false") || token.equals("true");
    }

    public static boolean isString(String token) {
        return token.startsWith("\"") && token.endsWith("\"");
    }

    public static boolean isLiteral(String token) {
        return isInt(token) || isChar(token)
                || isBool(token) || isString(token)
//                || isFloat(token)
                ;
    }

    public static boolean isEquOp(String token) {
        for (String s : equOps) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRelOp(String token) {
        for (String s : relOps) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAddOp(String token) {
        for (String s : addOps) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMulOp(String token) {
        for (String s : mulOps) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLogOp(String token) {
        for (String s : logOps) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOthOp(String token) {
        for (String s : othOps) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isUniOp(String token) {
        for (String s : uniOps) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isOperator(String token) {
        return (isEquOp(token) || isRelOp(token) || isAddOp(token)
                || isMulOp(token) || isLogOp(token) || isOthOp(token));
    }

    public static boolean isDop(String token) {
        for (String s : dops) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPunct(String token) {
        for (String s : puncts) {
            if (s.equals(token)) {
                return true;
            }
        }
        return false;
    }
    //
    // private variables and constants
    //
    private static final String[] stname = {
        "Error", "Keyword", "Type", "Identifier", "Integer Constant",
        "Boolean Constant", "Character Constant",
        "String Constant", "Operator", "Punctuation"};
    private static final String[] keywords = {"else", "for", "if",  "main",
        "return", "static", "while",};
    private static final String[] types = {"int", //"float",
        "char", "bool", "void",};
    private static final String idPatternRE = "[a-zA-Z][a-zA-Z0-9_]*";
    private static final Pattern idPattern = Pattern.compile(idPatternRE);
    private static final String ciPatternRE = "[0-9]+";
    private static final Pattern ciPattern = Pattern.compile(ciPatternRE);
//    private static final String cfPatternRE = "[0-9]+[.][0-9]+";
//    private static final Pattern cfPattern = Pattern.compile(cfPatternRE);
    private static final String[] equOps = {"==", "!="};
    private static final String[] relOps = {">", "<", ">=", "<="};
    private static final String[] addOps = {"+", "-"};
    private static final String[] mulOps = {"*", "/", "%"};
    private static final String[] logOps = {"!", "&&", "||"};
    private static final String[] othOps = {"=", "*", "&", "++", "--"};
    private static final String[] uniOps = {"!", "&", "*", "-", "++", "--"};
    private static final String[] dops = {"=", "<", ">", "!", "+", "-",};
    private static final String[] puncts = {",", ";", "[", "]",
        "{", "}", "(", ")",};
}
