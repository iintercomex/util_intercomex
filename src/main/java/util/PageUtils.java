package util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

import static util.DataUtil.calcTempoDuracao;

public class PageUtils {


    public static boolean loopExisteTexto(String texto1) {
        return loopExisteTexto(texto1,null, null);
    }

    public static boolean loopExisteTexto(String texto1, int tentativas) {
        return loopExisteTexto(texto1,null, null, tentativas);
    }

    public static boolean loopExisteTexto(String texto1, String texto2) {
        return loopExisteTexto(texto1,texto2, null);
    }

    public static boolean loopExisteTexto(String texto1, String texto2, int tentativas) {
        return loopExisteTexto(texto1,texto2, null, tentativas);
    }

    public static boolean loopExisteTexto(String texto1, String texto2, String texto3) {
        return loopExisteTexto(texto1,texto2, texto3, 60);
    }

    public static boolean loopExisteTexto(String texto1, String texto2, String texto3, int tentativas) {
        boolean encontrado = false;
        int qtdeArgs = 0;

        if (texto1 != null) {
            qtdeArgs = 1;
            if (texto2 != null) {
                qtdeArgs = 2;
                if (texto3 != null) {
                    qtdeArgs = 3;
                }
            }
        }

        if (qtdeArgs == 0) {
            throw new IllegalArgumentException("Parametros anteriores não informados.");
        }

        boolean contemTexto = false;
        int countTentativas = 0;

        do {
            switch (qtdeArgs) {
                case 1:
                    contemTexto = existeTexto(texto1);
                    break;
                case 2:
                    contemTexto = existeTexto(texto1) || existeTexto(texto2);
                    break;
                case 3:
                    contemTexto = existeTexto(texto1) || existeTexto(texto2) || existeTexto(texto3);
            }

            if (contemTexto) {
                encontrado = true;
            } else {
                countTentativas++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
            }

        } while (!encontrado && countTentativas<=tentativas);//5

        System.out.println("## texto(s)"+(encontrado?"":" não")+" encontrado(s) ##");

        return encontrado;
    }

