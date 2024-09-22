package sop.model;

import java.time.LocalDateTime;

public class User {
    private Long id; // ID người dùng
    private String name; // Tên người dùng
    private String uid; // UID người dùng
    private String email; // Email người dùng
    private String pwd; // Mật khẩu người dùng
    private int usrType; // Loại người dùng (0: admin, 1: user)
    private String phone; // Số điện thoại
    private String address; // Địa chỉ
    private LocalDateTime createdAt; // Ngày tạo tài khoản
    private int status; // Trạng thái (1: hoạt động, 0: không hoạt động)

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getUsrType() {
        return usrType;
    }

    public void setUsrType(int usrType) {
        this.usrType = usrType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

