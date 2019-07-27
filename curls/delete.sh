#!/bin/bash
curl -i -X DELETE -H "Content-Type:application/json" -d "'{$1}'" "localhost:8080/$2"
