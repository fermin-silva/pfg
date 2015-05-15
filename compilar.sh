find src -type f -name "*.java" | xargs javac -verbose -d target/ -cp "target/:lib/*:lib/jetty/*"
jar cfmv pfg.jar manifest.txt -C target/ .
