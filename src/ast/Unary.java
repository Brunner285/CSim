/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import lexer.Token;

/**
 *
 * @author Alan
 */
public class Unary extends Expression {

    public Unary(Token symbol) {
        super(symbol);
    }

    public Unary() {
    }

    public Unary(Token symbol, Operator op, Expression term) {
        super(symbol);
        this.op = op;
        this.term = term;
    }

    public Operator getOp() {
        return op;
    }

    public void setOp(Operator op) {
        this.op = op;
    }

    public Expression getTerm() {
        return term;
    }

    public void setTerm(Expression term) {
        this.term = term;
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Unary: ");
        sb.append(this.getOp().format(indent, true));
        sb.append(" ");
        sb.append(this.getTerm().format(indent, true));
        sb.append(" ]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Type getType() {
        Type t;
        switch (op.toString()) {
            case "!":
                return Type.BOOL_TYPE;
            case "-":
                return Type.INT_TYPE;
            case "&":
                t = new Type(getTerm().getType());
                t.setPointer(t.getPointer() + 1);
                return t;
            case "*":
                t = new Type(getTerm().getType());
                if (t.isPointer()) {
                    t.setPointer(t.getPointer() - 1);
                    return t;
                } else {
                    throw new RuntimeException("attempt to dereference a non-pointer");
                }
            case "++":
            case "--":
                return new Type(getTerm().getType());
        }
        return Type.VOID_TYPE;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        getTerm().typeCheck(msgs);
        switch (op.toString()) {
            case "!":
                if (!getTerm().getType().isTypeCompatible(Type.BOOL_TYPE)) {
                    msgs.add(
                            String.format("Logical operator on non-boolean type at line %d, column %d.",
                                    op.getSymbol().getLine(),
                                    op.getSymbol().getCol()));
                }
                break;
            case "-":
                if (!getTerm().getType().isTypeCompatible(Type.INT_TYPE)) {
                    msgs.add(
                            String.format("Negative operator on non-integer type at line %d, column %d.",
                                    op.getSymbol().getLine(),
                                    op.getSymbol().getCol()));
                }
                break;
            case "&":
                if (!(term instanceof VarRef)) {
                    msgs.add(
                            String.format("Address-of operator on non-variable at line %d, column %d.",
                                    op.getSymbol().getLine(),
                                    op.getSymbol().getCol()));
                }
                break;
            case "*":
                if (!term.getType().isPointer()) {
                    msgs.add(
                            String.format("Contents-of operator on non-pointer (%s) at line %d, column %d.",
                                    term,
                                    op.getSymbol().getLine(),
                                    op.getSymbol().getCol()));
                }
                break;
            case "++":
                if (!getTerm().getType().isTypeCompatible(Type.INT_TYPE)) {
                    msgs.add(
                            String.format("Increment operator on non-integer type at line %d, column %d.",
                                    op.getSymbol().getLine(),
                                    op.getSymbol().getCol()));
                }
                break;
            case "--":
                if (!getTerm().getType().isTypeCompatible(Type.INT_TYPE)) {
                    msgs.add(
                            String.format("Decrement operator on non-integer type at line %d, column %d.",
                                    op.getSymbol().getLine(),
                                    op.getSymbol().getCol()));
                }
                break;
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        VarRef vr;
        switch (op.toString()) {
            case "!":
                getTerm().generate(code, inFunction);
                code.add("\tjz *+3");
                code.add("\tldc 0");
                code.add("\tja *+2");
                code.add("\tldc 1");
                break;
            case "-":
                getTerm().generate(code, inFunction);
                code.add("\tpush");
                code.add("\tldc -1");
                code.add("mult");
                break;
            case "&":
                if (!(term instanceof VarRef)) {
                    throw new RuntimeException("& without variable reference");
                }
                vr = (VarRef) getTerm();
                if (!inFunction) {
                    if (vr.isArrayRef()) {
                        vr.getIndex().generate(code, inFunction);
                        code.add("\taddc " + vr.getQualifiedVariableName());
                    } else {
                        code.add("\tldc " + vr.getQualifiedVariableName());
                    }
                } else {
                    if (vr.isArrayRef()) {
                        vr.getIndex().generate(code, inFunction);
                        code.add("\taddc " + vr.getRelativeOffset());
                    } else {
                        code.add("\tldc " + vr.getRelativeOffset());
                    }
                    code.add("\tcora");
                }
                break;
            case "*":
                getTerm().generate(code, inFunction);
                code.add("\tldi");
                break;
            case "++":
                if (!(term instanceof VarRef)) {
                    throw new RuntimeException("++ without variable reference");
                }
                vr = (VarRef) getTerm();
                if (!inFunction) {
                    if (vr.isArrayRef()) {
                        vr.getIndex().generate(code, inFunction);
                        code.add("\taddc " + vr.getQualifiedVariableName());
                        code.add("\tldi");
                        code.add("\taddc 1");
                        code.add("\tpush");
                        vr.getIndex().generate(code, inFunction);
                        code.add("\taddc " + vr.getQualifiedVariableName());
                        code.add("\tsti");
                        vr.getIndex().generate(code, inFunction);
                        code.add("\taddc " + vr.getQualifiedVariableName());
                        code.add("\tldi");
                    } else {
                        code.add("\tld " + vr.getQualifiedVariableName());
                        code.add("\taddc 1");
                        code.add("\tst " + vr.getQualifiedVariableName());
                    }
                } else if (vr.isArrayRef()) {
                    vr.getIndex().generate(code, inFunction);
                    code.add("\taddc " + vr.getRelativeOffset());
                    code.add("\tldi");
                    code.add("\taddc 1");
                    code.add("\tpush");
                    vr.getIndex().generate(code, inFunction);
                    code.add("\taddc " + vr.getRelativeOffset());
                    code.add("\tsti");
                    vr.getIndex().generate(code, inFunction);
                    code.add("\taddc " + vr.getRelativeOffset());
                    code.add("\tldi");

                } else {
                    code.add("\tldr " + vr.getRelativeOffset());
                    code.add("\taddc 1");
                    code.add("\tstr " + vr.getRelativeOffset());
                }
                break;
            case "--":
                if (!(term instanceof VarRef)) {
                    throw new RuntimeException("-- without variable reference");
                }
                getTerm().generate(code, inFunction);
                code.add("\tsubc 1");
                break;
        }
    }
    //
    private Operator op;
    private Expression term;

}
