import authorizeAxiosInstance from '../hooks/authorizeAxiosInstance';

const postCreateNewTargetUser = async (newTargetUser) => {
    return await authorizeAxiosInstance.post('/targetUser/create-targetUser', newTargetUser);
};
const findTargetUserActive = async () => {
    return await authorizeAxiosInstance.get('/targetUser/list-targetUserActive')
};
const findTargetUser = async () => {
    return await authorizeAxiosInstance.get('/targetUser/list-targetUser')
};
const findByName = async (searchName) => {
    return await authorizeAxiosInstance.get(`/targetUser/list-targetUser-search?search=${searchName}`)
};
const updateStatusTargetUser = (idTargetUser, aBoolean) => {
    return authorizeAxiosInstance.put(`/targetUser/update-status?id=${idTargetUser}&aBoolean=${aBoolean}`);
};

export { findTargetUser, updateStatusTargetUser, postCreateNewTargetUser, findByName, findTargetUserActive };