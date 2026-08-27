# Spring AI Demo

A hands-on Spring Boot project for learning the fundamentals of **Spring AI** and integrating AI models into Java applications.

This project focuses on understanding how Spring AI constructs prompts, messages, and model requests, and how the higher-level `ChatClient` fluent API simplifies interaction with AI models.

## 🎯 Project Goals

The main goal of this project is to understand the core abstractions provided by Spring AI rather than simply calling an AI API.

The project explores:

* Prompts
* Prompt Templates
* Dynamic prompt values
* User Messages
* System Messages
* `Message`
* `Prompt`
* `ChatModel`
* `ChatResponse`
* `ChatClient`
* ChatClient Fluent API
* Spring AI auto-configuration
* Interaction between Spring Boot, Spring AI, and an AI model

## 🏗️ Spring AI Concepts

The core relationship between the concepts explored in this project can be visualized as:

```text
                    Spring Boot Application
                              │
                              ▼
                         ChatClient
                              │
                     Fluent API / Prompt
                              │
                              ▼
                           Prompt
                              │
                    ┌─────────┴─────────┐
                    ▼                   ▼
             System Message       User Message
                    │                   │
                    └─────────┬─────────┘
                              ▼
                          ChatModel
                              │
                              ▼
                         AI Provider
                              │
                              ▼
                         AI Model
                              │
                              ▼
                       ChatResponse
```

Spring AI's `ChatModel` provides a portable interface for interacting with different AI model providers, while `ChatClient` provides a more convenient fluent API for building and executing requests.

## 📚 Topics Covered

### 1. ChatModel

`ChatModel` is the lower-level abstraction for communicating with an AI chat model.

It accepts a `Prompt` and returns a `ChatResponse`.

Conceptually:

```text
Prompt
  │
  ▼
ChatModel
  │
  ▼
ChatResponse
```

Spring AI's `ChatModel` abstraction allows applications to interact with different AI model implementations through a common API.

---

### 2. Prompt

A `Prompt` represents the input sent to the AI model.

A prompt contains:

* Messages
* Model options

Conceptually:

```text
Prompt
 ├── System Message
 ├── User Message
 └── Chat Options
```

Spring AI represents a `Prompt` as a collection of `Message` objects together with optional model options.

---

### 3. Messages

Spring AI uses messages to represent different roles within an AI conversation.

Important message types include:

```text
System Message
User Message
Assistant Message
Tool-related messages
```

For this project, the main focus is on:

**System Message**

Provides instructions that guide the AI model's behavior.

Example:

```text
You are a helpful Java programming assistant.
Always explain concepts using Java examples.
```

**User Message**

Contains the actual request or question.

Example:

```text
Explain dependency injection in Spring Boot.
```

The model receives these messages as part of the prompt.

---

### 4. Prompt Templates

Prompt templates allow us to create reusable prompts containing dynamic values.

For example:

```text
What is {technology}?
Explain it using this example: {example}
```

The placeholders can be replaced at runtime:

```text
technology = Spring Boot
example = Dependency Injection
```

Result:

```text
What is Spring Boot?
Explain it using this example: Dependency Injection
```

This allows prompts to be separated from the data being supplied to the AI model.

Spring AI provides `PromptTemplate` and template rendering support for this purpose.

---

### 5. ChatClient

`ChatClient` provides a higher-level API for communicating with AI models.

Instead of manually creating all the underlying prompt objects, we can use a fluent API.

Example:

```java
chatClient
        .prompt()
        .user("Explain Spring Dependency Injection")
        .call()
        .content();
```

This can be read almost like a sentence:

```text
Create a prompt
      ↓
Add a user message
      ↓
Call the AI model
      ↓
Return the response content
```

Spring AI provides an auto-configured `ChatClient.Builder` when a supported `ChatModel` is configured.

---

## 🔗 ChatClient Fluent API

One of the major topics in this project is the `ChatClient` fluent API.

The API allows prompts to be built incrementally.

For example:

```java
chatClient
        .prompt()
        .system("You are an experienced Java developer.")
        .user("Explain Spring Boot.")
        .call()
        .content();
```

The fluent API supports configuring different parts of the request, including:

* System instructions
* User input
* Prompt templates
* Model options
* Advisors
* Tools
* Response handling

Spring AI describes `ChatClient` as a fluent API for communicating with an AI model, with synchronous and streaming programming models available.

---

## 🧩 ChatClient vs ChatModel

An important distinction explored in this project is the relationship between `ChatClient` and `ChatModel`.

