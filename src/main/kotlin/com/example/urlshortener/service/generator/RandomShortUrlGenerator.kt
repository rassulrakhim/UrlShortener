package com.example.urlshortener.service.generator


import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.security.SecureRandom

@Component
@Primary
class RandomShortUrlGenerator : ShortUrlGenerator {

    private val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    private val random = SecureRandom()

    override fun generateShortUrl(length: Int): String {
        val url = (1..length)
            .map { chars[random.nextInt(chars.length)] }
            .joinToString("")
        return url
    }
}
