// Classe que apresenta uma caixa de Diálogo.
package util;

import javax.swing.*;

/**
 * @author Cristiana
 */
public class Dialog extends JDialog {

    private static JPanel panel = new JPanel();
    private static String texto;

    //Mostra o diálogo de apartir dos parâmetros: titulo, texto e tamanhos.
    public Dialog(String titulo, String texto, int width, int height) {
        super(null,titulo,null);
        this.setSize(width, height);
        this.texto = texto;

        panel.add(new JLabel(this.texto));
        this.getContentPane().add(panel);
        this.setVisible(true);
    }

    //Altera o texto do diálogo atual:
    public void alterarText(String texto) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(texto));
        this.getContentPane().add(panel);
        this.setVisible(true);
    }

    //Adiciona texto no diálogo atual:
    public void adicionarText(String texto) {
        this.texto = this.texto + texto;
        JPanel panel = new JPanel();
        panel.add(new JLabel(this.texto));
        this.getContentPane().add(panel);
        this.setVisible(true);
    }

    //Fecha o diálogo atual:
    public static void fecharDialog() {
        System.exit(0);
    }
}
