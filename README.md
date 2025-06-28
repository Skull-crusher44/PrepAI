# PrepAI - AI-Powered Interview Coach

[](https://github.com)
[](https://opensource.org/licenses/MIT)
[](https://www.google.com/search?q=CONTRIBUTING.md)

**PrepAI is an intelligent, real-time interview preparation platform designed to simulate realistic technical and behavioral interviews. By leveraging the Gemini API, it provides a dynamic environment where users can practice coding, answer resume-specific questions, and receive instant, actionable feedback.**

This project bridges the gap between theoretical knowledge and practical interview performance by creating a low-pressure space for users to hone their skills.

## ✨ Core Features

* **📄 AI Resume Analysis**: Upload your resume (PDF/DOCX) and the AI parses it to ask personalized behavioral questions based on your experience and projects.
* **💻 Real-Time Technical Interviews**: Engage in a live coding session with a shared editor. The AI acts as your interviewer, observing your code as you type.
* **🤖 Instant AI-Powered Feedback**: Receive contextual hints and suggestions in real-time if you get stuck or make a syntax error, just like a helpful human interviewer.
* **🗣️ Personalized Behavioral Sessions**: The AI generates and asks questions directly related to your resume, such as, "Tell me more about the 'XYZ' project you listed."
* **📊 Performance Analytics Dashboard**: Get a comprehensive report after each session analyzing your coding proficiency, problem-solving approach, and communication skills.
* **🎙️ (Optional) Voice-to-Text Analysis**: Verbally explain your thought process and the AI can process the audio for a more holistic evaluation.


## 🛠️ Tech Stack

### Frontend

* **React**: Core UI library.
* **Recoil**: State management.
* **Tailwind CSS & Shadcn/ui**: Styling and UI components.
* **Socket.io-client**: Real-time communication via WebSockets.
* **WebRTC** (Optional): For real-time audio streaming.

### Backend

* **Spring Boot**: Core Java framework.
* **Spring for WebSocket**: Handling WebSocket connections.
* **socket.io-java-server**: Socket.IO server implementation.
* **Apache Tika**: Parsing resumes (PDF, DOCX).
* **Spring Security**: User authentication and authorization.

### Database

* **PostgreSQL**: Relational database.
* **Spring Data JPA**: Data access and object-relational mapping.

### AI Integration

* **Gemini API**: For code analysis, response evaluation, real-time hints, and generating resume-based questions.

## 🚀 Getting Started

### Prerequisites

* Java 17+
* Node.js v18+
* PostgreSQL
* Maven or Gradle
* A Gemini API Key

### 1\. Clone the repository

```bash
git clone https://github.com/your-username/PrepAI.git
cd PrepAI
```

### 2\. Backend Setup

```bash
# Navigate to the backend directory
cd backend

# Create an application.properties file in src/main/resources
# and add the following environment variables:
# spring.datasource.url=...
# spring.datasource.username=...
# spring.datasource.password=...
# gemini.api.key=YOUR_GEMINI_API_KEY

# Run the application
mvn spring-boot:run
```

### 3\. Frontend Setup

```bash
# Navigate to the frontend directory
cd ../frontend

# Install dependencies
npm install

# Create a .env.local file and add any necessary
# client-side environment variables
# REACT_APP_API_URL=http://localhost:8080

# Start the development server
npm start
```

## 🤝 How to Contribute

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

Please read `CONTRIBUTING.md` for the full guide.

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

-----
