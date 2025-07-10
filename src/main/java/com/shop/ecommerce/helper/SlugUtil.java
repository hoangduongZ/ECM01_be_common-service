package com.shop.ecommerce.helper;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.UUID;

@Component
public class SlugUtil {
    private String slugify(String input){
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
    }
    public String generateSlugWithUUID(String slug){
        String slugValue = slugify(slug);
        String suffix = UUID.randomUUID().toString().substring(0,8);
        return slugValue + "-" + suffix;
    }
}
