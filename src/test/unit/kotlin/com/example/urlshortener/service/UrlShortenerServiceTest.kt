package com.example.urlshortener.service


import com.example.urlshortener.model.UrlMapping
import com.example.urlshortener.repository.UrlMappingRepository
import com.example.urlshortener.service.generator.ShortUrlGenerator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class UrlShortenerServiceTest {

    @Mock
    lateinit var urlMappingRepository: UrlMappingRepository

    @Mock
    lateinit var shortUrlGenerator: ShortUrlGenerator

    @InjectMocks
    lateinit var service: UrlShortenerService

    companion object{
        const val DEFAULT_SHORT_URL = "abc123"
    }

    @Test
    fun `should return existing short url if already exists`() {
        val url = "https://nohello.net"
        val existingShortUrl = "exist12"
        val existingMapping = UrlMapping(shortUrl = existingShortUrl, url = url, createdAt = LocalDateTime.now())

        `when`(urlMappingRepository.findByUrl(url)).thenReturn(existingMapping)

        val result = service.shortenUrl(url)

        assertEquals(existingShortUrl, result)
        verify(urlMappingRepository, never()).save(any())
    }

    @Test
    fun `should generate new short url if not exists`() {
        val url = "https://nohello.net"

        `when`(urlMappingRepository.findByUrl(url)).thenReturn(null)
        `when`(shortUrlGenerator.generateShortUrl()).thenReturn(DEFAULT_SHORT_URL)
        `when`(urlMappingRepository.existsById(DEFAULT_SHORT_URL)).thenReturn(false)

        val result = service.shortenUrl(url)

        assertEquals(DEFAULT_SHORT_URL, result)
        verify(urlMappingRepository).save(any())
    }

    @Test
    fun `should throw exception when short url not found`() {
        val shortUrl = "not-found"
        `when`(urlMappingRepository.findById(shortUrl)).thenReturn(Optional.empty())

        val ex = assertThrows(IllegalArgumentException::class.java) {
            service.getUrl(shortUrl)
        }

        assertTrue(ex.message!!.contains("Short URL not found"))
    }

    @Test
    fun `should return original url when short url exists`() {
        val shortUrl = "found12"
        val url = "https://nohello.net"
        val mapping = UrlMapping(shortUrl = shortUrl, url = url, createdAt = LocalDateTime.now())

        `when`(urlMappingRepository.findById(shortUrl)).thenReturn(Optional.of(mapping))


        val result = service.getUrl(shortUrl)

        assertEquals(url, result)
    }

}
