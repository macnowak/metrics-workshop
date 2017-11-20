## Docker configuration :

* MySql :
docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=admin -e MYSQL_DATABASE=audit -e MYSQL_USER=audit -e MYSQL_PASSWORD=audit -d mysql:latest

## Running :

* docker-compose up
* ES - http://localhost:9200/
* KIBANA - http://localhost:5601
* GRAPHITE - http://localhost:80
* PROMETHEUS - http://localhost:9090

1. edytujemy prometheus/prometheus.yaml
2. curl -X POST http://localhost:9090/-/reload