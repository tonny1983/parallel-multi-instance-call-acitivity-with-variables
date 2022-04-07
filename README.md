# collecting variables from subprocesses created by parallel call activity

---

## Background

The project demonstrates how to collect variables from subprocesses created by parallel call activity 
using two different ways.

1. End Execution Listener : the listener will be called as the same times as the number of multi instance, 
therefore it can read variables from subprocesses via `variables out mapping` of call activity;
2. Service/Script task : a `Asynchronous Before` service or script task can query all subprocesses, 
the `super process` of which is just the parent process, and retrieve variables from `HistoryService`.

## Environment

The demonstration has been tested with `Camunda BPM 7.15` with OpenJDK 11.0.14.1 (Zulu 11.54+25).

## Limitation

No warranty is given for use in a production environment.

## Licenses

As the same Licenses as Camunda.