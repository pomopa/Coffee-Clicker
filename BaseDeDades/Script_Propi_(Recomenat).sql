DROP DATABASE IF EXISTS sql11698487;

CREATE DATABASE sql11698487;
USE sql11698487;
DROP table if exists PowerUps;
DROP table if exists Generator;
DROP table if exists Historic;
DROP table if exists Game;
DROP table if exists User;

CREATE TABLE IF NOT EXISTS User (
    userName VARCHAR(100) PRIMARY KEY,
    mail VARCHAR(100),
    password VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS Game (
    gameName VARCHAR(100) PRIMARY KEY,
    ongoingGame BOOLEAN, 
    numResources BIGINT, -- long en java
    timeOnGame INTEGER, 
    userName VARCHAR(100), 
    FOREIGN KEY (userName) REFERENCES User(userName) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Historic (
    idHistoric INTEGER PRIMARY KEY AUTO_INCREMENT,
    gameName VARCHAR(100),
    numResources BIGINT,
    timeOnGame INTEGER,
    userName VARCHAR(100),
    FOREIGN KEY (userName) REFERENCES User(userName) ON DELETE CASCADE,
    FOREIGN KEY (gameName) REFERENCES Game(gameName) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS Generator (
    idGenerator INTEGER PRIMARY KEY AUTO_INCREMENT,
    generatorsType VARCHAR(100),
    numGenerator INTEGER, 
    cost INTEGER,
    incremental DOUBLE,
    production DOUBLE,
    image VARCHAR(255),
    gameName VARCHAR(100), 
    FOREIGN KEY (gameName) REFERENCES Game(gameName) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PowerUps (
    idPowerUps INTEGER PRIMARY KEY AUTO_INCREMENT,
    powerUpsType VARCHAR(100),
    affects VARCHAR(100),
    name VARCHAR(100), 
    cost INTEGER,
    bonus DOUBLE,
    threshold INTEGER,
    image VARCHAR(255),
    purchased BOOLEAN,
    gameName VARCHAR(100), 
    FOREIGN KEY (gameName) REFERENCES Game(gameName) ON DELETE CASCADE
);





-- Afegir dades
INSERT INTO User (userName, mail, password) VALUES
    ('David', 'david@gmail.com', 'Hola1234')
;


INSERT INTO Game (gameName, ongoingGame, numResources, timeOnGame, userName) VALUES
     ('partida1', 0, 16105, 307, 'David'),
     ('partida2', 1, 7, 9, 'David')
;

INSERT INTO Historic (gameName, numResources, timeOnGame, userName) VALUES
        ('partida1', 0, 1, 'David'),
        ('partida1', 1554, 60, 'David'),
        ('partida1', 1305, 70, 'David'),
        ('partida1', 1324, 71, 'David'),
        ('partida1', 2061, 106, 'David'),
        ('partida1', 2099, 107, 'David'),
        ('partida1', 946, 115, 'David'),
        ('partida1', 989, 116, 'David'),
        ('partida1', 1485, 125, 'David'),
        ('partida1', 1533, 126, 'David'),
        ('partida1', 1707, 140, 'David'),
        ('partida1', 1769, 141, 'David'),
        ('partida1', 2910, 172, 'David'),
        ('partida1', 2992, 173, 'David'),
        ('partida1', 2730, 192, 'David'),
        ('partida1', 2842, 193, 'David'),
        ('partida1', 6307, 200, 'David'),
        ('partida1', 6419, 201, 'David'),
        ('partida1', 4213, 209, 'David'),
        ('partida1', 4357, 210, 'David'),
        ('partida1', 2699, 222, 'David'),
        ('partida1', 2883, 223, 'David'),
        ('partida1', 6449, 228, 'David'),
        ('partida1', 6633, 228, 'David'),
        ('partida1', 4782, 240, 'David'),
        ('partida1', 4782, 241, 'David'),
        ('partida1', 5109, 250, 'David'),
        ('partida1', 5109, 251, 'David'),
        ('partida1', 12209, 257, 'David'),
        ('partida1', 12477, 258, 'David'),
        ('partida1', 5521, 260, 'David'),
        ('partida1', 5801, 261, 'David'),
        ('partida1', 16877, 271, 'David'),
        ('partida1', 16877, 271, 'David'),
        ('partida1', 5037, 273, 'David'),
        ('partida1', 5037, 274, 'David'),
        ('partida1', 11805, 287, 'David'),
        ('partida1', 12313, 288, 'David'),
        ('partida1', 7782, 292, 'David'),
        ('partida1', 7782, 293, 'David'),
        ('partida1', 18956, 299, 'David'),
        ('partida1', 18956, 300, 'David'),
        ('partida1', 8083, 303, 'David'),
        ('partida1', 8656, 304, 'David'),
        ('partida1', 16105, 307, 'David'),
        ('partida2', 0, 1, 'David'),
        ('partida2', 7, 10, 'David'),
        ('partida2', 7, 10, 'David')
;


INSERT INTO Generator (generatorsType, numGenerator, cost, incremental, production, image, gameName) VALUES
         ('Barista', 8, 9, 1.07, 128, 'resources/barista.png', 'partida1'),
         ('Cascada de cafè', 2, 132, 1.15, 80, 'resources/cascade.jpg', 'partida1'),
         ('Cafetera', 6, 3815, 1.25, 160, 'resources/coffeemachine.png', 'partida1'),
         ('George Clooney', 3, 23526, 1.33, 50, 'resources/george-clooney.jpg', 'partida1'),
         ('Granja', 0, 100000, 1.45, 200, 'resources/farmer.png', 'partida1'),
         ('Fabrica', 0, 500000, 1.6, 550, 'resources/factory.png', 'partida1'),
         ('Oompa Loompa', 0, 1000000, 1.7, 3000, 'resources/wonka.png', 'partida1'),
         ('Central Perk', 0, 3000000, 1.9, 10000, 'resources/centralperk.png', 'partida1'),
         ('Barista', 0, 5, 1.07, 4, 'resources/barista.png', 'partida2'),
         ('Cascada de cafè', 0, 100, 1.15, 10, 'resources/cascade.jpg', 'partida2'),
         ('Cafetera', 0, 1000, 1.25, 20, 'resources/coffeemachine.png', 'partida2'),
         ('George Clooney', 0, 10000, 1.33, 50, 'resources/george-clooney.jpg', 'partida2'),
         ('Granja', 0, 100000, 1.45, 200, 'resources/farmer.png', 'partida2'),
         ('Fabrica', 0, 500000, 1.6, 550, 'resources/factory.png', 'partida2'),
         ('Oompa Loompa', 0, 1000000, 1.7, 3000, 'resources/wonka.png', 'partida2'),
         ('Central Perk', 0, 3000000, 1.9, 10000, 'resources/centralperk.png', 'partida2')
;


INSERT INTO PowerUps (powerUpsType, affects, name, cost, bonus, threshold, image, purchased, gameName) VALUES
       ('ClickIncrease', 'click', 'Increment clicker 1', 100, 2, 50, 'resources/click.png', 1, 'partida1'),
       ('ClickIncrease', 'click', 'Increment clicker 2', 500, 2, 300, 'resources/click.png', 1, 'partida1'),
       ('ClickIncrease', 'click', 'Increment clicker 3', 1000, 2, 1000, 'resources/click.png', 1, 'partida1'),
       ('ClickIncrease', 'click', 'Increment clicker 4', 5000, 2, 50000, 'resources/click.png', 1, 'partida1'),
       ('ClickIncrease', 'click', 'Increment clicker 5', 10000, 2, 500000, 'resources/click.png', 1, 'partida1'),
       ('BaristaIncrease', 'Barista', 'Increment barista 1', 250, 2, 300, 'resources/barista.png', 1, 'partida1'),
       ('BaristaIncrease', 'Barista', 'Increment barista 2', 2000, 2, 300, 'resources/barista.png', 1, 'partida1'),
       ('BaristaIncrease', 'Barista', 'Increment barista 3', 6000, 2, 1000, 'resources/barista.png', 1, 'partida1'),
       ('BaristaIncrease', 'Barista', 'Increment barista 4', 10000, 2, 50000, 'resources/barista.png', 1, 'partida1'),
       ('BaristaIncrease', 'Barista', 'Increment barista 5', 15000, 2, 500000, 'resources/barista.png', 1, 'partida1'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 1', 500, 2, 300, 'resources/cascade.jpg', 1, 'partida1'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 2', 2500, 2, 300, 'resources/cascade.jpg', 1, 'partida1'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 3', 7000, 2, 1000, 'resources/cascade.jpg', 1, 'partida1'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 4', 15000, 2, 50000, 'resources/cascade.jpg', 0, 'partida1'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 5', 20000, 2, 500000, 'resources/cascade.jpg', 0, 'partida1'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 1', 3000, 2, 300, 'resources/coffeemachine.png', 1, 'partida1'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 2', 9000, 2, 300, 'resources/coffeemachine.png', 1, 'partida1'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 3', 15000, 2, 1000, 'resources/coffeemachine.png', 1, 'partida1'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 4', 21000, 2, 50000, 'resources/coffeemachine.png', 0, 'partida1'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 5', 23000, 2, 500000, 'resources/coffeemachine.png', 0, 'partida1'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 1', 30000, 2, 300, 'resources/george-clooney.jpg', 0, 'partida1'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 2', 40000, 2, 300, 'resources/george-clooney.jpg', 0, 'partida1'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 3', 50000, 2, 1000, 'resources/george-clooney.jpg', 0, 'partida1'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 4', 60000, 2, 50000, 'resources/george-clooney.jpg', 0, 'partida1'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 5', 700000, 2, 500000, 'resources/george-clooney.jpg', 0, 'partida1'),
       ('FarmIncrease', 'Granja', 'Increment granja 1', 250000, 2, 300, 'resources/farmer.png', 0, 'partida1'),
       ('FarmIncrease', 'Granja', 'Increment granja 2', 500000, 2, 300, 'resources/farmer.png', 0, 'partida1'),
       ('FarmIncrease', 'Granja', 'Increment granja 3', 750000, 2, 1000, 'resources/farmer.png', 0, 'partida1'),
       ('FarmIncrease', 'Granja', 'Increment granja 4', 1000000, 2, 50000, 'resources/farmer.png', 0, 'partida1'),
       ('FarmIncrease', 'Granja', 'Increment granja 5', 1500000, 2, 500000, 'resources/farmer.png', 0, 'partida1'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 1', 1000000, 2, 300, 'resources/factory.png', 0, 'partida1'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 2', 150000, 2, 300, 'resources/factory.png', 0, 'partida1'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 3', 2000000, 2, 1000, 'resources/factory.png', 0, 'partida1'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 4', 2500000, 2, 50000, 'resources/factory.png', 0, 'partida1'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 5', 3000000, 2, 500000, 'resources/factory.png', 0, 'partida1'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 1', 4000000, 2, 300, 'resources/wonka.png', 0, 'partida1'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 2', 5000000, 2, 300, 'resources/wonka.png', 0, 'partida1'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 3', 6000000, 2, 1000, 'resources/wonka.png', 0, 'partida1'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 4', 7000000, 2, 50000, 'resources/wonka.png', 0, 'partida1'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 5', 8000000, 2, 500000, 'resources/wonka.png', 0, 'partida1'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 1', 6000000, 2, 300, 'resources/centralperk.png', 0, 'partida1'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 2', 7500000, 2, 300, 'resources/centralperk.png', 0, 'partida1'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 3', 9000000, 2, 1000, 'resources/centralperk.png', 0, 'partida1'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 4', 10500000, 2, 50000, 'resources/centralperk.png', 0, 'partida1'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 5', 12000000, 2, 500000, 'resources/centralperk.png', 0, 'partida1'),
       ('ClickIncrease', 'click', 'Increment clicker 1', 100, 2, 50, 'resources/click.png', 0, 'partida2'),
       ('ClickIncrease', 'click', 'Increment clicker 2', 500, 2, 300, 'resources/click.png', 0, 'partida2'),
       ('ClickIncrease', 'click', 'Increment clicker 3', 1000, 2, 1000, 'resources/click.png', 0, 'partida2'),
       ('ClickIncrease', 'click', 'Increment clicker 4', 5000, 2, 50000, 'resources/click.png', 0, 'partida2'),
       ('ClickIncrease', 'click', 'Increment clicker 5', 10000, 2, 500000, 'resources/click.png', 0, 'partida2'),
       ('BaristaIncrease', 'Barista', 'Increment barista 1', 250, 2, 300, 'resources/barista.png', 0, 'partida2'),
       ('BaristaIncrease', 'Barista', 'Increment barista 2', 2000, 2, 300, 'resources/barista.png', 0, 'partida2'),
       ('BaristaIncrease', 'Barista', 'Increment barista 3', 6000, 2, 1000, 'resources/barista.png', 0, 'partida2'),
       ('BaristaIncrease', 'Barista', 'Increment barista 4', 10000, 2, 50000, 'resources/barista.png', 0, 'partida2'),
       ('BaristaIncrease', 'Barista', 'Increment barista 5', 15000, 2, 500000, 'resources/barista.png', 0, 'partida2'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 1', 500, 2, 300, 'resources/cascade.jpg', 0, 'partida2'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 2', 2500, 2, 300, 'resources/cascade.jpg', 0, 'partida2'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 3', 7000, 2, 1000, 'resources/cascade.jpg', 0, 'partida2'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 4', 15000, 2, 50000, 'resources/cascade.jpg', 0, 'partida2'),
       ('WaterfallIncrease', 'Cascada de cafè', 'Increment cascada 5', 20000, 2, 500000, 'resources/cascade.jpg', 0, 'partida2'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 1', 3000, 2, 300, 'resources/coffeemachine.png', 0, 'partida2'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 2', 9000, 2, 300, 'resources/coffeemachine.png', 0, 'partida2'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 3', 15000, 2, 1000, 'resources/coffeemachine.png', 0, 'partida2'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 4', 21000, 2, 50000, 'resources/coffeemachine.png', 0, 'partida2'),
       ('CafeteraIncrease', 'Cafetera', 'Increment cafetera 5', 23000, 2, 500000, 'resources/coffeemachine.png', 0, 'partida2'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 1', 30000, 2, 300, 'resources/george-clooney.jpg', 0, 'partida2'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 2', 40000, 2, 300, 'resources/george-clooney.jpg', 0, 'partida2'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 3', 50000, 2, 1000, 'resources/george-clooney.jpg', 0, 'partida2'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 4', 60000, 2, 50000, 'resources/george-clooney.jpg', 0, 'partida2'),
       ('ClooneyIncrease', 'George Clooney', 'Increment Clooney 5', 700000, 2, 500000, 'resources/george-clooney.jpg', 0, 'partida2'),
       ('FarmIncrease', 'Granja', 'Increment granja 1', 250000, 2, 300, 'resources/farmer.png', 0, 'partida2'),
       ('FarmIncrease', 'Granja', 'Increment granja 2', 500000, 2, 300, 'resources/farmer.png', 0, 'partida2'),
       ('FarmIncrease', 'Granja', 'Increment granja 3', 750000, 2, 1000, 'resources/farmer.png', 0, 'partida2'),
       ('FarmIncrease', 'Granja', 'Increment granja 4', 1000000, 2, 50000, 'resources/farmer.png', 0, 'partida2'),
       ('FarmIncrease', 'Granja', 'Increment granja 5', 1500000, 2, 500000, 'resources/farmer.png', 0, 'partida2'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 1', 1000000, 2, 300, 'resources/factory.png', 0, 'partida2'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 2', 150000, 2, 300, 'resources/factory.png', 0, 'partida2'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 3', 2000000, 2, 1000, 'resources/factory.png', 0, 'partida2'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 4', 2500000, 2, 50000, 'resources/factory.png', 0, 'partida2'),
       ('FactoryIncrease', 'Fabrica', 'Increment fabrica 5', 3000000, 2, 500000, 'resources/factory.png', 0, 'partida2'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 1', 4000000, 2, 300, 'resources/wonka.png', 0, 'partida2'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 2', 5000000, 2, 300, 'resources/wonka.png', 0, 'partida2'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 3', 6000000, 2, 1000, 'resources/wonka.png', 0, 'partida2'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 4', 7000000, 2, 50000, 'resources/wonka.png', 0, 'partida2'),
       ('OLIncrease', 'Oompa Loompa', 'Increment Loompas 5', 8000000, 2, 500000, 'resources/wonka.png', 0, 'partida2'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 1', 6000000, 2, 300, 'resources/centralperk.png', 0, 'partida2'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 2', 7500000, 2, 300, 'resources/centralperk.png', 0, 'partida2'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 3', 9000000, 2, 1000, 'resources/centralperk.png', 0, 'partida2'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 4', 10500000, 2, 50000, 'resources/centralperk.png', 0, 'partida2'),
       ('CPIncrease', 'Central Perk', 'Increment Central Perk 5', 12000000, 2, 500000, 'resources/centralperk.png', 0, 'partida2');





