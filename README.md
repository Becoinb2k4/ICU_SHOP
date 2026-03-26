# Hướng dẫn test project ICU (đầy đủ)

## 1. Mục tiêu tài liệu
Tài liệu này hướng dẫn bạn test toàn bộ project ICU gồm:
- Backend Spring Boot (`BE/BE_Website_BeST_JAVA`)
- Frontend React (`FE/website_Best`)
- Các luồng nghiệp vụ chính: đăng nhập, sản phẩm, giỏ hàng, thanh toán, quản trị.

---

# account: tai khoan admin cung co the dat hang
    Tài khoản admin: Admin@gmail.com
    Mật khẩu admin: 1

    Tài Khoản user: customer@gmail.com
    Mật khẩu user: 1

    Tài Khoản nhân viên: employee@gmail.com
    Mật khẩu nhân viên: 1
    

## 2. Yêu cầu môi trường

## 2.1. Phần mềm cần cài
- `JDK 17`
- `Maven 3.8+` (hoặc dùng `mvnw`)
- `Node.js 18+` và `npm`
- `MySQL 8.x`
- Trình duyệt Chrome/Edge mới

## 2.2. Cổng mặc định
- Backend: `http://localhost:8080`
- Frontend: `http://localhost:3000`
- Base URL FE gọi API: `http://localhost:8080/api/v1` (đang cấu hình trong `src/hooks/authorizeAxiosInstance.js`)

---

## 3. Chuẩn bị cơ sở dữ liệu

File cấu hình hiện tại ở:
`BE/BE_Website_BeST_JAVA/src/main/resources/application.properties`

Thông số mặc định:
- DB URL: `jdbc:mysql://localhost:3306/db_best?createDatabaseIfNotExist=true`
- User: `root`
- Password: `123456`
- Hibernate: `spring.jpa.hibernate.ddl-auto=update`

Lưu ý:
- Nếu máy bạn khác mật khẩu MySQL, hãy sửa lại `spring.datasource.password`.
- Nên đổi thông tin nhạy cảm (DB, mail, jwt secret) sang biến môi trường khi deploy thật.

---

## 4. Chạy Backend

Mở terminal tại `D:\ICU\Online\BE\BE_Website_BeST_JAVA`:

```bash
mvn clean test
mvn spring-boot:run
```

Hoặc trên Windows:

```bash
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

Kết quả mong đợi:
- Backend chạy thành công trên cổng `8080`.
- Không lỗi kết nối MySQL.

---

## 5. Chạy Frontend

Mở terminal tại `D:\ICU\Online\FE\website_Best`:

```bash
npm install
npm test -- --watchAll=false
npm start
```

Kết quả mong đợi:
- FE chạy tại `http://localhost:3000`.
- Truy cập được trang chủ.

---

## 6. Danh sách test nhanh (smoke test)

## 6.1. Public site
1. Mở trang chủ.
2. Vào trang sản phẩm (`/allProducts`).
3. Xem chi tiết sản phẩm.
4. Thêm vào giỏ hàng.
5. Vào trang giỏ hàng (`/cart`).

## 6.2. Xác thực
1. Đăng ký tài khoản mới.
2. Đăng nhập bằng tài khoản vừa tạo.
3. Đăng xuất.
4. Đăng nhập lại để kiểm tra token.

## 6.3. Checkout
1. Chọn sản phẩm trong giỏ.
2. Nhập thông tin nhận hàng.
3. Áp voucher hợp lệ.
4. Đặt hàng.
5. Kiểm tra đơn trong hồ sơ cá nhân.

## 6.4. Khu quản trị (`/admins`)
1. Đăng nhập tài khoản `ADMIN` hoặc `EMPLOYEE`.
2. Vào quản lý hóa đơn.
3. Vào bán hàng tại quầy (bill by employee).
4. Kiểm tra quản lý sản phẩm.
5. Kiểm tra voucher/promotion (ADMIN).

---

## 7. Test nghiệp vụ chi tiết theo module

