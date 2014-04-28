#!/bin/bash

function bootstrap() {
  DATABASE=$1
  PGPASSWORD=postgres psql -U postgres -h localhost -d ${DATABASE} < db/baseline.sql
}

bootstrap "trailblazers"
