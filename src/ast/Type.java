package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class Type extends ASTNode implements Comparable {

    //set values for various types 
    public static final int VOID = 0;
    public static final int BOOL = 1;
    public static final int CHAR = 2;
    public static final int INT = 3;
    public static final int CHARP = 4;

    //set locations 
    public static final Type VOID_TYPE = new Type(VOID, null);
    public static final Type BOOL_TYPE = new Type(BOOL, null);
    public static final Type CHAR_TYPE = new Type(CHAR, null);
    public static final Type INT_TYPE = new Type(INT, null);
    public static final Type CHARP_TYPE = new Type(CHAR, 1, null);

    public Type(Token symbol) {
        super(symbol);
        this.typeCode = toTypeCode(symbol);
        this.pointer = 0;
        if (typeCode == CHARP) {
            typeCode = CHAR;
            pointer = 1;
        }
    }

    public Type(int typeCode, Token symbol) {
        this(typeCode, 0, symbol);
    }
    
    public Type(int typeCode, int pointer, Token symbol){
        this(typeCode, pointer, symbol, false);
    }
    
    public Type(Type t){
        this(t.typeCode, t.pointer, t.symbol, t.stat);
    }

    //set type values 
    public Type(int typeCode, int pointer, Token symbol, boolean stat) {
        super(symbol);
        this.typeCode = typeCode;
        this.pointer = pointer;
        this.symbol = symbol;
        this.stat = stat;
    }

    public void setType(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public boolean isPointer() {
        return pointer > 0;
    }

    //lex and return proper type 
    public static int toTypeCode(Token symbol) {
        lexer.SType stype = symbol.getSType();
        switch (stype) {
            case CI:
                return INT;
            case CC:
                return CHAR;
            case CB:
                return BOOL;
            case CS:
                return CHARP;
            default:
                return VOID;
        }
    }
    
    public int getAllocSize(){
        return 1;
    }
    
    public boolean isStatic(){
        return stat;
    }
    
    public void setStatic(boolean aStatic) {
        stat = aStatic;
    }

    //check to see if types are compatiable 
    public boolean isTypeCompatible(Type t) {
        return typeCode == t.typeCode && pointer == t.pointer;
    }

    public String toPolish() {
        StringBuilder sb = new StringBuilder();
        if (isPointer()) {
            for (int i = 0; i < pointer; ++i) {
                sb.append("p");
            }
        }
        sb.append(TYPENAME[typeCode].substring(0,1));
        return sb.toString();
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Type: ");
        sb.append(TYPENAME[getTypeCode()]);
        for (int i = 0; i < pointer; ++i) {
            sb.append("*");
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(isStatic()){
            sb.append("static ");
        }
        for(int i=0;i<pointer;++i){
            sb.append("*");
        }
        sb.append(TYPENAME[typeCode]);
        return sb.toString();
    }
    
    @Override
    public int compareTo(Object o) {
        if(o==null){
            return 1;
        }
        if(!(o instanceof Type)){
            return 1;
        }
        Type ot = (Type)o;
        return typeCode-ot.typeCode;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//
    private int typeCode;
    private int pointer;
    private boolean stat;
    private static final String[] TYPENAME = {"void", "bool", "char", "int", "char*",};
    
    

    

    
}
