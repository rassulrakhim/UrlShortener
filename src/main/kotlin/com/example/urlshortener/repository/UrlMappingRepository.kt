package com.example.urlshortener.repository

import com.example.urlshortener.model.UrlMapping
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Base jpa repository for DB actions on UrlMapping.
 */
interface UrlMappingRepository : JpaRepository<UrlMapping, String> {

    fun findByUrl(url: String): UrlMapping?
}