## 7.1. Tài khoản và phân quyền
- Kiểm tra tài khoản `CUSTOMER` không truy cập được `/admins`.
- Kiểm tra `ADMIN` truy cập đủ menu.
- Kiểm tra `EMPLOYEE` chỉ thấy các menu được phép.
- Khóa tài khoản và thử đăng nhập lại.

## 7.2. Sản phẩm và tồn kho
- Tạo sản phẩm + biến thể (`ProductDetail`).
- Cập nhật trạng thái sản phẩm (`ACTIVE/INACTIVE`).
- Đặt hàng với số lượng lớn hơn tồn kho để kiểm tra chặn lỗi.

## 7.3. Voucher
- Tạo voucher công khai và áp dụng khi checkout.
- Tạo voucher riêng tư và chỉ áp dụng cho account được gán.
- Dùng voucher đến hết số lượng -> kiểm tra chuyển trạng thái.

## 7.4. Promotion
- Tạo đợt giảm giá có thời gian.
- Gán sản phẩm vào promotion detail.
- Kiểm tra giá hiển thị và giá tính đơn.

## 7.5. Hóa đơn
- Tạo đơn mới.
- Chuyển trạng thái: `PENDING -> CONFIRMED -> WAITTING_FOR_SHIPPED -> SHIPPED -> COMPLETED`.
- Hủy đơn và kiểm tra hoàn tồn kho.

---

## 8. Test API bằng Postman (khuyến nghị)

Tạo collection `ICU API Test` với biến:
- `baseUrl = http://localhost:8080/api/v1`
- `token = ...` (sau khi login)

API nên test:
- `POST /auth/login`
- `GET /account/get-account-login`
- `GET /product/listProduct`
- `POST /cart-detail/add-product-to-cart/{idUser}`
- `POST /billByEmployee/payBillOnlinev2`
- `GET /bill/list-bills`
- `POST /voucher/create`
- `POST /promotion/createPromotion`

Header cần có sau login:
- `Authorization: Bearer <token>`

---

## 9. Tiêu chí pass/fail

Pass khi:
- FE và BE khởi động ổn định.
- API chính trả đúng mã trạng thái.
- Luồng mua hàng hoàn tất được.
- Phân quyền đúng vai trò.
- Không có lỗi nghiêm trọng trong console/log.

Fail khi:
- Không kết nối được DB.
- FE gọi API sai base URL/cổng.
- Lỗi 401/403 không đúng ngữ cảnh.
- Sai tính toán tiền hoặc sai trạng thái đơn.

---

## 10. Lỗi thường gặp và cách xử lý

## 10.1. FE không gọi được BE
- Kiểm tra BE đã chạy cổng `8080`.
- Kiểm tra baseURL trong `authorizeAxiosInstance.js`.
- Kiểm tra firewall hoặc proxy local.

## 10.2. Lỗi MySQL Access denied
- Sai user/password DB trong `application.properties`.
- Tạo lại quyền user MySQL hoặc sửa mật khẩu đúng.

## 10.3. Lỗi cổng bị trùng
- Đổi cổng BE hoặc FE.
- Đóng tiến trình đang chiếm cổng.

## 10.4. Đăng nhập xong bị đá về login
- Token không lưu được vào `localStorage`.
- Token hết hạn hoặc backend trả 401.

---

## 11. Checklist trước khi demo/bảo vệ
- [ ] `mvn clean test` chạy thành công.
- [ ] `npm test -- --watchAll=false` chạy được.
- [ ] Chạy được cả FE và BE cùng lúc.
- [ ] Đăng nhập được cả `CUSTOMER` và `ADMIN/EMPLOYEE`.
- [ ] Hoàn tất được 1 đơn hàng online.
- [ ] Hoàn tất được 1 luồng bán hàng tại quầy.
- [ ] Demo được quản lý voucher và promotion.
- [ ] Chuẩn bị sẵn dữ liệu mẫu để trình bày.

---

## 12. Gợi ý cải thiện quy trình test
- Viết test case theo bảng (ID, bước test, dữ liệu vào, kết quả mong đợi).
- Tách môi trường `dev/test/prod`.
- Dùng profile Spring (`application-test.properties`) cho test tự động.
- Bổ sung test E2E (Cypress/Playwright) cho luồng checkout.

