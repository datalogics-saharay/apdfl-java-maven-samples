# ReadLargeIntegerValues (Java)

Demonstrates reading PDF integer values above the signed 32-bit range
(2^31-1). `PDFInteger.getValue()` (32-bit) truncates them;
`PDFInteger.getLongValue()` (64-bit) returns them intact. The motivating case is
signature `/ByteRange` offsets in documents larger than 2 GB, read by the DocMDP
revision comparer.

## Library reference

`pom.xml` compiles against a **local DLE build** because the `getLongValue()`
accessor (APDFL-6717) is not yet in a released `com.datalogics.pdfl:pdfl`
package. The generators directory also holds the JNI bridge, the APDFL native
DLLs, and the `Resources` folder:

```
C:\Users\saharay\dle\build\release-x86_64\generators
```

Once the fix ships, replace the system-scoped dependency with the released
`pdfl` artifact + `jni`/`resources` classifiers, as in the other samples here.

## Build & run (Windows, cmd)

The system-scoped dependency is not bundled into a jar, so run from the compiled
classes with the generators directory on both `java.library.path` and `PATH`:

```bat
set "GEN=C:\Users\saharay\dle\build\release-x86_64\generators"
mvn -q compile
set "PATH=%GEN%;%PATH%"
java -cp "target\classes;%GEN%\com.datalogics.PDFL.jar" "-Djava.library.path=%GEN%" com.datalogics.pdfl.samples.ReadLargeIntegerValues
```

Pass a PDF path as the first argument to override the default input
(`gap2-bigint-test.pdf` on the Desktop).

## Expected output

```
/BigInt
  getValue()     (32-bit) = 705032704     <- truncated
  getLongValue() (64-bit) = 5000000000    (expected 5000000000)

Signature /ByteRange
  getValue()     (32-bit) = [0 -1073741824 -1073729479 5000]   <- the two > 2 GB offsets wrap negative
  getLongValue() (64-bit) = [0 3221225472 3221237817 5000]     (expected [0 3221225472 3221237817 5000])
```
