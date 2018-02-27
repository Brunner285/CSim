/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import ast.ASTNode;
import java.util.ArrayList;
import java.util.Iterator;
import lexer.Token;

/**
 *
 * @author Lab Admin
 */
public abstract class PTNode {

    public PTNode() {
        this(0);
    }

    public PTNode(int numChildren) {
        this(null, new ArrayList<PTNode>(), numChildren);
    }

    public PTNode(PTNode parent, ArrayList<PTNode> children) {
        this(parent, children, 0);
    }

    public PTNode(PTNode parent, ArrayList<PTNode> children, int numChildren) {
        this.parent = parent;
        this.children = children;
        this.symbol = null;
        initChildren(numChildren);
    }
    
    public PTNode(Token symbol){
        this.symbol = symbol;
        this.parent = null;
        this.children = null;
        
    }

    public ArrayList<PTNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<PTNode> children) {
        this.children = children;
    }

    public void addChild(PTNode child) {
        children.add(child);
    }

    public PTNode getParent() {
        return parent;
    }

    public void setParent(PTNode parent) {
        this.parent = parent;
    }

    public Token getSymbol() {
        return symbol;
    }

    public void setSymbol(Token symbol) {
        this.symbol = symbol;
    }

    private void initChildren(int numChilds) {
        for (int i = 0; i < numChilds; ++i) {
            boolean add = children.add(null);
        }
    }
    
    protected String indent(int amount){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<amount;++i){
            sb.append(" ");
        }
        return sb.toString();
    }
    
    public String format(int indent){
        return format(indent, false);
    }

    public String format(int indent, boolean suppressNewline) {
        StringBuilder sb = new StringBuilder();
        Iterator<PTNode> nodes = getChildren().iterator();
        if (nodes != null) {
            while (nodes.hasNext()) {
                sb.append(indent(indent));
                sb.append(nodes.next().format(indent));
                if(!suppressNewline){
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
    
    public abstract ASTNode toAST();
    //
    protected PTNode parent;
    protected ArrayList<PTNode> children;
    protected Token symbol;
}
