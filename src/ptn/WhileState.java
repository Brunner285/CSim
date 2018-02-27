/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ptn;

/**
 *
 * @author Alan
 */
public class WhileState extends Statement {
    
    public WhileState(){
        super(2);
    }
    
    public Expression getTest(){
        return (Expression) children.get(0);
    }

    public void setTest(Expression expr) {
        children.set(0,expr);
    }
    
    public Block getBody(){
        return (Block) children.get(1);
    }

    public void setBody(Block block) {
        children.set(1,block);
    }
    
    @Override
    public String format(int indent){
        StringBuilder sb = new StringBuilder();
        sb.append(indent(indent));
        sb.append("while(");
        sb.append(getTest().format(indent));
        sb.append(")");
        sb.append(getBody().format(indent+5));
        return sb.toString();
    }
    
    @Override
    public ast.Loop toAST(){
        ast.Loop loop = new ast.Loop();
        //loop.setInit(getInit().toAST());
        loop.setTest(getTest().toAST());
        //loop.setUpdate(getIncr().toAST());
        loop.setBody(getBody().toAST());
        return loop;
    }

}
