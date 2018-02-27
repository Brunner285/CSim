/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import lexer.Token;

/**
 *
 * @author Alan
 */
public class Type extends PTNode {

    public Type(Token s) {
        this.symbol = s;
        pointer = 0;
    }

    public boolean isPointer() {
        return pointer>0;
    }
    
    public int getPointer(){
        return this.pointer;
    }
    
    public void incPointer(){
        ++pointer;
    }

    public void setPointer(int n) {
        this.pointer = n;
    }

    public boolean isStatic() {
        return stat;
    }
    
    
    public void setStatic(boolean aStatic) {
        stat = aStatic;
    }
    
    public String toPolish(){
        StringBuilder sb = new StringBuilder();
        if(isPointer()){
            for(int i=0;i<pointer;++i){
                sb.append("p");
            }
        }
        sb.append(symbol.getSymbol().substring(0, 1));
        return sb.toString();
    }
    
    @Override
    public String toString(){
        if (!isPointer()) {
            return getSymbol().getSymbol();
        } else {
            StringBuilder pointerString = new StringBuilder();
            for(int i=0;i<pointer;++i){
                pointerString.append('*');
            }
            return getSymbol().getSymbol() + " " + pointerString.toString();
        }
    }

    @Override
    public String format(int indent) {
        return toString();
    }

    @Override
    public ast.Type toAST() {
        ast.Type retVal = null;
        switch (symbol.getSymbol()) {
            case "int":
                retVal = new ast.Type(ast.Type.INT, pointer, symbol);
                break;
            case "char":
                retVal = new ast.Type(ast.Type.CHAR, pointer, symbol);
                break;
            case "bool":
                retVal = new ast.Type(ast.Type.BOOL, pointer, symbol);
                break;
            default:
                retVal = new ast.Type(ast.Type.VOID, pointer, symbol);
        }
        retVal.setStatic(isStatic());
        return retVal;
    }
    
    private int pointer;
    private boolean stat;
    
}
