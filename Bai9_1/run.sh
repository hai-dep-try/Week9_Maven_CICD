#!/usr/bin/env bash
# =============================================================================
# run.sh – Build, test, and run MathUtils
# Usage:
#   ./run.sh          – compile + test + run main class
#   ./run.sh test     – compile + test only
#   ./run.sh clean    – clean build artefacts
#   ./run.sh package  – compile + test + create JAR in target/
# =============================================================================

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

ACTION="${1:-run}"

case "$ACTION" in
  clean)
    echo ">>> Cleaning build artefacts..."
    mvn clean
    ;;
  test)
    echo ">>> Compiling and running tests..."
    mvn clean test
    ;;
  package)
    echo ">>> Packaging JAR..."
    mvn clean package
    echo ">>> JAR created at: target/MathUtils-1.0-SNAPSHOT.jar"
    ;;
  run)
    echo ">>> Compiling..."
    mvn clean compile -q

    echo ">>> Running tests..."
    mvn test -q

    echo ">>> Running MathUtils main class..."
    mvn exec:java -Dexec.mainClass="com.example.MathUtils" -q
    ;;
  *)
    echo "Unknown action: $ACTION"
    echo "Usage: ./run.sh [clean|test|package|run]"
    exit 1
    ;;
esac
