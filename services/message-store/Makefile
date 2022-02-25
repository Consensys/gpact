.PHONY: build

build:
	go build -o ./build/api cmd/*

docker:
	docker build -t consensys/gpact/services/message-store .

clean:
	rm -rf ./build/*

utest:
	go test -v --count=1 ./internal/...
