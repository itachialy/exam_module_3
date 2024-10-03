
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm Mới Mặt Bằng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Thêm Mới Mặt Bằng</h2>
    <form action="?action=create" method="post">
        <div class="mb-3">
            <label for="maMb" class="form-label">Mã MB</label>
            <input type="text" class="form-control" id="maMb" name="maMb" required>
        </div>
        <div class="mb-3">
            <label for="dienTich" class="form-label">Diện Tích</label>
            <input type="number" class="form-control" id="dienTich" name="dienTich" required>
        </div>
        <div class="mb-3">
            <label for="trThai" class="form-label">Trạng Thái</label>
            <select class="form-control" id="trThai" name="trThai" required>
                <option value="1">Trống</option>
                <option value="2">Hạ Tầng</option>
                <option value="3">Đầy Đủ</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="tang" class="form-label">Tầng</label>
            <select class="form-control" id="tang" name="tang" required>
                <c:forEach var="i" begin="1" end="15">
                    <option value="${i}">${i}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-3">
            <label for="loaiVp" class="form-label">Loại Văn Phòng</label>
            <select class="form-control" id="loaiVp" name="loaiVp" required>
                <option value="1">Cho Thuê</option>
                <option value="2">Trọn Gói</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="giaThue" class="form-label">Giá Cho Thuê</label>
            <input type="number" class="form-control" id="giaThue" name="giaThue" required>
        </div>
        <div class="mb-3">
            <label for="startDate" class="form-label">Ngày Bắt Đầu</label>
            <input type="date" class="form-control" id="startDate" name="startDate" required>
        </div>
        <div class="mb-3">
            <label for="endDate" class="form-label">Ngày Kết Thúc</label>
            <input type="date" class="form-control" id="endDate" name="endDate" required>
        </div>
        <button type="submit" class="btn btn-primary">Lưu</button>
        <a href="list.jsp" class="btn btn-secondary">Hủy</a>
    </form>
</div>
<%-- Modal for email validation error --%>
<div class="modal fade" id="emailErrorModal" tabindex="-1" aria-labelledby="emailErrorModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="emailErrorModalLabel">Lỗi</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ${errorMessage}
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

<script>
    <% if (request.getAttribute("errorMessage") != null) { %>
    let emailErrorModal = new bootstrap.Modal(document.getElementById('emailErrorModal'));
    emailErrorModal.show();
    <% } %>
</script>
</body>
</html>
