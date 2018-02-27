/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class ArrayDecl extends VarDecl {

    //create array 
    public ArrayDecl(Token symbol, Name variable, Type type) {
        super(symbol, variable, type);
    }

    public ArrayDecl(Token symbol) {
        super(symbol);
    }

    public ArrayDecl() {
    }

    //determine size of array 
    public ArrayDecl(Token symbol, Name variable, Type type, Expression size) {
        super(symbol, variable, type);
        this.size = size;
    }

    //return size 
    public Expression getSize() {
        return size;
    }

    //set size of expression
    public void setSize(Expression size) {
        this.size = size;
    }

    //return list
    public ExprList getInitList() {
        return initList;
    }

    public void setInitList(ExprList initList) {
        this.initList = initList;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if (initList != null) {
            Iterator<Expression> inits = initList.iterator();
            while (inits.hasNext()) {
                Expression exp = inits.next();
                exp.typeCheck(msgs);
                if (!type.isTypeCompatible(exp.getType())) {
                    msgs.add(
                            String.format("Type of expression (%s) does not match type of declaration (%s) at line %d, column %d.",
                                    exp.getType(),
                                    type,
                                    exp.getSymbol().getLine(),
                                    exp.getSymbol().getCol()));
                }
            }
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (inFunction && this.getInitList() != null) {
            ListIterator inits = initList.listIterator();
            // advance the iterator to the end of the list
            while (inits.hasNext()) {
                inits.next();
            }
            while (inits.hasPrevious()) {
                Literal literal = (Literal) inits.previous();
                if (literal.getType().getTypeCode() == Type.BOOL) {
                    if (Boolean.parseBoolean(literal.getSymbol().getSymbol())) {
                        code.add("\tldc 1");
                        code.add("\tpush");
                    } else {
                        code.add("\tldc 0");
                        code.add("\tpush 0");
                    }
                } else {
                    code.add("\tldc " + literal.toObject().toString());
                    code.add("\tpush");
                }
            }
        } else if (inFunction) {
            if (size instanceof Literal) {
                code.add("\taloc " + ((Literal) size).toInt() + " ; " + variable.getName());
            }
        } else if (this.getInitList() != null) {
            String decl = (this.getQualifiedVariableName() + ": dw ");
            Iterator inits = initList.iterator();
            while (inits.hasNext()) {
                Literal literal = (Literal) inits.next();
                switch (literal.getType().getTypeCode()) {
                    case Type.INT:
                        decl += literal.toObject().toString();
                        break;
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
                decl = "\tdw ";
            }
        } else if (size instanceof Literal) {
            code.add(this.getQualifiedVariableName() + ": dw " + ((Literal) size).toObject().toString() + " dup 0");
        }
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[ArrayDecl ");
        sb.append(this.getVariable().format(indent, true));
        sb.append(" ");
        sb.append(this.getType().format(indent, true));
        sb.append(" ");
        if (getSize() != null) {
            sb.append("[Size: ");
            sb.append(getSize().format(indent, true));
            sb.append("]");
        }
        if (getInitList() != null) {
            sb.append("[Initializer: ");
            Iterator<Expression> inits = initList.iterator();
            while (inits.hasNext()) {
                sb.append(inits.next().format(indent, true));
                if (inits.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        }
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Expression size;
    private ExprList initList;

}
