# Just another pool game
![](https://gitlab.ewi.tudelft.nl/cse2115/2019-2020/PO/sem-group-90/template/raw/dev/desktop/assets/sprite/logo.png)


### Prerequisites
* Java 13 (JDK)

## Running

### Client

First build the client with 
```sh
gradlew shadowjar
```

then to run the client you can run use:
```sh
java -jar desktop/build/libs/desktop-1.0-all.jar
```

### Server (Normal)

Build the server jar as following:
```sh
gradlew bootjar
```

then run it with
```sh
java -jar server/build/libs/server.jar
```


### Server (Docker)
We also provide a Dockerfile for ease of deployment.

To build the image run:
```sh
docker build -t pool-server .
```

and run it with:
```sh
docker run -p 8080:8080 pool-server 
```

## Development
To run our project in IntelliJ you need to have the Lombok plugin.

Besides that everything should just work in IntelliJ.


## Deployment notes
By default the server uses an in memory H2 database, if you would like to switch
this you can either edit `server/src/main/resources/application.properties`
and comment out and modify the `spring.datasource.[url,username,password]`

This config can also be provided via the environment via the usual ways,
to see config options we refer to the [spring documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config)

