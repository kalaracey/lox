package com.laracey.lox.interpreter;

import com.laracey.lox.interpreter.Expr.Binary;
import com.laracey.lox.interpreter.Expr.Grouping;
import com.laracey.lox.interpreter.Expr.Literal;
import com.laracey.lox.interpreter.Expr.Ternary;
import com.laracey.lox.interpreter.Expr.Unary;

public class AstPrinter implements Expr.Visitor<String> {
  String print(Expr expr) {
    return expr.accept(this);
  }

  @Override
  public String visitBinaryExpr(Binary expr) {
    return parenthesize(expr.operator.lexeme, expr.left, expr.right);
  }

  @Override
  public String visitGroupingExpr(Grouping expr) {
    return parenthesize("group", expr.expression);
  }

  @Override
  public String visitLiteralExpr(Literal expr) {
    if (expr.value == null) return "nil";
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Unary expr) {
    return parenthesize(expr.operator.lexeme, expr.right);
  }

  @Override
  public String visitTernaryExpr(Ternary expr) {
    return parenthesize("ternary", expr.left, expr.middle, expr.right);
  }

  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    for (Expr expr : exprs) {
      builder.append(" ");
      builder.append(expr.accept(this));
    }
    builder.append(")");

    return builder.toString();
  }

  public static void main(String[] args) {
    Expr expression =
        new Expr.Binary(
            new Expr.Unary(new Token(TokenType.MINUS, "-", null, 1), new Expr.Literal(123)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(new Expr.Literal(45.67)));

    System.out.println(new AstPrinter().print(expression));
  }
}