package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.lang.String.valueOf;

public class DataUtil {
    public DataUtil() {
    }

    public static Date stringToDate(String dataString) {
        try {
            return (new SimpleDateFormat("dd/MM/yyyy")).parse(dataString);
        } catch (ParseException var2) {
            var2.printStackTrace();
            System.out.println(var2.getMessage());
            return null;
        }
    }

    public static String formatarHoras(long milisegundos) {
        int horas = (int)(milisegundos / 3600000L);
        int minutos = (int)(milisegundos % 3600000L / 60000L);
        int segundos = (int)(milisegundos % 60000L / 1000L);
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
    public static String calcTempoDuracao(Date inicioRobo, Date fimRobo){

        LocalDateTime dt1 = inicioRobo.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime dt2 = fimRobo.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Duration duracaoSeg =  Duration.between(dt1, dt2);
        long seconds = duracaoSeg.getSeconds();

        long horas = seconds / 3600;
        long minutos = (seconds - (horas * 3600)) / 60;
        long segundos = seconds - (horas * 3600) - (minutos * 60);

        String h = formatarHora(horas);
        String mm = formatarHora(minutos);
        String s = formatarHora(segundos);

        return h + ":" + mm + ":" + s;

    }
    private static String formatarHora(long num){
        if (num < 10){
            return "0" + num;
        }
        return valueOf(num);
    }
}

