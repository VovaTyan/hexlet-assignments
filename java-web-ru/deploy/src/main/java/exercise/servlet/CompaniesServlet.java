package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        PrintWriter result = response.getWriter();
        List<String> companies = getCompanies();
        if (request.getQueryString() == null || request.getParameter("search").length() == 0
                || !request.getQueryString().contains("search")) {
            for (String company : companies) {
                result.println(company);
            }
        } else {
            if (request.getQueryString().contains("search") && request.getParameter("search").length() > 0) {
                int i = 0;
                for (String company : companies) {
                    if (company.contains(request.getParameter("search"))) {
                        result.println(company);
                        i++;
                    }
                }
                if (i == 0) {
                    result.print("Companies not found");
                }
            }
        }
        result.close();
        // END
    }
}
