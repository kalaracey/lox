package com.laracey.lox.interpreter;

import java.util.List;

public interface LoxCallable {
    int arity();

    Object call(Interpreter interpreter, List<Object> arguments);
}
