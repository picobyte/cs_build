# cs_build
mvn build cs


make sure you have JAVA_HOME=/opt/java/jdk-17.0.2
or adapt this in the run.sh and mvn.sh scripts

update submodule
git submodule init
git submodule update

build and run
cd cs-app/
./mvn.sh
./run.sh
