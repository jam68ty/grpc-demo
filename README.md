
# merchant-chat-test

## Requirement

[Merchant Chat](https://docs.google.com/presentation/d/1labdouo64-23J1WpZ9Dmq6X3P2Ffcaaf/edit#slide=id.g112ea06d253_0_81)

## 架構

- lang：java 11 + spring boot
- 架構：[gRPC](https://grpc.io/)  
- db：[Apache Cassandra](https://cassandra.apache.org/_/index.html)
- 排程管理：[Quartz](http://www.quartz-scheduler.org/)
- 品質管理：[Sonar](https://www.sonarqube.org/)
- CodeStyle：[Google code style](https://google.github.io/styleguide/javaguide.html)

## 需要的東西

- gRPC 基本操作 (雙向)
- Apache Cassandra (起分散式 => docker-compose => local k8s)
- Cassandra在java內的操作 (JPA or client)
- Quartz 基本操作 (cluster, Job Stores)
- schema設計
