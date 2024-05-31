<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" />

<form  action="/AttendanceManagementSystemm/attendance" method="get">
<h3>Courses :</h3>
   <select name="courseId" class="form-select" onchange="this.form.submit()">
    <c:choose>
        <c:when test="${ not empty CourseAttendatesISExist}">

          <option selected>${CourseInfoattendate.getName()}     ${CourseInfoattendate.getId()}</option>
        </c:when>
        <c:otherwise>
           <option selected>Select Course</option>
        </c:otherwise>
    </c:choose>

      <c:forEach  items="${CoursesInfoAtten}" var="Course" >
      <option value="${Course.getId()}">${Course.getName()}    ${Course.getId()}</option>
      </c:forEach>

   </select>
 </form>
<c:if test="${not empty CourseAttendatesISExist}">
<div class="row align-items-center">
                    <div class="border-0 mb-4">
                        <div class="card-header py-3 no-bg bg-transparent d-flex align-items-center px-0 justify-content-between border-bottom flex-wrap">
                            <h3 class="fw-bold mb-0">Attendance Course ${CourseInfoattendate.getName()}    (${CourseInfoattendate.getId()}) :</h3>

                            <div class="col-auto d-flex mt-1 mt-sm-0">
                                <button type="button" class="btn btn-dark  w-sm-100 me-2" data-bs-toggle="modal" data-bs-target="#editattendance">
                                <i class="fa-regular fa-pen-to-square" style="color: #ffffff;font-size:18px;padding-right=10px;"></i>  Edit Attendance</button>


                            </div>
                        </div>
                    </div>
                </div>


<div class="row clearfix g-3">
                  <div class="col-sm-12">
                        <div class="card mb-3">
                            <div class="card-body">
                                <div class="atted-info d-flex mb-3 flex-wrap">
                                        <div class="full-present me-2">
                                           <i class="fa-solid fa-circle-check" style="color: #02e328; font-size:18px;padding-right=10px;"></i>
                                            <span>Present</span>
                                        </div>
                                        <div class="Half-day me-2">
                                           <i class="fa-regular fa-clock" style="color: #FFD43B;font-size:18px;padding-right=10px;"></i>
                                            <span>Not Assigned Yet</span>
                                        </div>
                                        <div class="absent me-2">
                                            <i class="fa-regular fa-circle-xmark" style="color: #dc3545;font-size:18px;padding-right=10px;"></i>
                                            <span>Absence</span>
                                        </div>


                                </div>
                                <h3 style="text-align: center;"> Number weeks</h3>
                                <div class="table-responsive">
                                    <table class="table table-hover align-middle mb-0" style="width:100%">
                                        <thead>
                                            <tr>
                                                <th>Student</th>
                                                <th>1</th>
                                                <th>2</th>
                                                <th>3</th>
                                                <th>4</th>
                                                <th>5</th>
                                                <th>6</th>
                                                <th>7</th>
                                                <th>8</th>
                                                <th>9</th>
                                                <th>10</th>
                                                <th>11</th>
                                                <th>12</th>
                                                <th>13</th>
                                                <th>14</th>
                                                <th>15</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${attendanceMap}" var="studentEntry">
                                               <tr>

                                            <td>
                                                <span class="fw-bold small">${studentEntry.key.getFirstName()}  ${studentEntry.key.getFirstName()} (id:${studentEntry.key.getId()})</span>
                                            </td>

                                             <c:forEach items="${studentEntry.value}" var="listElement">
                                             <td>

                                            <c:choose>
                                                        <c:when test="${listElement.getState_Student() eq 'P'.charAt(0)}">
                                                            <i class="fa-solid fa-circle-check" style="color: #02e328; font-size:18px;padding-right:10px;"></i>
                                                        </c:when>
                                                        <c:when test="${listElement.getState_Student() eq 'N'.charAt(0)}">
                                                            <i class="fa-regular fa-clock" style="color: #FFD43B;font-size:18px;padding-right:10px;"></i>
                                                        </c:when>
                                                        <c:when test="${listElement.getState_Student() eq 'A'.charAt(0)}">
                                                            <i class="fa-regular fa-circle-xmark" style="color: #dc3545;font-size:18px;padding-right:10px;"></i>
                                                        </c:when>
                                            </c:choose>
                                            </td>

                                           </c:forEach>
                                            </tr>

                                            </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <h3>Instructors</h3>
                                <select  id ="instructors" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" >

                                       <c:forEach items="${instructors}" var="instructorele">
                                        <option  value="Instructor:Member_profile/${instructorele.getId()}">
                                              ${instructorele.getFirstName()}  ${instructorele.getLastName()}    " ID: ${instructorele.getId()}"</option>
                                         </c:forEach>
                                </select>
                            </div>
                        </div>
                  </div>
                </div><!-- Row End -->
            </div>
<div class="modal fade" id="editattendance" tabindex="-1"  aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-md modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title  fw-bold" id="editattendanceLabel"> Edit Attendance</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                 <form action="/AttendanceManagementSystemm/attendance?courseId=${CourseInfoattendate.getId()}" method="post" >

                    <div class="modal-body">
                        <div class="mb-3">
                            <label class="form-label">Select Student</label>
                            <select class="form-select" name="idAttendance">

                            <c:forEach items="${attendanceMap}" var="studentEntry">
                              <option value="${studentEntry.key.getID_attendance()}"> <span class="fw-bold small">${studentEntry.key.getFirstName()}  ${studentEntry.key.getFirstName()}     ${studentEntry.key.getId()}</span>
                            </c:forEach >
                              </option>
                            </select>

                        </div>
                        <div class="deadline-form">
                                <div class="row g-3 mb-3">
                                  <div class="col-sm-12">
                                    <label for="datepickerdedass" class="form-label">Select Week</label>
                                     <select class="form-select" name="numberweek">
                                     <c:forEach var="i" begin="1" end="15">
                                       <option value="${i}">${i}</option>
                                     </c:forEach>

                                     </select>
                                  </div>
                                  <div class="col-sm-12">
                                        <label class="form-label">Attendance Type</label>
                                        <select class="form-select" name="typeAttendance">
                                            <option value="P"> Present</option>
                                            <option value="N">Not Assigned Yet</option>
                                            <option value="A">Absence</option>
                                        </select>
                                  </div>
                                </div>

                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary"data-bs-dismiss="modal" >Save</button>
                    </div>
                 </form>
                </div>
                </div>
        </div>

</c:if>

