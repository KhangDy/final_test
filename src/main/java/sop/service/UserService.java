package sop.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sop.model.User;

@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void saveUser(User user) {
        String sql = "INSERT INTO tbl_user (_name, _uid, _email, _pwd, _usr_type, _phone, _address, _created_at, _status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getUid(), user.getEmail(), user.getPwd(), user.getUsrType(), user.getPhone(), user.getAddress(), user.getCreatedAt(), user.getStatus());
    }
    
    public User register(User user) {
        // Mã hóa mật khẩu
        user.setPwd(new BCryptPasswordEncoder().encode(user.getPwd()));
        
        // Thiết lập các giá trị mặc định
        user.setUsrType(0); // Mặc định là user
        user.setStatus(0); // Mặc định trạng thái là không hoạt động
        user.setCreatedAt(LocalDateTime.now()); // Ngày tạo là thời điểm hiện tại
        
        // Lưu vào cơ sở dữ liệu
        saveUser(user);
        return user; // Trả về user đã đăng ký
    }
    

    public User findFirstByUid(String uid) {
        String sql = "SELECT * FROM tbl_user WHERE _uid = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{uid}, new BeanPropertyRowMapper<>(User.class));
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
    }
    
    public User login(String uid, String password) {
        // Truy vấn người dùng từ cơ sở dữ liệu qua uid
        String sql = "SELECT * FROM tbl_user WHERE _uid = :uid";
        
        // Tạo tham số cho câu truy vấn
        Map<String, Object> params = new HashMap<>();
        params.put("uid", uid);

        // Thực hiện truy vấn và map kết quả vào đối tượng User
        List<User> users = namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("_id"));
            user.setName(rs.getString("_name"));
            user.setUid(rs.getString("_uid"));
            user.setEmail(rs.getString("_email"));
            user.setPwd(rs.getString("_pwd"));
            user.setUsrType(rs.getInt("_usr_type"));
            user.setPhone(rs.getString("_phone"));
            user.setAddress(rs.getString("_address"));
            user.setCreatedAt(rs.getTimestamp("_created_at").toLocalDateTime());
            user.setStatus(rs.getInt("_status"));
            return user;
        });

        // Nếu không tìm thấy user nào, ném lỗi
        if (users.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        // Lấy user đầu tiên (kết quả mong đợi là chỉ có một user với uid duy nhất)
        User user = users.get(0);

        // So sánh mật khẩu nhập vào với mật khẩu đã mã hóa trong cơ sở dữ liệu
        if (new BCryptPasswordEncoder().matches(password, user.getPwd())) {
            return user; // Trả về user nếu đăng nhập thành công
        } else {
            throw new RuntimeException("Invalid password");
        }
    }

}
