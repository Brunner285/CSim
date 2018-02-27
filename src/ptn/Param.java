/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import parser.Scope;

/**
 *
 * @author Alan
 */
public class Param extends PTNode {
    
    public Param(){
        super(2);
    }
    
    public Type getType(){
        return (Type) children.get(0);
    }
    
    public void setType(Type t){
        children.set(0,t);
    }
    
    public Name getName(){
        return (Name) children.get(1);
    }
    
    public void setName(Name name){
        children.set(1, name);
    }
    
    public String toPolish(){
        return "_" + getType().toPolish();
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
        sb.append(getType().format(indent));
        sb.append(" ");
        sb.append(getName().format(indent));
        return sb.toString();
    }
    
    @Override
    public ast.Param toAST(){
        ast.Param param = new ast.Param();
        param.setType(getType().toAST());
        param.setVariable(getName().toAST());
        param.setScope(getScope());
        return param;
    }
    
    private Scope scope;
}
