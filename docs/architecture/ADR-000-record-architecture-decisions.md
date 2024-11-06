# ADR-000: Record Architecture Decisions

## Status
[//]: # ( Proposed, Accepted, Deprecated, Superseded, etc.)
Proposed (2024-11-06)

## Context
[//]: # ( What is the issue that we're seeing that is motivating this decision or change?)

We need to record the architectural decisions made on this project.

## Decision
[//]: # (What is the change that we're proposing and/or doing?)

We will use Architecture Decision Records, as described by Michael Nygard in this [article](http://thinkrelevance.com/blog/2011/11/15/documenting-architecture-decisions)

* We will keep ADRs in the project repository under docs/architecture/ADR-NNN-title.md
* We will use Markdown format based on this ADR as template
* ADRs will be numbered sequentially and monotonically. Numbers will not be reused.
* If a decision is reversed we will keep the original ADR, but mark it as superseded by a new ADR.
* ADR will consist of the following sections:
  * **Title** - short present tense imperative phrase describing the decision
  * **Status** - Proposed, Accepted, Deprecated, Superseded, etc. Each status transition is dated. New status will not replace the old one, but will be added to the list.
  * **Context** - informative description of what is the issue that we're seeing that is motivating this decision or change?
  * Decision - describes the change that we're proposing and/or doing.
  * **Consequences** - describes what becomes easier or more difficult to do because of this change.

## Consequences
[//]: # (What becomes easier or more difficult to do because of this change?)

> One ADR describes one significant decision for a specific project. It should be something that has an effect on how the rest of the project will run.

> The consequences of one ADR are very likely to become the context for subsequent ADRs. [...]

> Developers and project stakeholders can see the ADRs, even as the team composition changes over time.

> The motivation behind previous decisions is visible for everyone, present and future. Nobody is left scratching their heads to understand, "What were they thinking?" and the time to change old decisions will be clear from changes in the project's context.

see also this [article](http://thinkrelevance.com/blog/2011/11/15/documenting-architecture-decisions) by Michael Nygard. 
