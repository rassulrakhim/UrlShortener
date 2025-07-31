package com.example.urlshortener.model


import jakarta.persistence.*
import java.time.LocalDateTime

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
