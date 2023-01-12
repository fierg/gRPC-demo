# gRPC demo for Software Architectures 4 Enterprises

---

### Build and Use

Build jar with `mvn clean install` in the project path.
The runnable jar will be built under `target/gRPC-demo-1.0-SNAPSHOT.jar`.
A collection oft test cases is also available.

### Implementation description

The application is written in Kotlin for ease of use and conciseness. 
The gRPC features are split into client and server, the client will be calling the rpc method `solve()`which is described in the `service.proto` file and implemented by the server.

Further there exists a model data class for internal representation as well as JSON parsing and JSOn generation.

Solving is done by a Gurobi model, which encodes all missing variables and sets the contraints for te results of the sums. The the model is minimized by the solver and the variables are then backtracked to receive the result.