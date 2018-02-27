/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;
import parser.Scope;
import parser.SymbolTable;

/**
 *
 * @author Alan
 */
public abstract class ASTNode {
    
    public ASTNode(){
        this(null);
    }
    
    //set value for symbol 
    public ASTNode(Token symbol){
        this.symbol = symbol;
    }
    
    //returns value of symbol
    public Token getSymbol(){
        return symbol;
    }
    
    //set symbol equal to s 
    public void setSymbol(Token s){
        symbol = s;
    }
    
    public abstract void typeCheck(ArrayList<String> msgs);
    public abstract void generate(ArrayList<String> code, boolean dynamic); 
    
    //indent string to correct format
    protected String indent(int amount){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<amount;++i){
            sb.append(" ");
        }
        return sb.toString();
    }
    
    //return string
    public String format(int indent){
        return format(indent, false);
    }
    
    public abstract String format(int indent, boolean suppressNewline);
    
    //
    protected Token symbol;

    
}
