import java.io.*;


/**
 * Classe util para testar entradas e saidas de instancias de problemas em competicoes
 * online como o Google Code Jam
 * */
public class TemplateCompetitiveIO {
    private static final String IN = "";
    private static final String OUT = "";
    private static BufferedReader reader;
    private static PrintWriter writer;
    static {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(IN))));
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(OUT))), true);
        } catch (FileNotFoundException e) {}
    }
}
