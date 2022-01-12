terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.5.0"
    }
  }
  required_version = ">= 0.13"
}

provider "aws" {
  region = "ap-southeast-2"
}

resource "aws_instance" "relayer" {
  ami           = "ami-0567f647e75c7bc05"
  instance_type = "t2.small"
  tags = {
    Name = "relayer"
  }
  security_groups = ["security_relayer"]
  user_data       = <<-EOF
    #!/bin/sh
    cd /home/ubuntu/
    sudo apt-get update
    sudo apt install -y unzip git make build-essential ca-certificates curl gnupg lsb-release
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
    echo \
        "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
        $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    sudo apt-get update
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io
    sudo apt-get install -y docker-ce=5:20.10.12~3-0~ubuntu-focal docker-ce-cli=5:20.10.12~3-0~ubuntu-focal containerd.io
    wget https://github.com/grafana/loki/releases/download/v2.3.0/promtail-linux-amd64.zip
    wget https://golang.org/dl/go1.17.1.linux-amd64.tar.gz
    wget https://raw.githubusercontent.com/ConsenSys/gpact/cros-15-message-signer/messaging/relayer/deploy/promtail-cloud-config.yaml
    unzip ./promtail-linux-amd64.zip
    sudo tar -C /usr/local -xzf go1.17.1.linux-amd64.tar.gz
    mkdir /home/ubuntu/go
    export HOME=/home/ubuntu
    export GOPATH=/home/ubuntu/go
    export PATH=$PATH:/usr/local/go/bin:$GOPATH/bin
    git clone https://github.com/ConsenSys/gpact.git
    cd gpact/messaging/relayer
    make build
    export IP="${aws_instance.relayer-monitor.public_ip}"
    echo "      host: node1" >> ../../../promtail-cloud-config.yaml
    echo "clients:" >> ../../../promtail-cloud-config.yaml
    echo "  - url: http://$IP:3100/loki/api/v1/push" >> ../../../promtail-cloud-config.yaml
    docker run -d -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
    sleep 30
    cd ./build
    LOG_SERVICE_NAME=observer LOG_LEVEL=debug LOG_TARGET=STDOUT LOG_DIR=.observer/log LOG_FILE=observer.log LOG_MAX_BACKUPS=3 LOG_MAX_AGE=28 LOG_MAX_SIZE=500 LOG_COMPRESS=true LOG_TIME_FORMAT=RFC3339 OUTBOUND_MQ_ADDR=amqp://guest:guest@localhost:5672/ OUTBOUND_CH_NAME=channel1 API_PORT=9425 OBSERVER_DS_PATH=.relayer-observer/ nohup ./observer >> /home/ubuntu/all.log 2>&1 &
    LOG_SERVICE_NAME=relayer LOG_LEVEL=debug LOG_TARGET=STDOUT LOG_DIR=.relayer/log LOG_FILE=relayer.log LOG_MAX_BACKUPS=3 LOG_MAX_AGE=28 LOG_MAX_SIZE=500 LOG_COMPRESS=true LOG_TIME_FORMAT=RFC3339 INBOUND_MQ_ADDR=amqp://guest:guest@localhost:5672/ INBOUND_CH_NAME=channel1 OUTBOUND_MQ_ADDR=amqp://guest:guest@localhost:5672/ OUTBOUND_CH_NAME=channel2 API_PORT=9426 SIGNER_DS_PATH=.relayer-signer/ nohup ./relayer >> /home/ubuntu/all.log 2>&1 &
    LOG_SERVICE_NAME=dispatcher LOG_LEVEL=debug LOG_TARGET=STDOUT LOG_DIR=.dispatcher/log LOG_FILE=dispatcher.log LOG_MAX_BACKUPS=3 LOG_MAX_AGE=28 LOG_MAX_SIZE=500 LOG_COMPRESS=true LOG_TIME_FORMAT=RFC3339 INBOUND_MQ_ADDR=amqp://guest:guest@localhost:5672/ INBOUND_CH_NAME=channel2 API_PORT=9427 TRANSACTOR_DS_PATH=.relayer-transactor/ VERIFIER_DS_PATH=.relayer-verifier/ nohup ./dispatcher >> /home/ubuntu/all.log 2>&1 &
    cd ../../../../
    sleep 5
    nohup ./promtail-linux-amd64 -config.file=./promtail-cloud-config.yaml >> /home/ubuntu/promtail.log 2>&1 &
  EOF
}

resource "aws_instance" "relayer-monitor" {
  ami           = "ami-0567f647e75c7bc05"
  instance_type = "t2.small"
  tags = {
    Name = "relayer-monitor"
  }
  security_groups = ["security_relayer_monitor"]
  user_data       = <<-EOF
    #!/bin/sh
    cd /home/ubuntu/
    sudo apt-get update
    sudo apt install -y unzip
    wget https://github.com/grafana/loki/releases/download/v2.3.0/loki-linux-amd64.zip
    wget https://dl.grafana.com/oss/release/grafana-8.1.5.linux-amd64.tar.gz
    wget https://raw.githubusercontent.com/grafana/loki/v2.3.0/cmd/loki/loki-local-config.yaml
    wget https://raw.githubusercontent.com/ConsenSys/ipfs-lookup-measurement/main/monitor/grafana-datasources.yml
    wget https://raw.githubusercontent.com/ConsenSys/ipfs-lookup-measurement/main/monitor/grafana-dashboards.yml
    wget https://raw.githubusercontent.com/ConsenSys/ipfs-lookup-measurement/main/monitor/ipfs-dashboard.json
    unzip loki-linux-amd64.zip
    tar -zxvf grafana-8.1.5.linux-amd64.tar.gz
    mv grafana-datasources.yml ./grafana-8.1.5/conf/provisioning/datasources/datasources.yml
    mv grafana-dashboards.yml ./grafana-8.1.5/conf/provisioning/dashboards/dashboards.yml
    sudo mkdir --parents /var/lib/grafana/dashboards
    mv ipfs-dashboard.json /var/lib/grafana/dashboards/
    nohup ./loki-linux-amd64 -config.file=loki-local-config.yaml &
    cd ./grafana-8.1.5/bin
    nohup ./grafana-server &
  EOF
}

resource "aws_security_group" "security_relayer_monitor" {
  name        = "security_relayer_monitor"
  description = "security group for relayer monitor"

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 3000
    to_port     = 3000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 3100
    to_port     = 3100
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "security_relayer_monitor"
  }
}

resource "aws_security_group" "security_relayer" {
  name        = "security_relayer"
  description = "security group for relayer"

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 9425
    to_port     = 9427
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "security_relayer"
  }
}