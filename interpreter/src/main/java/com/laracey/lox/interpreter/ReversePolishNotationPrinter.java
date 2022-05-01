package com.laracey.lox.interpreter;

import com.laracey.lox.interpreter.Expr.Binary;
import com.laracey.lox.interpreter.Expr.Grouping;
import com.laracey.lox.interpreter.Expr.Literal;
import com.laracey.lox.interpreter.Expr.Unary;
import com.laracey.lox.interpreter.Expr.Visitor;

public class ReversePolishNotationPrinter implements Visitor<String> {
  String print(Expr expr) {
    return expr.accept(this);
  }

  @Override
  public String visitBinaryExpr(Binary expr) {
    return expr.left.accept(this) + " " + expr.right.accept(this) + " " + expr.operator.lexeme;
  }

  @Override
  public String visitGroupingExpr(Grouping expr) {
    return expr.expression.accept(this);
  }

  @Override
  public String visitLiteralExpr(Literal expr) {
    return expr.value == null ? "nil" : expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Unary expr) {
    return expr.right.accept(this) + " " + expr.operator.lexeme + " ";
  }

  public static void main(String[] args) {
    Expr expression =
        new Expr.Binary(
            new Expr.Unary(new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(123)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(new Expr.Literal(45.67)));

    System.out.println(new ReversePolishNotationPrinter().print(expression));
  }
}
