/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

import ast.ASTNode;
import java.util.ArrayList;
import java.util.Iterator;
import parser.ParseException;
import parser.SymbolTable;

/**
 *
 * @author Alan
 */
public class ParamList extends PTNode {

    public void addParam(Param param) {
        children.add(param);
    }

    public Param getParam(int index) {
        return (Param) children.get(index);
    }

    public ArrayList<PTNode> getParamList() {
        return children;
    }

    public void pushParams(SymbolTable stab) throws ParseException {
        Iterator<PTNode> expressions = children.iterator();
        while (expressions.hasNext()) {
            Param param = (Param) expressions.next();
            stab.add(param.getName(), param.getType());
            param.setScope(stab.getCurrentScope());
        }
    }

    public String toPolish() {
        StringBuilder sb = new StringBuilder("$");
        Iterator<PTNode> expressions = children.iterator();
        while (expressions.hasNext()) {
            Param param = (Param) expressions.next();
            sb.append(param.toPolish());
        }
        return sb.toString();
    }

    @Override
    public String format(int indent) {
        StringBuilder sb = new StringBuilder();
        Iterator<PTNode> expressions = children.iterator();
        while (expressions.hasNext()) {
            sb.append(expressions.next().format(indent));
            if (expressions.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public ArrayList<ast.Param> toParamList() {
        ArrayList<ast.Param> paramList = new ArrayList<>();
        Iterator<PTNode> params = children.iterator();
        while (params.hasNext()) {
            Param param = (Param) (params.next());
            paramList.add(param.toAST());
        }
        return paramList;
    }

    @Override
    public ASTNode toAST() {
        throw new UnsupportedOperationException("Not supported.");
    }

}
