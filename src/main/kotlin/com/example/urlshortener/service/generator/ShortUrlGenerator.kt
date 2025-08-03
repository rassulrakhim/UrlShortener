package com.example.urlshortener.service.generator

interface ShortUrlGenerator {

    /**
     * Generate short url with given length.
     */
    fun generateShortUrl(length: Int): String
}
