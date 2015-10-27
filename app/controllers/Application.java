package controllers;


import play.mvc.*;
import views.html.welcome;


public class Application extends Controller {
	

    public  Result welcome() {
        return ok(welcome.render());
    }

}