### ChatModel

Lower-level abstraction:

```text
Prompt
   ↓
ChatModel
   ↓
ChatResponse
```

### ChatClient

Higher-level fluent abstraction:

```text
ChatClient
    ↓
.prompt()
    ↓
.system()
    ↓
.user()
    ↓
.call()
    ↓
.content()
```

A useful way to think about it:

```text
ChatClient
   │
   │ higher-level API
   ▼
ChatModel
   │
   │ model abstraction
   ▼
AI Provider
   │
   ▼
AI Model
```

Spring AI's documentation describes `ChatClient` as being built on top of `ChatModel`, providing additional higher-level constructs such as advisors and other request-building capabilities.

---

## 🔄 Request Flow

A typical request in this project follows this flow:

```text
User Request
     │
     ▼
Spring Boot Controller
     │
     ▼
ChatClient
     │
     ▼
Prompt
     │
     ├── System Message
     │
     └── User Message
     │
     ▼
ChatModel
     │
     ▼
AI Provider
     │
     ▼
AI Model
     │
     ▼
ChatResponse
     │
     ▼
Application
```

This flow helps demonstrate how Spring AI abstracts the underlying AI provider while giving Java developers familiar Spring-style APIs.

---

## 🛠️ Technology Stack

* Java 17
* Spring Boot
* Spring AI
* Maven
* Spring Web
* IntelliJ IDEA
* AI Model Provider

## 📁 Project Structure

The project will evolve as new Spring AI concepts are introduced.

```text
spring-ai-demo/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── springaidemo/
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│
├── .gitignore
├── pom.xml
└── README.md
```

## 🧪 Examples

The project contains examples demonstrating different ways of interacting with Spring AI.

### Simple User Prompt

```java
chatClient
        .prompt()
        .user("What is Spring AI?")
        .call()
        .content();
```

### System + User Message

```java
chatClient
        .prompt()
        .system("You are a senior Java developer.")
        .user("Explain dependency injection.")
        .call()
        .content();
```

### Dynamic Prompt

```java
chatClient
        .prompt()
        .user(user -> user
                .text("What is {technology}?")
                .param("technology", "Spring AI"))
        .call()
        .content();
```

These examples demonstrate how Spring AI can move from simple text input to structured prompts containing multiple message roles and dynamic values.

## 🧠 Learning Progress

* [x] Spring Boot project setup
* [x] Maven configuration
* [x] Spring AI dependency setup
* [x] Understand `ChatModel`
* [x] Understand `Prompt`
* [x] Understand `Message`
* [x] Understand `UserMessage`
* [x] Understand `SystemMessage`
* [x] Understand `ChatResponse`
* [x] Create prompts programmatically
* [x] Create `PromptTemplate`
* [x] Use dynamic prompt values
* [x] Explore the `ChatClient`
* [x] Explore the `ChatClient` Fluent API
* [x] Compare `ChatClient` and `ChatModel`
* [x] Configure system instructions
* [x] Configure default prompts
* [x] Explore model options
* [ ] Explore streaming responses
* [ ] Structured output
* [ ] Advisors
* [ ] Embeddings
* [ ] Vector stores
* [ ] RAG
* [ ] Tool calling
* [ ] MCP
* [ ] AI Agents

## 📖 Official Documentation

This project follows the official Spring AI documentation and API references.

**Spring AI Chat Model API**

The Chat Model API documentation covers `ChatModel`, `Prompt`, `Message`, `ChatResponse`, model options, and supported model implementations.

**Spring AI ChatClient API**

The ChatClient documentation covers the fluent API, prompt construction, system/user messages, prompt templates, responses, defaults, and advisors.

**Spring AI Prompt Documentation**

The Prompt documentation covers prompts, messages, prompt templates, and template rendering.

## 🚧 Project Status

This is an ongoing learning project.

The code and documentation will be updated as new Spring AI concepts are explored and implemented.

The intention is to build understanding incrementally:

```text
ChatModel
    ↓
Prompt
    ↓
Messages
    ↓
Prompt Templates
    ↓
ChatClient
    ↓
Fluent API
    ↓
Structured Output
    ↓
Advisors
    ↓
RAG
    ↓
Tools
    ↓
MCP
    ↓
AI Agents
```

## 👨‍💻 Author

**Avneet Singh**

Senior Java Developer | Java | Spring Boot | Microservices | Cloud | AI

---

⭐ This repository documents my hands-on journey into **Spring AI and Generative AI development with Java and Spring Boot**.
