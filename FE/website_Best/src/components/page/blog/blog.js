import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './blog.scss';

const Blog = () => {
    const [isModalOpen, setModalOpen] = useState(false);
    const [selectedFAQ, setSelectedFAQ] = useState(null);
    const [isSidebarOpen, setSidebarOpen] = useState(false);

    const faqs = [
        {
            question: 'LÀM THẾ NÀO ĐỂ SỬ DỤNG MÃ PHIẾU GIẢM GIÁ?',
            answer:
                'Nhấp vào nút Thanh toán ở đầu trang để đến giỏ hàng của bạn. Trong phần tóm tắt đơn hàng, nhấp vào "Nhập mã khuyến mãi", sau đó nhập mã của phiếu giảm giá vào hộp và nhấp "Áp dụng".',
        },
        {
            question: 'TẠI SAO PHIẾU GIẢM GIÁ CỦA TÔI KHÔNG SỬ DỤNG ĐƯỢC?',
            answer:
                'Thẻ giảm giá dành cho cửa hàng trực tuyến chỉ có giá trị sử dụng đối với các sản phẩm nằm trong danh mục của chương trình khuyến mãi hoặc chiến dịch, vì vậy hãy kiểm tra xem chương trình khuyến mãi liên quan có còn thời hạn hay không. Ngoài ra, phiếu giảm giá không có giá trị kết hợp với các chương trình khuyến mãi khác và bạn không thể sử dụng phiếu giảm giá dành cho cửa hàng bán lẻ tại cửa hàng trực tuyến.',
        },
    ];

    const openModal = (faq) => {
        setSelectedFAQ(faq);
        setModalOpen(true);
    };

    const closeModal = () => {
        setModalOpen(false);
        setSelectedFAQ(null);
    };

    const toggleSidebar = () => {
        setSidebarOpen(!isSidebarOpen);
    };

    return (
        <div className="container">
            {/* Help Section */}
            <div className="row pt-5">
                <div className="menu p-5">
                    <div className="help-box">
                        <div className="content">
                            <h3>Nhận trợ giúp về đơn hàng của bạn</h3>
                            <p>
                                Đăng nhập để xem các đơn hàng gần đây, theo dõi trạng thái giao hàng hoặc sắp xếp việc đổi hoặc trả hàng.
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            {/* FAQ Section */}
            <div className="row faq-section">
                <h3 className="faq-title">CÂU HỎI THƯỜNG GẶP</h3>
                <div className="faq-grid">
                    {faqs.map((faq, index) => (
                        <div
                            className="faq-item"
                            key={index}
                            onClick={() => openModal(faq)}
                        >
                            <h4>{faq.question}</h4>
                            <p>Khuyến Mãi Và Phiếu Giảm</p>
                        </div>
                    ))}
                </div>
            </div>

            {/* Modal */}
            {isModalOpen && (
                <div className="modal-overlay p-5" onClick={closeModal}>
                    <div
                        className="modal-content"
                        onClick={(e) => e.stopPropagation()}
                    >
                        <button className="close-btn" onClick={closeModal}>
                            ×
                        </button>
                        <h3>{selectedFAQ?.question}</h3>
                        <p>{selectedFAQ?.answer}</p>
                    </div>
                </div>
            )}



            {/* Order Tracker */}
            <div className="order-tracker">
                <div className="tracker-content">
                    <h4>ĐƠN HÀNG CỦA TÔI Ở ĐÂU?</h4>
                    <p>
                        Đăng nhập hoặc nhập chi tiết đơn hàng của bạn vào trình theo dõi để xem thông tin cụ thể về đơn hàng của bạn.
                    </p>
                </div>
                <Link to="/profile" className="tracker-btn">
                    Theo dõi đơn hàng
                </Link>
            </div>

            {/* Customer Service */}
            <div className="row pb-5">
                <div className="customer-service">
                    <div className="service-content">
                        <h4>STILL CAN'T FIND YOUR ANSWER?</h4>
                        <p>
                            <strong>DỊCH VỤ KHÁCH HÀNG</strong>
                            <br />
                            <strong>Facebook:</strong> Thứ Hai đến Thứ Bảy, từ 9 giờ sáng đến 9 giờ tối.
                            <br />
                            <strong>ĐIỆN THOẠI:</strong> +84 888 888 8888
                        </p>
                    </div>
                    <Link to="/contact" className="contact-btn">
                        Liên hệ chúng tôi
                    </Link>
                </div>
            </div>
        </div>
    );
};

export default Blog;
