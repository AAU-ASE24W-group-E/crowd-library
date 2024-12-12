# ADR-002: Kubernetes

## Status
[//]: # ( Proposed, Accepted, Deprecated, Superseded, etc.)
Accepted

## Context
[//]: # ( What is the issue that we're seeing that is motivating this decision or change?)

As per ADR-001, we have to implement the infrastructure for microservices architecture.

## Decision
[//]: # (What is the change that we're proposing and/or doing?)

We have decided to use kubernetes (k8s) because of the following reasons:
* all team members have some knowledge of kubernetes from the Distributed Computing Infrastructures course
* k8s is de facto industry standard for microservices architecture

## Consequences
[//]: # (What becomes easier or more difficult to do because of this change?)

* We have additional restriction on the technology stack, i.e. support kubernetes
* We have to implement the kubernetes resources descriptors for each microservice and supplementary services