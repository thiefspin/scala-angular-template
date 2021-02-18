# Platform Template (Scala & Angular 2)

Template for creating platforms with UI's

* https://www.playframework.com/
* https://angular.io/

## Build Status

![Build Status](https://github.com/thiefspin/scala-angular-template/workflows/Scala%20CI/badge.svg?branch=master)

## Introduction

If you are like me and you really like Scala but you are not quite satisfied with the provided Twirl templating take a
look at this. It's a normal Scala/Play application but with Angular (currently version 8) baked in there. Instead of
creating two separate applications in their own git repositories this way of doing things gives you some creature
comforts. Don't worry you can still deploy separately if you wish. This example just follows the path of least
resistance so the UI and backend elements gets bundled together at build time.

## What do you actually get for free here

* A fully bootstrapped Play backend
* Working login endpoint with JWT based authentication.
* Fully working user repository (See database section)
* Build the whole application (including UI) as a docker image with one sbt command
* A login UI with fully working authentication
* A sample dashboard
* The UI already has all the bootstrap files included (Bootstrap 4) so no CSS magic (Unless you want to).
  See https://getbootstrap.com/

## Technologies

Here is the list of all the technologies used in the example:

* Playframework version 2.8 (https://www.playframework.com/documentation/2.8.x/Home)
* Angular version 8 (https://angular.io/)
* Docker (https://www.docker.com/)
* Bootstrap version 4 (https://getbootstrap.com/)
* Postgresql latest version (https://www.postgresql.org/)
* Java version 8 or 11
* Scala 2.13.*
* npm ( I used 7.4.0)
* NodeJS v15.6.0
* Angular CLI (https://cli.angular.io/)

## Getting started

First of all get the code on your machine (Preferably by using `git clone` but I'm not gonna judge here).
You will also need:
* Some JDK (8 or 11)
* SBT 
* NodeJS (includes NPM)
* The angular CLI

### The Database

The example includes a `docker-compose.yml` file to easily allow you to spin up a database. This will also
run all the sql scripts needed to create the structure as well as a dummy user for you.

If this tickles your fancy and you have docker installed on your machine just run the following command in the root
directory of the project.

```bash
docker-compose up
```

It should give you something like this in the logs:

```
listening on IPv4 address "0.0.0.0", port 5432
database system is ready to accept connections

```

If you do not have docker or prefer to use your own Postgres instance you are welcome to do that. Just note the
following:

* You can find the SQL to create the schema and tables in `./sql/schema.sql`
* You will need to set 3 ENV vars namely `POSTGRES_URL`, `POSTGRES_USER` and `POSTGRES_PASSWORD`
* Also if you do this I assume you know what you're doing so I'm just giving the basics

### Running the application
You obviously need Java and SBT installed (But you know this). Never hurts to double check though.
You also need `npm`, `ng` and `node` installed 

```bash
java -version
openjdk version "1.8.0_275"
OpenJDK Runtime Environment (build 1.8.0_275-bre_2020_11_16_16_29-b00)
OpenJDK 64-Bit Server VM (build 25.275-b00, mixed mode)

npm -v
7.4.0

node -v
v15.6.0
```

#### Development Mode

This will start the Play dev server. It will also run `npm install` in the UI directory to make sure all the node modules are there

```bash
sbt clean compile run
```

You can now visit `http://localhost:4200/` and you should see a login page.

#### Production Mode

There are 2 options here:

##### Docker
`sbt clean stage docker:publishLocal` will build you a docker image that you can run via `docker run -it -p 9000:9000 image_id`.
 This exposes the application via `http://localhost:9000/`. Or whatever you make the port mapping. If you do that I assume you know what you're doing.

##### Staging
`sbt clean stage` will build you a script located in `./target/universal/stage/bin/platform-template` (.bat for Windows) that you can just execute which will also expose the application on `http://localhost:9000/`

### Using the application

The default username and password to log in is:

`username: johndoe@gmail.com` \
`password: password`



### Just some notes
* This was tested on a Macbook Pro 16 (Intel) and Macbook air (M1) but linux should be fine
* This should work on Windows although it was not tested on Windows so beware there might be some weirdness.
* This was mostly done on JDK 8 although JDK 11 should be okay. There were linking issues on the M1 chip with OpenJdk@11 although AdoptOpendJdk 11 was fine :man_shrugging:


