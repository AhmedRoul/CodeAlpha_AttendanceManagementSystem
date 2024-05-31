<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gybBud7RduPuemT5d8DyAi0mwMvKV2hzZN7KU1a8Dosd7Gb7" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+Knujsl7/1L_dstPt3HV5HzF6Gvk/e9T9hz90HKjvG9MKHk" crossorigin="anonymous">

<div class="container">

<c:if test="${ not empty editStudentExist}">
<c:choose>
    <c:when test="${editStudent}">
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


        <h1>Student Info</h1>

        <form action="/AttendanceManagementSystemm/Admin" method="post" >
            <div class="form-group">
                <label for="firstName">First Name</label>
                <input type="text" class="form-control"  name="firstName" id="firstName"

                 value="${StudentProfileData.getFirstName()}" >
              </div>
              <div class="form-group">
                <label for="lastName">Last Name</label>
                <input type="text" class="form-control" id="lastName" name="lastName"
                 value="${StudentProfileData.getLastName()}" >
              </div>
              <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email"
                value="${StudentProfileData.getEmail()}" readonly>
              </div>
              <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username"
                value="${StudentProfileData.getUsername()}" readonly>
              </div>

              <div class="form-group">
                <label for="birthday">Birthday</label>
                <input type="date" class="form-control" id="birthday" name="birthday"
                value="${StudentProfileData.getBirthday()}" >
              </div>
              <div class="form-group">
                 <label for="RegisterDay">Birthday</label>
                 <input type="date" class="form-control" id="RegisterDay" name="RegisterDay"
                 value="${StudentProfileData.getRegisterDay()}" readonly>
             </div>


            <button type="submit" name="formPost"
            value="Student:Member_profile/${StudentProfileData.getId()}/editStudent" class="btn btn-primary">Save Changes</button>
        </form>
    </div>
