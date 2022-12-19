# Example JAX-RS Service with Maven

## Commands

- run: `mvn exec:java`
- build jar: `mvn package`

## Building Blocks

- `Main.java`: the application launcher, will start a Jersey Server
- `HTML.java`: JAX-RS 3.1 filter, see `jakarta.ws.rs.core.Feature` how to automagically register features

## References

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

## Use gitlab.com as maven repository

**CAVE**: 
- You **must** use `Private-Token`/`Deploy-Token` or `Job-Token` as `<name>` in `.m2/settings.xml` or you will always get a `401 Unauthorized`, see [Documentation][1].
- Use numeric project id in url

### Configuration

1. Project Token: Settings → Repository → Deploy Tokens: generate a new token with scope: `write_package_registry`
2. configure maven, set authentication in your `~/.m2/settings.xml` according to the example below
3. Set an environment Variable `GITLAB_WUNDERLINNET_WRITE` with the token password (you may need to restart your IDE/shell so that the new env variable gets available)
4. check if the environment variable is available: 
   1. `$ echo $GITLAB_WUNDERLINNET_WRITE` # or
   2. `c:\> echo %GITLAB_WUNDERLINNET_WRITE%`
5. configure your project, add deploy, repo channels as in `pom.xml`
6. prevent clutter in registry: Settings → Package and Registries → Manage Storage ...: Number of duplicate assets to keep: 1

#### .m2/settings.xml

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
	<servers>
		<server>
			<id>wunderlinnet-repository</id>
			<configuration>
				<httpHeaders>
					<property>
						<!-- Deploy-Token: is a project level token -->
						<name>Deploy-Token</name>
						<value>${env.GITLAB_WUNDERLINNET_WRITE}</value>
					</property>
				</httpHeaders>
			</configuration>
		</server>
	</servers>
</settings>
```
#### pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <properties>
        <gitlab.project.url>
            https://gitlab.com/api/v4/projects/35971698/packages/maven
        </gitlab.project.url>
    </properties>

    <!-- for reading and building in external projects -->
    <repositories>
        <repository>
            <!-- same as in settings.xml:servers/server/id -->
            <id>wunderlinnet-repository</id>
            <url>${gitlab.project.url}</url>
        </repository>
    </repositories>

    <!-- for updateing the repo -->
    <distributionManagement>
        <repository>
            <id>wunderlinnet-repository</id>
            <url>${gitlab.project.url}</url>
        </repository>
        <snapshotRepository>
            <id>wunderlinnet-repository</id>
            <url>${gitlab.project.url}</url>
        </snapshotRepository>
    </distributionManagement>

</project>
```

[1]: https://docs.gitlab.com/ee/user/packages/maven_repository/#edit-the-settingsxml