# Exercise 2 - KTOR

## Description

[Dockerhub image link.](https://hub.docker.com/r/ushka1/android-class-exercise2-ktor)

## Postman collection

1. In order to swiftly test the API using Postman, you may import the collection provided at the following
   location: [docs/exercise2-ktor.postman_collection.json](./docs/exercise2-ktor.postman_collection.json).

## Project notes

1. Prior to creating any **Product**, it is essential to have a minimum of one **Category** due to their interdependent
   relationship.
2. It is important to note that this project lacks sophisticated validation error handling. Consequently, if an
   incorrect request body is submitted, you may receive a generic error response (HTTP 500) without specific details
   regarding the underlying issue.
3. CORS has been correctly configured for both the `/products` and `/categories` routes, permitting access for two
   specific
   hosts: `localhost` and `example.com`.