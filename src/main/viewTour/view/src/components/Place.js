import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { ACTIVE_ACTIVITY_URL, PLACEHOLDER_IMG_URL } from '../http-utils';
import { NewActivityButton } from './buttons/PlaceButtons';

function Activities(props) {
  const [listActivities, setListActivities] = useState([]);
  const [loading, setLoading] = useState(true);

  const selectActivity = props.selectActivity;
  const placeId = props.placeId;

  useEffect(() => {
    axios.get(ACTIVE_ACTIVITY_URL(placeId)).then(res => {
      setListActivities(res.data.map((activity, _) => {
        return (
          <div key={activity.activity_id}>
            <h4 onClick={() => selectActivity(activity)}>Activity {activity.id}</h4>
            <img
              src={activity.imagePath ? activity.imagePath : PLACEHOLDER_IMG_URL}
              alt="Activity image"
              onClick={() => selectActivity(activity)}
            />
            <p>{activity.info}</p>
            <button onClick={() => selectActivity(activity)}>See activity</button>
            <br/>
            <hr/>
          </div>
        );
      }));
      setLoading(false);
    }).catch(err => {
      console.log("Cannot get activities of place " + placeId);
      console.log(err);
      return (<div>Error</div>);
    });
    
  }, [loading]);

  if (loading) {
    return (
      <div>
        Loading...
      </div>
    );
  }

  return (
    <div>
      {listActivities}
    </div>
  );
}

export function Place(props) {
  const place = props.place;
  const isContributor = props.roles.some(role => role === 'CONTRIBUTOR');
  const addActivity = () => props.func.addActivity(place);
  const selectActivity = props.func.selectActivity;


  const listImages = place.images.map((image, _) => {
    return (
      <img src={image.path ? image.path : PLACEHOLDER_IMG_URL} alt="place"/>
    )
  });

  return(
    <div>
      <div>
        {listImages}
        <h2>{place.name}</h2>
      </div>
      <h3>Click on an activity to see its details</h3>
      <hr/>
      <Activities placeId={place.place_id} selectActivity={selectActivity}/>
      <hr/>
      {isContributor && (<NewActivityButton addActivity={addActivity}/>)}
    </div>
  );
}