package com.example.urlshortener.service.generator

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.security.SecureRandom

/**
 * Primary/Default short url generator.
 * @property CHARS set of allowed chars.
 */
@Component
@Primary
class RandomShortUrlGenerator(@Value("\${url-shortener.short-url-length}") private val shortUrlLength: Int) :
    ShortUrlGenerator {

    companion object {
        private const val CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    }

    private val random = SecureRandom()

    override fun generateShortUrl(): String {
        val url = (1..shortUrlLength)
            .map { CHARS[random.nextInt(CHARS.length)] }
            .joinToString("")
        return url
    }
}
