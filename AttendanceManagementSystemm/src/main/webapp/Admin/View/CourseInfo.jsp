<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gybBud7RduPuemT5d8DyAi0mwMvKV2hzZN7KU1a8Dosd7Gb7" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl7/1L_dstPt3HV5HzF6Gvk/e9T9hz90HKjvG9MKHk" crossorigin="anonymous">

<div class="container">

<c:if test="${ not empty editCourseExist}">
<c:choose>
    <c:when test="${editCourse}">
        <div class="alert alert-success" role="alert">
          Done Saved Changes
        </div>
    </c:when>
    <c:otherwise>
       <div class="alert alert-danger" role="alert">
         No Saved Changes ,Try Again
       </div>
    </c:otherwise>
</c:choose>
</c:if>


        <h1>Course Info</h1>

        <form action="/AttendanceManagementSystemm/Admin" method="post" >
            <div class="form-group">
                <label for="NameCourse">Name Course</label>
                <input type="text" class="form-control"  name="NameCourse" id="NameCourse"

                 value="${CourseData.getName()}" >
              </div>
              <div class="form-group">
                <label for="Year">Year</label>
                <input type="text" class="form-control" id="Year" name="Year"
                 value="${CourseData.getYear()}" >
              </div>
              <div class="form-group">
                <label for="email">#Num Student</label>
                <input type="email" class="form-control" id="email"
                value="${CourseData.getNumStudent()}" readonly>
              </div>


            <button type="submit" name="formPost"

            value="Courses:course_Info/${CourseData.getId()}/editCourse" class="btn btn-primary">Save Changes</button>
        </form>
        <form action="/AttendanceManagementSystemm/Admin" method="post" >
<br>
        <br>
        <h3>Students</h3>
        <select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
          <option selected> </option>

           <c:forEach items="${StudentsData}" var="student">

         <button type="submit" name="formPost"
         value="Courses:Attendance/${student.getId()}" class="btn btn-primary">
         <option value="${student.getId()}">${student.getFirstName()}  ${student.getLastName()}  " ID: ${student.getId()}"</option>
         </button>

         </c:forEach>
        </select>

        </form>
        <br>
        <br>
        <h3>Instructors </h3>
        <form action="/AttendanceManagementSystemm/Admin" method="post" >
          <select name="formPost" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" onchange="this.form.submit()">
                  <option selected></option>
                   <c:forEach items="${instructorsData}" var="instructorele">

                 <option  value="Instructor:Member_profile/${instructorele.getId()}">

                 ${instructorele.getFirstName()}  ${instructorele.getLastName()}    " ID: ${instructorele.getId()}"</option>
                 </c:forEach>
          </select>
        </form>
    </div>
