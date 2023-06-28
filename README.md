# Getting Started

### Reference Documentation

Example application demoing purchase process and points calculation. Only for demo/discussion purposes. Still under
progress
and missing parts were mentioned in todo section.

Available operations can be obtained via swagger documentation:
http://localhost:8080/swagger-ui/#/Purchase

### ToDo

- add idempotency support for some of the request e.g. save purchase in case of 'double click'
- add 'version' field and @Version in order to support OptimisticLocking in concurrency issues (same as with
  hibernate/jpa)
- add support for transactions via spring boot replica configuration for mongo
- add testcontainers in order to test against close to 'real' db especially in case of need for transaction support
- add bdd scenario
- add jmeter tests
- add sonar
- add junit coverage
- add support for spring boot swagger
- add docker image with replica set enabled in order to support transactions
- deploy as docker image to k8s
- create separate project with event driven project and compare pros/cons
- create separate project where you prefer data security and transactional guarantees to be enforced and use postgresql

# rewardsprogram-java
