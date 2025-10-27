# URL Shortener

This is a simple URL shortening application built with Spring Boot. It allows users to shorten long URLs and track click statistics for the shortened links.

## Features

*   **Shorten URLs**: Convert long URLs into short, memorable codes.
*   **Redirect**: Redirect users from the short URL to the original long URL.
*   **Click Tracking**: Monitor the number of clicks for each shortened URL.
*   **URL Statistics**: View detailed statistics for a specific short URL.

## Technologies Used

*   **Spring Boot**: For building the web application.
*   **Spring Data JPA**: For data persistence with Hibernate.
*   **H2 Database**: An in-memory database used for development and testing.
*   **Gradle**: For dependency management and build automation.
*   **Thymeleaf**: For server-side rendering of HTML templates.

## Setup and Running the Application

To set up and run the application locally, follow these steps:

1.  **Clone the repository**:
    ```bash
    git clone <repository_url>
    cd url-shortener
    ```

2.  **Build the project**:
    ```bash
    ./gradlew build
    ```

3.  **Run the application**:
    ```bash
    ./gradlew bootRun
    ```
    The application will start on `http://localhost:8080` (or the port configured in `src/main/resources/application.properties`).

## Usage

Once the application is running:

*   **Homepage**: Open `http://localhost:8080` in your browser to access the URL shortening form.
*   **Shorten URL**: Enter a long URL in the input field and click "Shorten". The shortened URL will be displayed.
*   **Redirect**: Access a shortened URL (e.g., `http://localhost:8080/abcde`) to be redirected to the original URL.
*   **View Statistics**: To view statistics for a short code, navigate to `http://localhost:8080/stats/{shortCode}` (e.g., `http://localhost:8080/stats/abcde`).

## API Endpoints

*   `GET /`: Homepage to shorten URLs.
*   `POST /shorten`: Shortens a given URL.
*   `GET /{shortCode}`: Redirects to the original URL and increments click count.
*   `GET /stats/{shortCode}`: Displays statistics for a given short code.

## Configuration

The application can be configured via `src/main/resources/application.properties`:

*   `server.port`: The port on which the application runs.
*   `spring.datasource.*`: Database connection settings (currently H2 in-memory).
*   `app.base-url`: The base URL used for generating shortened links (e.g., `http://localhost:8080`).
