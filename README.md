
# 🧠 AI-Based Interview Evaluation API

## 📌 Project Overview

This is a lightweight **Spring Boot REST API** designed to evaluate written interview answers using **OpenAI's GPT-4o** model. 
The API receives a list of questions and candidate-written answers, sends them to OpenAI for evaluation, and returns:

- 📊 A **grade** (scale: 1 to 10)
- 📝 A **short feedback summary** describing the candidate's performance

## 💡 Key Features

- Accepts a list of `QuestionAnswer` objects via a POST endpoint
- Uses **Spring AI's `ChatClient`** to interact with OpenAI's GPT-4o model
- Generates AI-powered feedback and a numerical grade
- Simple JSON-based API for frontend or other client integration

## 📬 Example API Usage

### Endpoint:
`POST /api/evaluate`

### Request Body:
```json
[
  {
    "question": "What is polymorphism in OOP?",
    "answer": "Polymorphism allows objects to be treated as instances of their parent class."
  },
  {
    "question": "What is the difference between ArrayList and LinkedList in Java?",
    "answer": "ArrayList uses an array, LinkedList uses nodes."
  }
]
```

### Response: 
```json

{
  "grade": 8,
  "feedback": "The candidate demonstrates solid understanding of core concepts, but the explanations could benefit from more detail and real-world context."
}
```
