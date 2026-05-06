#!/usr/bin/env bash
# =============================================================================
# run.sh – BankSystem build, style-check, test and run
# Usage:
#   ./run.sh              – checkstyle + compile + test + run
#   ./run.sh checkstyle   – run checkstyle:check only
#   ./run.sh test         – compile + test only
#   ./run.sh clean        – clean target/
# =============================================================================
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

ACTION="${1:-run}"

case "$ACTION" in
  checkstyle)
    echo ">>> Running Checkstyle..."
    mvn checkstyle:check
    ;;
  test)
    echo ">>> Compile + Test..."
    mvn clean test
    ;;
  clean)
    mvn clean
    ;;
  run)
    echo ">>> Checkstyle + Compile + Test..."
    mvn clean test -q
    echo ">>> Running BankSystemApp..."
    mvn exec:java -Dexec.mainClass="com.example.bank.BankSystemApp" -q
    ;;
  *)
    echo "Usage: ./run.sh [checkstyle|test|clean|run]"
    exit 1
    ;;
esac
