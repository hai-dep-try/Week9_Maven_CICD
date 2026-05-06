# lib/

This directory is reserved for any JAR dependencies that are managed **outside** Maven (e.g. proprietary drivers or local artefacts that are not available on Maven Central).

## Usage

To install a local JAR into the local Maven repository so that `pom.xml` can reference it:

```bash
mvn install:install-file \
  -Dfile=lib/my-library.jar \
  -DgroupId=com.example \
  -DartifactId=my-library \
  -Dversion=1.0.0 \
  -Dpackaging=jar
```

All standard dependencies (Logback, Hibernate, JUnit Jupiter) are resolved automatically from **Maven Central** via `pom.xml` and do **not** need to be placed here.
