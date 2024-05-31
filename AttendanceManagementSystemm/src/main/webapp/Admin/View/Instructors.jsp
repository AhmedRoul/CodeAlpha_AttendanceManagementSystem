<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="body d-flex py-lg-3 py-md-2">
            <div class="container-xxl">
                <div class="row clearfix">
                    <div class="col-md-12">
                        <div class="card border-0 mb-4 no-bg">
                            <div class="card-header py-3 px-0 d-sm-flex align-items-center  justify-content-between border-bottom">
                                <h3 class=" fw-bold flex-fill mb-0 mt-sm-0">Instructors :</h3>
                                <button type="button" class="btn btn-dark me-1 mt-1 w-sm-100" ><i class="fa fa-plus me-2 fs-6"></i>Add Employee</button>

                            </div>
                        </div>
                    </div>

                </div><!-- Row End -->


                <div class="row g-3 row-cols-1 row-cols-sm-1 row-cols-md-1 row-cols-lg-2 row-cols-xl-2 row-cols-xxl-2 row-deck py-1 pb-4">
                    <div class="col">

                        <div class="card teacher-card">
                          <c:forEach items="${InstructorsList}" var="InstructorEle">
                            <div class="card-body d-flex">
                                <div class="profile-av pe-xl-4 pe-md-2 pe-sm-4 pe-4 text-center w220">
                                    <img src="/AttendanceManagementSystemm/default-photo.jpg" alt="image Instructor" class="avatar xl rounded-circle img-thumbnail shadow-sm">
                                </div>
                                <div class="teacher-info border-start ps-xl-4 ps-md-3 ps-sm-4 ps-4 w-100">
                                    <h6  class="mb-0 mt-2  fw-bold d-block fs-6">${InstructorEle.getFirstName()} ${InstructorEle.getLastName()}</h6>
                                    <span class="light-info-bg py-1 px-2 rounded-1 d-inline-block fw-bold small-11 mb-0 mt-1">Computer Science</span>
                                    <div class="video-setting-icon mt-3 pt-3 border-top">
                                        <p>details is null </p>
                                    </div>
                                    <form action="/AttendanceManagementSystemm/Admin" method="post" >

                                    <button  name="formPost" value="Instructor:Member_profile/${InstructorEle.getId()}"  style="all:unset;">
                                    <span class="btn btn-dark btn-sm mt-1"><i class="fa fa-edit me-2 fs-6"></i>Edit</span></button>

                                    <button  name="formPost" value="Instructor:Member_profile/${InstructorEle.getId()}" style="all:unset;">
                                    <span  class="btn btn-dark btn-sm mt-1"><i class="fa fa-user-circle-o me-2 fs-6"></i>Profile</span></button>
                                    </form>

                                </div>
                            </div>
                            </c:forEach>
                        </div>
                    </div>

                    </div>
                </div>


            </div>
        </div>