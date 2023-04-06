# Overview

Some dependencies for libsignal in protobuf format
First they have to be converted to java classes for the Plug-In to work
Then they need to be added into their respective directory

## Build java classes in src directory

1. Download protoc at http://code.google.com/p/protobuf/downloads/list
1. Extract the archive
1. Execute following:
```
.\protoc.exe --java_out="pathToSignalEncryptionDirectory"\src\ -I "pathToSignalEncryptionDirectory"\protobuf\ "pathToSignalEncryptionDirectory"\protobuf\*.proto
```





