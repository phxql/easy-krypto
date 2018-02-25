# Developer guide

## Building

Run `./mvnw clean package` and check the `target` folder.

## Publishing

1. Run `./mvnw release:clean release:prepare`
1. Check that the wonderful release plugin didn't do anything stupid
1. Push changes to upstream: `git push && git push --tags`
1. Run `./mvnw release:perform`
