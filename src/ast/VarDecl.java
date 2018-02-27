package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class VarDecl extends Declaration {

    public VarDecl() {
    }

    public VarDecl(Token symbol) {
        super(symbol);
    }

    public VarDecl(Token symbol, Name variable, Type type) {
        super(symbol);
        this.variable = variable;
        this.type = type;
    }

    public Expression getInit() {
        return init;
    }

    public void setInit(Expression init) {
        this.init = init;
    }

    @Override
    public Type getType() {
        System.out.println("Getting type of " + this + " as " + type);
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Name getVariable() {
        return variable;
    }

    public void setVariable(Name variable) {
        this.variable = variable;
    }

    @Override
    public int getAllocSize() {
        return getType().getAllocSize();
    }

    //return proper name ans scope 
    
    public String getQualifiedVariableName() {
        return "@" + scope.label() + this.getVariable().getName();
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if (getInit() != null) {
            this.getInit().typeCheck(msgs);
            if (!type.isTypeCompatible(getInit().getType())) {
                msgs.add(
                        String.format("Type of expression (%s) does not match type of declaration (%s) at line %d, column %d.",
                                getInit().getType(),
                                getType(),
                                getInit().getSymbol().getLine(),
                                getInit().getSymbol().getCol()));
            }
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (inFunction && !getType().isStatic()) {
            if (getInit() == null) {
                code.add("\taloc 1 ; " + variable.getName());
            } else {
                getInit().generate(code, true);
                code.add("\tpush ; " + variable.getName());
            }
        } else if (getInit() != null && (getInit() instanceof Literal)) {
            String decl = (this.getQualifiedVariableName() + ": dw ");
            Literal literal = (Literal) getInit();
            switch (literal.getType().getTypeCode()) {
                case Type.INT:
                case Type.CHAR:
                    decl += literal.toObject().toString();
                    break;
                case Type.BOOL:
                    if (Boolean.parseBoolean(literal.getSymbol().getSymbol())) {
                        decl += "1";
                    } else {
                        decl += "0";
                    }
                    break;
                default:
                // strings are different
            }
            code.add(decl);
        } else if (getInit() != null) {
            // an initializer that is not a literal
            throw new UnsupportedOperationException("Non-literal initializer.");
        } else {
            code.add(this.getQualifiedVariableName() + ": dw 0");
        }
    }

    @Override
    public String format(int indent, boolean suppressNL
    ) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[VarDecl: ");
        sb.append(this.getVariable().format(indent, true));
        sb.append(" ");
        sb.append(this.getType().format(indent, true));
        sb.append(" ");
        if (getInit() != null) {
            sb.append(getInit().format(indent, true));
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public String toString(){
        return type + " " + variable.getName();
    }
    //
    protected Name variable;
    protected Type type;
    protected Expression init;
    protected boolean isRelative;
    protected int relOffset;

}
