import React from 'react';
import { PLACEHOLDER_IMG_URL } from '../http-utils';
import { NewRatingButton, DeactivateButton, SeeRatingsButton } from './buttons/ActivityButtons';

export function Activity (props) {
  const activity = props.activity;
  const isAdmin = props.roles.some(role => role === 'ADMIN');
  const isContributor = props.roles.some(role => role === 'CONTRIBUTOR');
  const seeRatings = props.func.seeRatings;
  const rate = props.func.rate;
  const deactivate = props.func.deactivate;

  return(
    <div>
      <div>
        <img className ="mx-20"
          src={activity.image ? activity.image.path: PLACEHOLDER_IMG_URL}
          alt="Activity"
          onClick={() => seeRatings(activity)}
        />
        <h2 className ="mx-20 text-gray-800 leading-relaxed mb-6" onClick={() => seeRatings(activity)}>Activity {activity.activity_id}</h2>
      </div>
      <div>
        <p className ="mx-20 text-gray-800 leading-relaxed mb-6">{activity.info}</p>
      </div>
      <hr/>
      {isContributor && (<NewRatingButton rate={() => rate(activity)}/>)}
      {isAdmin && (<DeactivateButton deactivate={() => deactivate(activity)}/>)}
      <SeeRatingsButton seeRatings={() => seeRatings(activity)}/>
    </div>
  );

}
