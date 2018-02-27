/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import lexer.Token;

/**
 *
 * @author Alan
 */
public abstract class Expression extends ASTNode {

    public Expression(Token symbol) {
        super(symbol);
    }

    public Expression() {
        
    }
    
    public abstract Type getType();

    public String toPolish() {
        return "_" + getType().toPolish();
    }
    
    
    
}
