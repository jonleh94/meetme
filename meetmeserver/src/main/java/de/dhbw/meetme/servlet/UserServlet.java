package de.dhbw.meetme.servlet;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.UserDao;
import de.dhbw.meetme.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;

//import de.dhbw.meetme.database.dao.GeoDao;

/**
 * DONE(FELIX):
 * I've added new variables to the USER class and tested the assignement of new variables
 * -> JPA recognizes the new attributes perfectly and standard operations can be performed through this UserServlet :)
 * <p>
 * Adding new Users though web Interface works now,
 * /register.html redirects to /user and creates a new user with the form paramters and saves it to the database
 */

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @Inject
    UserDao userDao;
    @Inject
    Transaction transaction;


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("UserServlet get");

        transaction.begin();
        User user = new User(); //Create new user
        Collection<User> users = userDao.list();

        /** ----NEW USER IS CREATED WITH ATTRIBUTES */


        /** ASSIGN THE FORM ATTRIBUTES TO LOCAL VARIRABLES*/

        String username = request.getParameter("Username");
        String firstName = request.getParameter("FirstName");
        String lastName = request.getParameter("LastName");
        String location = request.getParameter("Location");
        String email = request.getParameter("E-mail");
        String gender = "unknown";  // request.getParameter("Gender");
        String team = getRandomTeam();

        //Generate 4-Digit Pin
        int meetmecode = generatePin();

        // check if passwords match
        String password = null;
        if (request.getParameter("Password").equals(request.getParameter("Passwordr")) && !request.getParameter("Password").isEmpty()) {
            // generate MD5 Hash to secure password
            password = getMD5(request.getParameter("Password"));
        } else if (request.getParameter("Password").isEmpty()) {
            try (PrintWriter writer = response.getWriter()) {
                // build HTML code
                String htmlRespone = "<?php\n" +
                        "\" +\n" +
                        "                    \"require_once('connect_to_mysql.php');\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"    $username = $_POST['username'];\\n\" +\n" +
                        "                    \"    $name = $_POST['name'];\\n\" +\n" +
                        "                    \"    $vorname = $_POST['vorname'];\\n\" +\n" +
                        "                    \"\\t$password = $_POST['password'];\\n\" +\n" +
                        "                    \"    $email = $_POST['email'];\\n\" +\n" +
                        "                    \"\\t  \\n\" +\n" +
                        "                    \"    \\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"  \\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"  $query = mysql_query(\\\"INSERT INTO meetme (username, name, vorname, email, password, date) VALUES('$username','$name','$vorname','$email','$password',now())\\\") or die(\\\"Could not insert your information\\\");\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\t\\n\" +\n" +
                        "                    \"?>\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"<html>\\n\" +\n" +
                        "                    \"\\t<head>\\n\" +\n" +
                        "                    \"    <meta charset=\\\"UTF-8\\\">\\n\" +\n" +
                        "                    \"\\t\\t<title>MeetMe - No Password Provided</title>\\n\" +\n" +
                        "                    \"<link rel=\\\"stylesheet\\\" type=\\\"text/css\\\" href=\\\"css/style.css\\\">\\t\\n\" +\n" +
                        "                    \"<meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=UTF-8\\\" />\\n\" +\n" +
                        "                    \"<meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1, user-scalable=no\\\" />\\n\" +\n" +
                        "                    \"<link href='http://fonts.googleapis.com/css?family=Quicksand:300,400,700' rel='stylesheet' type='text/css'>\\n\" +\n" +
                        "                    \"<script type=\\\"text/javascript\\\" src=\\\"js/jquery-1.10.2.min.js\\\"></script>    \\n\" +\n" +
                        "                    \"<script type=\\\"text/javascript\\\" src=\\\"js/jquery.walidate.js\\\"></script>   \\n\" +\n" +
                        "                    \"<script type=\\\"text/javascript\\\" src=\\\"js/walidate_register.js\\\"></script>  \\n\" +\n" +
                        "                    \"\\t</head>\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\t<body>\\n\" +\n" +
                        "                    \"    <div class=\\\"wrapper\\\">\\n\" +\n" +
                        "                    \"    \\n\" +\n" +
                        "                    \"    <div id=\\\"valid_box\\\"><img class=\\\"logo\\\" src=\\\"img/logo.png\\\" alt=\\\"MeetMe\\\" title=\\\"MeetMe\\\">\\n\" +\n" +
                        "                    \"    <div id=\\\"success\\\"><div id=\\\"check_background\\\"><img src=\\\"img/stop.png\\\"></div><h2>No Password Provided!</h2></div>\\n\" +\n" +
                        "\n" +
                        "                    \"    </div>\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"   \\n\" +\n" +
                        "                    \"    \\n\" +\n" +
                        "                    \"\\t\\t\\n\" +\n" +
                        "                    \"     </div>\\n\" +\n" +
                        "                    \"     <div class=\\\"footer\\\">\\n\" +\n" +
                        "                    \"     \\t<div class=\\\"nav_footer\\\">\\n\" +\n" +
                        "                    \"        \\t<ul>\\n\" +\n" +
                        "                    \"            \\t<li><a href=\\\"login.php\\\">Login</a></li>\\n\" +\n" +
                        "                    \"                <li><a href=\\\"register.php\\\">Register</a></li>\\n\" +\n" +
                        "                    \"                <li><a href=\\\"impressum.php\\\">Impressum</a></li>\\n\" +\n" +
                        "                    \"            </ul>\\n\" +\n" +
                        "                    \"        </div>\\n\" +\n" +
                        "                    \"     </div>\\n\" +\n" +
                        "                    \"    \\n\" +\n" +
                        "                    \"     <script type=\\\"text/javascript\\\">\\n\" +\n" +
                        "                    \"\\t $('#button').click(function () {\\n\" +\n" +
                        "                    \"\\t\\n\" +\n" +
                        "                    \"   $('#register_button').slideUp(1000, function () {   $('#register_form').slideDown(1000)});\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"   \\n\" +\n" +
                        "                    \"});\\n\" +\n" +
                        "                    \"\\t </script>\\n\" +\n" +
                        "                    \"     \\n\" +\n" +
                        "                    \"\\t</body>\\n\" +\n" +
                        "                    \"</html>";

                // return response
                writer.println(htmlRespone);
            }
        } else {
            // get response writer
            try (PrintWriter writer = response.getWriter()) {
                // build HTML code
                String htmlRespone = "<?php\n" +
                        "\" +\n" +
                        "                    \"require_once('connect_to_mysql.php');\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"    $username = $_POST['username'];\\n\" +\n" +
                        "                    \"    $name = $_POST['name'];\\n\" +\n" +
                        "                    \"    $vorname = $_POST['vorname'];\\n\" +\n" +
                        "                    \"\\t$password = $_POST['password'];\\n\" +\n" +
                        "                    \"    $email = $_POST['email'];\\n\" +\n" +
                        "                    \"\\t  \\n\" +\n" +
                        "                    \"    \\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"  \\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"  $query = mysql_query(\\\"INSERT INTO meetme (username, name, vorname, email, password, date) VALUES('$username','$name','$vorname','$email','$password',now())\\\") or die(\\\"Could not insert your information\\\");\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\t\\n\" +\n" +
                        "                    \"?>\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"<html>\\n\" +\n" +
                        "                    \"\\t<head>\\n\" +\n" +
                        "                    \"    <meta charset=\\\"UTF-8\\\">\\n\" +\n" +
                        "                    \"\\t\\t<title>MeetMe - WRONG PASSWORD</title>\\n\" +\n" +
                        "                    \"<link rel=\\\"stylesheet\\\" type=\\\"text/css\\\" href=\\\"css/style.css\\\">\\t\\n\" +\n" +
                        "                    \"<meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=UTF-8\\\" />\\n\" +\n" +
                        "                    \"<meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1, user-scalable=no\\\" />\\n\" +\n" +
                        "                    \"<link href='http://fonts.googleapis.com/css?family=Quicksand:300,400,700' rel='stylesheet' type='text/css'>\\n\" +\n" +
                        "                    \"<script type=\\\"text/javascript\\\" src=\\\"js/jquery-1.10.2.min.js\\\"></script>    \\n\" +\n" +
                        "                    \"<script type=\\\"text/javascript\\\" src=\\\"js/jquery.walidate.js\\\"></script>   \\n\" +\n" +
                        "                    \"<script type=\\\"text/javascript\\\" src=\\\"js/walidate_register.js\\\"></script>  \\n\" +\n" +
                        "                    \"\\t</head>\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\t<body>\\n\" +\n" +
                        "                    \"    <div class=\\\"wrapper\\\">\\n\" +\n" +
                        "                    \"    \\n\" +\n" +
                        "                    \"    <div id=\\\"valid_box\\\"><img class=\\\"logo\\\" src=\\\"img/logo.png\\\" alt=\\\"MeetMe\\\" title=\\\"MeetMe\\\">\\n\" +\n" +
                        "                    \"    <div id=\\\"success\\\"><div id=\\\"check_background\\\"><img src=\\\"img/stop.png\\\"></div><h2>Passwords do not match!</h2></div>\\n\" +\n" +
                        "\n" +
                        "                    \"    </div>\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"   \\n\" +\n" +
                        "                    \"    \\n\" +\n" +
                        "                    \"\\t\\t\\n\" +\n" +
                        "                    \"     </div>\\n\" +\n" +
                        "                    \"     <div class=\\\"footer\\\">\\n\" +\n" +
                        "                    \"     \\t<div class=\\\"nav_footer\\\">\\n\" +\n" +
                        "                    \"        \\t<ul>\\n\" +\n" +
                        "                    \"            \\t<li><a href=\\\"login.php\\\">Login</a></li>\\n\" +\n" +
                        "                    \"                <li><a href=\\\"register.php\\\">Register</a></li>\\n\" +\n" +
                        "                    \"                <li><a href=\\\"impressum.php\\\">Impressum</a></li>\\n\" +\n" +
                        "                    \"            </ul>\\n\" +\n" +
                        "                    \"        </div>\\n\" +\n" +
                        "                    \"     </div>\\n\" +\n" +
                        "                    \"    \\n\" +\n" +
                        "                    \"     <script type=\\\"text/javascript\\\">\\n\" +\n" +
                        "                    \"\\t $('#button').click(function () {\\n\" +\n" +
                        "                    \"\\t\\n\" +\n" +
                        "                    \"   $('#register_button').slideUp(1000, function () {   $('#register_form').slideDown(1000)});\\n\" +\n" +
                        "                    \"\\n\" +\n" +
                        "                    \"   \\n\" +\n" +
                        "                    \"});\\n\" +\n" +
                        "                    \"\\t </script>\\n\" +\n" +
                        "                    \"     \\n\" +\n" +
                        "                    \"\\t</body>\\n\" +\n" +
                        "                    \"</html>";

                // return response
                writer.println(htmlRespone);
            }
        }

        /** SET ALL USER ATTRIBUTES */

        user.setAllAttributes(team, 0, true, password, email, username, firstName, lastName, location, gender, meetmecode);


        log.debug(user.toString());  //little test, can be removed when everything is working
        userDao.persist(user);
        transaction.commit();


        users = new ArrayList<>(users); // cloning the read-only list so that we can add something
        users.add(user);

        //The User.toString() is responsible for the return values of this requests, when meetmeserver/user is executed. e.g. if toString() contains more attributes of the user, the meetmeserver/user will show more values
        response.setContentType("text/html");
        response.setBufferSize(8192);

        try (PrintWriter out = response.getWriter()) {
            out.println("<?php\n" +
                    "require_once('connect_to_mysql.php');\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "    $username = $_POST['username'];\n" +
                    "    $name = $_POST['name'];\n" +
                    "    $vorname = $_POST['vorname'];\n" +
                    "\t$password = $_POST['password'];\n" +
                    "    $email = $_POST['email'];\n" +
                    "\t  \n" +
                    "    \n" +
                    "\n" +
                    "  \n" +
                    "\n" +
                    "  $query = mysql_query(\"INSERT INTO meetme (username, name, vorname, email, password, date) VALUES('$username','$name','$vorname','$email','$password',now())\") or die(\"Could not insert your information\");\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\t\n" +
                    "?>\n" +
                    "\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "\t\t<title>MeetMe - Registration</title>\n" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\t\n" +
                    "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, user-scalable=no\" />\n" +
                    "<link href='http://fonts.googleapis.com/css?family=Quicksand:300,400,700' rel='stylesheet' type='text/css'>\n" +
                    "<script type=\"text/javascript\" src=\"js/jquery-1.10.2.min.js\"></script>    \n" +
                    "<script type=\"text/javascript\" src=\"js/jquery.walidate.js\"></script>   \n" +
                    "<script type=\"text/javascript\" src=\"js/walidate_register.js\"></script>  \n" +
                    "\t</head>\n" +
                    "\n" +
                    "\t<body>\n" +
                    "    <div class=\"wrapper\">\n" +
                    "    \n" +
                    "    <div id=\"valid_box\"><img class=\"logo\" src=\"img/logo.png\" alt=\"MeetMe\" title=\"MeetMe\">\n" +
                    "    <div id=\"success\"><div id=\"check_background\"><img src=\"img/check.png\"></div><h2>Willkommen bei Meet Me</h2></div>\n" +

                    "    </div>\n" +
                    "\n" +
                    "\n" +
                    "   \n" +
                    "    \n" +
                    "\t\t\n" +
                    "     </div>\n" +
                    "     <div class=\"footer\">\n" +
                    "     \t<div class=\"nav_footer\">\n" +
                    "        \t<ul>\n" +
                    "            \t<li><a href=\"login.php\">Login</a></li>\n" +
                    "                <li><a href=\"register.php\">Register</a></li>\n" +
                    "                <li><a href=\"impressum.php\">Impressum</a></li>\n" +
                    "            </ul>\n" +
                    "        </div>\n" +
                    "     </div>\n" +
                    "    \n" +
                    "     <script type=\"text/javascript\">\n" +
                    "\t $('#button').click(function () {\n" +
                    "\t\n" +
                    "   $('#register_button').slideUp(1000, function () {   $('#register_form').slideDown(1000)});\n" +
                    "\n" +
                    "   \n" +
                    "});\n" +
                    "\t </script>\n" +
                    "     \n" +
                    "\t</body>\n" +
                    "</html>");
        }
    }

    // generate MD5 Hash of password and return as string
    private static String getMD5(String pass) throws IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            pass = sb.toString();
        } catch (Exception ie) {
            ie.printStackTrace();
        }
        return pass;
    }

    // choose random team
    private static String getRandomTeam() {
        String team;
        int i = (int) Math.floor(Math.random() * 2);
        if (i == 0) {
            team = "blue";
        } else {
            team = "red";
        }
        return team;
    }

    // generate pin
    private static int generatePin() {
        //generate a 4 digit integer 1000 <10000
        int pin = (int) (Math.random() * 9000) + 1000;
        return pin;
    }
}
