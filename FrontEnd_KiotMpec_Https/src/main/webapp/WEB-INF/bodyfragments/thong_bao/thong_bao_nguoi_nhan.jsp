<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- Gửi thông báo css -->
<link rel="stylesheet" href="resources/dist/css/guithongbao.css">
<%--<script src="./dist/js/guithogbao.js"></script>--%>

<!-- Main content -->
<style>

    #items {
        width: 37em;
        height: 31em;
        line-height: 2em;
        border: 1px solid #ccc;
        padding: 0;
        margin: 0;
        overflow: scroll;
        overflow-x: hidden;
    }



</style>
<section class="content" id="pageBody">
    <div class="sennoti">
        <div class="senot-title">
            <span class="page-title">Gửi thông báo</span>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12 col-md-5">
            <div class="choosecom">
                <div class="chocom-title">
                    <span>Gửi đến</span>
                </div>
                <ul class="list-group list-name list-group-flush dropdown" id = "items">

                </ul>
            </div>
        </div>
        <div class="col-sm-12 col-md-7">
            <div class="senot-form">
                <div class="snf-item form-group">
                    <label>Tiêu đề:</label>
                    <input type="text" id="tieu-de">
                    <span class="help-block">Help block with success</span>
                </div>
                <div class="snf-item form-group">
                    <label>Nội dung:</label>
                    <textarea id="noi-dung"></textarea>
                    <span class="help-block">Help block with success</span>
                </div>
                <form class="snf-item inpf" id="form-file">
                    <label>Tài liệu kèm theo:</label>
                    <input type="file" id="file-dinh-kem" name="files" multiple>
                </form>
                <div class="snf-item lit">
                    <label>Yêu cầu có phản hồi</label>
                    <div class="mtinfi ">
                        <input type="radio" name="request" value="1" id="request">
                        <label> Có yêu cầu có phản hồi</label>
                    </div>
                </div>
                <button class="btn btn-primary" id="btn-gui-thong-bao"><i class="fa fa-share"></i> Gửi thông báo</button>
            </div>
        </div>
    </div>
</section>
<!-- Main js -->
<!-- /.content -->
<script src="resources/dist/js/utils/loadingOverlay.js"></script>
<script src="resources/model/upload_file/ajax_upload_file.js"></script>
<script src="resources/model/thong_bao/ajax_thong_bao_nguoi_nhan.js"></script>
<script src="resources/pages/thong_bao/ajax_thong_bao_nguoi_nhan.js"></script>