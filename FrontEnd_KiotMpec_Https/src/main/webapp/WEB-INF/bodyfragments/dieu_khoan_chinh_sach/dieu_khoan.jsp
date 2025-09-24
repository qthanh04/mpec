<%--
  Created by IntelliJ IDEA.
  User: QUY
  Date: 20/04/2020
  Time: 2:48 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="content">
    <!-- Content Header (Page header) -->
    <div class="content">
        <div class="container">

                  <div id="sample">
                  <script type="text/javascript" src="http://js.nicedit.com/nicEdit-latest.js"></script> <script type="text/javascript">
                   //<![CDATA[
                 bkLib.onDomLoaded(function() { nicEditors.allTextAreas() });
                   //]]>
                </script>
                 <br />
                 <h4>
                  Textarea
             </h4>
            <textarea id="noiDung" name="area2" style="width: 100%;">
             Some Initial Content was in this textarea
              </textarea><br />
               </div>
            </div>

    </div>

    <style>
        .intro {
          min-height: 100px !important;
        }
    </style>

    <div class="card-body">
        <div class="row text-center">
        <button type="button" class="btn btn-secondary m-1" id="btn-cap-nhap">Cập nhập</button>
        </div>
    </div>

</div>
<script src="resources/pages/chinh_sach_dieu_khoan/ajax_sua_dieu_khoan.js"></script>