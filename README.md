# Example JAX-RS Service with Maven

## repo

This repo uses sub modules for docker images. checkout with sumbodules:

```bash
git clone --recursive git@github.com:wunderlins/jax-rs-3.1-template.git
```

## Commands (build, run, etc.)

- run: `mvn exec:java`
- build jar: `mvn package`
- push jar to package registry: `mvn deploy` (cave, fails if version is already present in some registies)

## Building Blocks

- `Main.java`: the application launcher, will start a Jersey Server
- `HTML.java`: JAX-RS 3.1 filter, see `jakarta.ws.rs.core.Feature` how to automagically register features
- maven package registry see: [PACKAGE_REGISTRY.md](PACKAGE_REGISTRY.md)

## References

### JAX-RS 3.1 Tutorial

JAX-RS 3.1 Documentation: https://gitlab.com/mkarg/jaxrs-done-right/-/tree/javase-tutorial/
Example Code: https://gitlab.com/mkarg/jaxrs-done-right/-/blob/jaxrs-3.1/

1. https://www.youtube.com/watch?v=J-S0aXfLc5U
2. https://www.youtube.com/watch?v=EK4HGMmYQOM
3. https://www.youtube.com/watch?v=Y6Bh3HQn7S0
4. https://www.youtube.com/watch?v=5wYnx6ObTss
5. https://www.youtube.com/watch?v=BoNH1EVnup8
6. https://www.youtube.com/watch?v=jwpb7ne5NJE
7. https://www.youtube.com/watch?v=FOy6sH8WVg8

# OpenAPI Generator



# Keycloak integration

Official keycloak docker image:

```cmd
docker run -p 8080:8080 ^
    -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin ^
    quay.io/keycloak/keycloak:20.0.2 start-dev
```

https://www.keycloak.org/getting-started/getting-started-docker



