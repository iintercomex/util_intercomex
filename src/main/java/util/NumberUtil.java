package util;

import java.util.Random;

public class NumberUtil {
    public static Double stringDecimalParaDouble(String stringDecimal){
        if(stringDecimal != null)
            return Double.parseDouble(stringDecimal.replace(".", "").replace(",", "."));
        else
            return null;
    }
    public static int gerarNumero(int numMin, int numMax) {

        Random rand = new Random();
        int randomNum = rand.nextInt((numMax - numMin) + 1) + numMin;
        return randomNum;
    }
}
