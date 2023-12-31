# TradeApp

### Links
Link to Telegram Bot is provided: https://t.me/trade_ua14_bot

Link to fully usage instruction is also provided: 
Some link(xD)
### Description

An auction service application, similar to eBay, facilitates online buying and selling through a dynamic and competitive bidding process. 
This application creates a virtual marketplace where users can list items for sale, bid on items of interest, and engage in transactions.

### System Requirements
The main functional requirements for system:
- User registration and profiling
- Place/Edit/Delete an item, each of them could be seen by other users on marketplace
- Each user could bid each item one time
- Each user could see last bid on any item 
- Owner of item could finish the bidding acceptance process and start interaction with users, who placed bids
- Before any item would be placed on the marketplace, it should be checked by administration
- After the interaction owner of item should set the status of his item as 'SOLD', 
then administration should proof that transaction was successful.
- Any user could complain in administration and get quick support

### Tech Stack
The tech stack of the project consists of:
- Java Core 17
- Spring Boot 3.2.0
- Spring MVC, Spring Data, Spring Cloud
- Redis
- RabbitMQ
- Docker
- Hibernate
- JPA
- Swagger  
- JUnit
- Mockito 
### Architecture
An application consists of two independent services, which provide interaction between 
client and server and could work separate from each other. Here are TelegramService
and TradeAPP API. First service ensures interaction between client (Telegram Server) 
and our server instance(application), sends responses, and enforces TradeApp API.
TradeApp API provides us REST API and access to all required resources and data.

The advantage of this approach is that we can provide any other client - server interaction,
in fact that we have the same RESTful service for each on-front server-instance. 
For instance, we could extend our service and create Mobile Application or a web-site.
We could easily implement it, and our REST service is useful in all cases.

### API Endpoints
Our service including two layers of client-server interaction.
First is the Telegram Server. Telegram Server get request from the user, and than
our server-instance handle it. Other server-instance have is a REST API, which provide
access to all required data. Here are provided all require API endpoints:

### API Documentation
API documentation, generated by Swagger

### Use-Cases
Here are provided the full expected usage of the service,
and also provided testing of API endpoints in PostMan
