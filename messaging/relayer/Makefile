.PHONY: build

build:
	go build -o ./build/relayer cmd/relayer/* 
	go build -o ./build/observer cmd/observer/* 
	go build -o ./build/dispatcher cmd/dispatcher/* 

docker:
	docker build -t consensys/gpact/messaging/relayer .

clean:
	rm -rf ./build/*

utest:
	go test  ./...

