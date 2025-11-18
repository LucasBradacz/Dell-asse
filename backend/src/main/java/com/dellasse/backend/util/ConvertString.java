package com.dellasse.backend.util;

import java.util.UUID;

/**
 * @author Equipe Compilador 
 * @version 1.0
 * Classe utilitária para conversão de strings.
 */
public class ConvertString {
    
    public static UUID toUUID(String uuid) {
        return UUID.fromString(uuid);
    }

}
