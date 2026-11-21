public sealed interface Expr permits NumberExpr, VarExpr, OpExpr {}
public record NumberExpr(int val)  implements Expr {}
public record VarExpr(String name) implements Expr {}
public sealed interface OpExpr extends Expr permits AddExpr, MulExpr {}
public record AddExpr(Expr left, Expr right) implements OpExpr {}
public record MulExpr(Expr left, Expr right) implements OpExpr {}

static String eval(Expr e) {
    return switch (e) {           // compile SE KHONG CHO phep thieu case nao
        case NumberExpr n -> String.valueOf(n.val());
        case VarExpr v    -> v.name();
        case AddExpr a    -> eval(a.left()) + " + " + eval(a.right());
        case MulExpr m    -> eval(m.left()) + " * " + eval(m.right());
    };
}
