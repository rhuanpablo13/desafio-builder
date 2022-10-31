package br.com.builder.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class DataUtils {
    
    public static final String yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMdd = "yyyy-MM-dd";

    /**
     * Retorna a diferença em dias entre duas datas
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    public static Integer diferencaEmDiasEntreDatas(Date dataInicial, Date dataFinal) {
        DateTime dateTimeInicial = new DateTime(dataInicial);
        DateTime dateTimeFinal = new DateTime(dataFinal);
        return Days.daysBetween(dateTimeInicial, dateTimeFinal).getDays();
    }

    /**
     * Formata uma data passada como string
     * @param param
     * @param format
     * @return
     */
    public static Date toDate(String param, String format) {
        SimpleDateFormat formato = new SimpleDateFormat(format);
        try {
            return formato.parse(param);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 
     * @param param
     * @param format
     * @return
     */
    public static String toString(Date param, String format) {
        if (param == null) return null;
        SimpleDateFormat formato = new SimpleDateFormat(format);
        return formato.format(param);
    }
}
