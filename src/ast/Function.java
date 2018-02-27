/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ast;

import java.util.ArrayList;
import java.util.Iterator;
import lexer.Token;
import parser.Scope;

/**
 *
 * @author Alan
 */
public class Function extends ASTNode {

    public Function(Token symbol) {
        super(symbol);
    }

    public Function() {
    }

    //set parameters  
    public Function(Token symbol, Name v, Type t, ArrayList<Param> params, Block body) {
        super(symbol);
        this.name = v;
        this.type = t;
        this.params = params;
        this.body = body;
    }

    //return params from list 
    public ArrayList<Param> getParams() {
        return params;
    }

    //set value for the param in the list 
    public void setParams(ArrayList<Param> params) {
        this.params = params;
    }

    //add param to list 
    public void addParam(Param param) {
        params.add(param);
    }

    //return value type
    public Type getType() {
        return type;
    }

    //set value of t
    public void setType(Type t) {
        this.type = t;
    }

    //return name 
    public Name getName() {
        return name;
    }

    //set value for name at value v
    public void setName(Name v) {
        this.name = v;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public Block getBody() {
        return body;
    }

    public void setBody(Block body) {
        this.body = body;
    }

    
    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public void typeCheck(ArrayList<String> msgs) {
        if(!isHeader()){
            body.typeCheck(msgs);
        }
    }

    @Override
    public void generate(ArrayList<String> code, boolean inFunction) {
        if (!isHeader()) {
            code.add(name.getName() + ":");
            code.add("\tesba");
            if (getParams() != null) {
                // due to the use of the base register in the optimal instruction
                // set of H1, parameters begin at offset 2 on the stack and
                // local variables have a negative offset
                int paramOffset = 2;
                for(Param param : getParams()){
                    param.getScope().setRelOffset(param.getVariable().getName(), paramOffset++);
                }
            }
            body.generate(code, true);
            code.add("\treba");
            code.add("\tret");
        } else {
            code.add("\textern " + name.getName());
        }
    }

    @Override
    public String format(int indent, boolean suppressNL) {
        StringBuilder sb = new StringBuilder();
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("[Function: ");
        sb.append(type.format(indent + 3, true));
        sb.append(name.format(indent + 3, true));
        if (getParams() != null) {
            Iterator<Param> theParams = params.iterator();
            while (theParams.hasNext()) {
                sb.append(theParams.next().format(indent + 3, false));
            }
        }
        if (!isHeader()) {
            sb.append("\n");
            sb.append(body.format(indent + 3, false));
        } else {
            sb.append("{}");
        }
        if (!suppressNL) {
            sb.append(indent(indent));
        }
        sb.append("]");
        if (!suppressNL) {
            sb.append("\n");
        }
        return sb.toString();
    }
    //
    private Name name;
    private Type type;
    private ArrayList<Param> params;
    private boolean header;
    private Block body;
    private Scope scope;

}
