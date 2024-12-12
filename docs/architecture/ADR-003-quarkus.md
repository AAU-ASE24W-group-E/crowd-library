# ADR-003: Quarkus

## Status
[//]: # ( Proposed, Accepted, Deprecated, Superseded, etc.)
Accepted

## Context
[//]: # ( What is the issue that we're seeing that is motivating this decision or change?)

As per ADR-001, we have to decide on the technology stack for microservices architecture.
We have to pick the framework for backend services.
We have to choose between Spring Boot introduces in Advanced Software Engineering course or alternative frameworks.

## Decision
[//]: # (What is the change that we're proposing and/or doing?)

We have decided to use Quarkus framework because of the following reasons:
* Quarkus supports standard Java/Jakarta EE and Microprofile APIs, minimizing the learning curve and vendor lock-in 
* at least one team member has good knowledge of Quarkus
* Quarkus supports k8s and containerization out of the box

## Consequences
[//]: # (What becomes easier or more difficult to do because of this change?)

* We have set up know-how transfer within the team
* We will implement the backend microservices in Quarkus