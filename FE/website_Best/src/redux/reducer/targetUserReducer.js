import { Fetch_Posts_TargetUser_Request, Fetch_Posts_TargetUser_Success, Fetch_Posts_TargetUser_Error }from '../types/targetUserTypes';

const INITIAL_STATE = {
    listTargetUser: [],
};

const counterReducer = (state = INITIAL_STATE, action) => {

    switch (action.type) {
        case Fetch_Posts_TargetUser_Request:
            return {
                ...state,
            };
        case Fetch_Posts_TargetUser_Success:
            return {
                ...state, listTargetUser: action.payload,

            };
        case Fetch_Posts_TargetUser_Error:
            return {
                ...state,

            };
        default: return state;

    }

};

export default counterReducer;