package com.example.urlshortener.service.generator

/**
 * Functional interface for generating short url.
 */
interface ShortUrlGenerator {

    /**
     * Generate short url.
     */
    fun generateShortUrl(): String
}
