#!/usr/bin/env bash
# run.sh – Bai9_5: JaCoCo coverage enforcement
# Usage:
#   ./run.sh           → mvn verify (test + jacoco report + 80% check)
#   ./run.sh test      → mvn test only
#   ./run.sh report    → open JaCoCo report (requires verify first)
#   ./run.sh clean     → mvn clean
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")"
ACTION="${1:-verify}"
case "$ACTION" in
  verify)  mvn verify -B ;;
  test)    mvn clean test -B ;;
  report)
    echo "Opening: target/site/jacoco/index.html"
    if command -v xdg-open &>/dev/null; then xdg-open target/site/jacoco/index.html
    elif command -v open &>/dev/null; then open target/site/jacoco/index.html
    else echo "Manual: open target/site/jacoco/index.html in a browser"; fi ;;
  clean)   mvn clean ;;
  *) echo "Usage: ./run.sh [verify|test|report|clean]"; exit 1 ;;
esac
