package ptn;

import parser.Scope;

/**
 *
 * @author Alan
 */
public class VarRef extends Expression {
    
    public Name getName(){
        return (Name) children.get(0);
    }
    
    public void setName(Name n){
        children.set(0,n);
    }
    
    public Expression getIndex(){
        return (Expression) children.get(1);
    }
    
    public void setIndex(Expression e){
        children.set(1, e);
    }
    
    public boolean isArrayRef(){
        return getIndex()!=null;
    }
    
    public boolean isIndirect(){
        return indirect>0;
    }
    
    public void setIndirect(int indirect){
        this.indirect = indirect;
    }
    
    public void incIndirect(){
        indirect++;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
    
    @Override
    public String toPolish(){
        StringBuilder sb = new StringBuilder("_");
        for(int i=0;i<scope.getType(getName().toString()).getPointer();++i){
            sb.append("p");
        }
        sb.append(scope.getType(getName().toString()).getSymbol().getSymbol().substring(0,1));
        return sb.toString();
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<indirect;++i){
            sb.append("*");
        }
        sb.append(getName().format(indent));
        if(isArrayRef()){
            sb.append("[");
            sb.append(getIndex().format(indent));
            sb.append("]");
        }
        return sb.toString();
    }
    
    @Override
    public ast.VarRef toAST(){
        ast.VarRef varRef = new ast.VarRef();
        varRef.setVariable(getName().toAST());
        if(this.isArrayRef()){
            varRef.setIndex(this.getIndex().toAST());
        }
        varRef.setIndirect(this.indirect);
        varRef.setScope(this.scope);
        return varRef;
    }
    
    private int indirect;
    private Scope scope;
    
}
