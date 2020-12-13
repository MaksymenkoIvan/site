package org.example;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.Collections;

public class App {
    public static Data data = new Data();
    public static DataCode dataCode = new DataCode();
    public static void main( String[] args )
    {
        Javalin javalin = Javalin.create().start("localhost",2014);
        javalin.get("/", ctx ->{
            System.out.println(ctx.status());
            ctx.render("regist.jte");
        });
        javalin.get("/home", App::renderMainPage);
        javalin.post("/api/regist/", ctx ->{
            System.out.println(ctx.formParam("login"));
            System.out.println(ctx.formParam("pass"));
            String login = ctx.formParam("login");
            String pass = ctx.formParam("pass");
            data.connect(login);
            if (data.register(login, pass) == true){
                System.out.println("GOOD");
                ctx.cookie("login", login);
                ctx.redirect("/home");
            }else {
                System.out.println("BAD");
            }
        });
        javalin.post("/api/check/", ctx ->{
            ctx.redirect("/checking");
            String email = ctx.formParam("email");
            System.out.println(email);
            dataCode.code(email);

        });
        javalin.get("/checking", ctx ->{
            System.out.println(ctx.status());
            ctx.render("check.jte");
        });
    }
    public static void renderMainPage(Context ctx){
        Data data = new Data();
        User user = new User();
        user.id = data.getId(ctx.cookie("login"));
        user.balance = data.getBalance(ctx.cookie("login"));
        ctx.render("home.jte", Collections.singletonMap("user", user));
    }
}
