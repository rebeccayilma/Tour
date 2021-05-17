# Tour Guide

### How to run the System

#### Prerequisites
- Have [Docker](https://www.docker.com/products/docker-desktop) installed on the computer and running
- Have [Node.js](https://nodejs.org/es/download/) installed on the computer

#### Commands
```sh
# Have the app built
> gradlew build

# Create containers and get the app running
#   backend: localhost:8080 (db at localhost:8036)
> docker compose up&

# Run frontend server
> cd src/main/viewTour/view
> npm install
> npm start&
> cd ../../../..
```
