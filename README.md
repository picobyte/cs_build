# cs_build
mvn build cs

On Linux:

after cloning the repository cd into it and update submodules:
```bash
git submodule init
git submodule update

# to get updates I sometimes also have to run
git submodule update --remote --merge
```

make sure you have `/opt/java/jdk-17.0.2` or adapt the run.sh and mvn.sh scripts

build and run
```bash
cd cs-app/
./mvn.sh
./run.sh
```
