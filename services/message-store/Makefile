.PHONY: build

build:
	go build -o ./build/api cmd/*

clean:
	rm -rf ./build/*

utest:
	go test -v --count=1 ./internal/...
