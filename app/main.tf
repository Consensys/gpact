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

resource "aws_instance" "dapp" {
  ami           = "ami-0567f647e75c7bc05"
  instance_type = "t2.small"
  tags = {
    Name = "dapp"
  }
  security_groups = ["security_dapp"]
  user_data       = <<-EOF
    #!/bin/sh
    cd /home/ubuntu/
    sudo apt-get update
    sudo apt install -y unzip git make build-essential ca-certificates curl gnupg lsb-release nodejs npm
    export HOME=/home/ubuntu
    git clone https://github.com/ConsenSys/gpact.git
    cd ./gpact
    git checkout cros-83-demo-2
    cd ./app2/buyer
    npm install
    nohup npm run serve &
    cd ./app2/seller
    npm install
    nohup npm run serve &
  EOF
}

resource "aws_security_group" "security_dapp" {
  name        = "security_dapp"
  description = "security group for dapp"

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 9011
    to_port     = 9011
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "security_dapp"
  }
}