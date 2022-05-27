You can build the interpreter with

```bash
$ make
```

You can build and run the interpreter with

```bash
$ ./run.sh
```

You can pass the file name of a script to the former, as in

```bash
$ ./run.sh examples/prints.lox
```

The interpreter can also be built with

```bash
$ mvn package -Dmaven.test.skip && mvn exec:java -pl interpreter 
```

