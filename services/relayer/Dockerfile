# Copyright (C) 2021 ConsenSys Software Inc
FROM golang:1.17-alpine as builder

FROM golang:1.17-alpine

RUN apk update && apk add --no-cache make gcc musl-dev linux-headers git
WORKDIR /app/
COPY . .
RUN make build