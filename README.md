## Mod is not executable

This project continues [Fabric API 1196](https://github.com/FabricMC/fabric/issues/1196),
which provides a `ModWarn.class` that is shrunk as small as possible (<1KB).

This `ModWarn.class` is highly recommended to be embedded into
[Loom](https://github.com/FabricMC/fabric-loom), with a configuration toggle in `remapJar`
like:
```groovy
    // If true (default behavior), the mod warn class will be included in
    // the jar, along with `Main-Class: ModWarn` manifest attribute
    // (if not present).
    //
    // Otherwise, the mod warn class won't be generated, nor the
    // manifest attribute.
    generateModClass(true)
```

We welcome Loom to add this function.

### How to generate `ModWarn.class`?

Simply type `./gradlew extractShrunkJar`. The output class
is in `pro-shrunk-output/class/`.

NOTE: A pre-compiled class sized 899 bytes is commited already.
If you don't trust it, you can regenerate it with the command
above, and you'll get one with no difference (unless you modify
it).
