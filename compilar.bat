dir /s /B *.java > sources.txt

javac -verbose -d target -cp "target;lib/*;lib/jetty/*" -encoding utf8 @sources.txt
jar cfmv pfg.jar manifest.txt -C target/ .

del sources.txt
