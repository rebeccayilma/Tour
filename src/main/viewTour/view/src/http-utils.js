export const BASE_URL = 'http://localhost:8080/';

export const PLACE_URL = 'api/place';
export const ACTIVITY_URL = 'api/activity';
export const RATING_URL = 'api/rating';
export const REGISTER_URL = 'user/new';
export const LOGIN_URL = 'login';

export const ACTIVE_ACTIVITY_URL = placeId => {return ACTIVITY_URL + '/' +  placeId + '/active';};
export const INACTIVE_ACTIVITY_URL = ACTIVITY_URL + '/inactive';

export const APPROVE_URL = (activityId) => {return ACTIVITY_URL + '/approve/' + activityId;}

export const DEACTIVATE_URL = (activityId) => {return ACTIVITY_URL + '/deactivate/' + activityId;}

export const COOKIES_URL = 'localhost';

export const PLACEHOLDER_IMG_URL = "https://images.pexels.com/photos/992763/pexels-photo-992763.jpeg";

