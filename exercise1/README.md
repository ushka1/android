# Android Class - Exercise 1

## Description

Project is a simple Java server responding "Hello, world!" to every request it receives.

## Commands

- Build Docker image

  ```cmd
  docker build -t ushka1/android-class-exercise-1 .
  ```

- Start Docker container

  ```cmd
  docker run --rm -p 8080:8080 ushka1/android-class-exercise-1
  ```

- Alternatively, you can use Docker Compose

  ```cmd
  docker-compose up
  ```

- Push Docker image to Docker Hub

  ```cmd
  docker push ushka1/android-class-exercise-1
  ```
