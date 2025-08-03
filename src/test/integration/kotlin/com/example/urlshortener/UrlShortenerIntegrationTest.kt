import com.example.urlshortener.UrlShortenerApplication
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest(classes = [UrlShortenerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UrlShortenerIntegrationTest(
    @Autowired private val restTemplate: TestRestTemplate,
    @Autowired private val redisTemplate: StringRedisTemplate,
) {

    companion object {
        private val redis = GenericContainer("redis:7").apply {
            withExposedPorts(6379)
        }


        private val postgres = PostgreSQLContainer<Nothing>("postgres:16").apply {
            withDatabaseName("urlshortener")
            withUsername("postgres")
            withPassword("postgres")
        }

        @JvmStatic
        @BeforeAll
        fun startContainers() {
            redis.start()
            postgres.start()
            println("Redis running at ${redis.host}:${redis.firstMappedPort}")
            println("Postgres running at ${postgres.host}:${postgres.firstMappedPort}")

            // Tell Spring Boot to override Redis and DB properties
            System.setProperty("spring.data.redis.host", redis.host)
            System.setProperty("spring.data.redis.port", redis.firstMappedPort.toString())
            System.setProperty("spring.datasource.url", postgres.jdbcUrl)
            System.setProperty("spring.datasource.username", postgres.username)
            System.setProperty("spring.datasource.password", postgres.password)
        }

        @JvmStatic
        @AfterAll
        fun stopContainers() {
            postgres.stop()
            redis.stop()
        }

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
            registry.add("spring.redis.host") { redis.host }
            registry.add("spring.redis.port") { redis.getMappedPort(redis.firstMappedPort) }
        }
    }

    @Test
    fun `should create short URL and cache it in Redis`() {
        // Create short url with POST
        val response =
            restTemplate.postForEntity("/api/url/shorten", mapOf("url" to "https://example.com"), String::class.java)
        assertEquals(200, response.statusCode.value())
        val shortUrl = response.body!!

        // Retrieve short url with GET
        val getResponse = restTemplate.getForEntity("/api/url/$shortUrl", String::class.java)
        assertEquals(200, getResponse.statusCode.value())

        // Check short url was cached
        val cached = redisTemplate.opsForValue().get("shortUrl::$shortUrl")
        println("Cached value with prefix: $cached")
        assertNotNull(cached)
    }
}
