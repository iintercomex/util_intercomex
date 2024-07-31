package util;

import javax.swing.*;
import java.awt.*;

public class ModalProgress extends JPanel{
    JProgressBar pbar;
    static JDialog test;
    static final int MY_MINIMUM = 0;
    static final int MY_MAXIMUM = 400;
    private static String titulo;
    private static String textoCorpo;

    public ModalProgress() {
        JLabel jl = new JLabel();
        jl.setText(textoCorpo);
        add(jl);
        jl.setPreferredSize(new Dimension(300,30));

        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        pbar.setPreferredSize(new Dimension(300,15));
        add(pbar);
    }

    public void setCompenentes(String titulo, String textoCorpo){
        this.titulo = titulo;
        this.textoCorpo = textoCorpo;
    }

    public void updateBar(int newValue) {
        pbar.setValue(newValue);
    }

    public static void modal() {
        final ModalProgress it = new ModalProgress();
        JFrame frame = new JFrame(titulo);

        frame.setPreferredSize(new Dimension(400,120));
        frame.setMinimumSize(new Dimension(400,120));
        frame.setLocation(300,300);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(it);
        frame.setState(Frame.ICONIFIED);
        frame.setState(Frame.NORMAL);
        frame.setVisible(true);

        for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
            final int percent = i;
            try {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        it.updateBar(percent);
                    }
                });
                Thread.sleep(10);
            } catch (InterruptedException e) {

            }
        }
    }
    public static void fecharModal(){
        System.exit(0);
    }
}
