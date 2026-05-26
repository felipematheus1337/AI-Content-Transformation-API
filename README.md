# AI Content Transformation API

A backend AI platform that transforms user-generated text into structured, persona-driven, and style-adapted content. Built with **Spring Boot 4**, **Spring AI**, and **Java 21**, it takes a single tweet and rewrites it into five distinct voices using a Large Language Model.

## Overview

The API accepts an original tweet along with a few tuning parameters and returns five rewritten variants, each in a different persona. The LLM is steered by a detailed system prompt that enforces tone, length, emoji density, and modernization rules, while preserving the original intent of the message.

### Voices

Every request produces exactly five variants, one per voice:

| Voice | Description |
|-------|-------------|
| `PIRATE` | Pirate vibe, nautical metaphors, playful, the occasional "arr". |
| `INSPIRATIONAL` | Uplifting keynote-speaker energy, encouraging and positive. |
| `TECH_BRO` | Startup/VC tone — "ship, scale, iterate, 10x" — kept readable. |
| `IMPOSTER` | Self-doubting voice with imposter syndrome, humble yet insightful, ends hopeful. |
| `MONK` | Calm, minimal, reflective, zen. |

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.6** (Spring Web MVC)
- **Spring AI 2.0.0-M6** with the OpenAI model starter
- **Maven** (wrapper included)

## Features

- Rewrites a single tweet into five persona-driven variants in one call.
- Configurable **emoji level** (`low`, `med`, `high`) controlling emoji density.
- Configurable **modernization level** (`LOW`, `MED`, `EXTREME`) to refresh dated concepts with modern framing (AI/ML, GenAI, cloud-native).
- Optional **topic hint** to steer the rewrite toward a chosen subject.
- Structured JSON output mapped to typed Java records via Spring AI's `BeanOutputConverter`.
- Both `POST` (JSON body) and `GET` (query params) endpoints.
- A static HTML front-end served at the application root.

## Getting Started

### Prerequisites

- JDK 21 or later
- An OpenAI API key (or a compatible provider exposing the OpenAI API)

### Configuration

The application reads credentials from environment variables:

| Variable | Required | Default |
|----------|----------|---------|
| `OPENAI_API_KEY` | Yes | — |
| `OPENAI_BASE_URL` | No | `https://api.openai.com/v1` |

```bash
export OPENAI_API_KEY="your-api-key-here"
# Optional: point to a compatible endpoint
export OPENAI_BASE_URL="https://api.openai.com/v1"
```

### Run

Using the Maven wrapper:

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

The application starts on `http://localhost:8080`.

### Build

```bash
./mvnw clean package
java -jar target/poc-0.0.1-SNAPSHOT.jar
```

## API Reference

Base path: `/api`

### `POST /api/tweets`

Rewrites a tweet from a JSON request body.

**Request body**

```json
{
  "originalTweet": "How to learn how to program fast",
  "topicHint": "Spring AI",
  "emojiLevel": "low",
  "modernizationLevel": "extreme"
}
```

**Example**

```bash
curl -X POST http://localhost:8080/api/tweets \
  -H "Content-Type: application/json" \
  -d '{
        "originalTweet": "How to learn how to program fast",
        "topicHint": "Spring AI",
        "emojiLevel": "low",
        "modernizationLevel": "extreme"
      }'
```

### `GET /api/tweets`

Rewrites a tweet using query parameters. All parameters are optional and fall back to defaults.

| Parameter | Default |
|-----------|---------|
| `originalTweet` | `How to learn how to program fast` |
| `topicHint` | `Spring AI` |
| `emojiLevel` | `low` |
| `modernizationLevel` | `extreme` |

**Example**

```bash
curl "http://localhost:8080/api/tweets?originalTweet=Hello%20world&emojiLevel=med"
```

### Response

Both endpoints return the same structure:

```json
{
  "variants": [
    {
      "text": "Arr, chart yer course to code...",
      "voice": "PIRATE",
      "score": 0.84
    }
  ]
}
```

| Field | Description |
|-------|-------------|
| `text` | The rewritten tweet (max 280 characters). |
| `voice` | The persona used for the variant. |
| `score` | A relevance/quality score for the variant. |

## Project Structure

```
src/main/java/slop_generator/poc
├── PocApplication.java          # Spring Boot entry point
├── constants/
│   └── SlopConstants.java       # System & user prompt templates
├── controller/
│   └── SlopController.java      # REST endpoints (/api/tweets)
├── dto/                         # Request/response records & Voice enum
└── service/
    └── SlopService.java         # Spring AI ChatClient integration

src/main/resources
├── application.yml              # OpenAI configuration
└── static/index.html           # Front-end page
```

## License

No license has been specified for this project.
