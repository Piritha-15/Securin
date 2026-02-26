Recipe API

This is a simple Spring Boot project to manage recipes.
It reads recipe data from a JSON file and stores it in a MySQL database.
It also provides REST APIs to create and fetch recipes.

What it does

Import recipes from JSON

Save data into MySQL

Create new recipe (POST)

Get all recipes

Get top recipes by rating

Automatically calculate totalTime (prepTime + cookTime)

Tech Used

Java

Spring Boot

Spring Data JPA

MySQL

How to Run

Start MySQL

Create a database

Update database details in application.properties

Run the application

Server runs on:

http://localhost:8080
Example API

POST /recipes

{
  "title": "Pasta",
  "cuisine": "Italian",
  "prepTime": 10,
  "cookTime": 20
}

The response returns the generated ID.
