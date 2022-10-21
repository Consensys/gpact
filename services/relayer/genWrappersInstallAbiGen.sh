#!/usr/bin/env bash
#
# This script installs abigen.
#
# Follow the steps here to install solc and abigen:
#  https://goethereumbook.org/smart-contract-compile/
#
set -e
#rm -rf build

go get -u github.com/ethereum/go-ethereum

cd ~/go/pkg/mod/github.com/ethereum/go-ethereum@v1.10.25

sudo make
# make devtools builds abigen and other tools
# The resulting binary is put in ~/go/bin/abigen
sudo make devtools
