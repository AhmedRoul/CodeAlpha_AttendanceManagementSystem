<link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

      <div class="table-responsive">

        <table class="table custom-table">
          <thead>
            <tr>
              <th scope="col">#ID</th>
              <th scope="col">First Name</th>
              <th scope="col">Last Name</th>
              <th scope="col">Email</th>
              <th scope="col">Birthday</th>
              <th scope="col">Register Day</th>
              <th scope="col">Current Level</th>
              <th scope="col">#NUM Courses Registered</th>

            </tr>
          </thead>
          <tbody>

           <c:forEach items="${StudentsList}" var="studentEle">
           <form action="/AttendanceManagementSystemm/Admin" method="post">
            <tr scope="row">

              <td>
              <button type="submit" name="formPost" value="Student:Member_profile/${studentEle.getId()}" style="all:unset;">
              ${studentEle.getId()}
              </button>
              </td>
              <td>${studentEle.getFirstName()} </td>
              <td>${studentEle.getLastName()} </td>
              <td> ${studentEle.getEmail()}</td>
              <td> ${studentEle.getUsername()}</td>
              <td> ${studentEle.getBirthday()}</td>
              <td> ${studentEle.getRegisterDay()}</td>
              <td> ${studentEle.getCurrentLevel()}</td>
              <td> 0</td>

            </tr>

            </form>
            </c:forEach>

          </tbody>

        </table>
      </div>