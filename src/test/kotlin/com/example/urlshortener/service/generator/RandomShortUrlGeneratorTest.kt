package com.example.urlshortener.service.generator

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class RandomShortUrlGeneratorTest {
    companion object{
        const val DEFAULT_SHORT_URL_LENGTH = 8
    }

    private val generator = RandomShortUrlGenerator(DEFAULT_SHORT_URL_LENGTH)

    @Test
    fun `generated short URL should have requested length`() {
        val shortUrl = generator.generateShortUrl()
        assertThat(shortUrl).hasSize(DEFAULT_SHORT_URL_LENGTH)
    }

    @Test
    fun `generated short URL should contain only allowed characters`() {
        val shortUrl = generator.generateShortUrl()
        shortUrl.forEach { assertTrue (it.isLetterOrDigit()) }
    }

    @Test
    fun `generator should produce different values`() {
        val first = generator.generateShortUrl()
        val second = generator.generateShortUrl()
        assertNotEquals(first, second)
    }

}