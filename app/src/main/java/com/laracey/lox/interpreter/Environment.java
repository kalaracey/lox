package com.laracey.lox.interpreter;

import java.util.HashMap;
import java.util.Map;

class Environment {
  final Environment enclosing;

  // We use a bare String, rather than a token, since "[a] token represents a unit of code at a
  // specific place in the source text, but when it comes to looking up variables, all identifier
  // tokens with the same name should refer to the same variable (ignoring scope for now)." (Ch. 8)
  private final Map<String, Object> values = new HashMap<>();

  Environment() {
    enclosing = null;
  }

  Environment(Environment enclosing) {
    this.enclosing = enclosing;
  }

  void define(String name, Object value) {
    values.put(name, value);
  }

  Object get(Token name) {
    if (values.containsKey(name.lexeme)) {
      return values.get(name.lexeme);
    }

    if (enclosing != null) return enclosing.get(name);

    throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.");
  }

  void assign(Token name, Object value) {
    if (values.containsKey(name.lexeme)) {
      values.put(name.lexeme, value);
      return;
    }

    if (enclosing != null) {
      enclosing.assign(name, value);
      return;
    }

    throw new RuntimeError(name, "Undefined variable '" + name.lexeme + "'.'");
  }
}
