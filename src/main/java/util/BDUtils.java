package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BDUtils {

    public int atualizarAutomacaoLog(Connection conexao, int idAutomacao, int idTarefa, String dtInicio, String dtFim, String dtMaquina){
        String procedureQuery = "{call ATUAL_AUTOMACAO_LOG(?,?,?,?,?)}";
        System.out.println(procedureQuery);
        try{
            CallableStatement callableSt = conexao.prepareCall(procedureQuery);
            callableSt.setInt(1, idAutomacao);
            callableSt.setInt(2, idTarefa);
            callableSt.setString(3, dtInicio);
            callableSt.setString(4, dtFim);
            callableSt.setString(5, dtMaquina);
            return callableSt.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int enviarMensagemErroResult(String nomeRobo, String caminhoArqErro, String caminhoImg, String caminhoHTML, Connection c) {
        String procedureQuery = "{call ENVIA_MSG_ERRO_ROBOT(?, ?, ?, ?, ?)}";
        System.out.println(procedureQuery);
        Blob arqErroBlob = null;
        Blob imgBlob = null;
        Blob htmlBlob = null;
        try {

            if (caminhoArqErro != null) {
                arqErroBlob = converterPdfBlob(caminhoArqErro, c);
                System.out.println("Tamanho do STACK TRACE ERRO BLOB: " + (arqErroBlob.length()));
            }
            if (caminhoImg != null) {
                imgBlob = converterPdfBlob(caminhoImg, c);
                System.out.println("Tamanho do PRINT BLOB: " + (imgBlob.length()));
            }
            if(caminhoHTML != null){
                htmlBlob = converterPdfBlob(caminhoHTML, c);
                System.out.println("Tamanho do HTML BLOB: " + (htmlBlob.length()));
            }

            CallableStatement callableSt = c.prepareCall(procedureQuery);
            callableSt.setString(1, nomeRobo);
            callableSt.setString(2, null);
            callableSt.setBlob(3, arqErroBlob);
            callableSt.setBlob(4, imgBlob);
            callableSt.setBlob(5, htmlBlob);
            return callableSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public Blob converterPdfBlob(String arq, Connection c) throws SQLException, IOException {
        File file = new File(arq);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
        }
        byte[] bytes = bos.toByteArray();

        Blob blob = c.createBlob();
        blob.setBytes(1, bytes);

        return blob;
    }

}
