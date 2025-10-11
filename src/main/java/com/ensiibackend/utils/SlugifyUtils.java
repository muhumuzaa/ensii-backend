package com.ensiibackend.utils;

import java.text.Normalizer;

public class SlugifyUtils {

    public static String slugify(String input){
        if(input == null || input.isBlank()){
            return null;
        }

        //Normalize
        String noWhiteSpace = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        //Replace non-alphabeticals with "-"
        String slug = noWhiteSpace.toLowerCase().replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
        return slug;
    }
}
