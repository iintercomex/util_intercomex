package util;

public class NumberUtil {
    public static Double stringDecimalParaDouble(String stringDecimal){
        if(stringDecimal != null)
            return Double.parseDouble(stringDecimal.replace(".", "").replace(",", "."));
        else
            return null;
    }
}
