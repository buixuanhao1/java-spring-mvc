<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Update - Hao-huengmin</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                            $("#imagePreview").css({ "display": "none" });
                        });
                    });
                </script>
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Update User</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active"><a href="/admin">Dashborad</a></li>
                                    <li class="breadcrumb-item active">Dashboard</li>
                                </ol>
                            </div>
                            <div class="container mt-5">
                                <div class="row">
                                    <div class="col-md-6 col-12 mx-auto">
                                        <h3>User Update</h3>
                                        <form:form method="post" action="/admin/product/update"
                                            enctype="multipart/form-data" modelAttribute="newProduct" class="row">
                                            <div class="mb-3 col-12 col-md-6" style="display: none;">
                                                <label for="id" class="form-label">Id</label>
                                                <form:input type="number" class="form-control" path="id" />
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <label for="name" class="form-label">Name</label>
                                                <form:input type="text" class="form-control" path="name" />
                                            </div>

                                            <div class="mb-3 col-12 col-md-6">
                                                <label for="price" class="form-label">Price</label>
                                                <form:input type="number" class="form-control" path="price" />
                                            </div>
                                            <div class="mb-3 col-12 col-md-12">
                                                <label for="detailDesc" class="form-label">Detail Desc</label>
                                                <form:input type="textArea" class="form-control" path="detailDesc" />
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <label for="shortDesc" class="form-label">Short Desc</label>
                                                <form:input type="textArea" class="form-control" path="shortDesc" />
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <label for="quantity" class="form-label">Quantity</label>
                                                <form:input type="number" class="form-control" path="quantity" />
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <label class="form-label">Factory</label>
                                                <form:select class="form-select" path="factory">
                                                    <form:option value="APPLE">Apple (MacBook)</form:option>
                                                    <form:option value="ASUS">Asus</form:option>
                                                    <form:option value="LENOVO">Lenovo</form:option>
                                                    <form:option value="DELL">Dell</form:option>
                                                    <form:option value="LG">LG</form:option>
                                                    <form:option value="ACER">Acer</form:option>
                                                </form:select>
                                            </div>

                                            <div class="mb-3 col-12 col-md-6">
                                                <label class="form-label">Target</label>
                                                <form:select class="form-select" path="target">
                                                    <form:option value="Gaming">Gaming</form:option>
                                                    <form:option value="SINHVIEN-VANPHONG">Sinh viên - văn phòng
                                                    </form:option>
                                                    <form:option value="THIET-KE-DO-HOA">Thiết kế đồ họa</form:option>
                                                    <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                                                    <form:option value="DOANH-NHAN">Doanh nhân</form:option>
                                                </form:select>
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <label for="avatarFile" class="form-label">Image</label>
                                                <input class="form-control" type="file" id="avatarFile"
                                                    accept=".png, .jpg, .jpeg" name="hao_File" />
                                            </div>
                                            <div class="col-12 mb-3">
                                                <img style="max-height: 250px; display: block;"
                                                    src="/images/product/${newProduct.image}" alt="image preview"
                                                    id="imagePreview">
                                            </div>
                                            <div class="col-12 mb-3">
                                                <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                    id="avatarPreview">
                                            </div>
                                            <button type="submit" class="btn btn-primary">Submit</button>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <!-- footer.jsp  -->
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
            </body>

            </html>