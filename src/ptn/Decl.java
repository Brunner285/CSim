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
public abstract class Decl extends PTNode {

    public Decl(int numChildren) {
        super(numChildren);
    }

    public Decl() {
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
    
    public boolean hasInit(){
        return children.get(2)!=null;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    public abstract PTNode getInit();
    public abstract void setInit(PTNode list);
    private Scope scope;
    
}
