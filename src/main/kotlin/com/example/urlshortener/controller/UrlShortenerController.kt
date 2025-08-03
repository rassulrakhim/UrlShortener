package com.example.urlshortener.controller

import com.example.urlshortener.service.UrlShortenerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Rest Controller for creating and retrieving urls.
 */
@RestController
@RequestMapping("\${url-shortener.api-path}")
class UrlShortenerController(
    private val urlShorteningService: UrlShortenerService
) {

    /**
     * POST /api/shorten
     * Accepts a URL and returns the shortened key.
     */
    @PostMapping
    fun shortenUrl(@RequestBody url: String): ResponseEntity<String> {
        val shortUrlKey = urlShorteningService.shortenUrl(url)
        return ResponseEntity.ok(shortUrlKey)
    }

    /**
     * GET /{shortUrl}
     * Redirects to the original URL.sho
     */
    @GetMapping("/{shortUrl}")
    fun redirectToOriginal(@PathVariable shortUrl: String): String {
        val originalUrl = urlShorteningService.getUrl(shortUrl)
        return originalUrl
    }
}
