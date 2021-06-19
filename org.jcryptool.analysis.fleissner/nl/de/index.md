
Unter der Bedienung bitte 4 Unterkapitel:
1) Analyse eines vorgegebenen Beispieltextes
2) Analyse eines eigenen Beispieltextes
3) Verschlüsselung eines Beispieltextes
4) Further samples for grille ciphertexts can be found here:




1) Analyse eines vorgegebenen Beispieltextes

Initial hat das Plug-in folgende Einstellungen, so dass Sie sofort loslegen können:
- Als Methode ist "Analyse" ausgewählt (für einen links-drehenden Beispieltext).
- Ein Beispiel-Geheimtext ist ausgewählt, der mit einer 6*6-Schablone verschlüsselt wurde.
  Dieser Geheimtext wird rechts oben angezeigt.
  Natürlich können Sie auch einen eigenen Geheimtext eingeben oder laden.
- Der für die Analyse verwendete Hill-Climbing-Algorithmus ist auf 6 Restarts eingestellt.
- Mit der Vermutung, dass der Geheimtext in deutsch ist, wurde eine 5-Gramm-Statistik der deutschen Sprache ausgewählt.

Im Englischen muss der letzte Punkt heißen:
- Mit der Vermutung, dass der Geheimtext in englisch ist, wurde eine 5-Gramm-Statistik der englischen Sprache ausgewählt.

==> JCT-DE_Grille01_Bildschirmfoto...png
oder
==> JCT-EN_Grille01_Bildschirmfoto...png


Mit einem Klick auf den Button "Start" wird die Analyse durchgeführt.

Es dauert einen Moment, bis der Mauszeiger sich ändert und unten rechts in der Statuszeile angezeigt wird, dass ein evtl. länger laufender Hintergrundjob (für die Analyse) läuft. Das folgende Bild zeigt das Ergebnis:

==> JCT-DE_Grille02_Bildschirmfoto...png
oder
==> JCT-EB_Grille02_Bildschirmfoto...png


Im Feld "Klartext" sieht man das Ergebnis -- ausgegeben in Zeilen der Länge 1/4 der Anzahl der Felder in der Schablone: hier also der Länge 9 = 36 / 4.

NENNTMICH
ISMAELEIN
PAARJAHRE
ISTSHERUN
WICHTIGWI
ELANGGENA
UDAHATTEI
CHWENIGBI
SGARKEING
...

Wenn man die entsprechenden Sonderzeichen (nicht im deutschen Alphabet) einfügt, erhält man:
NENNT MICH ISMAEL. EIN PAAR JAHRE IST'S HER, UNWICHTIG, WIE LANG GENAU. DA HATTE ICH WENIG BIS GAR KEIN G...


bzw. im Englischen:

CALLMEISH
MAELSOMEY
EARSAGONE
VERMINDHO
WLONGPREC
ISELYHAVI
NGLITTLEO
RNOMONEYI
NMYPURSEA

Wenn man die entsprechenden Sonderzeichen (nicht im englischen Alphabet) einfügt, erhält man:

CALL ME ISHMAEL. SOME YEARS AGO, NEVER MIND HOW LONG PRECISELY. HAVING LITTLE OR NO MONEY IN MY PURSE A...




In der Gruppierung "Ausgabe" (unten) steht das Log, das automatisch nach jedem Start neu erzeugt wird.

Darin finden Sie den gefundenen Schlüssel und auch den ursprünglich Schlüssel, der für den zufällig erzeugten Beispeiltext gewählt wurde:
- in sequentieller Form (die Zählweise ist zeilenweise und mit 1 beginnend): 7 9 11 ...,
- in Koordinaten-Form (die Zählweise ist spaltenweise und beginnt mit 0): (0,1) (0,2) (2,1), und
- in einer visuellen Form mit UTF8-Zeichen (ein helles Quadrat ist ein ausgestanztes Loch in der Schablone).

Ebenfalls ausgegeben wir der Wert der Kostenfunktion für den besten gefundenen Klartext. Je geringer dieser Wert ist, desto besser ist er.





2) Analyse eines eigenen Beispieltextes

Als Beispiel-Geheimtext wurde ein Text aus der deutschen Wikipedia (https://de.wikipedia.org/wiki/Flei%C3%9Fnersche_Schablone) genommen.
Das Verfahren ist linksdrehend und die Sprache deutsch.

KWNILK
LODPII
AIPENE
FEDEDR
IEEEII
NEZAYO

JCT findet als Klartext:
...

[[[Bitte noch ein paar Worte anahnd dieses Beispiels, dass man die Zeilen evtl. umsortieren muss.]]]





3) Verschlüsselung eines Beispieltextes
- Auswahl des entsprechenen Radiobuttons in der Gruppierung Methode
- Beispieltext wählen oder selbst einen Beispieltext eingeben oder laden
- Das Alphabet einstellen (initial steht es im deutschen JCT auf dem deutschen Alphabet)
oder
- Das Alphabet einstellen (initial steht es im englischen JCT auf dem englischen Alphabet)


