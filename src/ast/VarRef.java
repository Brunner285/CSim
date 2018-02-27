package ast;

import java.util.ArrayList;
import lexer.Token;
import parser.Scope;

/**
 *
 * @author Alan
 */
public class VarRef extends Expression {

    
    private Name v;
    private Expression index;
    private int indirect = 0;
    private Scope scope;

    public VarRef() {
    }

    public VarRef(Token symbol, Name v, Expression expr) {
        super(symbol);
        this.v = v;
        this.index = expr;
    }

    public boolean isArrayRef() {
        return index != null;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression expr) {
        this.index = expr;
    }

    public Name getVariable() {
        return v;
    }

    public void setVariable(Name v) {
        this.v = v;
    }

    public boolean isIndirect() {
        return indirect > 0;
    }

    public void setIndirect(int indirect) {
        this.indirect = indirect;
    }

    public void incIndirect() {
        this.indirect++;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public String getQualifiedVariableName() {
        return "@" + scope.label() + this.getVariable().getName();
    }
    
    //get relOffset value from scope
    public int getRelativeOffset(){
        return scope.getRelOffset(getVariable().getName());
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[VarRef: ");
        if (isIndirect()) {
            for (int i = 0; i < indirect; ++i) {
                sb.append("*");
            }
        }
        sb.append(this.getVariable().format(indent, true));
        sb.append(" ");
        if (isArrayRef()) {
            sb.append("index: ");
            sb.append(index.format(indent, true));
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<VarRef: ");
        sb.append(v);
        sb.append(" (");
        sb.append(indirect);
        sb.append(")");
        if (index != null) {
            sb.append(" = ");
            sb.append(index);
        }
        sb.append(" >");
        return sb.toString();
    }

    @Override
    public Type getType() {
        return getType(false);
    }
    
    //set pointers 
    public Type getType(boolean isTarget){
        
        Type t = new Type(scope.getType(v.getName()).toAST());
        if(isTarget && this.isIndirect()){
            t.setPointer(t.getPointer()-1);
        }
        if(t.isPointer() && isArrayRef()){
            t.setPointer(t.getPointer()-1);
        }
        System.out.println("Getting type of " + this + " as " + t);
        return t;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        return;
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (!getType().isStatic() && inFunction) {
            if (isArrayRef()) {
                getIndex().generate(code, inFunction);
                code.add("\taddc " + scope.getRelOffset(getVariable().getName()));
                code.add("\tcora");
                code.add("\tldi");
            } else {
                code.add("\tldr " + scope.getRelOffset(getVariable().getName()));
            }
        } else if (isArrayRef()) {
            getIndex().generate(code, inFunction);
            code.add("\taddc " + getQualifiedVariableName());
            code.add("\tldi");
//        } else if (getType().isPointer()) {
//            code.add("\tldc " + getQualifiedVariableName());
        } else {
            code.add("\tld " + getQualifiedVariableName());
        }
    }

}
