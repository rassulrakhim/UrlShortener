package com.example.urlshortener.service


import org.springframework.stereotype.Service

@Service
interface UrlShortenerService {

    /**
     * Create unique short url or return existing short url for given url.
     */
    fun shortenUrl(url: String): String

    /**
     * Return full url for given short url.
     */
    fun getUrl(shortUrl: String): String
}