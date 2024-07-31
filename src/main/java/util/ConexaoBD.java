// Classe que efetua a conexão com banco de dados Oracle.
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Cristiana
 */
public class ConexaoBD {
    public static Connection connect;
    private static String DRIVER = "";
    private static String DBURL = "";
    private static String USER = "";
    private static String SENHA = "";

    public ConexaoBD() {
        this.DRIVER = "oracle.jdbc.driver.OracleDriver";
        this.DBURL = "jdbc:oracle:thin:@intercomex.maxapex.net:1521:XE";
        this.USER = "appcomex";
        this.SENHA = "xoxotaxo";
    }

    /**
     * Método que estabelece a conexão com o banco de dados
     * @return True se conseguir conectar, falso em caso contrário.
     */
    public boolean connect() {
        boolean isConnected = false;

        try {
            Class.forName(DRIVER).newInstance();
            this.connect = DriverManager.getConnection(DBURL, USER, SENHA);
            isConnected = this.connect.isValid(5);
        } catch( SQLException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( ClassNotFoundException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( InstantiationException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( IllegalAccessException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        }
        return isConnected;
    }

    /**
     * Método que estabelece a desconexão com o banco de dados
     * @return True se conseguir desconectar, falso em caso contrário.
     */
    public boolean disconnect() {
        boolean isConnected = false;

        try {
            Class.forName(DRIVER).newInstance();
            this.connect = DriverManager.getConnection(DBURL,USER, SENHA);
            this.connect.close();
            isConnected = true;
        } catch( SQLException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( ClassNotFoundException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( InstantiationException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        } catch ( IllegalAccessException e ) {
            System.out.println(e.getMessage());
            isConnected = false;
        }
        return isConnected;
    }

    public Connection getConexao() {
        return this.connect;
    }

}