import React from 'react';
import './about.scss';

const About = () => {
    return (
        <div className="about-container">
            {/* Section 1: Title and Introduction */}
            <h1>BÁN áo BeTS CHÍNH HÃNG TẠI HÀ NỘI - BeTS GIỚI THIỆU</h1>
            <p>
                Nỗi sợ vì mua phải áo kém chất lượng, áo fake, từ nay không còn lo lắng nữa vì đã có{' '}
                <span className="highlight">#BeTS.VN</span>: hàng chính hãng nhập trực tiếp từ US, fullbox, nguyên tem.
            </p>
            <ul className="list-items">
                <li>👑 <strong>BeTS.VN</strong>: 15 Ngày Đổi Hàng / Giao Hàng Miễn Phí / Thanh Toán Khi Nhận Hàng / Bảo Hành Hàng Chính Hãng!!!</li>
            </ul>
            <p>
                Đến với <span className="highlight">"BeTS.VN"</span> quý khách hàng sẽ có những sản phẩm ưng ý nhất, chất lượng phục vụ tốt và giá thành tốt nhất, cùng những
                <strong> “Chương Trình Khuyến Mãi Đặc Biệt”.</strong>
            </p>

            {/* Section 2: Video Introduction */}
            {/* <h2>Video giới thiệu cửa hàng BeTS.VN</h2> */}

            <div className="image-container">
                <img
                    src="https://i.pinimg.com/736x/b8/80/c0/b880c045a2fd6d469d59fd5df651ace7.jpg"
                    alt="King Shoes Store"
                />
            </div>




            <p>
                ⚡ <strong>BeTS.VN</strong>: 15 Ngày Đổi Hàng / Giao Hàng Miễn Phí / Thanh Toán Khi Nhận Hàng / Bảo Hành Hàng Chính Hãng Trọn Đời!!!
            </p>
            <div className="image-container">
                <img
                    src="https://i.pinimg.com/736x/42/c5/a4/42c5a4af15c103cc76a92e25371c3aa5.jpg"
                    alt="King Shoes Store"
                />
            </div>
            <p>
                👑✨ <span className="highlight">BeTS.VN "You're King In Your Way"</span>!!! 👟💼🌟
            </p>
            <p>

            </p>
            <p>
                Đến với "BeTS.VN" quý khách hàng sẽ có những sản phẩm ưng ý nhất, chất lượng phục vụ tốt và giá thành tốt nhất, cùng những <strong>Chương Trình Khuyến Mãi Đặc Biệt.</strong>
            </p>
        </div>
    );
};

export default About;
