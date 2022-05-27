#!/usr/bin/env bash
make -s
java -cp interpreter/target/interpreter-1.0-SNAPSHOT-jar-with-dependencies.jar com.laracey.lox.interpreter.Lox $@
