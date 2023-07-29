# Signal-Encryption plug-in

This plug-in visualizes the DoubleRatchet algorithm from the signal-protocol.
This file is a **developer commentary** on the project.

## Cryptographic code

The plug-in uses a copied version of the
[libsignal-protocol-java](https://github.com/signalapp/libsignal-protocol-java)
library. As of 2023 this project has been archived and no further changes
are to be expected.

The libsignal-protocol code is somewhat modified. A class called JCrypToolCapturer
is sent through all stages of the algorithm and captures relevant values for the UI.

The encrypt method has been extended by a callback so the values can be captured and
displayed before the actual encryption message is entered
(see [src/org/whispersystems/libsignal/SessionCipher.java](src/org/whispersystems/libsignal/SessionCipher.java)).

In general this changes have not been tested if the algorithm has been broken
this way, but I am confident it works correct.

As always in JCrypTool code: **DO NOT USE THIS IN PRODUCTION!**


## Protobuf (dependency)

The libsignal-protocol library has a dependency on protobuf.
There is more info to this in [protobuf/README.md](protobuf/README.md).


## Structure of code

The project has some good and some bad parts.

You will notice a lot of duplication in the DoubleRatchetView.
The DoubleRatchetAliceSendingLogic and DoubleRatchetBobSendingLogic is more
or less all duplicated code. So **be careful when changing them**

The graphics package provides a neat way of drawing arrows and other stuff
Even though it is improvable, it is quite neat and could be re-used by other
projects. It's written quite generically.

## Contact

Initially started by students at the FH Oberösterreich Hagenberg.
Finished by Michael Altenhuber (michael@altenhuber.net)
