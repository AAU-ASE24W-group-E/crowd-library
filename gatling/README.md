Load Tests
=========

This is a simple load test for the main use case. It uses [Gatling](https://gatling.io/) to simulate the load.

Run test locally:
1. start the application in minikube
2. tunnel the service to localhost
3. run the test
    ```shell script
    ./mvnw gatling:test
    ```

