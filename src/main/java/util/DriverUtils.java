//Para alterar este modelo, escolha Ferramentas | Modelos e abra o modelo no editor.
package util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Samuel
 */
public class DriverUtils {

    private static WebDriver driver;

    private DriverUtils() {
    }

    public static WebDriver configuraChrome() {
        System.setProperty("webdriver.chrome.driver", "C:/ROBOS/chromedriver_win32/chromedriver109.exe");
        ChromeOptions options = new ChromeOptions();
        String userProfile = System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default";
        options.addArguments("user-data-dir=" + userProfile);
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        return driver;
    }

    public static boolean foiEncontradoElementos(SearchContext searcher, By by) {
        return foiEncontradoElementos(searcher, by, null, null, null, null);
    }

    public static boolean foiEncontradoElementos(SearchContext searcher, By by, Integer tentativas) {
        return foiEncontradoElementos(searcher, by, null, null, null, tentativas);
    }

    public static boolean foiEncontradoElementos(SearchContext searcher, By by1, By by2) {
        return foiEncontradoElementos(searcher, by1, by2, null, null, null);
    }

    public static boolean foiEncontradoElementos(SearchContext searcher, By by1, By by2, Integer tentativas) {
        return foiEncontradoElementos(searcher, by1, by2, null, null, tentativas);
    }

    public static boolean foiEncontradoElementos(SearchContext searcher, By by1, By by2, By by3) {
        return foiEncontradoElementos(searcher, by1, by2, by3, null, null);
    }

    public static boolean foiEncontradoElementos(SearchContext searcher, By by1, By by2, By by3, Integer tentativas) {
        return foiEncontradoElementos(searcher, by1, by2, by3, null, tentativas);
    }

    public static boolean foiEncontradoElementos(SearchContext searcher, By by1, By by2, By by3, By by4) {
        return foiEncontradoElementos(searcher, by1, by2, by3, by4, null);
    }

    public static boolean foiEncontradoElementos(SearchContext searcher, By by1, By by2, By by3, By by4, Integer tentativas) {
        if (searcher != null) {
            boolean faltaParametros = false;

            int qtdeParametros = 0;

            if (tentativas==null) {
                tentativas = 60;
            }
            System.out.println("## Buscando elementos: ");
            if (by4 != null) {
                if (by3 == null || by2 == null || by1 == null) {
                    faltaParametros = true;
                } else {
                    System.out.println(by1+", "+by2+", "+by3+" ou "+by4);
                    qtdeParametros = 4;
                }
            } else if (by3 != null) {
                if (by2 == null || by1 == null) {
                    faltaParametros = true;
                } else {

                    System.out.println(by1+", "+by2+" ou "+by3);
                    qtdeParametros = 3;
                }
            } else if (by2 != null) {
                if(by1 == null){
                    faltaParametros = true;
                } else {
                    System.out.println(by1+" ou "+by2);
                    qtdeParametros = 2;
                }
            } else if (by1 != null) {
                qtdeParametros = 1;
                System.out.println(String.valueOf(by1));
            }
            if (faltaParametros) {
                throw new IllegalArgumentException("Parametro(s) anteriores não informados.");
            }
            if (qtdeParametros > 0) {
                boolean encontrado = false;
                int countLoop = 0;
                do {
                    switch (qtdeParametros) {
                        case 1:
                            encontrado = existeElementos(searcher, by1);
                            break;
                        case 2:
                            encontrado = existeElementos(searcher, by1) || existeElementos(searcher, by2);
                            break;
                        case 3:
                            encontrado = existeElementos(searcher, by1) || existeElementos(searcher, by2) || existeElementos(searcher, by3);
                            break;
                        case 4:
                            encontrado = existeElementos(searcher, by1) || existeElementos(searcher, by2) || existeElementos(searcher, by3) || existeElementos(searcher, by4);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        System.out.println("## (!)java.lang.InterruptedException(!) Exceção ignorada ##");
                    }
                    countLoop++;
                } while (!encontrado && countLoop <= tentativas);

                System.out.println("## elemento(s)" + (encontrado ? "" : " não") + " encontrado(s) ##");

                return encontrado;
            }
        } else {
            throw new IllegalArgumentException("WebDriver não encontrado");
        }

        return false;
    }

