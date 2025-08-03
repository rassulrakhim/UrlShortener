package com.example.urlshortener.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * Data class represents Url mapping to short url aka id.
 */
@Entity
@Table(name = "url_mappings")
data class UrlMapping(
    @Id
    @Column(nullable = false, unique = true)
    val shortUrl: String,

    @Column(nullable = false)
    val url: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime
)
