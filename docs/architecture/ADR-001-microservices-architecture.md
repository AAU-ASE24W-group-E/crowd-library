# ADR-001: Microservices Architecture

## Status
[//]: # ( Proposed, Accepted, Deprecated, Superseded, etc.)
Accepted

## Context
[//]: # ( What is the issue that we're seeing that is motivating this decision or change?)

We need to decide on the architecture of the system.
We have to choose between monolithic and microservices architecture.

## Decision
[//]: # (What is the change that we're proposing and/or doing?)

We have decided to use microservices architecture from the beginning of the project because of the following reasons:
* we know that the target system have to be scalable
* we know that the target system final architecture is desired to be microservices by stakeholders
* we can start with microservices architecture from the beginning because we have successfully identified bounded contexts of the domain model and can start with separate services for each bounded context

## Consequences
[//]: # (What becomes easier or more difficult to do because of this change?)

* We have to implement the infrastructure for microservices architecture
* We have to pick the right technology stack for microservices architecture