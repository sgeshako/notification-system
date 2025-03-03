# Spring Boot Notification-system App

A Notification-system for sending messages through different channels (Email, Sms, Slack, etc.).

Built using Spring Boot 3 and Java 21.

## Dependencies
- PostgreSQL DB for storing message data
- RabbitMQ message queues for asynchronous processing of messages

## Basic functionality
- There is one API for each channel (email/sms/slack)  
  - A call to an API immediately returns with a generated message ID
  - Data for the message is stored in the DB
- A message queue is used to asynchronously process and send the message for the given channel
- A status of the message is persisted in the DB for each stage: Arrival -> In progress -> Send successful / Send failure
- The message ID can be used to track the current status of the message

## APIs
- POST /email
  - Request: { "recipient": "some\.email\@\domain\.com", "subject": "A subject", "body": "\<h1>Possible html body\</h1>" }
  - Response: { "messageId": "..." } 200 OK
- POST /sms
  - Request: { "recipient": "+123456789012", "body": "Text message" }
  - Response: { "messageId": "..." } 200 OK
- POST /slack
  - Request: { "channel": "C1234567890", "message": "Hello Slack" }
  - Response: { "messageId": "..." } 200 OK

## How to deploy on a Docker cluster using Swarm mode
1) Clone repo and run docker build at the project level to build the image locally:
```sh
git clone https://github.com/sgeshako/notification-system.git
cd notification-system

docker build -t notification-system .
```

2) Set the environment variables used by the containers in `docker-compose.prod.yml`.
Example using an .env file:
```
# .env
POSTGRES_USER=mypostgresuser
POSTGRES_PASSWORD=...
...
```
3) Create the above .env file, initialize swarm mode to create a cluster and use `docker stack deploy` to create a stack on the current swarm node using the compose file:
```sh
docker swarm init

eval $(< .env) docker stack deploy --compose-file docker-compose.prod.yml notifstack
```
4) Scale more App instances running on the current node or possible additional nodes:
```sh
docker service scale notifstack_notification-system-app=2

docker service ls # verify running instances
```
