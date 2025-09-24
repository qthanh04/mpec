<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/nguyenducquyhust/BackEnd_KiotMpec">
    <img src="images/logoUSTH.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Best-README-Template</h3>

  <p align="center">
    An awesome README template to jumpstart your projects!
    <br />
    <a href="https://github.com/nguyenducquyhust/BackEnd_KiotMpec.git"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://www.kiotmpec.cloud/dang-nhap">View Demo</a>
    ·
    <a href="https://github.com/nguyenducquyhust/BackEnd_KiotMpec/issues">Report Bug</a>
    ·
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
# Hệ Thống Quản Lý Bán Lẻ MPEC - Backend

## Giới Thiệu Dự Án

Backend MPEC là một hệ thống quản lý bán lẻ hiện đại dựa trên nền tảng điện toán đám mây, được xây dựng để đáp ứng nhu cầu ngày càng tăng của thị trường bán lẻ Việt Nam. Với hơn 1.8 triệu cửa hàng bán lẻ vừa và nhỏ tại Việt Nam, trong đó hơn 80% chưa áp dụng công nghệ thông tin vào quản lý, hệ thống này cung cấp một giải pháp toàn diện cho việc quản lý kinh doanh bán lẻ.

### Tính Năng Chính

- **Xác Thực & Phân Quyền**
  - Xác thực dựa trên JWT
  - Xác thực email
  - Phân quyền theo vai trò

- **Tính Năng Cho Khách Hàng**
  - Tìm kiếm sản phẩm với nhiều tiêu chí
  - Quản lý giỏ hàng
  - Đặt hàng và theo dõi đơn hàng
  - Quản lý thông tin cá nhân và đổi mật khẩu
  - Xem lịch sử đơn hàng

- **Tính Năng Cho Admin**
  - Quản lý sản phẩm (Thêm, sửa, xóa, xem)
  - Quản lý người dùng
  - Quản lý và theo dõi đơn hàng
  - Cấu hình hệ thống

### Công Nghệ Sử Dụng

- **Framework Backend**
  - Spring Boot
  - Spring Security
  - Spring Data JPA
  - JUnit & Mockito cho kiểm thử

- **Cơ Sở Dữ Liệu**
  - MySQL

- **Hạ Tầng Đám Mây (AWS)**
  - EC2 cho máy chủ
  - S3 cho lưu trữ
  - RDS cho cơ sở dữ liệu
  - CloudFront cho CDN
  - Route 53 cho DNS
  - VPC cho mạng riêng ảo

- **Công Cụ DevOps**
  - Docker
  - Maven
  - Git

## Cấu Trúc Dự Án

```
src/
├── main/
    ├── java/
    │   └── com/tavi/tavi_mrs/
    │       ├── aspect/        # Xử lý các khía cạnh chung
    │       ├── config/        # Cấu hình hệ thống
    │       ├── controller/    # Xử lý các request
    │       ├── entities/      # Các entity
    │       ├── payload/       # Các đối tượng request/response
    │       ├── repository/    # Tương tác với database
    │       ├── security/      # Bảo mật
    │       ├── service/       # Interface các service
    │       ├── service_impl/  # Implement các service
    │       └── utils/         # Công cụ tiện ích
    └── resources/
        ├── static/           # Tài nguyên tĩnh
        ├── file_upload/      # Thư mục upload file
        ├── data/            # Dữ liệu
        └── application.properties  # Cấu hình ứng dụng
```

## Hướng Dẫn Cài Đặt

### Yêu Cầu Hệ Thống

- JDK 11 trở lên
- Maven 3.6+
- MySQL 8.0+
- Docker (tùy chọn)

### Các Bước Cài Đặt

1. Clone dự án
   ```sh
   git clone https://github.com/your-username/backendmpec.git
   ```

2. Cấu hình application properties
   ```
   src/main/resources/application.properties
   ```

3. Build dự án
   ```sh
   mvn clean install
   ```

4. Chạy ứng dụng
   ```sh
   mvn spring-boot:run
   ```

### Triển Khai Bằng Docker

1. Build Docker image
   ```sh
   docker build -t backendmpec .
   ```

2. Chạy container
   ```sh
   docker run -p 8080:8080 backendmpec
   ```

## Tài Liệu API

Tài liệu API được cung cấp thông qua Swagger UI tại:
```
http://localhost:8080/swagger-ui.html
```

## Đóng Góp

1. Fork dự án
2. Tạo nhánh tính năng (`git checkout -b feature/TinhNangMoi`)
3. Commit các thay đổi (`git commit -m 'Thêm tính năng mới'`)
4. Push lên nhánh (`git push origin feature/TinhNangMoi`)
5. Tạo Pull Request

## Giấy Phép

Phân phối dưới Giấy phép MIT. Xem `LICENSE` để biết thêm thông tin.

## Liên Hệ

Link dự án: [https://github.com/your-username/backendmpec](https://github.com/your-username/backendmpec)

## Cảm Ơn

- [Spring Framework](https://spring.io/)
- [AWS Services](https://aws.amazon.com/)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/)

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/your-username/backendmpec.svg?style=for-the-badge
[contributors-url]: https://github.com/your-username/backendmpec/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/your-username/backendmpec.svg?style=for-the-badge
[forks-url]: https://github.com/your-username/backendmpec/network/members
[stars-shield]: https://img.shields.io/github/stars/your-username/backendmpec.svg?style=for-the-badge
[stars-url]: https://github.com/your-username/backendmpec/stargazers
[issues-shield]: https://img.shields.io/github/issues/your-username/backendmpec.svg?style=for-the-badge
[issues-url]: https://github.com/your-username/backendmpec/issues
[license-shield]: https://img.shields.io/github/license/your-username/backendmpec.svg?style=for-the-badge
[license-url]: https://github.com/your-username/backendmpec/blob/master/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/your-linkedin-username


