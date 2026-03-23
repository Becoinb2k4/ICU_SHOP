import { useState, useEffect } from 'react';
import ModelCreateTargetUser from "./ModelCreateTargetUser";
import TableTargetUser from './TableTargetUser'
import { useDebounce } from 'use-debounce';
import { useDispatch } from 'react-redux';
import { fetchAllTargetUser, fetchSearchTargetUser } from '../../../../../redux/action/targetUserAction';
import AuthGuard from "../../../../auth/AuthGuard";
import RoleBasedGuard from "../../../../auth/RoleBasedGuard";
const ManageTargetUser = () => {
    const dispatch = useDispatch();
    const [searchName, setSearchName] = useState("");
    const [debouncedSearchName] = useDebounce(searchName, 1000); // Sử dụng useDebounce với delay 1000ms
    useEffect(() => {
        if (debouncedSearchName) {
            dispatch(fetchSearchTargetUser(debouncedSearchName));
        } else {
            dispatch(fetchAllTargetUser());
        }
    }, [debouncedSearchName, dispatch]);
    return (
        <AuthGuard>
            <RoleBasedGuard accessibleRoles={["ADMIN"]}>
                <div className="manage-cart-container">
                    <div className="accordion accordion-flush" id="accordionFlushExample">
                        <div className="accordion-item">
                            <h2 className="accordion-header">
                                <button className="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
                                    <h3>Quản lý chất liệu Đối tượng sử dụng</h3>
                                </button>
                            </h2>
                            <div id="flush-collapseOne" className="accordion-collapse collapse show" data-bs-parent="#accordionFlushExample">
                                <div className="accordion-body">
                                    <div className="cart-content">
                                        <div className='shoe-content-hender'>
                                            <label className="form-label">Tên chất liệu Đối tượng sử dụng:</label>
                                            <div className='shoe-search-add row'>
                                                <div className="shoe-search mb-3 col-9">
                                                    <input
                                                        type="text"
                                                        className="form-control"
                                                        id="nameShoe"
                                                        placeholder="Tìm kiếm loại đối tượng sử dụng theo tên...."
                                                        onChange={(event) => setSearchName(event.target.value)}
                                                    />
                                                </div>
                                                <div className='shoe-add mb-3 col-3'>
                                                    <ModelCreateTargetUser />
                                                </div>
                                                <div className='shoe-content-body mt-3'>
                                                    <TableTargetUser />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </RoleBasedGuard>
        </AuthGuard>
    )
}
export default ManageTargetUser;