    public static boolean existeElementos(SearchContext searcher, By by) {
        if (searcher != null && by != null) {
            List<WebElement> elementos = searcher.findElements(by);
            if (elementos.size() > 0) {
                try {
                    Thread.sleep(50);
                    for (WebElement elemento : elementos) {
                        if (elemento.isDisplayed()) {
                            return true;
                        }
                    }
                } catch (InterruptedException ie) {
                    System.out.println("## (!)java.lang.InterruptedException(!) Exceção ignorada ##");
                } catch (NoSuchElementException se) {
                    System.out.println("## (!)java.util.NoSuchElementException(!) Exceção ignorada ##");
                } catch (org.openqa.selenium.NoSuchElementException se) {
                    System.out.println("## (!)org.openqa.selenium.NoSuchElementException(!) Exceção ignorada ##");
                } catch (StaleElementReferenceException st) {
                    System.out.println("## (!)org.openqa.selenium.StaleElementReferenceException(!) Exceção ignorada ##");
                }
            }
        }
        return false;
    }

    private static boolean existeElementos2(SearchContext searcher, By by) {
        if (searcher != null && by != null) {
            if (searcher.findElements(by).size() > 0) {
                try {
                    Thread.sleep(50);
                    return searcher.findElement(by).isDisplayed();
                } catch (InterruptedException ie) {
                    System.out.println("## (!)java.lang.InterruptedException(!) Exceção ignorada ##");
                } catch (NoSuchElementException se) {
                    System.out.println("## (!)java.util.NoSuchElementException(!) Exceção ignorada ##");
                } catch (org.openqa.selenium.NoSuchElementException se) {
                    System.out.println("## (!)org.openqa.selenium.NoSuchElementException(!) Exceção ignorada ##");
                } catch (StaleElementReferenceException st) {
                    System.out.println("## (!)org.openqa.selenium.StaleElementReferenceException(!) Exceção ignorada ##");
                }
            }
        }
        return false;
    }

    public static List<WebElement> findElementsWithContains(SearchContext searcher, By by, String contains) {
        return findElementsWithContainsOrEquals(searcher, by, contains, 1);
    }

    public static List<WebElement> findElementsWithEquals(SearchContext searcher, By by, String equals) {
        return findElementsWithContainsOrEquals(searcher, by, equals, 0);
    }

    public static WebElement findElementWithContains(SearchContext searcher, By by, String contains) {
        List<WebElement> listaAux = findElementsWithContains(searcher, by, contains);
        if (listaAux.size()>0){
            return listaAux.get(0);
        } else {
            return null;
        }
    }

    public static WebElement findElementWithEquals(SearchContext searcher, By by, String equals){
        List<WebElement> listaAux =  findElementsWithEquals(searcher, by, equals);
        if (listaAux.size()>0) {
            return listaAux.get(0);
        } else {
            return null;
        }
    }

    private static List<WebElement> findElementsWithContainsOrEquals(SearchContext searcher, By by, String containsOrEquals, int condition) {
        List<WebElement> lista = new ArrayList<WebElement>();
        List<WebElement> auxLista = searcher.findElements(by);
        if (!auxLista.isEmpty()) {
            for (WebElement auxElem : auxLista) {
                if (condition == 1 && auxElem.getText().contains(containsOrEquals)
                        || condition == 0 && auxElem.getText().equals(containsOrEquals)) {
                    lista.add(auxElem);
                }
            }
        }
        return lista;
    }

    public static boolean elementContains(SearchContext searcher, By by, String contains) {
        return elementContainsOrEquals(searcher, by, contains, 1);
    }

    public static boolean elementEquals(SearchContext searcher, By by, String equals) {
        return elementContainsOrEquals(searcher, by, equals, 0);
    }


    private static boolean elementContainsOrEquals(SearchContext searcher, By by, String containsOrEquals, int condition) {
        List<WebElement> elementos = searcher.findElements(by);
        for (WebElement elemento : elementos) {
            if (elemento.isDisplayed()) {
                String textElem = elemento.getText();
                if (condition == 1 && textElem.contains(containsOrEquals) || condition == 0 && textElem.equals(containsOrEquals)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void pause(WebDriver driver, boolean sair) {
        System.out.println("PAUSE!!!");
        JOptionPane.showMessageDialog(null, "PAUSE!!!");
        if (driver != null) {
            driver.quit();
            System.exit(0);
        } else if (sair) {
            System.exit(0);
        }
    }

    public static void pause() {
        pause(null, false);
    }

    public static void pause(boolean sair) {
        pause(null, sair);
    }


    public static void pause(WebDriver driver) {
        pause(driver, true);
    }

    public static void clicarElemento(WebDriver driver, WebElement elemento) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
    }

    // Função que espera o elemento sair da tela.
    public static boolean esperarInatividadeElemento(SearchContext searcher, By by, int segundos) {
        int count = 0;
        while (existeElementos(searcher, by) && count < segundos) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
            System.out.println("O elemento está na tela");
            count++;
        }

        if (count > 60) {
            return false;
        }
        System.out.println("O elemento não está mais na tela");
        return true;
    }

    public static boolean esperarInatividadeElemento(SearchContext searcher, By by) {
        return esperarInatividadeElemento(searcher, by, 60);
    }
}