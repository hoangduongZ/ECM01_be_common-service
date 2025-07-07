package com.shop.ecommerce.helper;

import java.text.Normalizer;
import java.util.UUID;

public abstract class SlugUtil {
    public static String slugify(String input){
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
    public static String generateSlugWithUUID(String slug){
        String slugValue = slugify(slug);
        String suffix = UUID.randomUUID().toString().substring(0,8);
        return slugValue + "-" + suffix;
    }
}
