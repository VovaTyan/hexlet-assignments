package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ArrayUtils;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        ObjectMapper objectMapper = new ObjectMapper();
        String stringJson = Files.readString(Path.of("./src/main/resources/users.json"));
        List<Map<String, String>> listUsers = objectMapper
                .readValue(stringJson, new TypeReference<>() {});
        return listUsers;
        // END
    }

    private void showUsers(HttpServletRequest request,
                           HttpServletResponse response)
            throws IOException {

        // BEGIN
        List <Map<String, String>> listUsers = getUsers();
        PrintWriter pw = response.getWriter();
        pw.write("<table>");
        for (Map<String, String> user: listUsers) {
            pw.write("  </tr>\n" +
                    "    <td>" + user.get("id") + " </td>\n" +
                    "    <td>" + "<a href=\"/users/" + user.get("id") + "\">" +
                    user.get("firstName") + " " + user.get("lastName") + "</a>\n" +
                    "    </td>\n" +
                    "  </tr>\n");
        }
        pw.write("</table>");
        // END
    }

    private void showUser(HttpServletRequest request,
                          HttpServletResponse response,
                          String id)
            throws IOException {

        // BEGIN
        List <Map<String, String>> listUsers = getUsers();
        String result = "";
        for (Map<String, String> user: listUsers) {
            if (user.get("id").equals(id)) {
                result = "<table\n>" +
                        "  <tr>\n" +
                        "    <td>" + user.get("id") + " </td>" +
                        "    <td>" + user.get("firstName") + " </td>" +
                        "    <td>" + user.get("lastName") + " </td>" +
                        "    <td>" + user.get("email") + " </td>" +
                        "  </tr>\n" +
                        "</table>";
            }
        }
        if (result.length() == 0) {
            response.sendError(SC_NOT_FOUND, "Not found");
        }
        PrintWriter pw = response.getWriter();
        pw.write(result);
        // END
    }
}