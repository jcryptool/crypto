# Overview

libsignal-protocol-java uses protobuf. This directory contains the schema
files. In protobuf schema files have to be converted to source-code files.

This has already been done for the plug-in, so everything should work.
Below is an instruction how to re-create the steps.

## Build java classes in src directory

1. Download protoc at http://code.google.com/p/protobuf/downloads/list
1. Extract the archive
1. Execute following:
```
.\protoc.exe --java_out="pathToSignalEncryptionDirectory"\src\ -I "pathToSignalEncryptionDirectory"\protobuf\ "pathToSignalEncryptionDirectory"\protobuf\*.proto
```





