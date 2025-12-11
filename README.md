# ☕️ Coffee Clicker 

## 👥 Authors
- [Pol Monné](https://github.com/pomopa)  
- [Marina Miranda](https://github.com/MarinaMiranda7)  
- [David Susagna](https://github.com/DavidSusagna)
- [Daniel Soler](https://github.com/dasolerfo/)
- [Ricard Morales]()

## 🧠 Project Description
Coffee Clicker is an incremental clicker-style game where the player’s goal is to produce as much coffee as possible through clicks, automated generators, and strategic upgrades. The project is designed to simulate a full game loop, including resource accumulation, generator management, and empowerer-based enhancements that improve efficiency and progression over time.

Coffee Clicker demonstrates concepts of object-oriented programming, configuration-driven system behavior, and real-time resource simulation. The result is a customizable and extensible incremental game framework that can be expanded with new mechanics, generators, or progression systems.


## 🗂️ Table of Contents

1. [Download & Installation](#1-download--installation)
2. [Requirements](#2--requirements)
3. [Database Configuration](#3--database-configuration)
   - [DatabaseInicialScript.sql](#databaseinicialscriptsql)
4. [Initial Game Configuration](#4--initial-game-configuration)
   - [newGame_Config.json](#newgame_configjson)
   - [generador_Config.json](#generador_configjson)
   - [empowerer_Config.json](#empowerer_configjson)


### 1. Download & Installation
```bash
git clone https://github.com/pomopa/Coffee-Clicker.git
```
## 2. 📋 Requirements:

The project requires the following libraries:
  - mysql-connector-j-8.3.0.jar
  - gson.jar per llegir documents JSON.

Make sure to have the libraries installed before running the project.

## 3. 📊 Database Configuration

To configure the database connection, follow these steps:

  1. Open the configuration file config.json.
      ```config/database_config.json```

  2. In this file you will find a section with the name "mysql" which contains the configuration for the MySql database.

  3. Modify the following fields according to your database configuration:
   ```json
      {
         "mysql": {
            "host": "host",
            "port": "port",
            "user": "user",
            "password": "password",
            "database": "database"
         }
      }
   ```
   - "**host**": Specifies the location of the database server.
   - "**port**": Corresponds to the port number in which the server is listening to connections.
   - "**user**": Specifies the username to autenticate in the database.
   - *password**": Insert the password associated with the username to authenticate the connection.
   - "**database**": Specify the name of the database to which the program will be connected.

4. Save the changes to the file config.json.

5. Configure the database with MySql

Finally, it's important to import the database configuration document, which can be found in the following route.

```
config/DatabaseInicialScript.sql
```
This document .sql is based on MySQL. Down below you will find a link to download and install mySQL. It is recomended to make use of MySQL Workbench to log into the database local server with the previously provided script.

Installation link for MySQL server and MySQL Workbench: [MySQL Community Downloads](https://dev.mysql.com/downloads/installer/)

Once the SQL script has been executed inside the server corresponding to the configuration document of the database, program execution can be started.

## 4. 👾 Initial Game Configuration

Below is the initial game configuration, which is composed of three JSON documents: newGame_Config.json, generador_Config.json, and empowerer_Config.json. A base configuration is provided, and if you don't wish to change anything you may not touch these files.
```
  config/generador_Config.json
  config/newGame_Config.json
  config/empowerer_Config.json
```

### 4.1 newGame_Config.json

This file contains the initial configuration for a new game. The parameters must be modified according to the needs of each game. The format is as follows:

```json
{
  "ongoingGame": true,
  "resource": "Cafe",
  "numResources": 0,
  "timeOnGame": 0
}
```
- **ongoingGame:** Indicates whether the game is in progress (true) or not (false).
- **resource:** Specifies the initial resource available to the player.
- **numResources:** Indicates the initial amount of the resource.
- **timeOnGame:** Total time elapsed in the game.

### 4.2 generador_Config.json

This file contains the initial configuration for the different types of generators available in the game. It is recommended to modify the parameters according to the desired characteristics of each generator. Each one follows the following format:

```json
{
  "generatorsType": "Barista Automàtic",
  "baseCost": 100.0,
  "incremental": 0.05,
  "production": 5.0,
  "image": "path.jpg",
  "increaseProduction": 0.0,
  "reduceBaseCost": 0.0
}
```

- **generatorsType:** Type of generator.
- **baseCost:** Base cost to acquire the generator.
- **incremental:** Production increase provided by the generator.
- **production:** Initial production of the generator.
- **image:** Path of the image that represents the generator.
- **increaseProduction:** This field represents a multiplier value between 0 and 1 that can increase the generator’s production.
- **reduceBaseCost:** This field represents a multiplier value between 0 and 1 that can reduce the generator’s cost.

The values of each field must be modified according to the game's specifications and the preferred configuration for each game.

### 4.3 empowerer_Config.json

This file contains the initial configuration for the different types of empowerers available in the game. It is recommended to modify the parameters according to the desired characteristics for each empowerer. Each one follows the following format:

```json
{
  "type": "reduceBaseCost",
  "baseCost": 40,
  "incremental": 0.15,
  "bonus": 0.3
}
```

- **type:** Name of the empowerer.
- **baseCost:** Represents the base cost of the empowerer.
- **incremental:** Represents the percentage that increases the price of the empowerer. This value must be between 0 and 1.
- **bonus:** Represents the value between 0 and 1 that benefits the generator in its corresponding field.
- 
The values of each field must be modified according to the game's specifications and the preferred configuration for each game.
