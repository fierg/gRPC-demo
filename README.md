# gRPC demo for Software Architectures 4 Enterprises

---

### Build and Use

Build jar with `mvn clean install` in the project path.
The runnable jar will be built under `target/gRPC-demo-1.0-SNAPSHOT.jar`.
A collection oft test cases is also available.

### Implementation description

The application is written in Kotlin for ease of use and conciseness. 
The gRPC features are split into client and server, the client will be calling the rpc method `solve()`which is described in the `service.proto` file and implemented by the server.

Solving is done by brute force.

### Dependency Injection for the Logger

Logging is done via the `LogConsumer` interface and the instances are handled by Kodein.
A simple HTTP Get request to `localhost:8080/log/{type}`changes the Logger impl during runtime. REST Handling is done by KTor.

Currently `console`, `file` and `mqtt` are available as Log Consumers. 