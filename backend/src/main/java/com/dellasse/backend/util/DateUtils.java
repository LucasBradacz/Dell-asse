package com.dellasse.backend.util;

import java.time.LocalDateTime;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Classe utilitária para manipulação de datas.
 */
public class DateUtils {

    private DateUtils() {}

    public static LocalDateTime now(){
        return LocalDateTime.now();
    }

}
