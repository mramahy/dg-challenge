#!/bin/sh
docker build -t dg-challange . && docker run --name superhero -p 8080:8080 dg-challange