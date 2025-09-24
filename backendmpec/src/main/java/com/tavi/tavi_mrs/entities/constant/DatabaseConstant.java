package com.tavi.tavi_mrs.entities.constant;

public class DatabaseConstant {

    public static class URL{
        private URL(){}

        public static final String AWS_S3 = "https://mpec.s3.us-east-2.amazonaws.com/";
    }



    public static class Directory{

        private Directory(){

        }

        public static final String URL_DIRECTORY = System.getProperty("catalina.base") + "/webapps/resources/micro-upload/";
        public static final String FONT_DIRECTORY = System.getProperty("catalina.base") + "/webapps/resources/fonts/";
    }

    private DatabaseConstant(){

    }

    public static class Table{

        private Table(){
        }

        public static class Roles{
            private Roles(){
            }

            public static final String ADMIN = "Admin";
            public static final String MANAGER = "Manager";
            public static final String EMPLOYEE = "Employee";
            public static final String CUSTOMER = "Customer";

        }

        public static class PermissionGroup{
            private PermissionGroup(){
            }

            public static final String HE_THONG = "Hệ thống";
            public static final String HANG_HOA = "Hàng hóa";
            public static final String GIAO_DICH = "Giao dịch";
            public static final String NHAN_VIEN = "Nhân viên";
            public static final String DOI_TAC = "Đối tác";
            public static final String BAO_CAO = "Báo cáo";
        }

        public static class Permissions{
            private Permissions(){
            }
        }
    }

    public static class TypeOfFile {
        private TypeOfFile() {
        }
        public static final Integer EXCEL_FILE = 1;
        public static final Integer PDF_FILE = 2;
        public static final Integer WORD_FILE = 3;
        public static final Integer IMAGE_FILE = 4;

    }
   public static class File{
       private File(){
       }

       public static class Excel{

           private Excel(){
           }
           public static final String NGUOI_DUNG_CA_LAM_VIEC_EXCEL_FILE = "Danh sách người dùng ca làm việc";
           public static final String CHI_NHANH_HANG_HOA_EXCEL_FILE = "Danh sách hàng hóa";
           public static final String PHIEU_THU_EXCEL_FILE = "Danh sách phiếu thu";
           public static final String PHIEU_CHI_EXCEL_FILE = "Danh sách phiếu chi";
           public static final String HOA_DON_EXCEL_FILE = "Danh sách hóa đơn";
           public static final String PHIEU_NHAP_EXCEL_FILE = "Danh sách phiếu nhập";
           public static final String KHACH_HANG_EXCEL_FILE = "Danh sách khách hàng";
           public static final String NHAN_VIEN_EXCEL_FILE = "Danh sách nhân viên";
           public static final String NHA_CUNG_CAP_EXCEL_FILE = "Danh sách nhà cung cấp";
           public static final String BANG_LUONG_EXCEL_FILE = "Danh sách bảng lương";
       }

       public static class PDF{
           private PDF(){

           }

           public static final String HOA_DON_PDF_FILE = "Hóa đơn";
           public static final String PHIEU_NHAP_PDF_FILE = "Phiếu nhập";
           public static final String PHIEU_TRA_KHACH_PDF_FILE = "Phiếu trả khách";
           public static final String PHIEU_TRA_NHA_CUNG_CAP_PDF_FILE = "Phiếu trả nhà cung cấp";
       }


   }

    public static class Host{
        private Host(){

        }

        public static final String URL_LOCAL_BACKEND = "http://localhost:8181/";
        public static final String URL_DEPLOY_BACKEND = "http://lab.kiotmpecbk.cloud:8080/";
        public static final String URL_LOCAL_FRONTEND = "http://localhost:8282/";
        public static final String URL_DEPLOY_FRONTEND = "http://lab.kiotmpecbk.cloud:8080/";
    }

    public static class DateTime{
        private DateTime(){

        }

        public static final String DATE_FORMAT_1 = "dd/MM/yyyy";
        public static final String DATE_FORMAT_2 = "MM/dd/yyyy";

        public static final String DATE_TIME_FORMAT_1 = "dd/MM/yyyy hh:mm:ss";
        public static final String DATE_TIME_FORMAT_2 = "MM/dd/yyyy hh:mm:ss";
    }
}
