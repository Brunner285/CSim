
package lexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * This class reads a file and does a lexigraphical analysis of the file, breaking
 * up the input stream into a series of tokens.
 * 
 * @author alan.whitehurst
 */
public class Lexer implements Iterator<Token> {

    /**
     * Constructor takes a filename corresponding to the file to open and
     * analyze. The Lexer expects filenames to end in <code>.csm</code>. 
     * 
     * @param filename The name (and optional path) of the file to open.
     */
    public Lexer(String filename) {
        this.filename = filename;
        if (!this.filename.endsWith(".csm")) {
            this.filename += ".csm";
        }
        reader = null;
        try {
            reader = new BufferedReader(new FileReader(filename));
            line = "";
            lineCount = 0;
            pTokenizer = new ParseTokenizer();
            getNextLine();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        } 
    }

    @Override
    public boolean hasNext() {
        if (pTokenizer.hasMoreTokens()) {
            return true;
        }
        getNextLine();
        return pTokenizer.hasMoreTokens();
    }

    @Override
    public Token next() {
        if (!hasNext()) {
            return null;
        }
        String token = pTokenizer.nextToken();
        SType type = SType.getType(token);
        int col = pTokenizer.getStartPos();
        Token sym = new Token(token, type, lineCount, col);
        //System.out.printf("Lexer: next => %s\n",sym);
        return sym;
    }

    /**
     * Peek implements a one token look-ahead. It returns what a subsequent call
     * to <code>next()</code> will return.
     * @return The next token on the input stream, or <code>null</code> if there
     * is no such token.
     */
    public Token peek() {
        if(!hasNext()){
            return null;
        }
        String token = pTokenizer.peekToken();
        SType type = SType.getType(token);
        int col = pTokenizer.getStartPos();
        Token sym = new Token(token, type, lineCount, col);
        //System.out.printf("Lexer: peek => %s\n",sym);
        return sym;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported.");
    }

    private void getNextLine() {
        try {
            while (line != null && !pTokenizer.hasMoreTokens()) {
                line = reader.readLine();
                if (line != null) {
                    ++lineCount;
                    pTokenizer = new ParseTokenizer(line, DELIMS, true, true);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void finalize() throws Throwable {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } finally {
            super.finalize();
        }
    }
    //
    private String filename;
    private BufferedReader reader;
    private String line;
    private int lineCount;
    private ParseTokenizer pTokenizer;
    private static final String [] DELIMS = {",",";","[","]","{","}","(",")",
        "==","=","++","+","--","-","*","/","%","&&","&","||","|","!=","!",
        "<=","<",">=",">"," ","\t"};
}
