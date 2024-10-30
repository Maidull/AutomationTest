# main.py
import pytest

def main():
    print("Chọn loại kiểm tra bạn muốn chạy:")
    print("1. Kiểm tra Báo cáo Tùy chỉnh")
    print("2. Kiểm tra Đăng xuất")

    choice = input("Nhập số tương ứng (1 hoặc 2): ")

    if choice == '1':
        pytest.main(["-v", "-s", "tests/test_customized_statement.py::TestCustomizedStatement"])  # Thay thế bằng tên tệp của
    elif choice == '2':
        pytest.main(["-v", "-s", "tests/test_logout.py::TestLogout"])  # Thay thế bằng tên tệp của
    else:
        print("Lựa chọn không hợp lệ. Vui lòng nhập 1 hoặc 2.")

if __name__ == "__main__":
    main()
