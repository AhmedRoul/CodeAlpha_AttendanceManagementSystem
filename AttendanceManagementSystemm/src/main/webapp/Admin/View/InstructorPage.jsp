<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Instructor Profile</title>


    <link rel="stylesheet" href="Admin/css/jquery-nestable.css"/>
    <link rel="stylesheet" href="Admin/css/style.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/icofont/2.9.1/icofont.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.css" rel="stylesheet"  type='text/css'>


</head>
<body>


<div id="mytask-layout" class="theme-indigo">

    <!-- sidebar -->
    <div class="sidebar px-4 py-4 py-md-5 me-0">
        <div class="d-flex flex-column h-100">
          <form  action="/AttendanceManagementSystemm/Instructor" method="get">
            <!-- Menu: main ul -->
            <ul class="menu-list flex-grow-1 mt-3">

                <li class="collapsed">
                    <a class="m-link active" data-bs-toggle="collapse" data-bs-target="#Ins-Components" >
                        <i class="fa fa-users"></i> <span>Instructor</span> <i class="fa fa-arrow-right" style=" padding-left: 20px ;font-size:18px"></i> </a>
                       <ul class="sub-menu collapse show" id="Ins-Components">
                        <li><button type="submit" name="formPost" style="all:unset;" value="MyProfile"> <span class="ms-link" >My Profile</span></button></li>
                    </ul>
                </li>


                <li class="collapsed">
                    <a class="m-link" data-bs-toggle="collapse" data-bs-target="#course-Components" href="#">
                        <i class="fa fa-book"></i> <span>Courses</span> <i class="fa fa-arrow-right" style=" padding-left: 20px ;font-size:18px"></i> </a>
                    <!-- Menu: Sub menu ul -->
                    <ul class="sub-menu collapse show" id="course-Components">
                        <li> <button  name="formPost" value="MyCourses" style="all:unset;"><span class="ms-link" >My Courses</span></button></li>
                         <li> <a href="/AttendanceManagementSystemm/attendance"><span class="ms-link"  >Attendance Students </span></a></li>
                    </ul>
                </li>

            </ul>

            <!-- Theme: Switch Theme -->
            <ul class="list-unstyled mb-0">
                <li class="d-flex align-items-center justify-content-center">
                    <div class="form-check form-switch theme-switch">
                        <input class="form-check-input" type="checkbox" id="theme-switch">
                        <label class="form-check-label" for="theme-switch">Enable Dark Mode!</label>
                    </div>
                </li>
            </ul>

            </form>

        </div>
    </div>

    <!-- main body area -->
    <div class="main px-lg-4 px-md-4">

        <!-- Body: Header -->
        <div class="header">
            <nav class="navbar py-4">
                <div class="container-xxl">

                    <!-- header rightbar icon -->
                    <div class="h-right d-flex align-items-center mr-5 mr-lg-0 order-1">


                        <div class="dropdown user-profile ml-2 ml-sm-3 d-flex align-items-center zindex-popover">
                            <div class="u-info me-2">
                                <p class="mb-0 text-end line-height-sm "><span class="font-weight-bold">${instructorData.getFirstName()} ${instructorData.getLastName()}</span></p>
                            </div>
                            <a class="nav-link dropdown-toggle pulse p-0" href="#" role="button" data-bs-toggle="dropdown" data-bs-display="static">
                                <img class="avatar lg rounded-circle img-thumbnail" src="Admin/assets/images/profile_av.png" alt="profile">
                            </a>
                            <div class="dropdown-menu rounded-lg shadow border-0 dropdown-animation dropdown-menu-end p-0 m-0">
                                <div class="card border-0 w280">
                                    <div class="card-body pb-0">
                                        <div class="d-flex py-1">
                                            <img class="avatar rounded-circle" src="" alt="profile">
                                            <div class="flex-fill ms-3">
                                                <p class="mb-0"><span class="font-weight-bold">${instructorData.getFirstName()} ${instructorData.getLastName()}</span></p>
                                                <small class="">${instructorData.getEmail()}</small>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="list-group m-2 ">
                                    <form method="post"  action="/AttendanceManagementSystemm/logout">
                                    <button  type="submit" class="list-group-item list-group-item-action border-0 ">
                                       <i  style="font-size:24px" class="fa fa-sign-out"></i>Signout

                                       </button>
                                       </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- menu toggler -->
                    <button class="navbar-toggler p-0 border-0 menu-toggle order-3" type="button" data-bs-toggle="collapse" data-bs-target="#mainHeader">
                        <span class="fa fa-bars"></span>
                    </button>

                    <div class="order-0 col-lg-4 col-md-4 col-sm-12 col-12 mb-3 mb-md-0 ">

                    </div>

                </div>
            </nav>
        </div>
         <c:choose >
        <c:when test="${instructorDataISExist}">
        <jsp:include page="/Admin/View/InstructorPageInfo.jsp" />
        </c:when>
        <c:when test="${Attendance}">
             <jsp:include page="/Admin/View/Attendance.jsp" />
        </c:when>
        <c:when test="${MyCoursespage}">
           <jsp:include page="/Admin/View/MyCoursespage.jsp" />
        </c:when>
        </c:choose>
    </div>
</div>

<!-- Jquery Core Js -->
<script src="Admin/js/libscripts.bundle.js"></script>

<!-- Jquery Page Js -->
<script src="Admin/js/template.js"></script>
</body>
</html>