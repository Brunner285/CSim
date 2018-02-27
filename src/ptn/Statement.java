/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public abstract class Statement extends PTNode {

    public Statement(Token symbol) {
        super(symbol);
    }

    public Statement(PTNode parent, ArrayList<PTNode> children, int numChildren) {
        super(parent, children, numChildren);
    }

    public Statement(PTNode parent, ArrayList<PTNode> children) {
        super(parent, children);
    }

    public Statement(int numChildren) {
        super(numChildren);
    }

    public Statement() {
    }
    
    
    
}
