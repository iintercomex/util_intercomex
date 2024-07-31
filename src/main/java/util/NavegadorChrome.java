package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class NavegadorChrome {

    private static WebDriver driver;

    public static WebDriver Inicializar(){
        System.setProperty("webdriver.chrome.driver", "C:/ROBOS/chromedriver_win32/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        String userProfile = System.getProperty("user.home") + "\\AppData\\Local\\Google\\Chrome\\User Data\\Default";
        options.addArguments("user-data-dir=" + userProfile);
        options.addArguments("--start-maximized");
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        driver = new ChromeDriver(options);
        return driver;
    }


}
