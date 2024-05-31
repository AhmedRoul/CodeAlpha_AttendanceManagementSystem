<link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h3> My Courses Teach </h3>
<br>
      <div class="table-responsive">

        <table class="table custom-table">
          <thead>
            <tr>
              <th scope="col">#ID</th>
              <th scope="col">Name Course</th>
              <th scope="col">year </th>
              <th scope="col">#NUM Student Registered</th>

            </tr>
          </thead>
          <tbody>

           <c:forEach items="${MyCoursesData}" var="courseEle">
           <form action="/AttendanceManagementSystemm/attendance" method="get">
            <tr scope="row">

              <td>
              <button type="submit" name="courseId" value="${courseEle.getId()}" style="all:unset;">
              ${courseEle.getId()}
              </button>
              </td>
              <td> ${courseEle.getName()}</td>
              <td> ${courseEle.getYear()}</td>
              <td> ${courseEle.getNumStudent()}</td>
            </tr>

            </form>
            </c:forEach>

          </tbody>

        </table>
      </div>