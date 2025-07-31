package com.example.urlshortener.controller


import com.example.urlshortener.service.UrlShortenerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UrlShortenerController(
    private val urlShorteningService: UrlShortenerService
) {

    /**
     * POST /api/shorten
     * Accepts a URL and returns the shortened key.
     */
    @PostMapping("/shorten")
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
