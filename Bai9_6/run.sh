#!/usr/bin/env bash
# run.sh – Bai9_6: Local build and cache analysis
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")"
ACTION="${1:-verify}"
case "$ACTION" in
  verify)
    echo ">>> Running mvn verify..."
    START=$(date +%s)
    mvn verify -B
    END=$(date +%s)
    echo ">>> Build completed in $((END - START)) seconds"
    ;;
  test)   mvn clean test -B ;;
  clean)  mvn clean ;;
  cache-size)
    echo ">>> Local ~/.m2/repository size:"
    du -sh ~/.m2/repository 2>/dev/null || echo "Not found"
    JAR_COUNT=$(find ~/.m2/repository -name "*.jar" 2>/dev/null | wc -l)
    echo ">>> JAR count: $JAR_COUNT"
    ;;
  *) echo "Usage: ./run.sh [verify|test|clean|cache-size]"; exit 1 ;;
esac
