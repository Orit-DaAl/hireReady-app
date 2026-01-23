# 🎓 HireReady - Smart Interview Preparation Platform

**HireReady** is a sophisticated interview preparation platform leveraging a **Microservices Architecture** to help candidates ace their job interviews. The system provides automated, company-specific preparation "kits" powered by **Google Gemini AI**, based on real-time job descriptions and community data.

Built with **Java Spring Boot** (backend) and **React + Vite** (frontend).

---

<div align="center">
  <h2>📺 Application Demo</h2>
  <video src="https://github.com/user-attachments/assets/96ee202f-0a3d-4558-9836-d5e37d3169f3" controls="controls" style="max-width: 100%; border-radius: 10px;">
    Your browser does not support the video tag.
  </video>
</div>

---

## 🏗️ System Architecture

The platform is designed for high scalability and modularity, using a distributed microservices approach:

### 🛠️ Core Infrastructure
* **API Gateway:** Central entry point using **Spring Cloud Gateway** for intelligent request routing and security.
* **Eureka Server:** Service registry enabling dynamic service discovery across the cluster.
* **Config Server:** Centralized external configuration management for all microservices.


### 💼 Business Services
* **User Service:** Manages user identity and profiles, integrated with **Keycloak** for authentication and **PostgreSQL** for persistence.
* **JobInterview Service:** Manages the preparation lifecycle for interviews. It tracks the interview details and the readiness status.
    * **Database:** **MongoDB**.
    * **Messaging:** Publishes events to **RabbitMQ** when a new interview is added.
    * **AI Service:** An asynchronous service that listens to RabbitMQ. It analyzes interview details using **Google Gemini AI** to generate a custom preparation kit.
    * **Database:** **MongoDB**.

![](./assets/flow.png)
---

## 🧠 AI Intelligence Layer (The "Prep Kit")

When an interview is added, the **AI Service** processes the request using a specialized career-coach prompt. It generates:

1.  **Role Overview:** Focused strategy and role analysis.
2.  **Key Requirements:** Crucial skills extracted from the Job Description.
3.  **Company Culture:** Insight into values and work environment.
4.  **Predicted Questions:** Questions tailored to the specific tech stack and interview type (Technical, HR, Manager, etc.).
5.  **Community Questions:** Real-world questions reported by candidates from platforms like Glassdoor, Reddit, and TheWorker.
6.  **Focus Areas & Tips:** Actionable technical and behavioral advice.

---

## 💻 Frontend Experience

Developed with **React + Vite**, the UI is designed for a focused and intuitive user experience:
* **Secure Login:** Integrated with **Keycloak** SSO.
* **Interview Dashboard:** A comprehensive list of upcoming interviews.
* **Smart Form:** Easy input for job descriptions and URLs.
* **Detailed Preparation View:** Interactive display of the AI-generated "Prep Kit" for each interview.

---

## 🛠️ Tech Stack

* **Backend:** Java 21, Spring Boot 3.x, Spring Cloud (Gateway, Config, Eureka), Maven.
* **Frontend:** React, Vite, Material UI.
* **Databases:** MongoDB (Job Interviews & AI Insights), PostgreSQL (Users).
* **Messaging:** RabbitMQ (Event-driven analysis).
* **Security:** Keycloak (OAuth2).
* **AI Engine:** Google Gemini API.

---

## 🏁 Getting Started

### 1. Prerequisites

* **Java 21** & **Maven** (For Backend Services)
* **Node.js & npm** (Required only for running the React Frontend)
* **Docker Desktop** (For Infrastructure: Postgres, Mongo, RabbitMQ, Keycloak)
* **Lombok Plugin** installed in your IDE

### 2. Infrastructure Setup
Run the following commands to start the required external tools:


#### Start Databases, Message Broker & Auth
```bash
docker run -d --name hire-postgres -p 5432:5432 -e POSTGRES_PASSWORD=password postgres
docker run -d --name hire-mongo -p 27017:27017 mongo
docker run -d --name hire-rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
docker run -d --name hire-keycloak -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
```

### 3. Launch Sequence
To ensure the microservices communicate correctly, start them in the following specific order:

1.  **Config Server** – Wait until the logs show the server is fully started.
2.  **Discovery Service (Eureka)** – Wait for the dashboard to be accessible at `http://localhost:8761`.
3.  **Business Services** – Launch the core logic services (They will register themselves in Eureka):
    * `User-Service`
    * `JobInterview-Service`
    * `AI-Service`
4.  **API Gateway** – Start the gateway last to ensure all routes are discoverable in the registry.
5.  **Frontend**:
    ```bash
    cd hireReady-app-frontend
    npm install
    npm run dev
    ```

6.   **Environment Variables** - 
Note: Ensure all Environment Variables (API Keys, DB Passwords) are set before launching the services.

To keep sensitive data secure, define the following variables in your environment:

| Variable | Purpose |
| :--- | :--- |
| `GEMINI_API_KEY` | Your Google Gemini API Key |
| `GEMINI_API_URL` | Your Google Gemini URL |
| `POSTGRES_PASSWORD` | Password for User Database |
| `RABBITMQ_USERNAME` | Username for Message Broker |
| `RABBITMQ_PASSWORD` | Password for Message Broker |
| `KEYCLOAK_JWK_URI` | URI for Token Validation |


📞 Contact with me: 
**Orit Alster** - [LinkedIn Profile](https://www.linkedin.com/in/orit-alster/)

