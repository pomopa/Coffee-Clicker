# Projecte Coffee Clicker 3

## Grup: dpoo_s2_project_c_coffee-clicker-3

### Integrants:
- Marina Miranda Riaza: marina.miranda@students.salle.url.edu
- Pol Monné Parera: pol.monne@students.salle.url.edu
- Ricard Morales Prat: ricard.morales@students.salle.url.edu
- Daniel Soler Fontanet: daniel.soler@students.salle.url.edu
- David Susagna Holgado: david.susagna@students.salle.url.edu


# Índex

1. [Descàrrega i Instal·lació](#des)
2. [Requisits](#requisits)
3. [Configuració de la Base de Dades](#configuració-de-la-base-de-dades)
   - [DatabaseInicialScript.sql](#newgame_configjson)
4. [Configuració Inicial del Joc](#configuració-inicial-del-joc)
   - [newGame_Config.json](#newgame_configjson)
   - [generador_Config.json](#generador_configjson)
   - [empowerer_Config.json](#empowerer_configjson)


### 1. Descarga e Instalación:
```bash
git clone https://bitbucket22.salle.url.edu:7943/scm/dpoo23/dpoo_s2_project_c_coffee-clicker-3.git
```
## 2. Requisits:

Es requereixen les següents biblioteques:
  - mysql-connector-j-8.3.0.jar
  - gson.jar per llegir documents JSON.

Assegura't de tenir aquestes biblioteques abans d'executar el projecte.

## 3. Configuració base de dades

Per configurar la connexió de l'aplicació a la base de dades, segueix aquests passos:

  1. Obre el fitxer de configuració config.json.
      ```config/database_config.json```

  2. Dins d'aquest fitxer, trobaràs una secció amb el nom "mysql" que conté la configuració per a la base de dades MySQL.

  3. Modifica els següents camps segons la configuració de la teva base de dades:
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
     1. "**host**": Especifica la direcció del servidor de la base de dades.
     2. "**port**": Indica el número de port en el qual el servidor de la base de dades està escoltant les connexions.
     3. "**user**": Proporciona el nom d'usuari per autenticar-se a la base de dades.
     4. "**password**": Introdueix la contrasenya associada al nom d'usuari per autenticar la connexió.
     5. "**database**": Especifica el nom de la base de dades a la qual l'aplicació es connectarà.

4. Desa els canvis en el fitxer config.json.

5. Configura la base de dades amb MySql

Per últim, és important importar el document de configuració de la base de dades, el qual es troba a la ruta que es mostra a continuació.

```
config/DatabaseInicialScript.sql
```
Aquest document .sql està basat en MySQL. A continuació, trobaràs un enllaç per descarregar i instal·lar MySQL.
Es recomana fer ús del MySQL Workbench per iniciar la base de dades en el servidor local amb el script proporcionat anteriorment.

Enllaç d'instal·lació de MySql server i MySQL Workbench: [MySQL Community Downloads](https://dev.mysql.com/downloads/installer/)

Un cop executat l'script SQL dins el servidor corresponent al document de configuració de la base de dades, es pot iniciar l'execució.

D'altra banda, cal destacar que amb la configuració per defecte, tot aquest procediment es pot obviar ja que el servidor de bases de dades actual està disponible en línia.



## 4. Configuració inicial d'una partida

A continuació, es detalla la configuració inicial del joc que es compon de tres documents JSON: newGame_Config.json, generador_Config.json i empowerer_Config.json.

```
  config/generador_Config.json
  config/newGame_Config.json
  config/empowerer_Config.json
```

### 4.1 newGame_Config.json

Aquest fitxer conté la configuració inicial per a una nova partida. Els paràmetres s'han de modificar segons les necessitats de cada partida. El format és el següent:

```json
{
  "ongoingGame": true,
  "resource": "Cafe",
  "numResources": 0,
  "timeOnGame": 0
}
```
- **ongoingGame:** Indica si la partida està en curs (true) o no (false).
- **resource:** Especifica el recurs inicial disponible per al jugador.
- **numResources:** Indica la quantitat inicial del recurs.
- **timeOnGame:** Temps total transcorregut en la partida.

### 4.2 generador_Config.json

Aquest fitxer conté la configuració inicial per als diferents tipus de generadors disponibles al joc. Es recomana modificar els paràmetres segons les característiques desitjades per a cada generador. El format de cadascun és el següent:

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

- **generatorsType:** Tipus de generador.
- **baseCost:** Cost base per adquirir el generador.
- **incremental:** Increment de producció proporcionat pel generador.
- **production:** Producció inicial del generador.
- **image:** Ruta de la imatge que representa el generador.
- **increaseProduction:** Aquest camp representa un valor multiplicador entre 0 i 1 que pot augmentar la producció del generador.
- **reduceBaseCost:** Aquest camp representa un valor multiplicador entre 0 i 1 que pot reduir el cost del generador.

S'han de modificar els valors de cada camp segons les especificacions del joc i les preferències de configuració de cada partida.

### 4.3 empowerer_Config.json

Aquest fitxer conté la configuració inicial per als diferents tipus de potenciadors disponibles al joc. Es recomana modificar els paràmetres segons les característiques desitjades per a cada potenciador. El format de cadascun és el següent:

```json
{
  "type": "reduceBaseCost",
  "baseCost": 40,
  "incremental": 0.15,
  "bonus": 0.3
}
```

- **type:** Nom del potenciador.
- **baseCost:** Representa el cost base del empoderador.
- **incremental:** Representa el percentatge que incrementa el preu de l'empoderador. Aquest valor ha d'estar entre 0 i 1.
- **bonus:** Representa el valor entre 0 i 1 que beneficia en el seu camp al generador.

S'han de modificar els valors de cada camp segons les especificacions del joc i les preferències de configuració de cada partida.




