package com.example.urlshortener.repository

import com.example.urlshortener.model.UrlMapping
import org.springframework.data.jpa.repository.JpaRepository

interface UrlMappingRepository : JpaRepository<UrlMapping, String>{
   fun findByUrl(url:String):UrlMapping?
}