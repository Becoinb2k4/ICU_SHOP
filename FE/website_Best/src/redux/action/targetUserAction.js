import { Fetch_Posts_TargetUser_Request, Fetch_Posts_TargetUser_Success, Fetch_Posts_TargetUser_Error } from '../types/targetUserTypes';
import { findTargetUser, findByName, postCreateNewTargetUser, updateStatusTargetUser, findTargetUserActive } from '../../Service/ApiTargetUserService';
import { toast } from 'react-toastify';

export const fetchAllTargetUserActive = () => {
    return async (dispatch, getState) => {
        dispatch(fetchPostsTargetUserRequest());
        try {
            const response = await findTargetUserActive();
            if (response.status === 200) {
                const data = response.data;
                dispatch(fetchPostsTargetUserSuccess(data))
            } else {
                toast.error('Error')
                dispatch(fetchPostsTargetUserError);
            }
        } catch (error) {
            dispatch(fetchPostsTargetUserError())
        }

    }
}
export const fetchAllTargetUser = () => {
    return async (dispatch, getState) => {
        dispatch(fetchPostsTargetUserRequest());
        try {
            const response = await findTargetUser();
            if (response.status === 200) {
                const data = response.data;
                dispatch(fetchPostsTargetUserSuccess(data))
            } else {
                toast.error('Error')
                dispatch(fetchPostsTargetUserError);
            }
        } catch (error) {
            dispatch(fetchPostsTargetUserError())
        }

    }
}
export const fetchSearchTargetUser = (searchName) => {
    return async (dispatch, getState) => {
        dispatch(fetchPostsTargetUserRequest());
        try {
            const response = await findByName(searchName);
            if (response.status === 200) {
                const data = response.data;
                dispatch(fetchPostsTargetUserSuccess(data))
            } else {
                toast.error('Error')
                dispatch(fetchPostsTargetUserError);
            }
        } catch (error) {
            dispatch(fetchPostsTargetUserError())
        }

    }
}
export const createNewTargetUser = (createTargetUser) => {
    return async (dispatch) => {
        try {
            //Đếm thời gian loading
            const response = await postCreateNewTargetUser(createTargetUser);
            if (response.status === 200) {
                dispatch(fetchAllTargetUser());
                toast.success("Thêm loại đối tượng sử dụng thành công!");
            }
        } catch (error) {
            console.error("Lỗi khi thêm loại đối tượng sử dụng:", error);

            if (error.response) {
                const statusCode = error.response.status;
                const errorData = error.response.data;

                if (statusCode === 400) {
                    // Xử lý lỗi validation (400 Bad Request)
                    if (Array.isArray(errorData)) {
                        errorData.forEach(err => {
                            toast.error(err); // Hiển thị từng lỗi trong mảng
                        });
                    } else {
                        toast.error("Đã xảy ra lỗi xác thực. Vui lòng kiểm tra lại.");
                    }
                } else if (statusCode === 409) {
                    const { mess } = errorData;
                    toast.error(mess);
                } else {
                    // Xử lý các lỗi khác
                    toast.error("Lỗi hệ thống. Vui lòng thử lại sau.");
                }
            } else if (error.request) {
                // Lỗi do không nhận được phản hồi từ server
                toast.error("Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng.");
            } else {
                // Lỗi khác (cấu hình, v.v.)
                toast.error("Đã xảy ra lỗi. Vui lòng thử lại sau.");
            }
            dispatch(fetchPostsTargetUserError());
        }
    };
};
export const updateStatusTargetUserById = (idSize, aBoolean) => {
    return async (dispatch) => {
        try {
            const response = await updateStatusTargetUser(idSize, aBoolean);
            if (response.status === 200) {
                dispatch(fetchAllTargetUser());
                toast.success("Cập nhật trạng loại đối tượng sử dụng thành công!");
            }
        } catch (error) {
            console.error("Lỗi khi cập nhật loại đối tượng sử dụng:", error);

            if (error.response) {
                const statusCode = error.response.status;
                const errorData = error.response.data;

                if (statusCode === 400) {
                    // Xử lý lỗi validation (400 Bad Request)
                    if (Array.isArray(errorData)) {
                        errorData.forEach(err => {
                            toast.error(err); // Hiển thị từng lỗi trong mảng
                        });
                    } else {
                        toast.error("Đã xảy ra lỗi xác thực. Vui lòng kiểm tra lại.");
                    }
                } else if (statusCode === 409) {
                    const { mess } = errorData;
                    toast.error(mess);
                } else {
                    // Xử lý các lỗi khác
                    toast.error("Lỗi hệ thống. Vui lòng thử lại sau.");
                }
            } else if (error.request) {
                // Lỗi do không nhận được phản hồi từ server
                toast.error("Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng.");
            } else {
                // Lỗi khác (cấu hình, v.v.)
                toast.error("Đã xảy ra lỗi. Vui lòng thử lại sau.");
            }

            dispatch(fetchPostsTargetUserError());
        }
    };
};
export const fetchPostsTargetUserRequest = () => {
    return {
        type: Fetch_Posts_TargetUser_Request
    }
}
export const fetchPostsTargetUserSuccess = (payload) => {
    return {
        type: Fetch_Posts_TargetUser_Success,
        payload
    }
}

export const fetchPostsTargetUserError = () => {
    return {
        type: Fetch_Posts_TargetUser_Error
    }
}