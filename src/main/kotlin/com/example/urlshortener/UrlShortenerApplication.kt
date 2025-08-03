package com.example.urlshortener

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class UrlShortenerApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<UrlShortenerApplication>(*args)
}
