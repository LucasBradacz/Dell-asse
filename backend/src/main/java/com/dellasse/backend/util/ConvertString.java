package com.dellasse.backend.util;

import java.util.UUID;

public class ConvertString {
    
    public static UUID toUUID(String uuid) {
        return UUID.fromString(uuid);
    }

}