    public static boolean existeTexto(String texto) {
        if (texto != null) {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_C);
                robot.keyRelease(KeyEvent.VK_C);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                Thread.sleep(500);

                String areaCopiada = "";
                areaCopiada = (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                return areaCopiada.contains(texto);
            } catch (UnsupportedFlavorException e) {
                System.out.println(e.toString());
            } catch (IOException e) {
                System.out.println(e.toString());
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            } catch (AWTException e) {
                System.out.println(e.toString());
            }
        }
        return false;
    }

    public static void limpaCacheNavegador(WebDriver driver) throws InterruptedException, AWTException {
        driver.get("chrome://settings/clearBrowserData");
        Thread.sleep(5000);
//        driver.findElement(By.id("clear-browser-data-commit")).click();
        Robot r = new Robot();
        for (int i = 0; i < 9; i++) {
            r.keyPress(KeyEvent.VK_TAB);
            r.keyRelease(KeyEvent.VK_TAB);
            Thread.sleep(1000);
        }
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(10000);
    }
    public static String criarArqErro(Exception exception, String caminhoRobo) {
        String arqErro = "";
        if (exception != null) {
            try {
                String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
                arqErro = caminhoRobo + "\\ERRO.txt";
                BufferedWriter arquivo = new BufferedWriter(new FileWriter(arqErro));
                PrintWriter printArq = new PrintWriter(arquivo);
                printArq.println("========= " + date + " =========");
                printArq.println("== StackTrace do Erro ==");
                exception.printStackTrace(printArq);
                arquivo.close();
            } catch (NullPointerException e) {
                System.out.println(e.toString());
            } catch (IOException io) {
                System.out.println(io.toString());
            }
        }
        return arqErro;
    }
    public static String criarArqHtml(WebDriver driver, String caminhoRobo) {
        String date = "";
        if (driver != null) {
            try {
                Date d = new Date();
                date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(d);
                String arqHTML = caminhoRobo + "\\html.txt";
                System.out.println("caminho html: " + arqHTML);
                BufferedWriter arquivo = new BufferedWriter(new FileWriter(arqHTML));
                PrintWriter printArq = new PrintWriter(arquivo);
                printArq.println("========= " + date + " =========");
                printArq.println("== HTML da Página ==");
                printArq.println(driver.findElement(By.xpath("//*")).getAttribute("outerHTML"));
                arquivo.close();
            } catch (NullPointerException e) {
                System.out.println(e.toString());
            } catch (IOException io) {
                System.out.println(io.toString());
            }
        }
        return date;
    }
    public static String salvarImagem(String caminhoRobo) throws AWTException, IOException, InterruptedException {
        System.out.println("SALVANDO IMAGEM......");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;
        System.out.println("Height: " + height);
        Robot robot = new Robot();

        BufferedImage img = null;
        Rectangle r = new Rectangle(width, height);
        //r.x = 85;
        img = robot.createScreenCapture(r);
        String imgErro = caminhoRobo + "/print_erro.png";
        ImageIO.write(img, "png", new File(imgErro));
        return imgErro;
    }
    public static String extrairDadosLogin(String campo, String caminhoLogin) {
        String info = null;
        try {
            Scanner arqLido = new Scanner(new FileReader(caminhoLogin + "\\" + campo + ".ini"));
            while (arqLido.hasNextLine()) {
                info = arqLido.next();
                break;
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            info = null;
            System.out.println("O arquivo " + campo + " não foi encontrado");
        }
        return info;
    }
    public static void fazerDownload(String caminhoPdf) throws InterruptedException, AWTException {

        Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
        ClipboardOwner selection = new StringSelection(caminhoPdf);
        board.setContents((Transferable) selection, selection);
        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(5500);

        while (!new File(caminhoPdf).exists() || existeTexto("Salvar")) {

            if (!new File(caminhoPdf).exists() && existeTexto("Salvar")) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                Thread.sleep(1500);

            } else if (new File(caminhoPdf).exists() && existeTexto("Salvar")) {
                robot.keyPress(KeyEvent.VK_LEFT);
                robot.keyRelease(KeyEvent.VK_LEFT);
                Thread.sleep(1000);
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                break;
            }
        }
    }
    private static String pegarTextoSite() throws AWTException, InterruptedException, IOException, UnsupportedFlavorException {

        Robot acessaSite = new Robot();
        Thread.sleep(1000);

        acessaSite.keyPress(KeyEvent.VK_CONTROL);
        acessaSite.keyPress(KeyEvent.VK_A);
        acessaSite.keyRelease(KeyEvent.VK_A);
        acessaSite.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);

        acessaSite.keyPress(KeyEvent.VK_CONTROL);
        acessaSite.keyPress(KeyEvent.VK_C);
        acessaSite.keyRelease(KeyEvent.VK_C);
        acessaSite.keyRelease(KeyEvent.VK_CONTROL);
        Thread.sleep(1000);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        String textoTela = clipboard.getData(DataFlavor.stringFlavor).toString();

        return textoTela;

    }

    public static void gerarRelatorioLog (String nomeRobo, Date dataMaquina, Date inicioRobo, Date fimRobo, String caminhoRelatorio, Map<String, List<String>> dadosAtualizados) throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String caminhoArquivo = caminhoRelatorio + "\\relatorioRobo" + nomeRobo + ".txt";
        File relatorioTempoRobo = new File(caminhoArquivo);
        String dataHoje = dateFormat.format(dataMaquina);

        String tempoExecucao = calcTempoDuracao(inicioRobo, fimRobo);

        if (!relatorioTempoRobo.exists()) {
            System.out.println(relatorioTempoRobo.createNewFile() ? "Arquivo relatorioTempoRobo.txt criado com sucesso!" : "Arquivo relatorioTempoRobo.txt não foi criado.");
        }

        FileWriter writer = new FileWriter(relatorioTempoRobo, relatorioTempoRobo.exists());
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.println("DATA: " + dataHoje + " TEMPO: " + tempoExecucao);

        if(!dadosAtualizados.isEmpty()){
            printWriter.println("DADOS ATULIZADOS");
            for(String dado : dadosAtualizados.keySet()){
                printWriter.println(dadosAtualizados.get(dado).toString() + "\n");
            }
        }
        else {
            printWriter.println("SEM DADOS PARA ATUALIZAR!!");
        }

        printWriter.println("################################################# \n");

        printWriter.close();
        System.out.println("Relatório criado com sucesso!");
    }

}
