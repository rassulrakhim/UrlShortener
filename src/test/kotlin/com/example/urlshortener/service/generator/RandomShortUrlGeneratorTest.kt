package com.example.urlshortener.service.generator

import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class RandomShortUrlGeneratorTest {
    private val generator = RandomShortUrlGenerator()

    @Test
    fun `generated short URL should have requested length`() {
        val length = 12
        val shortUrl = generator.generateShortUrl(length)
        assertThat(shortUrl).hasSize(length)
    }

    @Test
    fun `generated short URL should contain only allowed characters`() {
        val length = 20
        val shortUrl = generator.generateShortUrl(length)
        shortUrl.forEach { assertTrue (it.isLetterOrDigit()) }
    }

    @Test
    fun `generator should produce different values`() {
        val first = generator.generateShortUrl(8)
        val second = generator.generateShortUrl(8)
        assertNotEquals(first, second)
    }

    @Test
    fun `generator should handle small lengths`() {
        val shortUrl = generator.generateShortUrl(1)
        assertTrue(shortUrl.length == 1)
    }

}