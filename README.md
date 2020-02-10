# FakeNetwork

A fake Socket/DatagramSocket implementation for creating a simple, virtual network inside your JVM.

This library is written in Kotlin and it's only dependency is Kotlin-stdlib.

Licensed under the [MIT License](https://github.com/arudiscord/fakenetwork/blob/master/LICENSE).

### Installation

![Latest Version](https://api.bintray.com/packages/arudiscord/maven/fakenetwork/images/download.svg)

Using in Gradle:

```gradle
repositories {
  jcenter()
}

dependencies {
  compile 'pw.aru.libs:fakenetwork:LATEST' // replace LATEST with the version above
}
```

Using in Maven:

```xml
<repositories>
  <repository>
    <id>central</id>
    <name>bintray</name>
    <url>http://jcenter.bintray.com</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>pw.aru.libs</groupId>
    <artifactId>fakenetwork</artifactId>
    <version>LATEST</version> <!-- replace LATEST with the version above -->
  </dependency>
</dependencies>
```

### Usage

Create a `FakeNetwork` instance, which manages all your fake network infrastructure.

To register a new listener device, implement the `FakeNetwork` interface and call `FakeNetwork#connectDevice(FakeNetworkDevice)` to get a `FakeNetworkConnection` associated with it, which you can use to interact with other devices.

### Example Implementations:

See [src/test/kotlin/pw/aru/libs/fakenetwork](https://github.com/arudiscord/fakenetwork/tree/master/src/test/kotlin/pw/aru/libs/fakenetwork) for example implementations.

### Support

Support is given on [Aru's Discord Server](https://discord.gg/URPghxg)

[![Aru's Discord Server](https://discordapp.com/api/guilds/403934661627215882/embed.png?style=banner2)](https://discord.gg/URPghxg)