/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.binary;

import ast.Binary;

/**
 *
 * @author alan.whitehurst
 */
public class Factory {

    public static Binary getExpression(String op) {
        switch (op) {
            case "&&":
                return new ast.binary.LandExpression();
            case "||":
                return new ast.binary.LorExpression();
            case "==":
                return new ast.binary.EqExpression();
            case "!=":
                return new ast.binary.NeqExpression();
            case ">":
                return new ast.binary.GtExpression();
            case "<":
                return new ast.binary.LtExpression();
            case ">=":
                return new ast.binary.GeqExpression();
            case "<=":
                return new ast.binary.LeqExpression();
            case "+":
                return new ast.binary.AddExpression();
            case "-":
                return new ast.binary.SubExpression();
            case "*":
                return new ast.binary.MulExpression();
            case "/":
                return new ast.binary.DivExpression();
            case "%":
                return new ast.binary.RemExpression();
            default:
                throw new RuntimeException("Unsupported operator: " + op);
        }
    }

}
