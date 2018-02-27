/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer;

/**
 *
 * @author alan.whitehurst
 */
public class Token {

    public Token(String symbol, SType stype, int line, int col) {
        this.symbol = symbol;
        this.stype = stype;
        this.line = line;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String token) {
        this.symbol = token;
    }

    public SType getSType() {
        return stype;
    }

    public void setSType(SType type) {
        this.stype = type;
    }

    @Override
    public String toString() {
        return String.format("'%s' <%s> at line %d, column %d", symbol, stype, line, col);
    }

    private String symbol;
    private SType stype;
    private int line;
    private int col;

}