Im der Gruppierung "Klartext" ist der Klartext einzugeben.
Im Gruppierungstitel wird noch die Länge angegeben: "gefiltert" meint die Länge der Zeichen im Alphabet; "ungefiltert" meint die Länge des ursprünglichen Beispiel-Textes (aus dem bei der Filterung alle Nichtalphabet-Zeichen herausgefiltert werden, außerdem werden Kleinbuchstaben als Großbuchstaben interpretiert, wie das in den n-Gramm-Statistiken üblich ist).

Das Ergebnis können Sie kopieren und auch wieder entschlüsseln.





4) Further samples for Fleissner grille ciphertexts can be found here:


a) You can try a text from  https://scienceblogs.de/klausis-krypto-kolumne/2017/01/19/how-my-readers-solved-the-fleissner-challenge/  using a  20*20 grille.
As the original ciphertext there had a mistake, you might use this correctly generated ciphertext:
PIENLZLALANIEGDSTFETHMOEEVEINONDTBRRMEAAUATNNMNPEOASROODPMFMHOAEMOTOEONDOAERPNXLAINEPFXECORPRDTEEIEDTHNETIUTNRIIOTYINEOWAINTDHMSSATOOURTINAGINONVATTEERDEDHDEUAMTROIINOENGSWALNRGAMTYAHTNESDAOEEIMSINTSAGAPBJOELSOENIHRSDHOMUAHRNDAYWTEMEIRLELERIAAITNFATAENRCTAHRRESHYOTVIICNLEIULCETSNEUAANRLRCOIHUATELSLIPWOEOENNSRGNSTELBSHBEAYSEUINDNCEISHNREGVOTUOFIEDNEBTAHRNEASCUENPAUNNSDWPIOEXITPTYUNGIFLIHKATSMVARAET

We get as plaintext 
PLANSFORMANNEDMOONEXPEDITIONSORIGINATEDDURINGTHEEISENHOWERERAINANARTICLESERIESWERNHERVONBRAUNPOPULARIZEDTHEIDEAOFAMOONEXPEDITIONAMANNEDMOONLANDINGPOSEDMANYTECHNICALCHALLENGESBESIDESGUIDANCEANDWEIGHTMANAGEMENTATMOSPHERICREENTRYWITHOUTOVERHEATINGWASAMAJORHURDLEAFTERTHESOVIETUNIONSLAUNCHOFTHESPUTNIKSATELLITEVONBRAUNPROMOTEDAPLANFORTHEUNITEDSTATESARMYTOESTABLISHAMILITARYLUNAROUTPOSTBYNINETEENSIXTYFIVE

As bigger the grille and the shorter the ciphertext, the higher number of restarts should be set. So you might choose 50 restarts (or more).

As the plaintext is English, please choose the English alphabet.



b) https://scienceblogs.de/klausis-krypto-kolumne/2020/09/29/can-you-solve-this-turning-grille-cryptogram-from-1870/


c) https://de.wikipedia.org/wiki/Flei%C3%9Fnersche_Schablone#Beispiel
(deutscher Text, 6*6, rechtsdrehend)

DWNIIK
LEKFIL
RIPONE
PEIEDA
EEEODI
NIZAYE




d) https://informatika.stei.itb.ac.id/~rinaldi.munir/Kriptografi/2010-2011/cryptanalysis.pdf
From the book Gaines, Helen (1956): Cryptanalysis - a study of ciphers and their solution.
[[[Bitte noch die Size der jeweiligen Schablone dazu schreiben.]]]

#### p.35/Fig.22
TSTHE
TTUSH
OEDGF
RDOEO
GRISA
AMSNM
QEUGI
BRIEL
NOSTH
SICLS
ETSWA
THABR
YPAE

#### p.35/Fig.23
AEKDS
PVTOO
NNAAO
NRONP
ROCTI
EHTRE
HNETI
AFGSR
HTNIL
OVTEF
FALMK
IECLA
ASNM
```
 
#### p.35/Fig.25
RNIII
NGTFL
AILNN
DEETD
RVEUS
ESTHR
EIGEY
FIANO
URRDL
GYTNH
AEONR
NEKCD
EEISE
YBSEF
WYPGR
LOLOE
UOFHP
ATVER
EHERA
EDFMI
TRHNE
EISYT
QTSII
SAUSG
IEAIC
ASLLK
LLTTX
HVHEA
RXAX




