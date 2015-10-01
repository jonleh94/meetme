package de.dhbw.meetme.servlet;

import de.dhbw.meetme.database.Transaction;
//import de.dhbw.meetme.database.dao.GeoDao;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.GeoData;
import de.dhbw.meetme.domain.User;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 *DONE(FELIX):
 * I've added new variables to the USER class and tested the assignement of new variables
 * -> JPA recognizes the new attributes perfectly and standard operations can be performed through this UserServlet :)
 *
 * Adding new Users though web Interface works now,
 * /register.html redirects to /user and creates a new user with the form paramters and saves it to the database
 *
 *
 *
 * TODO:
 * check what's the differnce between Servelets and REST(@Jonas,Tim,Moritz why is Jörn using Servlets instead of Webservices?
 * implement the GEO Data, maybe a new web service is needed, because user creation in browser doent support GPS Data
 *
 */

@WebServlet("/user")
public class UserServlet extends HttpServlet {
  private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

  @Inject UserDao userDao;
  @Inject Transaction transaction;
  //@Inject GeoDao geoDao;

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    log.debug("UserServlet get");

    transaction.begin();
    Collection<User> users = userDao.list();

    /** ----NEW USER IS CREATED WITH ATTRIBUTES */
      User user = new User(); //Create new user

      /** ASSIGN THE FORM ATTRIBUTES TO LOCAL VARIRABLES*/

      String username = request.getParameter("Username");
      String firstName = request.getParameter("FirstName");
      String lastName = request.getParameter("LastName");
      String location = request.getParameter("Location");
      String email = request.getParameter("E-mail");
      Integer password = request.getParameter("Password").hashCode();
      Integer passwordR = request.getParameter("Passwordr").hashCode();
      String gender = request.getParameter("Gender");
      /** -----------------*/


    // check if passwords match
    // tbd
      if (password.equals(passwordR)) {
      } //dummy method; needs to be filled with a real check and method to resolve the error
      else {
      }

    // select team
      String team;
      int i = (int) Math.floor(Math.random()*2);
      if(i==0)
      {
        team = "blue";
      }
      else
      {
        team = "red";
      }

      /** SET ALL USER ATTRIBUTES */
    user.setAllAttributes(team, 0, true, password, email, username, firstName, lastName, location, gender);


    log.debug(user.toString());  //little test, can be removed when everything is working
    userDao.persist(user);
      transaction.commit();

/**              ------------- needs to be extended and improved----------------
    transaction.begin();
    GeoData geoData = new GeoData();
    geoData.setGeolength("Länge");
    geoData.setGeowidth("Breite");
    log.debug(geoData.toString());
    geoDao.persist(geoData);
    transaction.commit();
                    ---------------------------------------------------------
**/
    users = new ArrayList<>(users); // cloning the read-only list so that we can add something
    users.add(user);

    //The User.toString() is responsible for the return values of this requests, when meetmeserver/user is executed. e.g. if toString() contains more attributes of the user, the meetmeserver/user will show more values
    response.setContentType("text/html");
    response.setBufferSize(8192);

    try (PrintWriter out = response.getWriter()) {
      out.println("<html lang=\"en\"><head><title>Servlet Hello</title></head>");

      // then write the data of the response
      out.println("<body  bgcolor=\"#ffffff\">"
          + "<h2>Known users:</h2>");

      for(User u: users) {
        out.println(u + "<br/>");
      }

      username = request.getParameter("username");
      if (username != null && username.length() > 0) {
        RequestDispatcher dispatcher =
            getServletContext().getRequestDispatcher("/response");

        if (dispatcher != null) {
          dispatcher.include(request, response);
        }
      }
      out.println("</body></html>");
    }
  }
}
