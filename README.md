# URL Shortener 🚀

A simple Kotlin + Spring Boot service to shorten URLs, with Redis caching and configurable settings.

---

## 📦 Configuration

Configuration is located in application.yml and application-dev.yml.
Developers should use the dev Spring profile for local development, or create their own profile as needed:
```yaml
spring:
  profiles:
    active: dev
```
```yaml
url-shortener:
  api-path: /api/url/short      # Base API path for shortening & resolving URLs
  short-url-length: 6           # Length of generated short URLs
  cache-ttl-minutes: 10         # Cache TTL in minutes for resolving short URLs
```
There is also a docker-compose.yml file for local development. You can create a .env file with environment variables and start the required services using:
```bash
docker compose up
```

---

## 🔗 API

### POST `${api-path}`

Create a short URL.

**Request body:**

```json

   "https://example.com/some/very/long/url"

```

**Response:**

```json

  "abc123"

```

---

### GET `${api-path}/{shortUrl}`

Resolve a short URL to the original full URL.

**Example:**

```
GET /api/url/short/abc123
```

**Response:**

```json

  "https://example.com/some/very/long/url"

```

---

## 🧪 Testing

| Task                        | Description              |
| --------------------------- | ------------------------ |
| `./gradlew test`            | Run unit tests           |
| `./gradlew integrationTest` | Run integration tests    |
| `./gradlew detekt`          | Run static code analysis |

---

## ⚙️ Build & Run

Build the project:

```bash
./gradlew build
```

Run locally:

```bash
./gradlew bootRun
```

---

## ✅ Code Quality

This project uses:

* [Detekt](https://detekt.dev/) for static analysis
* JUnit 5 for testing
* Testcontainers for integration testing

---

## 📌 Requirements

* JDK 21
* Gradle (wrapper included)
* Docker (for integration tests using Testcontainers)

---


