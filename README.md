# Superhero Application API

This application is a Spring Boot application that provides 
simple CRUD (Create, Retrieve(All/Single), Update, Delete) operations for superheroes.

this API is made in the simplest form, the requirement is to create and get superheros.
1 table is created for superhero storage with 3 other wrapper tables to store
weapons/associations/powers of each superhero in a one to many form. which can be changed easily 
to many-to-many relation if business required.

### Endpoints

- Add a new Superhero
  - `POST /superhero`
    - Request Body:
    ```
      {
        alias: 'Iron Man',
        name: 'Tony Stark',
        powers: ['genius-intelligence', 'wealth'],
        weapons: ['arc-reactor', 'iron-man-suit', 'iron-legion'],
        origin: 'Kidnapped in Afghanistan, created the first iron-man suit to escape.',
        associations: ['war-machine', 'avengers', 'jarvis', 'thanos', 'pepper-potts']
      }
    ```

    - Response: (Same as request body if the addition is successful)
  
- Update an existing superhero
  - `PUT /superhero`
  - Request and response same as `POST`

- Fetch all superheros
  - `GET /superhero`

- Fetch specific superhero
  - `GET /superhero/{alias}`

- Delete a specific superhero
  - `DELETE /superhero/{alias}`

### Application requirements

* Java Development Kit (JDK) 17.0.1
* Spring Boot 2.6.1
* Spring Data JPA
* Lombok
* Jakarta EE
* liquibase
* H2 Database

### How to run the application
This application can be run in a Docker container. 
Assuming you have Docker and Docker Compose installed, you can run the application using:

**Build and start the containers**
`./start-docker.sh`

**Alternatively, use docker-compose**
`docker-compose build`
`docker-compose up`