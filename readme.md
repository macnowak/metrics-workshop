# Metrics + ELK workshop :

Sample application build to present how to gather basic metrics 

* Spring boot ( JPA, Web, Actuator )
* H2
* Hikari
* DropWizard metrics 
* Prometheus

## Running app

* ./gradlew bootRun


##  Running docker:

* docker-compose up
* ES - http://localhost:9200/
* KIBANA - http://localhost:5601
* GRAFANA - http://localhost:3000
* PROMETHEUS - http://localhost:9090


#### Tips & Tricks 

 Reload prometheus config:
 * edit prometheus/prometheus.yaml
 * curl -X POST http://localhost:9090/-/reload