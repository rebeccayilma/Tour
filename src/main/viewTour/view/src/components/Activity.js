import React from 'react';
import { PLACEHOLDER_IMG_URL } from '../http-utils';
import { NewRatingButton, DeactivateButton } from './buttons/ActivityButtons';

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
        <img
          src={activity.image ? activity.image.path: PLACEHOLDER_IMG_URL}
          alt="Activity"
          onClick={() => seeRatings(activity)}
        />
        <h2 onClick={() => seeRatings(activity)}>Activity {activity.id}</h2>
      </div>
      <div>
        <p>{activity.info}</p>
      </div>
      <hr/>
      {isContributor && (<NewRatingButton rate={() => rate(activity)}/>)}
      {isAdmin && (<DeactivateButton deactivate={() => deactivate(activity)}/>)}
    </div>
  );

}
