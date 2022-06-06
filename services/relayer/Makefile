.PHONY: build itest

buildall:
	go test -run=nope ./...

build:
	go build -o ./build/relayer cmd/relayer/* 
	go build -o ./build/observer cmd/observer/* 
	go build -o ./build/dispatcher cmd/dispatcher/* 
	go build -o ./build/admin cmd/admin/* 

docker:
	docker build -t consensys/gpact/services/relayer .

clean:
	rm -rf ./build/*

utest:
	go test -v --count=1 ./internal/...

itest:
	go test -v --count=1 ./itest/
