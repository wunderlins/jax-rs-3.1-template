# Maven Package Registry

## Use gitlab.com as maven package registry

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
6. set the nummeric id of your project in the url (replace `35971698`)
7. prevent clutter in registry: Settings → Package and Registries → Manage Storage ...: Number of duplicate assets to keep: 1

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


## Use github.com as maven package registry

1. generate [personal access token][2]: Profile → Settings → Developer Settings → Personal access tokens → Tokens (Classic) → Generate new Token (classic)
   1. enter note (`github-wunderlins` in my case)
   2. Permissions
      1. repo
      2. admin:repo_hook
      3. write:packages
      4. delete:packages
      5. delete_repo (documentation suggest to add this?
   3. generate token
2. configure maven, set authentication in your `~/.m2/settings.xml` according to the example below
3. Set an environment Variable `GITHUB_WUNDERLINS_WRITE` with the token password (you may need to restart your IDE/shell so that the new env variable gets available)
4. check if the environment variable is available: 
   1. `$ echo $GITHUB_WUNDERLINS_WRITE` # or
   2. `c:\> echo %GITHUB_WUNDERLINS_WRITE%`
5. configure your project, add deploy, repo channels as in `pom.xml`

#### .m2/settings.xml

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
	<servers>
		<server>
			<id>github-wunderlins</id>
			<!-- github user name -->            
			<username>wunderlins</username>
			<password>${env.GITHUB_WUNDERLINS_WRITE}</password>
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
        <github.project.url>https://maven.pkg.github.com/wunderlins/maven-public</github.project.url>
    </properties>

    <repositories>
        <repository>
            <!-- same as in settings.xml:servers/server/id -->
            <id>github-wunderlins</id>
            <url>${github.project.url}</url>
        </repository>
    </repositories>
    <distributionManagement>
        <repository>
            <id>github-wunderlins</id>
            <url>${github.project.url}</url>
        </repository>
        <snapshotRepository>
            <id>github-wunderlins</id>
            <url>${github.project.url}</url>
        </snapshotRepository>
    </distributionManagement>
</project>
```

### use gitea.com as maven package registry

#### settings.xml

```xml
<settings>
  <servers>
    <server>
      <id>gitea</id>
      <configuration>
        <httpHeaders>
          <property>
            <name>Authorization</name>
            <value>token ${env.GITEA_WUS_WRITE}</value>
          </property>
        </httpHeaders>
      </configuration>
    </server>
  </servers>
</settings>
```

#### pom.xml

```xml
<repositories>
  <repository>
    <id>gitea</id>
    <url>http://workshop2.intra.wunderlin.net:3001/api/packages/wus/maven</url>
  </repository>
</repositories>
<distributionManagement>
  <repository>
    <id>gitea</id>
    <url>http://workshop2.intra.wunderlin.net:3001/api/packages/wus/maven</url>
  </repository>
  <snapshotRepository>
    <id>gitea</id>
    <url>http://workshop2.intra.wunderlin.net:3001/api/packages/wus/maven</url>
  </snapshotRepository>
</distributionManagement>
```


## Upload packages

```bash
mvn deploy
```

## Browse package repository

Both repos offer you an insight for the available packages and how to 
configure them in your `pom.xml`.

### Gitlab.com

1. Package registry
2. Select Package

### Github.com

1. Tab: Code
2. In the right Pane: Packages
3. Click on packgage

## Complete configuration

### pom.xml

Place holders:

- `PROJECT_ID`: gitlab project id: Settings → General → Project ID
- `USER`: github.com user or company name
- `REPO`: name of the git repository: Settings → General → Repository name

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>net.wunderlin.example</groupId>
    <artifactId>jaxrs</artifactId>
    <version>0.0.3</version>

    <properties>
        <mainClass>net.wunderlin.example.jaxrs.Main</mainClass>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.release>17</maven.compiler.release>
        <jaxrs.version>3.1.0</jaxrs.version>
        <jersey.version>3.1.0</jersey.version>
        <gitlab.project.url>https://gitlab.com/api/v4/projects/PROJECT_ID/packages/maven</gitlab.project.url>
        <github.project.url>https://maven.pkg.github.com/USER/REPO</github.project.url>
    </properties>

    <repositories>
        <repository>
            <id>wunderlinnet-repository</id>
            <url>${gitlab.project.url}</url>
        </repository>

        <repository>
            <id>github-wunderlins</id>
            <url>${github.project.url}</url>
        </repository>
    </repositories>

    <distributionManagement>
        <!--repository>
            <id>wunderlinnet-repository</id>
            <url>${gitlab.project.url}</url>
        </repository>
        <snapshotRepository>
            <id>wunderlinnet-repository</id>
            <url>${gitlab.project.url}</url>
        </snapshotRepository-->

        <repository>
            <id>github-wunderlins</id>
            <url>${github.project.url}</url>
        </repository>
        <snapshotRepository>
            <id>github-wunderlins</id>
            <url>${github.project.url}</url>
        </snapshotRepository>
    </distributionManagement>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.glassfish.jersey</groupId>
                <artifactId>jersey-bom</artifactId>
                <version>${jersey.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
```

### settings.xml

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

	<!-- switch between profiles for deployment -->
	<activeProfiles>
		<activeProfile>wunderlinnet-repository</activeProfile>
	</activeProfiles>

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

		<server>
			<id>github-wunderlins</id>
			<username>wunderlins</username>
			<password>${env.GITHUB_WUNDERLINS_WRITE}</password>
		</server>
	</servers>

	<profiles>
		<profile>
			<id>github-wunderlins</id>
			<repositories>
				<repository>
					<id>central</id>
					<url>https://repo1.maven.org/maven2</url>
				</repository>
				<repository>
					<id>github</id>
					<url>https://maven.pkg.github.com/wunderlins/maven-public</url>
					<snapshots>
						<enabled>true</enabled>
					</snapshots>
				</repository>
			</repositories>
		</profile>

		<profile>
			<id>wunderlinnet-repository</id>
			<repositories>
				<repository>
					<id>central</id>
					<url>https://repo1.maven.org/maven2</url>
				</repository>
				<repository>
					<id>gitlab</id>
					<url>https://gitlab.example.com/api/v4/projects/35971698/packages/maven</url>
					<snapshots>
						<enabled>true</enabled>
					</snapshots>
				</repository>
			</repositories>
		</profile>
	</profiles>

	<!--mirrors>
		<mirror>
			<id>central-proxy</id>
			<name>GitLab proxy of central repo</name>
			<url>https://gitlab.example.com/api/v4/projects/35971698/packages/maven</url>
			<mirrorOf>central</mirrorOf>
		</mirror>
	</mirrors-->
</settings>
```

[1]: https://docs.gitlab.com/ee/user/packages/maven_repository/#edit-the-settingsxml
[2]: https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token#creating-a-personal-access-token-classic