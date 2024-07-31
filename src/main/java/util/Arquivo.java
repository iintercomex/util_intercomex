package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Arquivo {

    public Arquivo() {
    }

    public static void createTxt(String diretorio, String informacao) throws IOException {
        if (diretorio == null || informacao == null) {
            throw new NullPointerException("Diretório ou informação nulos");
        }

        File arquivo;
        if (!new File(diretorio).exists()) {
            arquivo = Diretorio.verificaDiretorio(diretorio);
        } else {
            arquivo = new File(diretorio);
        }
        File arquivoTxt = new File(arquivo.getPath().concat("\\informacao.txt"));
        if (!arquivoTxt.exists()) {
            System.out.println(arquivoTxt.createNewFile()
                    ? "Arquivo txt criado em: " + arquivoTxt.getPath()
                    : "Arquivo txt não foi criado.");
        }
        PrintWriter writer = new PrintWriter(arquivoTxt);
        writer.println(informacao);
        writer.close();
    }
}
