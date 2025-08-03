package com.example.urlshortener.service.generator

interface ShortUrlGenerator {

    /**
     * Generate short url.
     */
    fun generateShortUrl(): String
}
