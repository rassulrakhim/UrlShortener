package com.example.urlshortener


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class UrlShortener

fun main(args: Array<String>) {
    runApplication<UrlShortener>(*args)
}