pack:
	clj -A:pack -m mach.pack.alpha.skinny --no-libs --project-path target/emoji.jar

pom:
	clojure -Spom

deploy-clojars:
	mvn deploy:deploy-file -Dfile=target/emoji.jar -DrepositoryId=clojars -Durl=https://clojars.org/repo -DpomFile=pom.xml
