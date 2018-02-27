/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ptn;

import parser.Scope;

/**
 *
 * @author Lab Admin
 */
public class Block extends Statement {
    
    public Block(){
        super(1);
    }
    
    public DeclStats getBody(){
        return (DeclStats) children.get(0);
    }
    
    public void setBody(DeclStats s){
        children.set(0, s);
    }
    
    public boolean isEmpty(){
        return getBody().isEmpty();
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(getBody().format(indent));
        sb.append(indent(Math.max(indent-5,0)));
        sb.append("}\n");
        return sb.toString();
    }
    
    @Override
    public ast.Block toAST(){
        ast.Block block = new ast.Block();
        block.setDecls(getBody().toDeclarations());
        block.setStats(getBody().toStatements());
        block.setScope(getScope());
        return block;
    }
    //
    private Scope scope;
}
