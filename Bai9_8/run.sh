#!/usr/bin/env bash
# run.sh – Bai9_8: Package and run the executable JAR
set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")"
ACTION="${1:-run}"
case "$ACTION" in
  package)
    echo ">>> mvn clean package..."
    mvn clean package -B
    echo ">>> Done. JAR: target/BankSystem-Executable.jar"
    ls -lh target/BankSystem-Executable.jar
    ;;
  run)
    if [ ! -f target/BankSystem-Executable.jar ]; then
      echo ">>> JAR not found. Running mvn clean package first..."
      mvn clean package -B -q
    fi
    echo ">>> Running: java -jar target/BankSystem-Executable.jar"
    java -jar target/BankSystem-Executable.jar
    ;;
  manifest)
    echo ">>> MANIFEST.MF contents:"
    unzip -p target/BankSystem-Executable.jar META-INF/MANIFEST.MF
    ;;
  clean) mvn clean ;;
  *) echo "Usage: ./run.sh [package|run|manifest|clean]"; exit 1 ;;
esac
