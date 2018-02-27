package ptn;

import parser.Scope;

/**
 *
 * @author Lab Admin
 */
public class Funct extends PTNode {

    public Funct() {
        super(4);
    }

    public Type getType() {
        return (Type) children.get(0);
    }

    public void setType(Type t) {
        children.set(0, t);
    }

    public Name getName() {
        return (Name) children.get(1);
    }

    public void setName(Name n) {
        children.set(1, n);
    }

    public String getNameMangle() {
        if (getParamList() == null) {
            return getName().toString() + "$";
        } else {
            return getName().toString() + getParamList().toPolish();
        }
    }

    public ParamList getParamList() {
        return (ParamList) children.get(2);
    }

    public void setParamList(ParamList p) {
        children.set(2, p);
    }

    public Block getBody() {
        return (Block) children.get(3);
    }

    public void setBody(Block b) {
        children.set(3, b);
    }

    public boolean isHeader() {
        return getBody().isEmpty();
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append(getType().format(indent));
        sb.append(" ");
        //sb.append(getName().format(indent));
        sb.append(getName());
        sb.append("(");
        if (getParamList() != null) {
            sb.append(getParamList().format(indent));
        }
        sb.append(")");
        sb.append(getBody().format(indent + 5));
        return sb.toString();
    }

    @Override
    public ast.ASTNode toAST() {
        ast.Function f = new ast.Function();
        f.setName(getName().toAST());
        f.setType(getType().toAST());
        f.setScope(getScope());
        if (getParamList() != null) {
            f.setParams(getParamList().toParamList());
        }
        if (getBody().isEmpty()) {
            f.setHeader(true);
        } else {
            f.setBody(getBody().toAST());
        }
        return f;
    }
    //
    private Scope scope;
}
