#!/bin/bash

function createdb() {
  DATABASE=$1
  PGPASSWORD=demopostgres psql -U demopostgres -d template1 -h localhost -c "drop database ${DATABASE}"
  PGPASSWORD=demopostgres psql -U demopostgres -d template1 -h localhost -c "create database ${DATABASE}"
}
createuser -s demopostgres
createdb demopostgres
createdb "demotrailblazers"