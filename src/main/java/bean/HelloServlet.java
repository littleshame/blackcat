package bean;

import com.blackcat.HttpRequest;
import com.blackcat.HttpResponse;
import com.blackcat.Servlet;
import com.blackcat.WebServlet;

/**
 * @author: tjc
 * @Date: 2019-7-13
 */
@WebServlet("/hello")
public class HelloServlet extends Servlet {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        System.out.println("hello get");
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {

    }
}
