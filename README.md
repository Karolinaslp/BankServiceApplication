# Bank Service

Bank Service is REST application build in Java 17 using the Spring Boot, Hibernate, PostgresSQL.

## Table of Contents

* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [API Reference](#api-reference)
* [Setup](#setup)
* [Project Status](#project-status)
* [Room for Improvement](#room-for-improvement)
* [Authors](#authors)

## General Information

Bank Service is REST application written in Java 17 using the Spring Boot framework and Hibernate as an ORM.
The application uses a PostgreSQL database managed by Docker container and Maven as a build tool and dependency manager.
Unit tests have been written using JUnit 5 framework.
Application uses the Exchange Rates Data API to retrieve currency exchange rates.
Additionally, I have implemented a notification system using the MQTT protocol, which automatically informs users of any operations performed on their account.

## Technologies Used

- Java 17
- Spring Boot 3.0.3
- Maven 4.0.0
- Hibernate 6.1.7.Final
- PostgresSQL 42.2.19.jre17
- Lombok 1.18.24
- JUnit5 5.8.2
- Mockito 4.5.1
- Docker 
- Eclipse Mosquitto 5.0

## Features

- Retrieving currency exchange rates using the Exchange Rates Data API.
- Adding clients and account.
- Transactions between different currencies.
- Withdrawing money from account.
- Search for client and account

## API Reference

#### Get Client By Email

```http
  GET /api/user
```

| Parameter    | Type     | Description               |
|:-------------|:---------|:--------------------------|
| `user email` | `string` | **Required**. User email. |

Takes user email and returns client.

#### Create new client

```http
  POST /api/user
```

| Parameter | Type      | Description                |
|:----------|:----------|:---------------------------|
| `name`    | `string ` | **Required**. Client name. |
| `email`   | `string ` | **Required**. Cliwt email. |

Takes name, email and a save client.

#### Create Transfer

```http
  POST /api/transfer
```

| Parameter       | Type      | Description                                |
|:----------------|:----------|:-------------------------------------------|
| `amount`        | `list`    | **Required**. List of items to be ordered. |
| `user login`    | `string ` | **Required**. Login of admin or user.      |
| `user password` | `string ` | **Required**. Password of admin or user.   |

Takes id, login and password of an order, along with the items to be ordered, and creates a new order with its details.

#### Create Transfer

```http
  POST /api/transfer
```

| Parameter         | Type     | Description                                                               |
|:------------------|:---------|:--------------------------------------------------------------------------|
| `amount`          | `number` | **Required**. Amount of money to be transferred .                         |
| `currencys`       | `string` | **Required**. Currency of money to be transferred.                        |
| `account from id` | `long`   | **Required**. id of the account from which the transfer is to be made.    |
| `account to id`   | `long`   | **Required**. id of the account to which the funds are to be transferred. |

Takes transfer request and transfer money from account from to account to.

#### Get Rates

```http
  GET /api/latest
```

| Parameter       | Type     | Description                                              |
|:----------------|:---------|:---------------------------------------------------------|
| `base currency` | `string` | **Required**. Currency for which we want to check rates. |

Takes base currency and returns map of currency names and rates.

#### Exchange

```http
  GET /api/convert
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `amount`      | `number` | **Required**. Amount of money to be transferred. |
| `account from id`      | `long` | **Required**.Id of the account from which the transfer is to be made. |
| `account to id`      | `long ` | **Required**. Id of the account to which the funds are to be transferred. |

Takes amount, account from id, account to id and make transaction with currency conversion.

#### Get Account By Id

```http
  GET /api/account
```

| Parameter | Type      | Description                       |
|:----------|:----------|:----------------------------------|
| `user id` | `long `   | **Optional**. Id of the account.  |

Takes id of account and returns account.

#### Create Account

```http
  POST /api/account
```

| Parameter   | Type     | Description                                        |
|:------------|:---------|:---------------------------------------------------|
| `user id`   | `long`   | **Required**. Id of user.                          |
| `balance`   | `number` | **Required**. Balance of account.                  |
| `currencys` | `string` | **Required**. Currency of money to be transferred. |

Takes account request and creates new account.

#### Withdraw

```http
  PATCH /api/withdraw
```

| Parameter | Type     | Description                                      |
|:----------|:---------|:-------------------------------------------------|
| `id`      | `long`   | **Required**. Id of account to be withdraw from. |
 | `amount`  | `number` | **Required**. Amount of money to be transferred. |

Takes id ot account,amount of money withdraws funds from the account.  

## Project Status

Project is: _in progress_

## Room for Improvement

To do:

- Add login option for user and admin
- Add separation of access to endpoints for the admin and client

## Authors

- [@Karolina Ślemp-Rejowska](https://www.github.com/karolinaslp)
