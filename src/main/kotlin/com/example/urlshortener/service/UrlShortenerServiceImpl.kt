package com.example.urlshortener.service

import com.example.urlshortener.model.UrlMapping
import com.example.urlshortener.repository.UrlMappingRepository
import com.example.urlshortener.service.generator.ShortUrlGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * Implementation of UrlShortenerService.
 */
@Service
class UrlShortenerServiceImpl : UrlShortenerService {

    @Autowired
    @Qualifier("randomShortUrlGenerator")
    private lateinit var shortUrlGenerator: ShortUrlGenerator

    @Autowired
    private lateinit var urlMappingRepository: UrlMappingRepository

    override fun shortenUrl(url: String): String {
        return urlMappingRepository.findByUrl(url)?.shortUrl ?: createUrlMapping(url)
    }

    private fun createUrlMapping(url: String): String {
        var shortUrl: String
        do {
            shortUrl = shortUrlGenerator.generateShortUrl()
        } while (urlMappingRepository.existsById(shortUrl))
        val urlMapping = UrlMapping(shortUrl = shortUrl, url = url, createdAt = LocalDateTime.now())
        urlMappingRepository.save(urlMapping)
        return shortUrl
    }

    @Cacheable("shortUrl")
    override fun getUrl(shortUrl: String): String {
        val urlMapping = urlMappingRepository.findByIdOrNull(shortUrl)
            ?: throw IllegalArgumentException("Short URL not found: $shortUrl")
        return urlMapping.url
    }
}
