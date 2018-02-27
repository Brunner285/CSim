/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ptn;

import ast.ASTNode;

/**
 *
 * @author Lab Admin
 */
public class Program extends PTNode {

    public Program() {
        super(2);
    }

    public Functs getFuncts(){
        return (Functs)children.get(0);
    }

    public void setFuncts(Functs f){
        children.set(0,f);
    }

    public Block getBody(){
        return (Block)children.get(1);
    }

    public void setBody(Block b){
        children.set(1,b);
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        if(getFuncts()!=null){
            sb.append(getFuncts().format(indent));
        }
        sb.append("main ()");
        sb.append(getBody().format(indent+5));
        return sb.toString();
    }
    
    @Override
    public ASTNode toAST(){
        ast.Program p = new ast.Program();
        if(getFuncts()!=null){
            p.setFuncts(getFuncts().toFunctions());
        }
        if(getBody()!=null){
            p.setBody(getBody().toAST());
        }
        return p;
    }

}
